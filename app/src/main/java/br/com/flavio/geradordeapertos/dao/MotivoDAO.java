package br.com.flavio.geradordeapertos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.flavio.geradordeapertos.modelo.Motivo;

public class MotivoDAO extends SQLiteOpenHelper {
    public MotivoDAO(@Nullable Context context) {
        super(context, "GeradorDeApertos", null, 1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Motivo (id INTEGER PRIMARY KEY, nome TEXT NOT NULL);";
        db.execSQL(sql);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Motivo";
        db.execSQL(sql);
        onCreate(db);
    }
    
    public void insere(Motivo motivo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosMotivo(motivo);
        db.insert("Motivo", null, dados);
    }
    
    private ContentValues pegaDadosMotivo(Motivo motivo) {
        ContentValues dados = new ContentValues();
        dados.put("nome", motivo.getNome());
        return dados;
    }
    
    public List<Motivo> buscaMotivos() {
        String sql = "SELECT * FROM Motivo";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        
        ArrayList<Motivo> motivos = new ArrayList<Motivo>();
        while (c.moveToNext()) {
            Motivo motivo = new Motivo();
            motivo.setId(c.getInt(c.getColumnIndex("id")));
            motivo.setNome(c.getString(c.getColumnIndex("nome")));
            motivos.add(motivo);
        }
        c.close();
        return motivos;
    }
    
    public void deleta(Motivo motivo) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {String.valueOf(motivo.getId())};
        db.delete("Motivo", "id=?", parametros);
    }
    
    public void altera(Motivo motivo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosMotivo(motivo);
        String[] parametros = {String.valueOf(motivo.getId())};
        db.update("Motivo", dados, "id=?", parametros);
    }
    
    public Motivo busca(int idMotivo) {
        String sql = "SELECT id, nome FROM motivo WHERE id = " + idMotivo;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        
        Motivo motivo = new Motivo();
        while (c.moveToNext()) {
            motivo.setId(c.getInt(c.getColumnIndex("id")));
            motivo.setNome(c.getString(c.getColumnIndex("nome")));
        }
        c.close();
        return motivo;
    }
}
