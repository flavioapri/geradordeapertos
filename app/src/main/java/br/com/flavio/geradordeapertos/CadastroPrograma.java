package br.com.flavio.geradordeapertos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import br.com.flavio.geradordeapertos.dao.ApertadeiraDAO;
import br.com.flavio.geradordeapertos.dao.ProgramaDAO;
import br.com.flavio.geradordeapertos.helper.ProgramaHelper;
import br.com.flavio.geradordeapertos.mascara.Mascara;
import br.com.flavio.geradordeapertos.modelo.Apertadeira;
import br.com.flavio.geradordeapertos.modelo.Programa;

public class CadastroPrograma  extends BaseActivity{
    private TextView tv_apertadeira;
    private TextView tv_nome;
    private TextView tv_ciclos;
    private TextView tv_nominal;
    private TextView tv_angulo;
    private Apertadeira apertadeira;
    private Programa programa;
    private ProgramaHelper helper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_programa);
        
        tv_apertadeira = findViewById(R.id.tv_cadastro_programa_apertadeira);
        tv_nome = findViewById(R.id.tv_cadastro_programa_nome);
        tv_ciclos = findViewById(R.id.tv_cadastro_programa_ciclos);
        tv_nominal = findViewById(R.id.tv_cadastro_programa_nominal);
        tv_angulo = findViewById(R.id.tv_cadastro_programa_angulo);
       
        
        helper = new ProgramaHelper(this);
        
        Intent intent = getIntent();
        programa = (Programa) intent.getSerializableExtra("programa");
        if (programa != null) {
            helper.preencheFormulario(programa);
        } else
            programa = new Programa();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    public void carregaApertadeiras(View view) {
        final Spinner spinner = criaSpinnerDeApertadeiras();
        new AlertDialog.Builder(this)
                .setTitle(R.string.ad_titulo_cadastrar_programa_apertadeira)
                .setMessage(R.string.ad_msg_cadastrar_programa_apertadeira)
                .setView(spinner)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    apertadeira = (Apertadeira) spinner.getSelectedItem();
                    programa.setApertadeira(apertadeira);
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
                .setTitle(R.string.alert_titulo_nome_programa)
                .setMessage(R.string.alert_msg_programa)
                .setView(editText)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    String nome = editText.getText().toString();
                    programa.setNome(nome);
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
                    programa.setCiclos(numberPicker.getValue());
                    tv_ciclos.setText(String.valueOf(programa.getCiclos()));
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void insereValorNominal(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.editext_nominal, null);
        final EditText editText = v.findViewById(R.id.et_programa_nominal);
        editText.addTextChangedListener(Mascara.insert(Mascara.MASCARA_TORQUE, editText));
        
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_titulo_nominal)
                .setMessage(R.string.alert_msg_nominal)
                .setView(editText)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    String valor = editText.getText().toString();
                    programa.setValorNominal(valor);
                    String exibicao = getString(R.string.unidade_newton_metro, programa.getValorNominal());
                    tv_nominal.setText(exibicao);
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void insereAngulo(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.edittext_angulo, null);
        final EditText editText = v.findViewById(R.id.et_programa_angulo);
        
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_titulo_angulo)
                .setMessage(R.string.alert_msg_angulo)
                .setView(editText)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    String valor = editText.getText().toString();
                    programa.setAngulo(valor);
                    String exibicao = getString(R.string.unidade_grau, programa.getAngulo());
                    tv_angulo.setText(exibicao);
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    public void inserePrograma(View view) {
        if (programa.isPreenchido()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.alert_programa_titulo_salvar)
                    .setMessage(R.string.alert_programa_msg_salvar)
                    .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                        ProgramaDAO dao = new ProgramaDAO(CadastroPrograma.this);
                        if (programa.getId() != 0)
                            dao.altera(programa);
                        else
                            dao.insere(programa);
                        dao.close();
                        limpaFormulario();
                        new AlertDialog.Builder(CadastroPrograma.this)
                                .setMessage(R.string.msg_programa_salvo)
                                .show();
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
        } else {
            new AlertDialog.Builder(CadastroPrograma.this)
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
        programa = new Programa();
    }
    
    public void vaiParaHome(View view) {
        startActivity(new Intent(this, Main.class));
    }
    
}