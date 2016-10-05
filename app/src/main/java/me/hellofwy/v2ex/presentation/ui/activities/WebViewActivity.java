package me.hellofwy.v2ex.presentation.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hellofwy.v2ex.R;

public class WebViewActivity extends AppCompatActivity {

    @Bind(R.id.v2ex_web_view)
    WebView v2exWebView;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        progressBar.setMax(100);


        String url = getIntent().getStringExtra("url");
        v2exWebView.getSettings().setJavaScriptEnabled(true);
        v2exWebView.setWebViewClient(new WebViewClientProgress());
        v2exWebView.setWebChromeClient(new WebChromeClientProgress());
        v2exWebView.loadUrl(url);

    }

    private class WebViewClientProgress extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(0);
        }
    }
    private class WebChromeClientProgress extends WebChromeClient {
        @Override
        public void onProgressChanged(android.webkit.WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && v2exWebView.canGoBack()) {
            v2exWebView.goBack();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
