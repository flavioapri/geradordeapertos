package br.com.flavio.geradordeapertos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormularioProgramaIndividual extends AppCompatActivity {
    private EditText et_np;
    private EditText et_data;
    private static final int ACTIVITY_LEITURA = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_individual);
        
        et_np = findViewById(R.id.et_formulario_np);
        et_data = findViewById(R.id.et_formulario_data);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(FormularioProgramaIndividual.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                calendario.set(ano, mes, dia);
                String formato = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.ENGLISH);
                Date data;
                try {
                    data = sdf.parse(sdf.format(calendario.getTime()));
                    String sDia = new SimpleDateFormat("dd", Locale.ENGLISH).format(data);
                    String sMes = new SimpleDateFormat("MM", Locale.ENGLISH).format(data);
                    String sAno = new SimpleDateFormat("yyyy", Locale.ENGLISH).format(data);
                    et_data.setText((sDia + "/" + sMes + "/" + sAno));
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
    
}


