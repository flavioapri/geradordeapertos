package br.com.flavio.geradordeapertos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity base com os métodos para criação do menu da ActionBar. As outras Activitys herdando faz com que evite se repetir o código em
 * todas elas.
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.mi_gerar_registro:
            case R.id.mi_cadastro:
                intent = null; // Se os itens que abrem os submenus forem selecionados intent aponta para nulo
                break;
            case R.id.mi_inicio:
                intent.setClass(this, Main.class);
                break;
            case R.id.mi_gerar_processo:
                intent.setClass(this, GeradorRegistroProcesso.class);
                break;
            case R.id.mi_apertadeira:
                intent.setClass(this, CadastroApertadeira.class);
                break;
            case R.id.mi_processo:
                intent.setClass(this, RelacaoProcessos.class);
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
        if (!(intent == null)) //Se o item selecionado for o que abre o submenu não tenta iniciar activity
            startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    
    public void gotoNovoRegistro(View view) {
        Intent intent = new Intent().setClass(this, GeradorRegistroProcesso.class);
        startActivity(intent);
    }
}
