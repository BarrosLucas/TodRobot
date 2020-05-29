package com.example.lucas.todrobot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class Compiler extends Activity {
    DBEstatisticas est = new DBEstatisticas(this);
    private int indices;
    String codigoGerado = "";
    char[] codigo;
    String id;
    int chaves = 0;
    private final String[] reservadas = new String[]{"andarFrente", "girarEsquerda", "girarDireita", "soltar", "pegar", "se(vazioFrente)", "se(maoDesocupada)", "se(maoOcupada)", "senao", "enquanto(vazioFrente)", "enquanto(maoDesocupada)", "enquanto(maoOcupada)", "senaose(vazioFrente)", "senaose(maoDesocupada)", "senaose(maoOcupada)", "senaose(!vazioFrente)","enquanto(!vazioFrente)","se(!vazioFrente)"};
    char[] codigos = {'1', '2', '3', '4', '5', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compiler);
        Intent intent = getIntent();
        codigo = (intent.getStringExtra("codi")).toCharArray();
        id = (intent.getStringExtra("id"));
        String saida = identificarCodigo(codigo);
        Intent mIntent = new Intent();
        mIntent.putExtra("texto", saida);
        if (id.equals("enviar")) {
            setResult(2, mIntent);
        }else{
            setResult(3, mIntent);
        }
        finish();
    }

    private String identificarCodigo(char[] c) {
        int correto = 0;
        String codigo = "";
        String codSe = "";
        String codEn = "";
        String codSenao = "";
        char caractere;
        int rept;
        for (int a = 0; a < c.length; a++) {
            caractere = c[a];
            if (caractere != '\n' && caractere != '(' && caractere != ')' && caractere != '}' && caractere != '{' && caractere != ';' && caractere != ' ') {
                int t = codigoGerado.length();
                if (codigo.equals("se") || codigo.equals("enquanto") || codigo.equals("senaose")){
                    correto ++; //Falta abrir chaves
                    Errado e = new Errado(0,6);
                    est.save(e);
                }else if(codigo.equals("andarFrente") || codigo.equals("girarEsquerda") || codigo.equals("girarDireita") || codigo.equals("pegar") || codigo.equals("soltar")){
                    correto ++; //Falta abrir parentesis
                    Errado e = new Errado(0,7);
                    est.save(e);
                }else{
                    codigo += caractere;
                }
            } else if (caractere == '(' && codigo.equals("se") == false && codigo.equals("enquanto") == false && codigo.equals("senaose") == false) {
                rept = repeticao(a, c);
                a = indices;
                if (rept > 0) {
                    int situacao = 0;
                    for (int i = 0; i < reservadas.length; i++) {
                        if (codigo.equals(reservadas[i])) {
                            situacao++;
                            for (int j = 0; j < rept; j++) {
                                codigoGerado += codigos[i];
                            }
                        }
                    }
                    if (situacao == 0) {
                        Log.i("LFB2", codigo);
                        correto++;//Erro... Código não identificado
                        Errado e = new Errado(0, 1);
                        est.save(e);
                    }
                } else {
                    Log.i("LFB3", codigo);
                    correto++; //Valor entre parentesis indefinido
                    Errado e = new Errado(0, 2);
                    est.save(e);
                }

            } else if (caractere == ';') {
                int sit = 0;
                for (int i = 0; i < reservadas.length; i++) {
                    if (codigo.equals(reservadas[i])) {
                        sit ++;
                    }
                }
                if (sit != 1){
                    Log.i("LFB", codigo);
                    correto ++;
                    Errado e = new Errado(0, 1);//Codigo inexistente
                    est.save(e);
                }
                codigo = "";
            } else if (codigo.equals("senaose") || codSenao.equals("senaose")) {
                codSenao = "senaose";
                codigo += caractere;
                if (caractere == ')') {
                    codSenao = "";
                }
            } else if (codigo.equals("se") || codSe.equals("se")) {
                codSe = "se";
                codigo += caractere;
                if (caractere == ')') {
                    codSe = "";
                }
            } else if (codigo.equals("enquanto") || codEn.equals("enquanto")) {
                codEn = "enquanto";
                codigo += caractere;
                if (caractere == ')') {
                    codEn = "";
                }
            } else if (caractere == '{') {
                char cG = ' ';
                int coerencia = 0;
                for (int j = 0; j < reservadas.length; j++) {
                    if (reservadas[j].equals(codigo)) {
                        coerencia ++;
                        cG = codigos[j];
                        codigoGerado += codigos[j];
                    }
                }
                if (coerencia != 0){
                    int t = codigoGerado.length();
                    if (codigoGerado.charAt(t-1) == 'a' || codigoGerado.charAt(t-1) == 'b' || codigoGerado.charAt(t-1) == 'c' || codigoGerado.charAt(t-1) == 'd' || codigoGerado.charAt(t-1) == 'e' || codigoGerado.charAt(t-1) == 'f' || codigoGerado.charAt(t-1) == 'g' || codigoGerado.charAt(t-1) == 'h' || codigoGerado.charAt(t-1) == 'i' || codigoGerado.charAt(t-1) == 'j' || codigoGerado.charAt(t-1) == 'k' || codigoGerado.charAt(t-1) == 'l' || codigoGerado.charAt(t-1) == 'm'){
                        chaves ++;
                        codigoGerado += "{";
                        codigo = "";
                    }else{
                        correto ++; //Uso de chaves sem estrutura de bloco
                        Errado e = new Errado(0,3);
                        est.save(e);
                    }

                }else{
                    correto++;//Uso da chaves sem sentido
                    Errado e = new Errado(0,4);
                    est.save(e);
                }

            } else if (caractere == '}') {
                if(chaves != 0){
                    chaves --;
                    codigoGerado += caractere;
                }else{
                    correto ++;//Fechou uma chave sem têla aberto
                    Errado e = new Errado(0,5);
                    est.save(e);
                }
            }
        }
        if (correto == 0 && codigoGerado.equals("") == false && chaves == 0 && codigo.equals("")) {
            return codigoGerado;
        } else {
            if (codigoGerado.equals("")){
                Errado e = new Errado(0,1);
                est.save(e);
            }else if(chaves != 0){
                Errado e = new Errado(0,8);
                est.save(e);
            }else if(codigo.equals("")==false){
                Errado e = new Errado(0,9);
                est.save(e);
            }
            return "Erros encontrados";
        }
    }

    private int repeticao(int indice, char[] c) {
        int retorno = 0;
        String numero = "";
        int num;
        int ind = indice + 1;
        while (c[ind] != ')' && c[ind] != '\n' && c[ind] != ';') {
            numero += c[ind];
            ind++;
        }
        if (c[ind] == ')') {
            try {
                num = Integer.parseInt(numero);
                retorno = num;
            } catch (Exception e) {
                //ERRO
                retorno--;
            }
        } else {
            retorno--;
        }
        indices = ind;
        return retorno;
    }
}

