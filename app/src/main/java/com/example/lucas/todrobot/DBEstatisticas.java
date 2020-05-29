package com.example.lucas.todrobot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Lucas on 26/07/2016.
 */
public class DBEstatisticas extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    public static final String NOME_BANCO = "estatisticas.sqlite";
    private static final int VERSAO_BANCO = 1;
    public DBEstatisticas(Context context){
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }
/*
codigoErrado(id);
abrirChaves(id);
valorParenteses(id);
usoIndevidoDeChaves(id);
fechouSemAbrir(id);
 */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists erro(_id integer primary key autoincrement, tipo integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Caso mude a versão do banco de dados
    }
    public long save(Errado c){
        long id = c.id;
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues valores = new ContentValues();
            valores.put("tipo",c.tipo);
            if (id != 0){
                String _id = String.valueOf(c.id);
                String[] whereArgs = new String[]{_id};
                int count = db.update("erro",valores,"_id=?", whereArgs);//"Carro"
                return count;
            }else {
                id = db.insert("erro","",valores);
                return id;
            }
        }finally {
            db.close();
        }
    }
    public int delete(String tipo){
        SQLiteDatabase db = getWritableDatabase();
        try{
            int count = db.delete("erro","tipo=?",new String[]{tipo});
            return count;
        }finally{
            db.close();
        }
    }
    public int quantidade(String tipo) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCount= db.rawQuery("select count(*) from erro where erro.tipo = "+tipo+";", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }
    public void execSQL(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql);
        }finally {
            db.close();
        }
    }
    public void execSQL(String sql, Object[] args){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql,args);
        }finally {
            db.close();
        }
    }
}
