package com.example.lucas.todrobot;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class About extends Activity {
    WebView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        texto = (WebView) findViewById(R.id.webView);
        String text = "<html><body>"
                + "<p align=\"justify\">"
                + getString(R.string.caracteristicas)
                + "</p> "
                + "</body></html>";
        texto.loadData(text, "text/html", "utf-8");

    }
}
