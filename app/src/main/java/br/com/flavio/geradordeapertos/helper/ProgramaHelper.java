package br.com.flavio.geradordeapertos.helper;

import android.widget.TextView;

import br.com.flavio.geradordeapertos.CadastroPrograma;
import br.com.flavio.geradordeapertos.R;
import br.com.flavio.geradordeapertos.modelo.Programa;

public class ProgramaHelper {
    private final Programa programa;
    private final TextView tv_processo;
    private final TextView tv_nome;
    private final TextView tv_ciclos;
    private final TextView tv_nominal;
    private final TextView tv_angulo;
    
    public ProgramaHelper(CadastroPrograma activity) {
        this.programa = new Programa();
        tv_processo = activity.findViewById(R.id.tv_cadastro_programa_processo);
        tv_nome = activity.findViewById(R.id.tv_cadastro_programa_nome);
        tv_ciclos = activity.findViewById(R.id.tv_cadastro_programa_ciclos);
        tv_nominal = activity.findViewById(R.id.tv_cadastro_programa_nominal);
        tv_angulo = activity.findViewById(R.id.tv_cadastro_programa_angulo);
    }
    
    public void preencheFormulario(Programa programa) {
        tv_processo.setText(programa.getProcesso().getNome());
        tv_nome.setText(programa.getNome());
        tv_ciclos.setText(String.valueOf(programa.getCiclos()));
        tv_nominal.setText(String.valueOf(programa.getValorNominal()));
        tv_angulo.setText(String.valueOf(programa.getAngulo()));
    }
}
