package br.com.flavio.geradordeapertos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.flavio.geradordeapertos.adapter.ProcessoAdapter;
import br.com.flavio.geradordeapertos.dao.ProcessoDAO;
import br.com.flavio.geradordeapertos.modelo.Processo;

public class RelacaoProcessos extends BaseActivity {
    private RecyclerView rv_processos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relacao_processos);
        rv_processos = findViewById(R.id.rv_relacao_processo_lista);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        carregaListaProcessos();
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Desabilita a opção da activity no menu
        menu.findItem(R.id.mi_processo).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
    
    /**
     * Busca a relação dos processos no banco e insere no recyclerview
     */
    private void carregaListaProcessos() {
        ProcessoDAO processoDAO = new ProcessoDAO(this);
        List<Processo> processos = processoDAO.buscaProcessos();
        processoDAO.close();
        ProcessoAdapter adapter = new ProcessoAdapter(processos, this);
        rv_processos.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_processos.setLayoutManager(layoutManager);
    }
    
    public void gotoCadastroProcesso(View view) {
        Intent intent = new Intent(this, CadastroProcesso.class);
        startActivity(intent);
    }
}
