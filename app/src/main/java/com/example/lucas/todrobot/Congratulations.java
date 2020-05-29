package com.example.lucas.todrobot;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class Congratulations extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);
        WebView texto = (WebView) findViewById(R.id.mens);
        String text = "<html><body>" + "<p align=\"justify\">" + getString(R.string.parabens) + "</p> " + "</body></html>";
        texto.loadData(text, "text/html", "utf-8");
    }
}
