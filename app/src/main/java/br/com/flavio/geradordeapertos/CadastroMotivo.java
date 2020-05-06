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

import br.com.flavio.geradordeapertos.adapter.MotivoAdapter;
import br.com.flavio.geradordeapertos.dao.MotivoDAO;
import br.com.flavio.geradordeapertos.modelo.Motivo;

public class CadastroMotivo extends AppCompatActivity {
    private RecyclerView rv_motivos;
    private MotivoAdapter adapter;
    private List<Motivo> motivos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_motivo);
        rv_motivos = findViewById(R.id.rv_cadastro_motivo_lista);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        carregaListaMotivos();
    }
    
    /**
     * Busca a relação dos motivos no banco e insere no recyclerview
     */
    private void carregaListaMotivos() {
        MotivoDAO motivoDAO = new MotivoDAO(this);
        motivos = motivoDAO.buscaMotivos();
        motivoDAO.close();
        adapter = new MotivoAdapter(motivos, this);
        rv_motivos.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_motivos.setLayoutManager(layoutManager);
    }
    
    /**
     * Exibe popup para persistir um novo ll_historico
     *
     * @param view
     */
    public void cadastrarMotivo(View view) {
        final EditText et_dialog_motivo = new EditText(this);
        final MotivoDAO motivoDAO = new MotivoDAO(this);
        final Motivo motivo = new Motivo();
        //TODO personalizar estilo do dialog
        new AlertDialog.Builder(this)
                .setTitle(R.string.cadastro_motivo)
                .setMessage(R.string.informe_nome_motivo)
                .setView(et_dialog_motivo)
                .setPositiveButton(R.string.cadastrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        motivo.setNome(et_dialog_motivo.getText().toString());
                        motivoDAO.insere(motivo);
                        carregaListaMotivos();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
        motivoDAO.close();
    }
    
    /**
     * Chama a activity da tela inicial
     *
     * @param view
     */
    public void vaiParaHome(View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}
