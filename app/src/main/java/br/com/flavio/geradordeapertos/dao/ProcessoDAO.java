package br.com.flavio.geradordeapertos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.flavio.geradordeapertos.helper.BancoDeDadosHelper;
import br.com.flavio.geradordeapertos.modelo.Apertadeira;
import br.com.flavio.geradordeapertos.modelo.Processo;

public class ProcessoDAO extends SQLiteOpenHelper {
    private static final int VERSAO_BANCO = 1;
    private Context context;
    
    public ProcessoDAO(@Nullable Context context) {
        super(context, BancoDeDadosHelper.NOME_BANCO, null, VERSAO_BANCO);
        this.context = context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    
    public void insere(Processo processo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDados(processo);
        db.insert("processo", null, dados);
    }
    
    private ContentValues pegaDados(Processo processo) {
        ContentValues dados = new ContentValues();
        dados.put("id_apertadeira", processo.getApertadeira().getId());
        dados.put("nome", processo.getNome());
        dados.put("ciclos", processo.getCiclos());
        dados.put("valor_nominal", processo.getValorNominal());
        dados.put("angulo", processo.getAngulo());
        return dados;
    }
    
    //TODO tentar simplificar os metodos buscaProcessos
    public List<Processo> buscaProcessos() {
        String sql = "SELECT * FROM processo";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        int idApertadeira;
        ApertadeiraDAO apertadeiraDAO = new ApertadeiraDAO(context);
        ArrayList<Processo> processos = new ArrayList<Processo>();
        while (c.moveToNext()) {
            Processo processo = new Processo();
            processo.setId(c.getInt(c.getColumnIndex("id")));
            processo.setNome(c.getString(c.getColumnIndex("nome")));
            processo.setCiclos(c.getInt(c.getColumnIndex("ciclos")));
            processo.setValorNominal(c.getFloat(c.getColumnIndex("valor_nominal")));
            processo.setAngulo(c.getInt(c.getColumnIndex("angulo")));
            
            idApertadeira = c.getInt(c.getColumnIndex("id_apertadeira"));
            processo.setApertadeira(apertadeiraDAO.buscaApertadeira(idApertadeira));
            
            processos.add(processo);
        }
        apertadeiraDAO.close();
        c.close();
        return processos;
    }
    
    public List<Processo> buscaProcessos(Apertadeira apertadeira) {
        int idApertadeira = apertadeira.getId();
        String sql = "SELECT * FROM processo WHERE id_apertadeira = " + idApertadeira;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        ApertadeiraDAO apertadeiraDAO = new ApertadeiraDAO(context);
        ArrayList<Processo> processos = new ArrayList<Processo>();
        while (c.moveToNext()) {
            Processo processo = new Processo();
            processo.setId(c.getInt(c.getColumnIndex("id")));
            processo.setNome(c.getString(c.getColumnIndex("nome")));
            processo.setCiclos(c.getInt(c.getColumnIndex("ciclos")));
            processo.setValorNominal(c.getFloat(c.getColumnIndex("valor_nominal")));
            processo.setAngulo(c.getInt(c.getColumnIndex("angulo")));
            
            idApertadeira = c.getInt(c.getColumnIndex("id_apertadeira"));
            processo.setApertadeira(apertadeiraDAO.buscaApertadeira(idApertadeira));
            
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
        ContentValues dados = pegaDados(processo);
        String[] parametros = {String.valueOf(processo.getId())};
        db.update("processo", dados, "id=?", parametros);
    }
    
    public Processo buscaProcesso(int idProcesso) {
        String sql = "SELECT id, nome, ciclos, valor_nominal, angulo, id_apertadeira FROM processo WHERE id = " + idProcesso;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        int idApertadeira;
        ApertadeiraDAO apertadeiraDAO = new ApertadeiraDAO(context);
        Processo processo = new Processo();
        while (c.moveToNext()) {
            processo.setId(c.getInt(c.getColumnIndex("id")));
            processo.setNome(c.getString(c.getColumnIndex("nome")));
            processo.setCiclos(c.getInt(c.getColumnIndex("ciclos")));
            processo.setValorNominal(c.getFloat(c.getColumnIndex("valor_nominal")));
            processo.setAngulo(c.getInt(c.getColumnIndex("angulo")));
            
            idApertadeira = c.getInt(c.getColumnIndex("id_apertadeira"));
            processo.setApertadeira(apertadeiraDAO.buscaApertadeira(idApertadeira));
        }
        c.close();
        return processo;
    }
}

