package br.com.flavio.geradordeapertos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import br.com.flavio.geradordeapertos.adapter.RegistroAdapter;
import br.com.flavio.geradordeapertos.dao.RegistroDAO;
import br.com.flavio.geradordeapertos.helper.BancoDeDadosHelper;
import br.com.flavio.geradordeapertos.modelo.Registro;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv_registros;
    private RegistroAdapter adapter;
    private List<Registro> registros;
    private Intent intent;
    private static final String NOME_BANCO_DADOS = "gerador_de_apertos";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_registros = findViewById(R.id.rv_registros_lista);
        intent = new Intent();
        criaBancoDeDados();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        criaLista();
    }
    
    public void criaBancoDeDados() {
        File arquivoBanco = this.getDatabasePath(NOME_BANCO_DADOS);
        if (!arquivoBanco.exists()) {
            new BancoDeDadosHelper(this);
        }
    }
    
    private void criaLista() {
        RegistroDAO dao = new RegistroDAO(this);
        registros = dao.buscaTodos();
        dao.close();
        adapter = new RegistroAdapter(registros, this);
        rv_registros.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_registros.setLayoutManager(layoutManager);
    }
    
    /**
     * Abre menu superior
     *
     * @param view Botão que exibe o menu.
     */
    public void mostraMenuSuperior(View view) {
        MontaMenus.mostra(view, this);
    }
    
    /**
     * Exibe o menu para seleção do tipo de geração de apertos e chama a activity do formulário correspondente.
     *
     * @param view Botão de adicionar
     */
    public void mostraMenuNovo(View view) {
        PopupMenu popup = MontaMenus.getPopup(view, R.menu.menu_novo, this);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.mi_programa_individual:
                    intent.setClass(MainActivity.this, FormularioProgramaIndividual.class);
                    break;
                case R.id.mi_programa_lote:
                    intent.setClass(MainActivity.this, FormularioProgramaLote.class);
                    break;
            }
            startActivity(intent);
            return false;
        });
        popup.show();
    }
}
