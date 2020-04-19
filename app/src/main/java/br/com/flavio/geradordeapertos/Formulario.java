package br.com.flavio.geradordeapertos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Formulario extends AppCompatActivity {
    private EditText et_np;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        
        et_np = findViewById(R.id.et_formulario_np);
        
        Intent intentLeitura = getIntent();
        if (intentLeitura != null) {
            Bundle parametros = intentLeitura.getExtras();
            if (parametros != null) {
                String npLido = parametros.getString("npLido");
                et_np.setText(npLido);
            }
        }
    }
    
    /**
     * Devolve o fluxo novamente para a activity de leitura pra que um código seja lido novamente
     * @param view Botão que dispara o método
     */
    public void relerCodigo(View view) {
        Intent intent = new Intent(Formulario.this, Leitura.class);
        startActivity(intent);
    }
}
