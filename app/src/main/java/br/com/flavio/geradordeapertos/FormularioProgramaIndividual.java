package br.com.flavio.geradordeapertos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import br.com.flavio.geradordeapertos.dao.MotivoDAO;
import br.com.flavio.geradordeapertos.dao.ProcessoDAO;
import br.com.flavio.geradordeapertos.dao.ProgramaDAO;
import br.com.flavio.geradordeapertos.dao.RegistroDAO;
import br.com.flavio.geradordeapertos.mascara.Mascara;
import br.com.flavio.geradordeapertos.modelo.Motivo;
import br.com.flavio.geradordeapertos.modelo.Processo;
import br.com.flavio.geradordeapertos.modelo.Programa;
import br.com.flavio.geradordeapertos.modelo.Registro;

public class FormularioProgramaIndividual extends AppCompatActivity {
    private EditText et_np;
    private TextView tv_data;
    private Spinner sp_processos;
    private Spinner sp_programas;
    private Motivo motivo;
    private Processo processo;
    private Programa programa;
    private List<CheckBox> ciclos;
    private static final int ACTIVITY_LEITURA = 1;
    private TextView tv_ciclos;
    private Spinner sp_motivos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_individual);
        
        et_np = findViewById(R.id.et_formulario_np);
        tv_data = findViewById(R.id.tv_formulario_data);
        tv_ciclos = findViewById(R.id.tv_formulario_ciclos);
        sp_processos = findViewById(R.id.sp_formulario_processo);
        sp_programas = findViewById(R.id.sp_formulario_programa);
        sp_motivos = findViewById(R.id.sp_formulario_motivo);
        et_np.addTextChangedListener(Mascara.insert(Mascara.MASCARA_NP, et_np));
        // Inicializada o formulário com a data atual
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date dataAtual = new Date();
        String dataFormatada = formataData.format(dataAtual);
        tv_data.setText(dataFormatada);
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        carregaProcessos();
        carregaMotivos();
    }
    
    public void carregaProcessos() {
        ProcessoDAO dao = new ProcessoDAO(this);
        final List<Processo> processos = dao.buscaProcessos();
        dao.close();//TODO tratar quando a lista de processos esta vazia
        ArrayAdapter<Processo> adapter = new ArrayAdapter<Processo>(this, android.R.layout.simple_spinner_item, processos);
        adapter.setDropDownViewResource(R.layout.sp_cadastro_programa_processo);//TODO renomear este layout desta fonte
        sp_processos.setAdapter(adapter);
        sp_processos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                processo = processos.get(position);
                carregaProgramas(processo);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    
    private void carregaProgramas(Processo processo) {
        ProgramaDAO dao = new ProgramaDAO(this);
        final List<Programa> programas = dao.buscaProgramas(processo);
        dao.close();//TODO tratar quando a lista de programas estiver vazia
        ArrayAdapter<Programa> adapter = new ArrayAdapter<Programa>(this, android.R.layout.simple_spinner_item, programas);
        adapter.setDropDownViewResource(R.layout.sp_cadastro_programa_processo);
        sp_programas.setAdapter(adapter);
        sp_programas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                programa = programas.get(position);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    
    public void selecionaCiclos(View view) {
        final LinearLayout grupoCheckBoxes = criaGrupoCheckBoxes();
        
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(R.string.alert_titulo_registro_ciclos)
                .setMessage(R.string.alert_msg_registro_ciclos)
                .setView(grupoCheckBoxes)
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        verificaCheckBoxesSelecionados(grupoCheckBoxes);
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    private LinearLayout criaGrupoCheckBoxes() {
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        
        LinearLayout.LayoutParams parametrosCheckBox = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        parametrosCheckBox.setMargins(0, 0, 0, 20);
        
        for (int cont = 0; cont < programa.getCiclos(); cont++) {
            checkBoxes.add(new CheckBox(this));
            checkBoxes.get(cont).setText(Integer.toString((cont + 1)));
            checkBoxes.get(cont).setScaleY(1.5f);
            checkBoxes.get(cont).setScaleX(1.5f);
            checkBoxes.get(cont).setChecked(true);
            checkBoxes.get(cont).setLayoutParams(parametrosCheckBox);
            layout.addView(checkBoxes.get(cont));
        }
        return layout;
    }
    
    private void verificaCheckBoxesSelecionados(LinearLayout grupoCheckBoxes) {
        ciclos = new ArrayList<>();
        StringBuilder ciclos = new StringBuilder();//TODO tentar utilizar strinf builder no projeto
        for (int cont = 0; cont < grupoCheckBoxes.getChildCount(); cont++) {
            View view = grupoCheckBoxes.getChildAt(cont);
            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    this.ciclos.add(checkBox);
                    ciclos.append(checkBox.getText()).append("    ");//TODO solução mais elegante para esta string
                }
            }
        }
        tv_ciclos.setText(ciclos.toString());
    }
    
    public void carregaMotivos() {
        MotivoDAO dao = new MotivoDAO(this);
        final List<Motivo> motivos = dao.buscaMotivos();
        dao.close();//TODO tratar quando a lista de processos esta vazia
        ArrayAdapter<Motivo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, motivos);
        adapter.setDropDownViewResource(R.layout.sp_motivos);//TODO renomear layout desta fonte
        sp_motivos.setAdapter(adapter);//TODO implementar "Sem motivo" no spinner
        sp_motivos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                motivo = motivos.get(position);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    
    /**
     * Mostra um calendario em um dialog para que seja selecionada uma data
     *
     * @param view
     */
    public void mostrarCalendario(View view) {
        final Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        //TODO tentar ocultar o teclado
        DatePickerDialog datePickerDialog = new DatePickerDialog(FormularioProgramaIndividual.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        calendario.set(ano, mes, dia);
                        String formato = getString(R.string.formato_data);
                        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.ENGLISH);
                        Date data;
                        try {
                            data = sdf.parse(sdf.format(calendario.getTime()));
                            String sDia = new SimpleDateFormat(getString(R.string.formato_dia), Locale.ENGLISH).format(data);
                            String sMes = new SimpleDateFormat(getString(R.string.formato_mes), Locale.ENGLISH).format(data);
                            String sAno = new SimpleDateFormat(getString(R.string.formato_ano), Locale.ENGLISH).format(data);
                            tv_data.setText((sDia + getString(R.string.formato_separador) + sMes + getString(R.string.formato_separador) + sAno));
                        } catch (ParseException ignored) {
                        }
                    }
                }, ano, mes, dia);
        datePickerDialog.show();
        datePickerDialog.getDatePicker();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pega os dados da intent da activity Leitura e extrai o número do NP
        if (requestCode == ACTIVITY_LEITURA)
            if (resultCode == Activity.RESULT_OK)
                et_np.setText(data.getStringExtra("np"));
    }
    
    /**
     * Devolve o fluxo novamente para a activity de leitura para que o código seja lido novamente
     *
     * @param view Botão que dispara o método
     */
    public void leCodigo(View view) {
        Intent intentLeitura = new Intent(this, Leitura.class);
        startActivityForResult(intentLeitura, ACTIVITY_LEITURA);
    }
    
    public void vaiParaHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    
    public void geraRegistro(View view) {
        if (verificaFormulario()) {
            if (confirmaRegistro()) {
                double torque;
                for (CheckBox ciclo : ciclos) {
                    Registro registro = new Registro();
                    registro.setPrograma(programa);
                    registro.setNP(et_np.getText().toString());
                    registro.setCiclo(ciclo.getText().toString());
                    registro.setMotivo(motivo);
                    registro.setData(tv_data.getText().toString());
                    torque = geraTorque(programa.getValorNominal());
                    registro.setValor(torque);
                    Log.d("valores", String.valueOf(registro));
                    gravaRegistro(registro);
                }
            }
        }
    }
    
    private boolean confirmaRegistro() {
        final boolean[] confirmado = {true};// Array porque só assim da para setar valor do onclick confirmar
        
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(R.string.alert_titulo_gravar_registro)
                .setMessage(R.string.alert_msg_gravar_registro)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    confirmado[0] = true;
                    Log.d("confirmado1", String.valueOf(confirmado[0]));
                    limpa();
                    new AlertDialog.Builder(FormularioProgramaIndividual.this, R.style.AlertDialog)
                            .setMessage(R.string.alert_confirmacao_gravar_registro)
                            .show();
                })
                .setNegativeButton(R.string.cancelar, (dialog, which) -> confirmado[0] = false)
                .show();
        Log.d("confirmado2", String.valueOf(confirmado[0]));
        return confirmado[0];
    }
    
    private void gravaRegistro(Registro registro) {
        RegistroDAO dao = new RegistroDAO(this);
        dao.insere(registro);
        dao.close();
    }
    
    private boolean verificaFormulario() {
        String np = et_np.getText().toString().trim();
        
        if (np.length() < 11 | ciclos.isEmpty()) {
            new AlertDialog.Builder(this, R.style.AlertDialog)
                    .setTitle(R.string.alert_formulario_titulo_incompleto)
                    .setMessage(R.string.alert_formulario_msg_incompleto)
                    .setPositiveButton(R.string.ok, (dialog, which) -> { })
                    .show();
            return false;
        }
        return true;
    }
    
    private double geraTorque(double valorNominal) {
        double taxaTolerancia = 0.05;//TODO implementar através das configurações
        double tolerancia = valorNominal * taxaTolerancia;
        double limiteSuperior = valorNominal + tolerancia;
        double limiteInferior = valorNominal - tolerancia;
        
        return ThreadLocalRandom.current().nextDouble(limiteInferior, limiteSuperior);
    }
    
    public void limpa() {
        et_np.setText("");
        carregaProcessos();
        tv_ciclos.setText("");
        ciclos.clear();
    }
    
    public void botaoLimpa(View view) {
        limpa();
    }
    
}


