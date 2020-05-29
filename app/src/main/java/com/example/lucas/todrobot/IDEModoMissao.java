package com.example.lucas.todrobot;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class IDEModoMissao extends Activity {
    int nivel;
    String[] reservados = new String[]{"15","15111314","1m{5}","1m{5}111m{3}14","e{1}5","e{1}5e{1}314"};
    BluetoothAdapter bt;
    BluetoothSocket btSocket = null;
    boolean server=false;
    String myUUID = "00001101-0000-1000-8000-00805F9B34FB";
    String mac = "98:D3:31:80:11:8D";
    boolean runnig = false;
    static InputStream input;
    static OutputStream output;
    TextView texto;
    private AlertDialog alerta;
    private String codigo;
    private EditText msg;
    private String origem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idemodo_missao);
        Intent inte = getIntent();
        nivel = Integer.parseInt(inte.getStringExtra("nivel"));
        origem = inte.getStringExtra("origem");
        texto = (TextView) findViewById(R.id.tMsgRecebidas);
        msg = (EditText) findViewById(R.id.tMsg);
        msg.setText(codigo);
        bt = BluetoothAdapter.getDefaultAdapter();
        if (bt != null){ //Celular suporta bluetooth
            if (bt.isEnabled()){
                if (ModoMissao.sit = false) {
                    conect();
                }
                listener();
            }else{
                //Bora ligar
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 0);
                conect();
                listener();
            }
            if (ModoMissao.conection==false){
                conect();
            }
        }else{
            Toast.makeText(this, "Dispositivo não suporta Bluetooth.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }
    public void onClickEnviarMsg(View view) throws IOException {
        EditText msg = (EditText) findViewById(R.id.tMsg);
        String mensagem = msg.getText().toString();
        Intent compilador = new Intent(IDEModoMissao.this, Compiler.class);
        compilador.putExtra("codi", mensagem);
        compilador.putExtra("id", "enviar");
        startActivityForResult(compilador, 2);//6 é um número perfeito, assim como nosso projeto <3
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Olhe","Veio aqui.");
        texto.setText("Tá aqui");
        if (requestCode == 2){
            String message = data.getStringExtra("texto");
            texto.setText(message);
            if(message.equals("Erros encontrados")){
                texto.setText("Tod: Você cometeu erros no código.");
            }else {
                Log.i("Pá",message);
                if (avaliar(nivel, message)){
                    texto.setText("Tod: Me observe fazer isso!");
                    try {
                        send(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(origem.equals("mm")) {
                        nivel++;
                        if (nivel <= 6) {
                            Log.i("TOME", "Veio...");
                            Intent intent = new Intent(this, Descricao.class);
                            intent.putExtra("nivel", "" + nivel);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(this, Congratulations.class);
                            startActivity(intent);
                        }
                    }else if(origem.equals("esc")){
                        Intent intent = new Intent(this, Escolha.class);
                        startActivity(intent);
                    }


                }else{
                    texto.setText("Tod: Hmm acho que você pode fazer melhor.");
                }

            }
        }
    }
    public void conect(){
        try{
            BluetoothDevice btDevice = bt.getRemoteDevice(mac);
            btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString(myUUID));
            bt.cancelDiscovery();
            ModoMissao.sit=true;
            if (btSocket != null) {
                btSocket.connect();
                ModoMissao.conection = true;
                Toast.makeText(this,"Conexão estabelecida.",Toast.LENGTH_LONG).show();
                input=btSocket.getInputStream();
                output=btSocket.getOutputStream();
            }
        }catch (IOException e){
            Toast.makeText(this,"Erro na conexão.",Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }
    public void listener(){
        new Thread(){
            @Override
            public void run(){
                runnig = true;
                byte[] bytes = new byte[1024];
                int lenght;
                while (runnig){
                    try{
                        lenght = input.read(bytes);
                        String msg = new String(bytes, 0, lenght);
                        texto.setText(msg);
                    }catch (Exception e){
                        runnig = false;
                        Log.e("chat", "Error: " + e.getMessage(), e);
                    }
                }
            }
        }.start();
    }
    public void send(String msg) throws IOException {
        if(output!=null){
            output.write(msg.getBytes());
        }else{
            texto.setText("Problemas no envio");
        }
    }
    protected boolean avaliar(int nu, String cod) {
        boolean retorno = false;
        if (cod.equals(reservados[nu - 1])) {
            retorno = true;
        }
        return retorno;
    }
    public void compartilhar(){
        EditText msg = (EditText) findViewById(R.id.tMsg);
        String mensagem = msg.getText().toString();
        Intent compartilha = new Intent(Intent.ACTION_SEND);
        compartilha.setType("text/plain");
        compartilha.putExtra(Intent.EXTRA_SUBJECT, "Compartilhando Código");
        compartilha.putExtra(Intent.EXTRA_TEXT, "Código Fonte:\n"+mensagem);
        startActivity(Intent.createChooser(compartilha, "Compartilhar Código"));
    }
        /*char n = (""+nu).charAt(0);
        boolean retorno = false;
        switch (n){
            case '1':
                if(codigo.equals(reservados[0])){
                    retorno = true;
                }
            case '2':
                if(codigo.equals(reservados[1])){
                    retorno = true;
                }
            case '3':
                if(codigo.equals(reservados[2])){
                    retorno = true;
                }
            case '4':
                if(codigo.equals(reservados[3])){
                    retorno = true;
                }
            case '5':
                if(codigo.equals(reservados[4])){
                    retorno = true;
                }
            case '6':
                if(codigo.equals(reservados[5])){
                    retorno = true;
                }
        }*/
      //  return retorno;
    //}
    /*private void alert() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Parabéns");
        //define a mensagem
        builder.setMessage("Missão concluida com sucesso, vamos para próxima!");
        String desc = "desc"+(nivel+1);

        builder.setPositiveButton("Próxima", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                nivel ++;
                msg.setText("");
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }*/


}
