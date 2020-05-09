package br.com.flavio.geradordeapertos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import br.com.flavio.geradordeapertos.dao.ApertadeiraDAO;
import br.com.flavio.geradordeapertos.dao.ProcessoDAO;
import br.com.flavio.geradordeapertos.helper.ProcessoHelper;
import br.com.flavio.geradordeapertos.mascara.Mascara;
import br.com.flavio.geradordeapertos.modelo.Apertadeira;
import br.com.flavio.geradordeapertos.modelo.Processo;

public class CadastroProcesso extends BaseActivity {
    private TextView tv_apertadeira;
    private TextView tv_nome;
    private TextView tv_ciclos;
    private TextView tv_nominal;
    private TextView tv_angulo;
    private Apertadeira apertadeira;
    private Processo processo;
    private ProcessoHelper helper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_processo);
        
        tv_apertadeira = findViewById(R.id.tv_cadastro_processo_apertadeira);
        tv_nome = findViewById(R.id.tv_cadastro_processo_nome);
        tv_ciclos = findViewById(R.id.tv_cadastro_processo_ciclos);
        tv_nominal = findViewById(R.id.tv_cadastro_processo_nominal);
        tv_angulo = findViewById(R.id.tv_cadastro_processo_angulo);
        
        helper = new ProcessoHelper(this);
        
        Intent intent = getIntent();
        processo = (Processo) intent.getSerializableExtra("processo");
        if (processo != null) {
            helper.preencheFormulario(processo);
        } else
            processo = new Processo();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Desabilita a opção da activity no menu
        menu.findItem(R.id.mi_processo).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
    
    public void carregaApertadeiras(View view) {
        final Spinner spinner = criaSpinnerDeApertadeiras();
        new AlertDialog.Builder(this)
                .setTitle(R.string.ad_titulo_cadastrar_processo_apertadeira)
                .setMessage(R.string.ad_msg_cadastrar_processo_apertadeira)
                .setView(spinner)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    apertadeira = (Apertadeira) spinner.getSelectedItem();
                    processo.setApertadeira(apertadeira);
                    tv_apertadeira.setText(apertadeira.getNome());
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    private Spinner criaSpinnerDeApertadeiras() {
        ApertadeiraDAO apertadeiraDAO = new ApertadeiraDAO(this);
        List<Apertadeira> apertadeiras = apertadeiraDAO.buscaApertadeiras();
        //TODO tratar quando a lista de apertadeiras esta vazia
        apertadeiraDAO.close();
        ArrayAdapter<Apertadeira> adapter = new ArrayAdapter<Apertadeira>(this, R.layout.textview_spinner, apertadeiras);
        Spinner spinner = new Spinner(this);
        spinner.setAdapter(adapter);
        return spinner;
    }
    
    public void insereNome(View view) {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(R.string.ad_titulo_cadastrar_processo_nome)
                .setMessage(R.string.ad_msg_cadastrar_processo_nome)
                .setView(editText)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    String nome = editText.getText().toString();
                    processo.setNome(nome);
                    tv_nome.setText(nome);
                    //TODO forçar usuário a inserir nome
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void insereCiclos(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.numberpicker_ciclos, null);
        final NumberPicker numberPicker = v.findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        
        new AlertDialog.Builder(this)
                .setTitle(R.string.alerto_title_ciclos)
                .setMessage(R.string.alert_msg_ciclos)
                .setView(numberPicker)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    processo.setCiclos(numberPicker.getValue());
                    tv_ciclos.setText(String.valueOf(processo.getCiclos()));
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void insereValorNominal(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.edittext_nominal, null);
        final EditText editText = v.findViewById(R.id.et_processo_nominal);
        editText.addTextChangedListener(Mascara.insert(Mascara.MASCARA_TORQUE, editText));
        
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_titulo_nominal)
                .setMessage(R.string.alert_msg_nominal)
                .setView(editText)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    String valor = editText.getText().toString();
                    processo.setValorNominal(valor);
                    String exibicao = getString(R.string.unidade_newton_metro, processo.getValorNominal());
                    tv_nominal.setText(exibicao);
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void insereAngulo(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.edittext_angulo, null);
        final EditText editText = v.findViewById(R.id.et_processo_angulo);
        
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_titulo_angulo)
                .setMessage(R.string.alert_msg_angulo)
                .setView(editText)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    String valor = editText.getText().toString();
                    processo.setAngulo(valor);
                    String exibicao = getString(R.string.unidade_grau, processo.getAngulo());
                    tv_angulo.setText(exibicao);
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void insereProcesso(View view) {
        if (processo.isPreenchido()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.ad_titulo_cadastrar_processo_inserir)
                    .setMessage(R.string.ad_msg_cadastrar_processo_inserir)
                    .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                        ProcessoDAO dao = new ProcessoDAO(CadastroProcesso.this);
                        if (processo.getId() != 0)
                            dao.altera(processo);
                        else
                            dao.insere(processo);
                        dao.close();
                        limpaFormulario();
                        new AlertDialog.Builder(CadastroProcesso.this)
                                .setMessage(R.string.msg_processo_salvo)
                                .show();
                        Intent intent = new Intent().setClass(this, RelacaoProcessos.class);
                        startActivity(intent);
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
        } else {
            new AlertDialog.Builder(CadastroProcesso.this)
                    .setMessage(R.string.msg_formulario_incompleto)
                    .show();
        }
    }
    
    public void limpaFormulario() {
        tv_apertadeira.setText(R.string.string_valor_vazio);
        tv_nome.setText(R.string.string_valor_vazio);
        tv_ciclos.setText(R.string.string_valor_vazio);
        tv_nominal.setText(R.string.string_valor_vazio);
        tv_angulo.setText(R.string.string_valor_vazio);
        processo = new Processo();
    }
}