/*
public class Compiler extends Activity {
    DBEstatisticas est = new DBEstatisticas(this);
    private int indices;
    String codigoGerado = "";
    char[] codigo;
    String id;
    int chaves = 0;
    private final String[] reservadas = new String[]{"andarFrente", "girarEsquerda", "girarDireita", "soltar", "pegar", "se(vazioFrente)", "se(maoDesocupada)", "se(maoOcupada)", "senao", "enquanto(vazioFrente)", "enquanto(maoDesocupada)", "enquanto(maoOcupada)", "senaose(vazioFrente)", "senaose(maoDesocupada)", "senaose(maoOcupada)", "senaose(!vazioFrente)","enquanto(!vazioFrente)","se(!vazioFrente)"};
    char[] codigos = {'1', '2', '3', '4', '5', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compiler);
        Intent intent = getIntent();
        codigo = (intent.getStringExtra("codi")).toCharArray();
        id = (intent.getStringExtra("id"));
        String saida = identificarCodigo(codigo);
        Intent mIntent = new Intent();
        mIntent.putExtra("texto", saida);
        if (id.equals("enviar")) {
            setResult(2, mIntent);
        }else{
            setResult(3, mIntent);
        }
        finish();
    }

    private String identificarCodigo(char[] c) {
        int correto = 0;
        String codigo = "";
        String codSe = "";
        String codEn = "";
        String codSenao = "";
        char caractere;
        int pedido = 0;
        int rept;
        for (int a = 0; a < c.length; a++) {
            caractere = c[a];
            if (caractere != '\n' && caractere != '(' && caractere != ')' && caractere != '}' && caractere != '{' && caractere != ';' && caractere != ' ') {
                if (pedido == 1){
                    correto ++; //Falta abrir chaves
                    Errado e = new Errado(0,6);
                    est.save(e);
                    pedido = 0;
                }
                codigo += caractere;
            } else if (caractere == '(' && codigo.equals("se") == false && codigo.equals("enquanto") == false && codigo.equals("senaose") == false) {
                if (pedido == 1){
                    correto ++; //Falta abrir chaves
                    Errado e = new Errado(0,6);
                    est.save(e);
                    pedido = 0;
                }
                rept = repeticao(a, c);
                a = indices;
                if (rept > 0) {
                    int situacao = 0;
                    for (int i = 0; i < reservadas.length; i++) {
                        if (codigo.equals(reservadas[i])) {
                             situacao++;
                             for (int j = 0; j < rept; j++) {
                                 codigoGerado += codigos[i];
                             }
                        }
                    }
                    if (situacao == 0) {
                        correto++;//Erro... Código não identificado
                        Errado e = new Errado(0,1);
                        est.save(e);
                    }
                } else {
                    correto++; //Valor entre parentesis indefinido
                    Errado e = new Errado(0,2);
                    est.save(e);
                }
            } else if (caractere == ';') {
                codigo = "";
            } else if (codigo.equals("senaose") || codSenao.equals("senaose")) {
                codSenao = "senaose";
                codigo += caractere;
                if (caractere == ')') {
                    codSenao = "";
                    pedido = 1;
                }
            } else if (codigo.equals("se") || codSe.equals("se")) {
                codSe = "se";
                codigo += caractere;
                if (caractere == ')') {
                    codSe = "";
                    pedido = 1;
                }
            } else if (codigo.equals("enquanto") || codEn.equals("enquanto")) {
                codEn = "enquanto";
                codigo += caractere;
                if (caractere == ')') {
                    codEn = "";
                    pedido = 1;
                }
            } else if (caractere == '{') {
                char cG = ' ';
                int coerencia = 0;
                for (int j = 0; j < reservadas.length; j++) {
                    if (reservadas[j].equals(codigo)) {
                        coerencia ++;
                        cG = codigos[j];
                        codigoGerado += codigos[j];
                    }
                }
                if (coerencia != 0){
                    if (pedido == 1){
                        chaves ++;
                        codigoGerado += "{";
                        codigo = "";
                        pedido = 0;
                    }else{
                        correto ++; //Uso de chaves sem estrutura de bloco
                        Errado e = new Errado(0,3);
                        est.save(e);
                    }

                }else{
                    correto++;//Uso da chaves sem sentido
                    Errado e = new Errado(0,4);
                    est.save(e);
                }

            } else if (caractere == '}') {
                if(chaves != 0){
                    chaves --;
                    codigoGerado += caractere;
                }else{
                    correto ++;//Fechou uma chave sem têla aberto
                    Errado e = new Errado(0,5);
                    est.save(e);
                }
            }
        }
        if (correto == 0) {
            return codigoGerado;
        } else {
            return "Erros encontrados";
        }
    }

    private int repeticao(int indice, char[] c) {
        int retorno = 0;
        String numero = "";
        int num;
        int ind = indice + 1;
        while (c[ind] != ')' && c[ind] != '\n' && c[ind] != ';') {
            numero += c[ind];
            ind++;
        }
        if (c[ind] == ')') {
            try {
                num = Integer.parseInt(numero);
                retorno = num;
            } catch (Exception e) {
                //ERRO
                retorno--;
            }
        } else {
            retorno--;
        }
        indices = ind;
        return retorno;
    }
}*/

