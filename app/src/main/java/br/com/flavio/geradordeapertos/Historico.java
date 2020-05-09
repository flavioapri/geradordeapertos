package br.com.flavio.geradordeapertos;

import android.os.Bundle;
import android.view.Menu;

public class Historico  extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Desabilita a opção da activity no menu
        menu.findItem(R.id.mi_historico).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
