package br.com.flavio.geradordeapertos;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
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
        carregaListaRegistros();
    }
    
    public void criaBancoDeDados() {
        File arquivoBanco = this.getDatabasePath(NOME_BANCO_DADOS);
        if (!arquivoBanco.exists()) {
            new BancoDeDadosHelper(this);
        }
    }
    
    private void carregaListaRegistros() {
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
        PopupMenu popup = getPopupMenu(view, R.menu.menu_main);
        
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.mi_cadastrar:
                    intent.setClass(MainActivity.this, Cadastro.class);
                    break;
                case R.id.mi_configuracoes:
                    intent.setClass(MainActivity.this, Configuracoes.class);
                    break;
                case R.id.mi_historico:
                    intent.setClass(MainActivity.this, Historico.class);
                    break;
                case R.id.mi_sobre:
                    intent.setClass(MainActivity.this, Sobre.class);
                    break;
            }
            startActivity(intent);
            return false;
        });
        popup.show();
    }
    
    /**
     * Exibe o menu para seleção do tipo de geração de apertos e chama a activity do formulário correspondente.
     *
     * @param view Botão de adicionar
     */
    public void mostraMenuNovo(View view) {
        PopupMenu popup = getPopupMenu(view, R.menu.menu_novo);
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
    
    /**
     * Cria um popup para exibição dos menus
     *
     * @param view   O botão que foi clicado
     * @param idMenu ID do menu correspondente
     * @return popup criado
     */
    private PopupMenu getPopupMenu(View view, int idMenu) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(idMenu, popup.getMenu());
        return popup;
    }
}
