package br.com.flavio.geradordeapertos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.flavio.geradordeapertos.adapter.ProgramaAdapter;
import br.com.flavio.geradordeapertos.dao.ProgramaDAO;
import br.com.flavio.geradordeapertos.modelo.Programa;

public class RelacaoProgramas  extends BaseActivity{
    private RecyclerView rv_programas;
    private ProgramaAdapter adapter;
    private List<Programa> programas;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relacao_programas);
        rv_programas = findViewById(R.id.rv_relacao_programa_lista);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        carregaListaProgramas();
    }
    
    /**
     * Busca a relação dos programas no banco e insere no recyclerview
     */
    private void carregaListaProgramas() {
        ProgramaDAO programaDAO = new ProgramaDAO(this);
        programas = programaDAO.buscaProgramas();
        programaDAO.close();
        adapter = new ProgramaAdapter(programas, this);
        rv_programas.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_programas.setLayoutManager(layoutManager);
    }
    
    /**
     * Chama a activity da tela inicial
     *
     * @param view
     */
    public void gotoHome(View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
    
    public void gotoCadastroPrograma(View view){
        Intent intent = new Intent(this, CadastroPrograma.class);
        startActivity(intent);
    }
}
