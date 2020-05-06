package br.com.flavio.geradordeapertos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.mi_gerar_programa:
                intent.setClass(this, GeraRegistro.class);
                break;
            case R.id.mi_gerar_grupo:
                intent.setClass(this, GeraRegistroGrupo.class);
                break;
            case R.id.mi_processo:
                intent.setClass(this, CadastroProcesso.class);
                break;
            case R.id.mi_programa:
                intent.setClass(this, CadastroPrograma.class);
                break;
            case R.id.mi_motivo:
                intent.setClass(this, CadastroMotivo.class);
                break;
            case R.id.mi_historico:
                intent.setClass(this, Historico.class);
                break;
            case R.id.mi_configuracoes:
                intent.setClass(this, Configuracoes.class);
                break;
            case R.id.mi_sobre:
                intent.setClass(this, Sobre.class);
                break;
            
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
