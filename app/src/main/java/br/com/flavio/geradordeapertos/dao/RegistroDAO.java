package br.com.flavio.geradordeapertos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.flavio.geradordeapertos.modelo.Programa;
import br.com.flavio.geradordeapertos.modelo.Registro;

public class RegistroDAO extends SQLiteOpenHelper {
    private static final int VERSAO_BANCO = 1;
    private static final String NOME_BANCO = "gerador_de_apertos";
    private Context context;
    
    public RegistroDAO(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
        this.context = context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    
    public void insere(Registro registro) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosRegistro(registro);
        db.insert("registro", null, dados);
    }
    
    private ContentValues pegaDadosRegistro(Registro registro) {
        ContentValues dados = new ContentValues();
        dados.put("np", registro.getNP());
        dados.put("data", registro.getData());
        dados.put("id_programa", registro.getPrograma().getId());
        dados.put("ciclo", registro.getCiclo());
        dados.put("valor", registro.getValor());
        dados.put("id_motivo", registro.getMotivo().getId());
        return dados;
    }
    
    //TODO tentar simplificar os metodos buscaRegistros
    public List<Registro> buscaTodos() {
        String sql = "SELECT * FROM registro";
        SQLiteDatabase db = getReadableDatabase();
        int idPrograma;
        int idMotivo;
        ProgramaDAO programaDAO = new ProgramaDAO(context);
        MotivoDAO motivoDAO = new MotivoDAO(context);
    
        ArrayList<Registro> registros = new ArrayList<Registro>();
        try {
            Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Registro registro = new Registro();
            registro.setId(c.getInt(c.getColumnIndex("id")));
            registro.setNP(c.getString(c.getColumnIndex("np")));
            registro.setData(c.getString(c.getColumnIndex("data")));
            registro.setCiclo(c.getInt(c.getColumnIndex("ciclo")));
            registro.setValor(c.getDouble(c.getColumnIndex("valor")));
            
            idPrograma = c.getInt(c.getColumnIndex("id_programa"));
            registro.setPrograma(programaDAO.buscaPrograma(idPrograma));
            
            idMotivo = c.getInt(c.getColumnIndex("id_motivo"));
            registro.setMotivo(motivoDAO.busca(idMotivo));
            
            registros.add(registro);
        }
        programaDAO.close();
        motivoDAO.close();
        c.close();
            return registros;
        } catch (SQLiteException e){
            return registros;
        }
    }
    
    public List<Registro> buscaTodosPorPrograma(Programa programa) {
        int idPrograma = programa.getId();
        String sql = "SELECT * FROM registro WHERE id_programa = " + idPrograma;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        
        int idMotivo;
        ProgramaDAO programaDAO = new ProgramaDAO(context);
        MotivoDAO motivoDAO = new MotivoDAO(context);
    
        ArrayList<Registro> registros = new ArrayList<Registro>();
        while (c.moveToNext()) {
            Registro registro = new Registro();
            registro.setId(c.getInt(c.getColumnIndex("id")));
            registro.setNP(c.getString(c.getColumnIndex("np")));
            registro.setData(c.getString(c.getColumnIndex("data")));
            registro.setCiclo(c.getInt(c.getColumnIndex("ciclo")));
            registro.setValor(c.getDouble(c.getColumnIndex("valor")));
        
            idPrograma = c.getInt(c.getColumnIndex("id_programa"));
            registro.setPrograma(programaDAO.buscaPrograma(idPrograma));
        
            idMotivo = c.getInt(c.getColumnIndex("id_motivo"));
            registro.setMotivo(motivoDAO.busca(idMotivo));
        
            registros.add(registro);
        }
        programaDAO.close();
        motivoDAO.close();
        c.close();
        return registros;
    }
    
    public void deleta(Registro registro) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {String.valueOf(registro.getId())};
        db.delete("registro", "id=?", parametros);
    }
    
    public void altera(Registro registro) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosRegistro(registro);
        String[] parametros = {String.valueOf(registro.getId())};
        db.update("registro", dados, "id=?", parametros);
    }
}

