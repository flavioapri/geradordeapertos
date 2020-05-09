package br.com.flavio.geradordeapertos;

import android.os.Bundle;
import android.view.Menu;

public class Sobre extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Desabilita a opção da activity no menu
        menu.findItem(R.id.mi_sobre).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
