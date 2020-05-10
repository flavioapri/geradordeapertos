package br.com.flavio.geradordeapertos.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.List;

import br.com.flavio.geradordeapertos.modelo.Registro;

/**
 * Envia os registro por mensagem via Whatsapp
 */
public class EmissorMensagem {
    public static void enviaMensagem(Context contexto, List<Registro> registros, List<String> valores) {
        String msg = "";
        for (int i = 0; i < registros.size(); i++) {
            msg += registros.get(i) + valores.get(i);
        }
        envia(contexto, msg);
    }
    
    /**
     * Envia o registro
     */
    public static void enviaMensagem(Context contexto, Registro registro, String valores) {
        String msg = registro.toString() + valores;
        envia(contexto, msg);
    }
    
    private static void envia(Context contexto, String msg){
        PackageManager pm = contexto.getPackageManager();
        
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
