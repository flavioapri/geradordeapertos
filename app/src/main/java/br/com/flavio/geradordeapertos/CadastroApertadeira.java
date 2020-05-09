package br.com.flavio.geradordeapertos;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.flavio.geradordeapertos.adapter.ApertadeiraAdapter;
import br.com.flavio.geradordeapertos.dao.ApertadeiraDAO;
import br.com.flavio.geradordeapertos.modelo.Apertadeira;

public class CadastroApertadeira extends BaseActivity {
    private RecyclerView rv_apertadeiras;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_apertadeira);
        rv_apertadeiras = findViewById(R.id.rv_cadastro_apertadeira_lista);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        carregaListaApertadeiras();
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Desabilita a opção da activity no menu
        menu.findItem(R.id.mi_apertadeira).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
    
    /**
     * Busca a relação dos apertadeiras no banco e insere no recyclerview
     */
    private void carregaListaApertadeiras() {
        ApertadeiraDAO apertadeiraDAO = new ApertadeiraDAO(this);
        List<Apertadeira> apertadeiras = apertadeiraDAO.buscaApertadeiras();
        apertadeiraDAO.close();
        ApertadeiraAdapter adapter = new ApertadeiraAdapter(apertadeiras, this);
        rv_apertadeiras.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_apertadeiras.setLayoutManager(layoutManager);
    }
    
    /**
     * Exibe popup para persistir um novo cardview
     *
     * @param view
     */
    public void cadastrarApertadeira(View view) {
        final EditText et_dialog_apertadeira = new EditText(this);
        final ApertadeiraDAO apertadeiraDAO = new ApertadeiraDAO(this);
        final Apertadeira apertadeira = new Apertadeira();
        //TODO personalizar estilo do dialog
        new AlertDialog.Builder(this)
                .setTitle(R.string.ad_titulo_cadastrar_apertadeira)
                .setMessage(R.string.ad_msg_cadastrar_apertadeira)
                .setView(et_dialog_apertadeira)
                .setPositiveButton(R.string.cadastrar, (dialog, which) -> {
                    apertadeira.setNome(et_dialog_apertadeira.getText().toString());
                    apertadeiraDAO.insere(apertadeira);
                    carregaListaApertadeiras();
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
        apertadeiraDAO.close();
    }
}
