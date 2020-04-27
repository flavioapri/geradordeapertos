package br.com.flavio.geradordeapertos.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Cria o banco de dados com todas as tabelas
 */
public class BancoDeDadosHelper extends SQLiteOpenHelper {
    private static final int VERSAO_BANCO = 1;
    private static final String NOME_BANCO = "gerador_de_apertos";
    // Nome das tabelas
    private static final String TABELA_PROCESSO = "processo";
    private static final String TABELA_PROGRAMA = "programa";
    //    private static final String TABELA_MOTIVO = "motivo";
//    private static final String TABELA_REGISTRO = "registro";
//    private static final String TABELA_CABINA= "cabina";
//    private static final String TABELA_DATA = "data";
    // Nomes de campos em comum
    private static final String ID = "id";
    private static final String NOME = "nome";
    // Tabela PROCESSO - campos
    // Tabela PROGRAMA - campos
    private static final String ID_PROCESSO = "id_processo";
    private static final String CICLOS = "ciclos";
    private static final String VALOR_NOMINAL = "valor_nominal";
    private static final String ANGULO = "angulo";
    // Declaração para criação das tabelas
    // Tabela PROCESSO
    private static final String CRIAR_TABELA_PROCESSO =
            "CREATE TABLE " + TABELA_PROCESSO + " (" + ID + " INTEGER PRIMARY KEY, " + NOME + " TEXT NOT NULL);";
    // Tabela PROGRAMA
    private static final String CRIAR_TABELA_PROGRAMA =
            "CREATE TABLE " + TABELA_PROGRAMA + " (" + ID + " INTEGER PRIMARY KEY, " + NOME + " TEXT NOT NULL, " + ID_PROCESSO + " INTEGER" +
                    " NOT NULL, " + CICLOS + " " +
                    "INTEGER NOT NULL, " + VALOR_NOMINAL + " REAL NOT NULL, " + ANGULO + " INTEGER NOT NULL);";
    
    public BancoDeDadosHelper(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criando as tabelas
        db.execSQL(CRIAR_TABELA_PROCESSO);
        db.execSQL(CRIAR_TABELA_PROGRAMA);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Ao atualizar remover tabelas antigas
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PROGRAMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PROCESSO);
        // Criar as novas tabelas
        onCreate(db);
    }
}
