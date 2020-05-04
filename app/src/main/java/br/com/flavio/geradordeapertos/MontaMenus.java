package br.com.flavio.geradordeapertos;

import android.content.Context;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

public class MontaMenus {
    /**
     * Monta e exibe o menu superior
     *
     * @param view Botão que exibe o menu.
     */
    public static void mostra(View view, Context contexto) {
        Intent intent = new Intent();
        PopupMenu popup = getPopup(view, R.menu.menu_main, contexto);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.mi_cadastrar:
                    intent.setClass(contexto, Cadastro.class);
                    break;
                case R.id.mi_configuracoes:
                    intent.setClass(contexto, Configuracoes.class);
                    break;
                case R.id.mi_historico:
                    intent.setClass(contexto, Historico.class);
                    break;
                case R.id.mi_sobre:
                    intent.setClass(contexto, Sobre.class);
                    break;
            }
            contexto.startActivity(intent);
            return false;
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
    public static PopupMenu getPopup(View view, int idMenu, Context contexto) {
        PopupMenu popup = new PopupMenu(contexto, view);
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(idMenu, popup.getMenu());
        return popup;
    }
}
