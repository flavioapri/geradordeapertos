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

public class ApertadeiraDAO extends SQLiteOpenHelper {
    private static final int VERSAO_BANCO = 1;
    
    public ApertadeiraDAO(@Nullable Context context) {
        super(context, BancoDeDadosHelper.NOME_BANCO, null, VERSAO_BANCO);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    
    public void insere(Apertadeira apertadeira) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosApertadeira(apertadeira);
        db.insert("apertadeira", null, dados);
    }
    
    private ContentValues pegaDadosApertadeira(Apertadeira apertadeira) {
        ContentValues dados = new ContentValues();
        dados.put("nome", apertadeira.getNome());
        return dados;
    }
    
    public List<Apertadeira> buscaApertadeiras() {
        String sql = "SELECT * FROM apertadeira";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        
        ArrayList<Apertadeira> apertadeiras = new ArrayList<Apertadeira>();
        while (c.moveToNext()) {
            Apertadeira apertadeira = new Apertadeira();
            apertadeira.setId(c.getInt(c.getColumnIndex("id")));
            apertadeira.setNome(c.getString(c.getColumnIndex("nome")));
            apertadeiras.add(apertadeira);
        }
        c.close();
        return apertadeiras;
    }
    
    public void deleta(Apertadeira apertadeira) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {String.valueOf(apertadeira.getId())};
        db.delete("processo", "id_apertadeira=?", parametros);
        db.delete("apertadeira", "id=?", parametros);
    }
    
    public void altera(Apertadeira apertadeira) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosApertadeira(apertadeira);
        String[] parametros = {String.valueOf(apertadeira.getId())};
        db.update("apertadeira", dados, "id=?", parametros);
    }
    
    public Apertadeira buscaApertadeira(int idApertadeira) {
        String sql = "SELECT id, nome FROM apertadeira WHERE id = " + idApertadeira;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        Apertadeira apertadeira = new Apertadeira();
        
        while (c.moveToNext()) {
            apertadeira.setId(c.getInt(c.getColumnIndex("id")));
            apertadeira.setNome(c.getString(c.getColumnIndex("nome")));
        }
        c.close();
        return apertadeira;
    }
}
