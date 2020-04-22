package br.com.flavio.geradordeapertos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.flavio.geradordeapertos.modelo.Processo;

public class ProcessoDAO extends SQLiteOpenHelper {
    public ProcessoDAO(@Nullable Context context) {
        super(context, "GeradorDeApertos", null, 1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Processo (id INTEGER PRIMARY KEY, nome TEXT NOT NULL);";
        db.execSQL(sql);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Processo";
        db.execSQL(sql);
        onCreate(db);
    }
    
    public void insere(Processo processo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosProcesso(processo);
        db.insert("Processo", null, dados);
    }
    
    private ContentValues pegaDadosProcesso(Processo processo) {
        ContentValues dados = new ContentValues();
        dados.put("nome", processo.getNome());
        return dados;
    }
    
    public List<Processo> buscaProcessos() {
        String sql = "SELECT * FROM Processo";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        
        ArrayList<Processo> processos = new ArrayList<Processo>();
        while (c.moveToNext()) {
            Processo processo = new Processo();
            processo.setId(c.getLong(c.getColumnIndex("id")));
            processo.setNome(c.getString(c.getColumnIndex("nome")));
            processos.add(processo);
        }
        c.close();
        return processos;
    }
    
    public void deleta(Processo processo) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {String.valueOf(processo.getId())};
        db.delete("Processo", "id=?", parametros);
    }
    
    public void altera(Processo processo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosProcesso(processo);
        String[] parametros = {String.valueOf(processo.getId())};
        db.update("Processo", dados, "id=?", parametros);
    }
}
