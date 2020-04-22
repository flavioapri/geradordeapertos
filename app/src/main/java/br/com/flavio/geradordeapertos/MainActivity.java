package br.com.flavio.geradordeapertos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent();
    }
    
    /**
     * Abre menu superior
     *
     * @param view Botão que exibe o menu.
     */
    public void mostraMenuSuperior(View view) {
        PopupMenu popup = getPopupMenu(view, R.menu.menu_main);
        
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mi_cadastrar:
                        intent.setClass(MainActivity.this, Cadastro.class);
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
     * Exibe o menu para seleção do tipo de geração de apertos e chama a activity do formulário correspondente.
     *
     * @param view Botão de adicionar
     */
    public void mostraMenuNovo(View view) {
        PopupMenu popup = getPopupMenu(view, R.menu.menu_novo);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mi_programa_individual:
                        intent.setClass(MainActivity.this, FormularioProgramaIndividual.class);
                        break;
                    case R.id.mi_programa_lote:
                        intent.setClass(MainActivity.this, FormularioProgramaLote.class);
                        break;
                }
                startActivity(intent);
                return false;
            }
        });
        popup.show();
    }
    
    /**
     * Cria um popup para exibição dos menus
     *
     * @param view   O botão que foi clicado
     * @param idMenu ID do menu correspondente
     * @return popup criado
     */
    private PopupMenu getPopupMenu(View view, int idMenu) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(idMenu, popup.getMenu());
        return popup;
    }
}
