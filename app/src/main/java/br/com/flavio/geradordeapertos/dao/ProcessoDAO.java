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
    private static final int VERSAO_BANCO = 1;
    private static final String NOME_BANCO = "gerador_de_apertos";
    
    public ProcessoDAO(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    
    public void insere(Processo processo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosProcesso(processo);
        db.insert("processo", null, dados);
    }
    
    private ContentValues pegaDadosProcesso(Processo processo) {
        ContentValues dados = new ContentValues();
        dados.put("nome", processo.getNome());
        return dados;
    }
    
    public List<Processo> buscaProcessos() {
        String sql = "SELECT * FROM processo";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        
        ArrayList<Processo> processos = new ArrayList<Processo>();
        while (c.moveToNext()) {
            Processo processo = new Processo();
            processo.setId(c.getInt(c.getColumnIndex("id")));
            processo.setNome(c.getString(c.getColumnIndex("nome")));
            processos.add(processo);
        }
        c.close();
        return processos;
    }
    
    public void deleta(Processo processo) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {String.valueOf(processo.getId())};
        db.delete("processo", "id=?", parametros);
    }
    
    public void altera(Processo processo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosProcesso(processo);
        String[] parametros = {String.valueOf(processo.getId())};
        db.update("processo", dados, "id=?", parametros);
    }
    
    public Processo buscaProcesso(int idProcesso) {
        String sql = "SELECT id_processo, nome FROM processo WHERE id = " + idProcesso;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        Processo processo = new Processo();
        
        while (c.moveToNext()) {
            processo.setId(c.getInt(c.getColumnIndex("id")));
            processo.setNome(c.getString(c.getColumnIndex("nome")));
        }
        c.close();
        return processo;
    }
}
