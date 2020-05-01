package br.com.flavio.geradordeapertos.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import br.com.flavio.geradordeapertos.modelo.Registro;

public class EmissorMensagem {
    /**
     * Envia o registro como texto via Whatsapp
     */
    public static void envia(Context contexto, Registro registro, String valores) {
        PackageManager pm = contexto.getPackageManager();
        
        String msg = registro.toString() + valores;
        
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            intent.setPackage("com.whatsapp");
            
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            contexto.startActivity(intent);
            
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(contexto, "WhatsApp não instalado", Toast.LENGTH_SHORT).show();
        }
    }
}
