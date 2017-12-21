package com.example.aluno.calendario;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class OngActivity extends AppCompatActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ong);


        wv = (WebView) findViewById(R.id.webView);

        if (!checkInternetConnection(this)) {

            Toast.makeText(getApplicationContext(), "Ops... Você está sem internet! :'(", Toast.LENGTH_SHORT).show();
            finish();

        } else {

            Toast.makeText(getApplicationContext(), "Carregando... :')", Toast.LENGTH_SHORT).show();

            WebSettings ws = wv.getSettings();
            wv.clearCache(true);
            wv.clearHistory();
            ws.setJavaScriptEnabled(true);
            ws.setSupportZoom(false);
            ws.setJavaScriptCanOpenWindowsAutomatically(true);
            wv.loadUrl("https://www.facebook.com/qualidadedevidamulheroncologica/");
            wv.setWebViewClient(new myWebViewClient());
        }
    }

    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wv.canGoBack()) {
                        wv.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}

class myWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}


