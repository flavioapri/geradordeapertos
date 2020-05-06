package br.com.flavio.geradordeapertos.helper;

import android.widget.TextView;

import br.com.flavio.geradordeapertos.CadastroProcesso;
import br.com.flavio.geradordeapertos.R;
import br.com.flavio.geradordeapertos.modelo.Processo;

public class ProcessoHelper {
    private final Processo processo;
    private final TextView tv_apertadeira;
    private final TextView tv_nome;
    private final TextView tv_ciclos;
    private final TextView tv_nominal;
    private final TextView tv_angulo;
    
    public ProcessoHelper(CadastroProcesso activity) {
        this.processo = new Processo();
        tv_apertadeira = activity.findViewById(R.id.tv_cadastro_processo_apertadeira);
        tv_nome = activity.findViewById(R.id.tv_cadastro_processo_nome);
        tv_ciclos = activity.findViewById(R.id.tv_cadastro_processo_ciclos);
        tv_nominal = activity.findViewById(R.id.tv_cadastro_processo_nominal);
        tv_angulo = activity.findViewById(R.id.tv_cadastro_processo_angulo);
    }
    
    public void preencheFormulario(Processo processo) {
        tv_apertadeira.setText(processo.getApertadeira().getNome());
        tv_nome.setText(processo.getNome());
        tv_ciclos.setText(String.valueOf(processo.getCiclos()));
        tv_nominal.setText(String.valueOf(processo.getValorNominal()));
        tv_angulo.setText(String.valueOf(processo.getAngulo()));
    }
}
