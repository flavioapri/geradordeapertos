package br.com.flavio.geradordeapertos.helper;

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
    private SQLiteDatabase db;
    // Nome das tabelas
    private static final String TABELA_PROCESSO = "processo";
    private static final String TABELA_PROGRAMA = "programa";
    private static final String TABELA_MOTIVO = "motivo";
    private static final String TABELA_REGISTRO = "registro";
    // Nomes de campos em comum
    private static final String ID = "id";
    private static final String NOME = "nome";
    // Tabela PROCESSO - campos
    // Tabela MOTIVO - campos
    // Tabela PROGRAMA - campos
    private static final String ID_PROCESSO = "id_processo";
    private static final String CICLOS = "ciclos";
    private static final String VALOR_NOMINAL = "valor_nominal";
    private static final String ANGULO = "angulo";
    // Tabela REGISTRO
    private static final String NP = "np";
    private static final String DATA = "data";
    private static final String ID_PROGRAMA = "id_programa";
    private static final String CICLO = "ciclo";
    private static final String VALOR = "valor";
    private static final String ID_MOTIVO = "id_motivo";
    // Declaração para criação das tabelas
    // Tabela PROCESSO
    private static final String CRIAR_TABELA_PROCESSO =
            "CREATE TABLE " + TABELA_PROCESSO + " (" + ID + " INTEGER PRIMARY KEY, " + NOME + " TEXT NOT NULL);";
    
    // Tabela PROGRAMA
    private static final String CRIAR_TABELA_PROGRAMA =
            "CREATE TABLE " + TABELA_PROGRAMA + " (" + ID + " INTEGER PRIMARY KEY, " + NOME + " TEXT NOT NULL, " + ID_PROCESSO + " " +
                    "INTEGER" +
                    " NOT NULL, " + CICLOS + " " +
                    "INTEGER NOT NULL, " + VALOR_NOMINAL + " REAL NOT NULL, " + ANGULO + " INTEGER NOT NULL);";
    
    // Tabela REGISTRO
    private static final String CRIAR_TABELA_REGISTRO =
            "CREATE TABLE " + TABELA_REGISTRO + " (" + ID + " INTEGER PRIMARY KEY, " + NP + " INTEGER NOT NULL, " + DATA + " TEXT NOT " +
                    "NULL, " + ID_PROGRAMA + " " +
                    "INTEGER" +
                    " NOT NULL, " + CICLO + " " +
                    "INTEGER NOT NULL, " + VALOR + " REAL NOT NULL, " + ID_MOTIVO + " INTEGER NOT NULL);";
    
    // Tabela MOTIVO
    private static final String CRIAR_TABELA_MOTIVO =
            "CREATE TABLE " + TABELA_MOTIVO + " (" + ID + " INTEGER PRIMARY KEY, " + NOME + " TEXT NOT NULL);";
    
    public BancoDeDadosHelper(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
        this.db = getWritableDatabase();
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criando as tabelas        
        db.execSQL(CRIAR_TABELA_PROCESSO);
        db.execSQL(CRIAR_TABELA_PROGRAMA);
        db.execSQL(CRIAR_TABELA_MOTIVO);
        db.execSQL(CRIAR_TABELA_REGISTRO);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Ao atualizar remover tabelas antigas
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PROGRAMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PROCESSO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_MOTIVO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_REGISTRO);
        // Criar as novas tabelas
        onCreate(db);
    }
}
