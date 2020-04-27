package br.com.flavio.geradordeapertos.helper;

import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.flavio.geradordeapertos.FormularioProgramaIndividual;
import br.com.flavio.geradordeapertos.R;

public class RegistroHelper {
    private final EditText campoNP;
    private final Spinner campoProcesso;
    private final Spinner campoPrograma;
    //private final List<CheckBox> campoCiclos;
    private final Spinner campoMotivo;
    private final TextView campoData;
    
    public RegistroHelper(FormularioProgramaIndividual activity){
        this.campoNP = activity.findViewById(R.id.et_formulario_np);
        this.campoProcesso = activity.findViewById(R.id.sp_formulario_processo);
        this.campoPrograma = activity.findViewById(R.id.sp_formulario_programa);
//        this.campoCiclos = activity.findViewById(R.id.tv_formulario_ciclos);
        this.campoMotivo = activity.findViewById(R.id.sp_formulario_motivo);
        this.campoData = activity.findViewById(R.id.tv_formulario_data);
    }
    
    public void testa() throws NoSuchFieldException {
        
        Log.d("valor", String.valueOf(campoProcesso.getSelectedItem().getClass().getField("nome")));
        
    }
    
    
}
