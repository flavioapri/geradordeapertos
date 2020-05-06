package br.com.flavio.geradordeapertos.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Cria o banco de dados com todas as tabelas
 */
public class BancoDeDadosHelper extends SQLiteOpenHelper {
    private static Context context;
    private static final int VERSAO_BANCO = 1;
    public static final String NOME_BANCO = "gerador_de_apertos";
    private SQLiteDatabase db;
    // Nome das tabelas
    private static final String TABELA_APERTADEIRA = "apertadeira";
    private static final String TABELA_PROGRAMA = "programa";
    private static final String TABELA_MOTIVO = "motivo";
    private static final String TABELA_REGISTRO = "registro";
    // Nomes de campos em comum
    private static final String ID = "id";
    private static final String NOME = "nome";
    // Tabela PROCESSO - campos
    // Tabela MOTIVO - campos
    // Tabela PROGRAMA - campos
    private static final String ID_APERTADEIRA = "id_apertadeira";
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
    private static final String CRIAR_TABELA_APERTADEIRA =
            "CREATE TABLE " + TABELA_APERTADEIRA + " (" + ID + " INTEGER PRIMARY KEY, " + NOME + " TEXT NOT NULL);";
    
    // Tabela PROGRAMA
    private static final String CRIAR_TABELA_PROGRAMA =
            "CREATE TABLE " + TABELA_PROGRAMA + " (" + ID + " INTEGER PRIMARY KEY, " + NOME + " TEXT NOT NULL, " + ID_APERTADEIRA + " " +
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
        this.context = context;
        this.db = getWritableDatabase();
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criando as tabelas        
        db.execSQL(CRIAR_TABELA_APERTADEIRA);
        db.execSQL(CRIAR_TABELA_PROGRAMA);
        db.execSQL(CRIAR_TABELA_MOTIVO);
        db.execSQL(CRIAR_TABELA_REGISTRO);
        // Insere todos os dados do banco que estão em arquivo
        try {
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream = assetManager.open("db_data.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                db.execSQL(linha);
            }
            inputStream.close();
        } catch (IOException e) {
        }
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Ao atualizar remover tabelas antigas
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PROGRAMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_APERTADEIRA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_MOTIVO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_REGISTRO);
        // Criar as novas tabelas
        onCreate(db);
    }
}
