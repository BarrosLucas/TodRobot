package com.example.lucas.todrobot;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Compilador extends IntentService {

    private String[] codigoUser; //Codigo que sera formatado como o usuario digitou
    private String codigoBruto; //Codigo que vem pela chamada ("andarfrente;pegar;soltar;")
    private String enviarArduino; //Codigo para o Arduino
    private int tamanhoArray; //Pegara a quantidade de codigos digitados pelo usuario

    public Compilador() {
        super("Compilador");
        tamanhoArray = 0;
        enviarArduino = "";
    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Bundle b = intent.getExtras();
        if (b != null){
            codigoBruto = b.getString("codigo");
        }
        return super.onStartCommand(intent, flags, startId);
    }*/


    protected void onHandleIntent(Intent intent) {

        //Pega a String passada ("andarfrente;pegar;")
        String codigoBruto = intent.getStringExtra("codigo");
        Log.d("Entrei bixo!!!",codigoBruto);

        //Quebra a String em chars
        char [] codigoSeparado= codigoBruto.toCharArray();

        //Pega a quantidade de codigos e define o tamanho do Array que contera os codigos do usuario
        for(char letra:codigoSeparado){
            if (letra == ';') {
                tamanhoArray+=1;
            }
        }
        codigoUser = new String[tamanhoArray];

        //Setando posições como ""
        for(int i=0;i<codigoUser.length;i++){
            codigoUser[i]="";
        }

        //Salva codigos completos no array
        int indice=0;
        for (char letra : codigoSeparado){
            if (letra != ';') {
                codigoUser[indice] += letra;
            } else indice++;

        }

        //Palavras reservadas
        final String[] reservadas = new String[] {"andarfrente","giraresquerda","girardireita","olhar","soltar","pegar"};


        //Compara o codigo do usuario com as palavras reservadas e salva o comando para o arduino
        for (String comandoUser : codigoUser) {
            for (int i = 0; i < reservadas.length; i++) {
                if (comandoUser.toLowerCase().equals(reservadas[i])) {
                    enviarArduino +=i+1;
                }

            }
        }

        Log.d("isso mermo",enviarArduino);

        Intent mIntent = new Intent(this, Teste.class);
        mIntent.putExtra("texto", enviarArduino);
        startActivity(mIntent);

        tamanhoArray = 0;
        enviarArduino = "";
    }
}