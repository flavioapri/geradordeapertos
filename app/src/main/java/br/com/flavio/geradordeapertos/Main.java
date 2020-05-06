package br.com.flavio.geradordeapertos;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import br.com.flavio.geradordeapertos.adapter.RegistroAdapter;
import br.com.flavio.geradordeapertos.dao.RegistroDAO;
import br.com.flavio.geradordeapertos.helper.BancoDeDadosHelper;
import br.com.flavio.geradordeapertos.modelo.Registro;

public class Main extends BaseActivity {
    private RecyclerView rv_registros;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_registros = findViewById(R.id.rv_registros_lista);
        criaBancoDeDados();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        criaLista();
    }
    
    public void criaBancoDeDados() {
        File arquivoBanco = this.getDatabasePath(BancoDeDadosHelper.NOME_BANCO);
        if (!arquivoBanco.exists()) {
            new BancoDeDadosHelper(this);
        }
    }
    
    private void criaLista() {
        RegistroDAO dao = new RegistroDAO(this);
        List<Registro> registros = dao.buscaTodos();
        dao.close();
        RegistroAdapter adapter = new RegistroAdapter(registros, this);
        rv_registros.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_registros.setLayoutManager(layoutManager);
    }
}
