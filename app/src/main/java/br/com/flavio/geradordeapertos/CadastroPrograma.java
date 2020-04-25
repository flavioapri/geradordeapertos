package br.com.flavio.geradordeapertos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.flavio.geradordeapertos.dao.ProcessoDAO;
import br.com.flavio.geradordeapertos.dao.ProgramaDAO;
import br.com.flavio.geradordeapertos.mask.MascaraWatcher;
import br.com.flavio.geradordeapertos.modelo.Processo;
import br.com.flavio.geradordeapertos.modelo.Programa;

public class CadastroPrograma extends AppCompatActivity {
    private TextView tv_processo;
    private TextView tv_nome;
    private TextView tv_ciclos;
    private TextView tv_nominal;
    private TextView tv_angulo;
    private Processo processo;
    private Programa programa;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_programa);
        
        tv_processo = findViewById(R.id.tv_cadastro_programa_processo);
        tv_nome = findViewById(R.id.tv_cadastro_programa_nome);
        tv_ciclos = findViewById(R.id.tv_cadastro_programa_ciclos);
        tv_nominal = findViewById(R.id.tv_cadastro_programa_nominal);
        tv_angulo = findViewById(R.id.tv_cadastro_programa_angulo);
        
        programa = new Programa();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    public void selecionaProcesso(View view) {
        final Spinner spinner = criaSpinnerDeProcessos();
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(R.string.selecao_processo)
                .setMessage(R.string.selecione_processo_lista)
                .setView(spinner)
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        processo = (Processo) spinner.getSelectedItem();
                        programa.setIdprocesso(processo.getId());
                        tv_processo.setText(processo.getNome());
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    private Spinner criaSpinnerDeProcessos() {
        ProcessoDAO processoDAO = new ProcessoDAO(this);
        List<Processo> processos = processoDAO.buscaProcessos();
        processoDAO.close();
        ArrayAdapter<Processo> adapter = new ArrayAdapter<Processo>(this, R.layout.sp_cadastro_programa_processo, processos);
        Spinner spinner = new Spinner(this);
        spinner.setAdapter(adapter);
        return spinner;
    }
    
    public void insereNome(View view) {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(R.string.alert_titulo_nome_programa)
                .setMessage(R.string.alert_msg_programa)
                .setView(editText)
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        programa.setNome(editText.getText().toString());
                        tv_nome.setText(programa.getNome());
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void insereCiclos(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.np_cadastro_programa_ciclos, null);
        final NumberPicker numberPicker = v.findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);
        
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(R.string.alerto_title_ciclos)
                .setMessage(R.string.alert_msg_ciclos)
                .setView(numberPicker)
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        programa.setCiclos(numberPicker.getValue());
                        tv_ciclos.setText(String.valueOf(programa.getCiclos()));
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void insereValorNominal(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.et_cadastro_programa_nominal, null);
        final EditText editText = v.findViewById(R.id.et_programa_nominal);
        editText.addTextChangedListener(new MascaraWatcher(getString(R.string.mascara_valor_nominal)));
        
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(R.string.alert_titulo_nominal)
                .setMessage(R.string.alert_msg_nominal)
                .setView(editText)
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String valor = editText.getText().toString();
                        programa.setValorNominal(Float.parseFloat(valor));
                        String exibicao = getString(R.string.unidade_newton_metro, programa.getValorNominal());
                        tv_nominal.setText(exibicao);
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void insereAngulo(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.et_cadastro_programa_angulo, null);
        final EditText editText = v.findViewById(R.id.et_programa_angulo);
        
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(R.string.alert_titulo_angulo)
                .setMessage(R.string.alert_msg_angulo)
                .setView(editText)
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String valor = editText.getText().toString();
                        programa.setAngulo(Integer.parseInt(valor));
                        String exibicao = getString(R.string.unidade_grau, programa.getAngulo());
                        tv_angulo.setText(exibicao);
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void inserirPrograma(View view){
        ProgramaDAO dao = new ProgramaDAO(this);
        dao.insere(programa);
        dao.close();
    }
}