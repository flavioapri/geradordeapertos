package br.com.flavio.geradordeapertos.mask;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Cria uma mascara para ser utilizada no campo do valor nominal do programa
 */
public class MascaraWatcher implements TextWatcher {
    private boolean isRunning = false;
    private boolean isDeleting = false;
    private final String mascara;
    
    public MascaraWatcher(String mascara) {
        this.mascara = mascara;
    }
    
    public static MascaraWatcher buildValorNominal() {
        return new MascaraWatcher("###.#");
    }
    
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        isDeleting = count > after;
    }
    
    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    }
    
    @Override
    public void afterTextChanged(Editable editable) {
        if (isRunning || isDeleting) {
            return;
        }
        isRunning = true;
        
        int editableLength = editable.length();
        if (editableLength < mascara.length()) {
            if (mascara.charAt(editableLength) != '#') {
                editable.append(mascara.charAt(editableLength));
            } else if (mascara.charAt(editableLength - 1) != '#') {
                editable.insert(editableLength - 1, mascara, editableLength - 1, editableLength);
            }
        }
        isRunning = false;
    }
}