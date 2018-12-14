package com.mysheng.office.kkanshop.MIMC.av;

import android.media.AudioRecord;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.mysheng.office.kkanshop.MIMC.listener.OnAudioCapturedListener;


/**
 * Created by houminjiang on 18-5-28.
 */

public class AudioRecorder implements Capture {
    private AudioCapture audioCapture;
    private Thread captureThread;
    private volatile boolean isLoopExit = false;
    private boolean isCaptureStarted = false;
    private OnAudioCapturedListener onAudioCapturedListener;
    private static final String TAG = "AudioRecorder";

    public void setOnAudioCapturedListener(OnAudioCapturedListener onAudioCapturedListener) {
        this.onAudioCapturedListener = onAudioCapturedListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean start() {
        if (isCaptureStarted) {
            Log.w(TAG, "Capture has already been started.");
            return false;
        }

        isLoopExit = false;
        audioCapture = new AudioCapture();
        boolean result = audioCapture.start();
        if (result) {
            captureThread = new Thread(new AudioCaptureRunnable());
            captureThread.start();
            isCaptureStarted = true;
        }

        return result;
    }

    @Override
    public void stop() {
        if (!isCaptureStarted) {
            return;
        }
        isLoopExit = true;
        try {
            captureThread.join(50);
            captureThread = null;
        } catch (InterruptedException e) {
            Log.e(TAG, "Interrupted exception:", e);
        }
        audioCapture.stop();
        isCaptureStarted = false;
    }

    private class AudioCaptureRunnable implements Runnable {
        @Override
        public void run() {
            while (!isLoopExit) {
                byte[] buffer = new byte[audioCapture.getMinBufferSize()];
                int result = audioCapture.capture(buffer, 0, audioCapture.getMinBufferSize());
                if (result == AudioRecord.ERROR_INVALID_OPERATION) {
                    Log.w(TAG, " The object isn't properly initialized.");
                } else if (result == AudioRecord.ERROR_BAD_VALUE) {
                    Log.w(TAG, "The parameters don't resolve to valid data and indexes.");
                } else if (result == AudioRecord.ERROR_DEAD_OBJECT) {
                    Log.w(TAG, "The object is not valid anymore and needs to be recreated.");
                } else if (result == AudioRecord.ERROR) {
                    Log.w(TAG, "Other error.");
                } else {
                    if (onAudioCapturedListener != null) {
                        onAudioCapturedListener.onAudioCaptured(buffer);
                    }
                    Log.d(TAG, String.format("Success captured " + result + "bytes."));
                }
            }
            Log.i(TAG, "Audio capture thread exit.");
        }
    }
}
