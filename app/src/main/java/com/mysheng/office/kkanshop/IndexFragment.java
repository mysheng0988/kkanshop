package com.mysheng.office.kkanshop;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mysheng.office.kkanshop.util.MikyouCommonDialog;


public class IndexFragment extends Fragment {
	private WebView webView;
	private ProgressBar progressBar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		View view= inflater.inflate(R.layout.tab01, container, false);
//		webView=view.findViewById(R.id.webView);
//		progressBar=view.findViewById(R.id.progressbar);
//		initWebViewData();
		return view;
	}
	@SuppressLint("JavascriptInterface")
	private void initWebViewData() {
		String url="http://39.104.72.213/new/fz/jingning/shebeijiankong.html";
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setSupportMultipleWindows(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setAllowFileAccessFromFileURLs(true);
		webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
		webView.addJavascriptInterface(this,"Android");
		webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		webView.getSettings().setBuiltInZoomControls(false);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			webView.getSettings().setDisplayZoomControls(false);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			webView.setWebContentsDebuggingEnabled(true);
		}
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if(newProgress==100){
					progressBar.setVisibility(View.GONE);

				} else {
					progressBar.setVisibility(View.VISIBLE);
					progressBar.setProgress(newProgress);//设置加载进度
				}

			}



			@Override
			public boolean onConsoleMessage(ConsoleMessage cm) {
				Log.d("mys", cm.message() + " -- From line "
						+ cm.lineNumber() + " of "
						+ cm.sourceId() );
				return true;
			}
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				result.cancel();
				return true;
			}
			@Override
			public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
				new MikyouCommonDialog(getActivity(), message,"确认删除","确定","取消")
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


		webView.addJavascriptInterface(this,"Android");

		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() );
	}
}
