package br.com.flavio.geradordeapertos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import br.com.flavio.geradordeapertos.adapter.EmissorMensagem;
import br.com.flavio.geradordeapertos.dao.ApertadeiraDAO;
import br.com.flavio.geradordeapertos.dao.MotivoDAO;
import br.com.flavio.geradordeapertos.dao.ProcessoDAO;
import br.com.flavio.geradordeapertos.dao.RegistroDAO;
import br.com.flavio.geradordeapertos.mascara.Mascara;
import br.com.flavio.geradordeapertos.modelo.Apertadeira;
import br.com.flavio.geradordeapertos.modelo.Motivo;
import br.com.flavio.geradordeapertos.modelo.Processo;
import br.com.flavio.geradordeapertos.modelo.Registro;

public class GeradorRegistroProcesso extends BaseActivity {
    private EditText et_np;
    private TextView tv_data;
    private Spinner sp_apertadeiras;
    private Spinner sp_processos;
    private Motivo motivo;
    private Apertadeira apertadeira;
    private Processo processo;
    private List<CheckBox> ciclos;
    private List<Registro> registros;
    private List<String> valoresRegistros;
    private static final int ACTIVITY_LEITURA = 1;
    private TextView tv_ciclos;
    private Spinner sp_motivos;
    private String valores;
    private Registro registro;
    private Button bt_ciclos;
    private LinearLayout grupoCheckBoxes;
    private boolean porProcesso;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gera_registro_processo);
        
        et_np = findViewById(R.id.et_gera_registro_np);
        tv_data = findViewById(R.id.tv_gera_registro_data);
        tv_ciclos = findViewById(R.id.tv_gera_registro_ciclos);
        sp_apertadeiras = findViewById(R.id.sp_gera_registro_apertadeira);
        sp_processos = findViewById(R.id.sp_gera_registro_processo);
        sp_motivos = findViewById(R.id.sp_gera_registro_motivo);
        et_np.addTextChangedListener(Mascara.insert(Mascara.MASCARA_NP, et_np));
        bt_ciclos = findViewById(R.id.bt_gera_registro_ciclos);
        porProcesso = true;
        
        RadioGroup rg_tipo = findViewById(R.id.rg_gera_registro_tipo);
        rg_tipo.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_gera_registro_tipo_processo:
                    sp_processos.setEnabled(true);
                    bt_ciclos.setEnabled(true);
                    porProcesso = true;
                    break;
                case R.id.rb_gera_registro_tipo_apertadeira:
                    sp_processos.setEnabled(false);
                    bt_ciclos.setEnabled(false);
                    porProcesso = false;
                    break;
            }
        });
        // Inicializada o formulário com a data atual
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date dataAtual = new Date();
        String dataFormatada = formataData.format(dataAtual);
        tv_data.setText(dataFormatada);
        
        carregaApertadeiras();
        carregaMotivos();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Desabilita a opção da activity no menu
        menu.findItem(R.id.mi_gerar_processo).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
    
    public void carregaApertadeiras() {
        ApertadeiraDAO dao = new ApertadeiraDAO(this);
        final List<Apertadeira> apertadeiras = dao.buscaApertadeiras();
        // Cria objeto com o valor padrão para o spinner exibir
        Apertadeira valorPadraoNome = new Apertadeira();
        valorPadraoNome.setNome("Apertadeira");
        apertadeiras.add(0, valorPadraoNome);
        dao.close();
        ArrayAdapter<Apertadeira> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, apertadeiras);
        adapter.setDropDownViewResource(R.layout.textview_spinner);//TODO renomear este layout desta fonte
        sp_apertadeiras.setAdapter(adapter);
        sp_apertadeiras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                apertadeira = apertadeiras.get(position);
                carregaProcessos(apertadeira);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    
    private void carregaProcessos(Apertadeira apertadeira) {
        ProcessoDAO dao = new ProcessoDAO(this);
        final List<Processo> processos = dao.buscaProcessos(apertadeira);
        Processo valorPadraoNome = new Processo();
        valorPadraoNome.setNome("Processo");
        processos.add(0, valorPadraoNome);
        dao.close();//TODO tratar quando a lista de processos estiver vazia
        ArrayAdapter<Processo> adapter = new ArrayAdapter<Processo>(this, android.R.layout.simple_spinner_item, processos);
        adapter.setDropDownViewResource(R.layout.textview_spinner);
        sp_processos.setAdapter(adapter);
        sp_processos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                processo = processos.get(position);
                grupoCheckBoxes = criaGrupoCheckBoxes();
                verificaCheckBoxesSelecionados(grupoCheckBoxes);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    
    public void selecionaCiclos(View view) {
        grupoCheckBoxes = criaGrupoCheckBoxes();
        
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_titulo_registro_ciclos)
                .setMessage(R.string.alert_msg_registro_ciclos)
                .setView(grupoCheckBoxes)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> verificaCheckBoxesSelecionados(grupoCheckBoxes))
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    
    private LinearLayout criaGrupoCheckBoxes() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.checkboxes_ciclos, null);
        //TODO tornar Linear Layout global e extrair criação para outro método
        for (int cont = 9; cont >= processo.getCiclos(); cont--) {
            layout.removeViewAt(cont);
        }
        return layout;
    }
    
    private void verificaCheckBoxesSelecionados(LinearLayout grupoCheckBoxes) {
        StringBuilder s = new StringBuilder();//TODO tentar utilizar string builder no projeto
        ciclos = new ArrayList<>();
        for (int cont = 0; cont < grupoCheckBoxes.getChildCount(); cont++) {
            View view = grupoCheckBoxes.getChildAt(cont);
            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    ciclos.add(checkBox);
                    s.append(checkBox.getText()).append("    ");//TODO solução mais elegante para esta string
                }
            }
        }
        tv_ciclos.setText(s.toString());
    }
    
    public void carregaMotivos() {
        MotivoDAO dao = new MotivoDAO(this);
        final List<Motivo> motivos = dao.buscaMotivos();
        dao.close();//TODO tratar quando a lista de apertadeiras esta vazia
        Motivo valorPadraoNome = new Motivo();
        valorPadraoNome.setNome("Motivo");
        motivos.add(0, valorPadraoNome);
        ArrayAdapter<Motivo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, motivos);
        adapter.setDropDownViewResource(R.layout.textview_spinner);//TODO renomear layout desta fonte
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(GeradorRegistroProcesso.this,
                (datePicker, ano1, mes1, dia1) -> {
                    calendario.set(ano1, mes1, dia1);
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
    
    public void geraRegistro(View view) {
        if (porProcesso)
            geraRegistroPorProcesso();
        else
            geraRegistroPorApertadeira();
    }
    
    public void geraRegistroPorProcesso() {
        if (verificaFormulario()) {
            if (confirmaRegistro()) {
                valores = "";
                double torque;
                for (CheckBox ciclo : ciclos) {
                    registro = new Registro();
                    registro.setProcesso(processo);
                    registro.setNP(et_np.getText().toString());
                    registro.setCiclo(ciclo.getText().toString());
                    registro.setMotivo(motivo);
                    registro.setData(tv_data.getText().toString());
                    torque = geraTorque(processo.getValorNominal());
                    registro.setValor(torque);
                    gravaRegistro(registro);
                    // Seta a data que não esta formatada com a data formatada para utilizar o registro para envio
                    registro.setData(registro.getDataComMascara());
                    valores += "Ciclo " + registro.getCiclo() + ": " + registro.getValor() + "\n";
                }
            }
        }
    }
    
    private boolean confirmaRegistro() {
        final boolean[] confirmado = {true};// Array porque só assim da para setar valor do onclick confirmar
        
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_titulo_gravar_registro)
                .setMessage(R.string.alert_msg_gravar_registro)
                .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                    confirmado[0] = true;
                    limpa();
                    new AlertDialog.Builder(GeradorRegistroProcesso.this)
                            .setMessage(R.string.alert_confirmacao_gravar_registro)
                            .show();
                    if (porProcesso)
                        EmissorMensagem.enviaMensagem(this, registro, valores);
                    else
                        EmissorMensagem.enviaMensagem(this, registros, valoresRegistros);
                })
                .setNegativeButton(R.string.cancelar, (dialog, which) -> confirmado[0] = false)
                .show();
        return confirmado[0];
    }
    
    private void gravaRegistro(Registro registro) {
        RegistroDAO dao = new RegistroDAO(this);
        dao.insere(registro);
        dao.close();
    }
    
    private boolean verificaFormulario() {
        //TODO verificar se não estão selecionados os valores padrão dos spinners
        String np = et_np.getText().toString().trim();
        boolean preenchidoProcesso =
                (!(np.length() < 11 || ciclos == null || ciclos.isEmpty() || motivo.getNome() == "Motivo" || processo.getNome() ==
                        "Processo" || apertadeira.getNome() == "Apertadeira"));
        boolean preenchidoApertadeira = (!(np.length() < 11 || motivo.getNome() == "Motivo" || apertadeira.getNome() == "Apertadeira"));
        if (!(preenchidoApertadeira || preenchidoProcesso)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.ad_titulo_gera_registro_incompleto)
                    .setMessage(R.string.ad_msg_gera_registro_incompleto)
                    .setPositiveButton(R.string.ok, (dialog, which) -> { })
                    .show();
            return false;
        }
        return true;
    }
    
    private double geraTorque(double valorNominal) {
        double taxaTolerancia = 0.013;//TODO implementar através das configurações
        double tolerancia = valorNominal * taxaTolerancia;
        double limiteSuperior = valorNominal + tolerancia;
        double limiteInferior = valorNominal - tolerancia;
        
        return ThreadLocalRandom.current().nextDouble(limiteInferior, limiteSuperior);
    }
    
    private void geraRegistroPorApertadeira() {
        if (verificaFormulario()) {
            if (confirmaRegistro()) {
                valores = "";
                double torque;
                ProcessoDAO dao = new ProcessoDAO(this);
                List<Processo> processos = dao.buscaProcessos(apertadeira);
                dao.close();
                registros = new ArrayList<>();
                valoresRegistros = new ArrayList<>();
                for (Processo processo : processos) {
                    for (int cont = 0; cont < processo.getCiclos(); cont++) {
                        registro = new Registro();
                        registro.setProcesso(processo);
                        registro.setNP(et_np.getText().toString());
                        registro.setCiclo(cont + 1);
                        registro.setMotivo(motivo);
                        registro.setData(tv_data.getText().toString());
                        torque = geraTorque(processo.getValorNominal());
                        registro.setValor(torque);
                        gravaRegistro(registro);
                        // Seta a data que não esta formatada com a data formatada para utilizar o registro para envio
                        registro.setData(registro.getDataComMascara());
                        valores += "Ciclo " + registro.getCiclo() + ": " + registro.getValor() + "\n";
                        if (!registros.contains(registro))
                            registros.add(registro);
                    }
                    valoresRegistros.add(valores);
                    valores = "";
                }
                
                for (int cont = 0; cont < registros.size(); cont++) {
                    System.out.println(registros.get(cont));
                    System.out.println(valoresRegistros.get(cont));
                }
            }
        }
    }
    
    public void limpa() {
        et_np.setText("");
        carregaApertadeiras();
        tv_ciclos.setText("");
        carregaMotivos();
        if (ciclos != null)
            ciclos.clear();
    }
    
    public void botaoLimpa(View view) {
        limpa();
    }
}


