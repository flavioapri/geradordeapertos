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
import br.com.flavio.geradordeapertos.modelo.Programa;

public class ProgramaDAO extends SQLiteOpenHelper {
    private static final int VERSAO_BANCO = 1;
    private static final String NOME_BANCO = "gerador_de_apertos";
    private Context context;
    
    public ProgramaDAO(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
        this.context = context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    
    public void insere(Programa programa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosPrograma(programa);
        db.insert("programa", null, dados);
    }
    
    private ContentValues pegaDadosPrograma(Programa programa) {
        ContentValues dados = new ContentValues();
        dados.put("id_processo", programa.getProcesso().getId());
        dados.put("nome", programa.getNome());
        dados.put("ciclos", programa.getCiclos());
        dados.put("valor_nominal", programa.getValorNominal());
        dados.put("angulo", programa.getAngulo());
        return dados;
    }
    //TODO tentar simplificar os metodos buscaProgramas
    public List<Programa> buscaProgramas() {
        String sql = "SELECT * FROM programa";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        int idProcesso;
        ProcessoDAO processoDAO = new ProcessoDAO(context);
        ArrayList<Programa> programas = new ArrayList<Programa>();
        while (c.moveToNext()) {
            Programa programa = new Programa();
            programa.setId(c.getInt(c.getColumnIndex("id")));
            programa.setNome(c.getString(c.getColumnIndex("nome")));
            programa.setCiclos(c.getInt(c.getColumnIndex("ciclos")));
            programa.setValorNominal(c.getFloat(c.getColumnIndex("valor_nominal")));
            programa.setAngulo(c.getInt(c.getColumnIndex("angulo")));
    
            idProcesso = c.getInt(c.getColumnIndex("id_processo"));
            programa.setProcesso(processoDAO.buscaProcesso(idProcesso));
    
            programas.add(programa);
        }
        processoDAO.close();
        c.close();
        return programas;
    }
    
    public List<Programa> buscaProgramas(Processo processo) {
        int idProcesso = processo.getId();
        String sql = "SELECT * FROM programa WHERE id_processo = " + idProcesso;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        ProcessoDAO processoDAO = new ProcessoDAO(context);
        ArrayList<Programa> programas = new ArrayList<Programa>();
        while (c.moveToNext()) {
            Programa programa = new Programa();
            programa.setId(c.getInt(c.getColumnIndex("id")));
            programa.setNome(c.getString(c.getColumnIndex("nome")));
            programa.setCiclos(c.getInt(c.getColumnIndex("ciclos")));
            programa.setValorNominal(c.getFloat(c.getColumnIndex("valor_nominal")));
            programa.setAngulo(c.getInt(c.getColumnIndex("angulo")));
    
            idProcesso = c.getInt(c.getColumnIndex("id_processo"));
            programa.setProcesso(processoDAO.buscaProcesso(idProcesso));
            
            programas.add(programa);
        }
        c.close();
        return programas;
    }
    
    public void deleta(Programa programa) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {String.valueOf(programa.getId())};
        db.delete("programa", "id=?", parametros);
    }
    
    public void altera(Programa programa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosPrograma(programa);
        String[] parametros = {String.valueOf(programa.getId())};
        db.update("programa", dados, "id=?", parametros);
    }
}

