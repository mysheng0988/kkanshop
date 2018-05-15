package com.mysheng.office.kkanshop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import com.mysheng.office.kkanshop.util.MikyouCommonDialog;


/**
 * Created by myaheng on 2018/4/23.
 */

public class FlowActivity extends Activity {
    private TextView mTextView;
    private WebView mWebView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flow_activity);
        mTextView=findViewById(R.id.common_title);
        mTextView.setText("流程图");
        mWebView=findViewById(R.id.id_webView);
        initWebViewData();
    }
    @SuppressLint("JavascriptInterface")
    private void initWebViewData() {
        String url="http://m.intelligenconnection.com:8080/";
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebView.addJavascriptInterface(this,"Android");
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.getSettings().setBuiltInZoomControls(false);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            mWebView.getSettings().setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.setWebChromeClient(new WebChromeClient(){



            @Override
            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d("mys", cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId() );
                return true;
            }
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(FlowActivity.this, message, Toast.LENGTH_SHORT).show();
                result.cancel();
                return true;
            }
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new MikyouCommonDialog(FlowActivity.this, message,"确认删除","确定","取消")
                        .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                            @Override
                            public void dialogPositiveListener(View customView, DialogInterface dialogInterface, int which) {
                                result.confirm();
                                dialogInterface.dismiss();

                            }
                            @Override
                            public void dialogNegativeListener(View customView, DialogInterface dialogInterface, int which) {
                                result.cancel();
                            }
                        }).showDialog();

                return true;
            }
        });


        mWebView.addJavascriptInterface(this,"Android");

        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() );
    }
    public void onBtnClick(View view){
        finish();
    }
}
