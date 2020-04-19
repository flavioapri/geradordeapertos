package br.com.flavio.geradordeapertos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent();
    }
    
    /**
     * Cria um popup para exibir o menu.
     *
     * @param view Botão que exibe o menu.
     */
    public void mostraMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, popup.getMenu());
        
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mi_cadastrar:
                        intent.setClass(MainActivity.this, CadastroAperto.class);
                        break;
                    case R.id.mi_configuracoes:
                        intent.setClass(MainActivity.this, Configuracoes.class);
                        break;
                    case R.id.mi_historico:
                        intent.setClass(MainActivity.this, Historico.class);
                        break;
                    case R.id.mi_sobre:
                        intent.setClass(MainActivity.this, Sobre.class);
                        break;
                }
                startActivity(intent);
                return false;
            }
        });
        popup.show();
    }
    
    /**
     * Fluxo vai para a activity que realiza a leitura do código de barras
     *
     * @param view Botão que executa a ação. (bt_novo)
     */
    public void vaiParaLeitura(View view){
        intent.setClass(MainActivity.this, Leitura.class);
        startActivity(intent);
    }
}
