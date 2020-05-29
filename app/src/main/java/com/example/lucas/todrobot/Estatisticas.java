package com.example.lucas.todrobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;

public class Estatisticas extends AppCompatActivity {
    DBEstatisticas est = new DBEstatisticas(this);
    TextView d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);
        d = (TextView)findViewById(R.id.dados);
        resultados();
    }
    public void historico(View v){
        for(int i = 0; i < 10; i++){
            est.delete(""+i);
        }
        resultados();
    }
    protected void resultados(){
        try {
            int t1 = est.quantidade("1");
            int t2 = est.quantidade("2");
            int t3 = est.quantidade("3");
            int t4 = est.quantidade("4");
            int t5 = est.quantidade("5");
            int t6 = est.quantidade("6");
            int t7 = est.quantidade("7");
            int t8 = est.quantidade("8");
            int t9 = est.quantidade("9");
            d.setText("Código inexistente: "+t1+"\nValor entre parênteses inválido: "+t2+"\nUso de chaves sem estrutura de bloco: "+t3+"\nUso de chaves em código incoerente: "+t4+"\nFechou chave sem tê-lo aberta: "+t5+"\nEsqueceu de abrir chaves: "+t6+"\nFaltou parêntesis: "+t7+"\nEsqueceu de fechar chaves: "+t8+"\nFaltou ponto e vígula: "+t9);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
