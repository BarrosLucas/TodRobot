package com.example.lucas.todrobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Teste extends Activity {
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        Intent intent = getIntent();
        String text = intent.getStringExtra("codi");
        //Intent intent = new Intent();
        //codigoBruto = intent.getStringExtra("codi");
        texto = (TextView) findViewById(R.id.texto);
        texto.setText(text);
    }
}
