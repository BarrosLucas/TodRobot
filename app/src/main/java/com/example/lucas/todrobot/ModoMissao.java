package com.example.lucas.todrobot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ModoMissao extends Activity {
    public static boolean sit = false;
    public static boolean conection = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_missao);
    }
    public void comecar(View v) {
        Intent intent = new Intent(ModoMissao.this, Descricao.class);
        intent.putExtra("nivel", "1");
        intent.putExtra("origem","mm");
        startActivity(intent);
    }
    public void escolhas(View v){
        Intent intent = new Intent(ModoMissao.this, Escolha.class);
        startActivity(intent);
    }


}
