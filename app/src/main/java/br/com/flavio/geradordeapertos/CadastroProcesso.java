package br.com.flavio.geradordeapertos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.flavio.geradordeapertos.adapter.ProcessoAdapter;
import br.com.flavio.geradordeapertos.helper.BancoDeDadosHelper;
import br.com.flavio.geradordeapertos.dao.ProcessoDAO;
import br.com.flavio.geradordeapertos.modelo.Processo;

public class CadastroProcesso extends AppCompatActivity {
    private BancoDeDadosHelper bancoDeDadosHelper;
    private RecyclerView rv_processos;
    private ProcessoAdapter adapter;
    private List<Processo> processos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_processo);
        rv_processos = findViewById(R.id.rv_cadastro_processo_lista);
        
        bancoDeDadosHelper = new BancoDeDadosHelper(this);
        bancoDeDadosHelper.getWritableDatabase();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        carregaListaProcessos();
    }
    
    /**
     * Busca a relação dos processos no banco e insere no recyclerview
     */
    private void carregaListaProcessos() {
        ProcessoDAO processoDAO = new ProcessoDAO(this);
        processos = processoDAO.buscaProcessos();
        processoDAO.close();
        adapter = new ProcessoAdapter(processos, this);
        rv_processos.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_processos.setLayoutManager(layoutManager);
    }
    
    /**
     * Exibe popup para persistir um novo ll_historico
     *
     * @param view
     */
    public void cadastrarProcesso(View view) {
        final EditText et_dialog_processo = new EditText(this);
        final ProcessoDAO processoDAO = new ProcessoDAO(this);
        final Processo processo = new Processo();
        //TODO personalizar estilo do dialog
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(R.string.cadastro_processo)
                .setMessage(R.string.informe_nome_processo)
                .setView(et_dialog_processo)
                .setPositiveButton(R.string.cadastrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        processo.setNome(et_dialog_processo.getText().toString());
                        processoDAO.insere(processo);
                        carregaListaProcessos();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
        processoDAO.close();
    }
    
    /**
     * Chama a activity da tela inicial
     *
     * @param view
     */
    public void vaiParaHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
