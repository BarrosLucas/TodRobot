package com.example.lucas.todrobot;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ModoLivre extends AppCompatActivity {
    BluetoothAdapter bt;
    BluetoothSocket btSocket = null;
    boolean server=false;
    String myUUID = "00001101-0000-1000-8000-00805F9B34FB";
    String mac = "98:D3:31:80:11:8D";
    boolean runnig = false;
    static InputStream input;
    static OutputStream output;
    TextView texto;
    private String codigo;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_livre);
        Intent inte = getIntent();
        codigo = inte.getStringExtra("codigo");
        status = inte.getStringExtra("status");
        texto = (TextView) findViewById(R.id.tMsgRecebidas);
        EditText msg = (EditText) findViewById(R.id.tMsg);
        msg.setText(codigo);
        bt = BluetoothAdapter.getDefaultAdapter();
        if (bt != null){ //Celular suporta bluetooth
            if (bt.isEnabled()){
                if (status.equals("1")) {
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
        }else{
            Toast.makeText(this,"Dispositivo não suporta Bluetooth.",Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }
    public void testar(View v) throws IOException{
        EditText msg = (EditText) findViewById(R.id.tMsg);
        String mensagem = msg.getText().toString();

        Intent compilador = new Intent(ModoLivre.this, Compiler.class);
        compilador.putExtra("codi", mensagem);
        compilador.putExtra("id","testar");
        startActivityForResult(compilador, 3);
    }
    public void onClickEnviarMsg(View view) throws IOException {
        EditText msg = (EditText) findViewById(R.id.tMsg);
        String mensagem = msg.getText().toString();

//        Intent intent = new Intent(this, Compiler.class);
  //      intent.putExtra("texto",mensagem);
     //   startActivityForResult();



        Intent compilador = new Intent(ModoLivre.this, Compiler.class);
        compilador.putExtra("codi",mensagem);
        compilador.putExtra("id","enviar");
        startActivityForResult(compilador,2);//6 é um número perfeito, assim como nosso projeto <3

        //Intent inte = getIntent();
        //String msge = inte.getStringExtra("texto");

        //send(mensagem);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 2){
            String message = data.getStringExtra("texto");
            if(message.equals("Erros encontrados")){
                texto.setText("Tod: Você cometeu erros no código.");
            }else {
                texto.setText("Tod: Me observe fazer isso!");
                try {
                    send(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(requestCode == 3){
            String message = data.getStringExtra("texto");
            if(message.equals("Erros encontrados")){
                texto.setText("Tod: Você errou");
            }else{
                texto.setText("Tod: Parabéns! Código correto!");
            }
        }
    }
    public void conect(){
        try{
            BluetoothDevice btDevice = bt.getRemoteDevice(mac);
            btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString(myUUID));
            bt.cancelDiscovery();
            if (btSocket != null) {
                btSocket.connect();
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
                        Log.e("chat","Error: "+e.getMessage(),e);
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
    public void save(View view){
        TextView mensa = (TextView) findViewById(R.id.tMsg);
        String mensag = mensa.getText().toString();

        Intent intent = new Intent(this, Save.class);
        intent.putExtra("codigo",mensag);

        startActivity(intent);
    }

    public void open(View view){
        Intent intent = new Intent(this, Open.class);
        startActivity(intent);
    }

}