/*
    protected void executar(String codigoBruto) {

        //Quebra a String em chars
        char[] codigoSeparado = codigoBruto.toCharArray();

        //Pega a quantidade de codigos e define o tamanho do Array que contera os codigos do usuario
        for (char letra : codigoSeparado) {
            if (letra == ';') {
                tamanhoArray += 1;
            }
        }
        codigoUser = new String[tamanhoArray];

        //Setando posições como ""
        for (int i = 0; i < codigoUser.length; i++) {
            codigoUser[i] = "";
        }

        /*

        //Salva codigos completos no array
        int indice = 0;
        for (char letra : codigoSeparado) {
            if (letra != ';') {
                if (letra != '\n'){
                    if(letra!=' ')
                        codigoUser[indice] += letra;
                }
            } else indice++;

        }


        int indice = 0;
        for (int i = 0; i < codigoSeparado.length; i++){
            if (codigoSeparado[i] != '('){
                codigoUser[indice] += codigoSeparado[i];
            }else{
                int acharparent = i;
                while (codigoSeparado[acharparent] != ')' && codigoSeparado[acharparent] != ';' && codigoSeparado[acharparent] != '\n'){
                    acharparent ++;
                }
                String valor = "";
                if(codigoSeparado[acharparent] == ')'){
                    for(i=i+1; i< acharparent; i++){
                        valor += codigoSeparado[i];
                    }
                    try{
                        boolean avaliacao;
                        int val = Integer.parseInt(valor);
                        for(int d = 0; d < val; d++){
                            gerarCodigoArduino(codigoUser[indice]);
                        }
                    }catch (Exception e){
                        //Erro... Não foi informado número
                    }
                }else{
                    //Erro... Faltou fechar parenteses
                }
            }
        }
        Log.d("Tasdghjk",codigoUser[0]);
        Log.d("Tasdghj","coisa->"+enviarArduino);

        //Palavras reservadas


        //Compara o codigo do usuario com as palavras reservadas e salva o comando para o arduino
        //for (String comandoUser : codigoUser) {
           // for (int i = 0; i < reservadas.length; i++) {
           //     if (comandoUser.toLowerCase().equals(reservadas[i])) {
                  //  enviarArduino += i + 1;
                //}

         //   }
        //}

        Log.d("enviar",enviarArduino);
        Intent mIntent = new Intent();
        mIntent.putExtra("texto", enviarArduino);
        setResult(2, mIntent);
        finish();
    }
    boolean gerarCodigoArduino(String codigo){
        boolean retorno = false;
        for (int i=0; i< reservadas.length; i++){
            if (codigo.toLowerCase().equals(reservadas[i])){
                retorno = true;
                enviarArduino+= i + 1;
            }
        }
        return retorno;
    }
}

        //tamanhoArray = 0;
        //enviarArduino = "";
*/