package br.com.flavio.geradordeapertos.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.flavio.geradordeapertos.R;

public class ProcessoViewHolder extends RecyclerView.ViewHolder {
    private TextView processoNome;
    
    public ProcessoViewHolder(@NonNull View itemView) {
        super(itemView);
        this.processoNome = itemView.findViewById(R.id.tv_processo_nome);
    }
    
    public TextView getProcessoNome() {
        getAdapterPosition();
        return processoNome;
    }

}