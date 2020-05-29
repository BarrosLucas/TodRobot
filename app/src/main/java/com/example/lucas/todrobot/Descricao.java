package com.example.lucas.todrobot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Vector;

public class Descricao extends Activity {
    int level;
    String origem;
    String[] descricaos = new String[]{"Estrutura Sequencial\nPosicione o robô na célula C1 do ambiente. Posicione o objeto na célula C3 do ambiente. Programe o robô para que ele possa pegar o objeto utilizando comandos simples.","Estrutura Sequencial\nPosicione o robô na célula C1 do ambiente. Posicione o objeto na célula C3 do ambiente. Programe o robô para que ele possa pegar o objeto e soltá-lo na célula E5. Programe o robô para que ele possa pegar o objeto utilizando comandos simples.","Estrutura de Decisão\nPosicione o robô na célula C1 do ambiente. Posicione o objeto na célula C3 do ambiente. Programe o robô para que ele possa pegar o objeto utilizando comandos de decisão (se/senao/senaose).","Estrutura de Decisão\nPosicione o robô na célula C1 do ambiente. Posicione o objeto na célula C3 do ambiente. Programe o robô para que ele possa pegar o objeto e soltá-lo na célula E5. Programe o robô para que ele possa pegar o objeto utilizando comandos de decisão (se/senao/senaose).","Estrutura de Repetição\nPosicione o robô na célula C1 do ambiente. Posicione o objeto em qualquer célula desta mesma coluna. Programe o robô para que ele possa pegar o objeto utilizando comando de repetição (enquanto).","Estrutura de Repetição\nPosicione o robô na célula C1 do ambiente. Posicione o objeto em qualquer célula desta mesma coluna. Programe o robô para que ele possa pegar o objeto e leva-lo à célula E5 utilizando comandos de repetição (enquanto)"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("VEIO", "Veio");
        setContentView(R.layout.activity_descricao);
        Log.i("VEIO", "Veio");
        Intent intent = getIntent();
        Log.i("VEIO","Veio e pá");
        level = Integer.parseInt(intent.getStringExtra("nivel"));
        origem = intent.getStringExtra("origem");
        TextView nivel = (TextView)findViewById(R.id.nivel);
        nivel.setText("Nível "+level);
        TextView desc = (TextView)findViewById(R.id.desc);
        desc.setText(descricaos[level-1]);
    }
    public void start(View v){
        Intent i = new Intent(this,IDEModoMissao.class);
        i.putExtra("nivel",""+level);
        i.putExtra("origem",origem);
        startActivity(i);
    }
}
