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

import br.com.flavio.geradordeapertos.adapter.RecyclerViewAdapter;
import br.com.flavio.geradordeapertos.dao.ProcessoDAO;
import br.com.flavio.geradordeapertos.modelo.Processo;

public class CadastroProcesso extends AppCompatActivity {
    private RecyclerView rv_processsos;
    private EditText et_dialog_processo;
    private RecyclerViewAdapter adapter;
    private List<Processo> processos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_processo);
        rv_processsos = findViewById(R.id.rv_cadastro_processo_lista);
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
        adapter = new RecyclerViewAdapter(processos, this);
        rv_processsos.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_processsos.setLayoutManager(layoutManager);
    }
    
    /**
     * Exibe popup para persistir um novo item_lista
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
