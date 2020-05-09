package br.com.flavio.geradordeapertos;

import android.os.Bundle;
import android.view.Menu;

public class Configuracoes extends BaseActivity {
    //TODO criar tabela no banco para as configurações
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Desabilita a opção da activity no menu
        menu.findItem(R.id.mi_configuracoes).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
