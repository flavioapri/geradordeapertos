package br.com.flavio.geradordeapertos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Cadastro extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }
    
    /**
     * Chama a acitivity correspondente
     * @param view
     */
    public void vaiParaActivity(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.bt_cadastro_processo:
                intent.setClass(this, CadastroProcesso.class);
                break;
//            case R.id.bt_cadastro_motivo:
//                intent.setClass(this, CadastroMotivo.class);
//                break;
//            case R.id.bt_cadastro_programa:
//                intent.setClass(this, CadastroPrograma.class);
//                break;
            default:
                intent.setClass(this, MainActivity.class);
                break;
        }
        startActivity(intent);
    }
}
