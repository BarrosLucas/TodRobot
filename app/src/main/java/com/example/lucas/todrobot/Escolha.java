package com.example.lucas.todrobot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Escolha extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);
    }
    public void nivel1(View v){
        Intent intent = new Intent(Escolha.this, Descricao.class);
        intent.putExtra("nivel","1");
        intent.putExtra("origem", "esc");
        startActivity(intent);
    }
    public void nivel2(View v){
        Intent intent = new Intent(Escolha.this, Descricao.class);
        intent.putExtra("nivel","2");
        intent.putExtra("origem","esc");
        startActivity(intent);
    }
    public void nivel3(View v){
        Intent intent = new Intent(Escolha.this, Descricao.class);
        intent.putExtra("nivel","3");
        intent.putExtra("origem","esc");
        startActivity(intent);
    }
    public void nivel4(View v){
        Intent intent = new Intent(Escolha.this, Descricao.class);
        intent.putExtra("nivel","4");
        intent.putExtra("origem","esc");
        startActivity(intent);
    }
    public void nivel5(View v){
        Intent intent = new Intent(Escolha.this, Descricao.class);
        intent.putExtra("nivel","5");
        intent.putExtra("origem","esc");
        startActivity(intent);
    }
    public void nivel6(View v){
        Intent intent = new Intent(Escolha.this, Descricao.class);
        intent.putExtra("nivel","6");
        intent.putExtra("origem","esc");
        startActivity(intent);
    }
}
