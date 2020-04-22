package br.com.flavio.geradordeapertos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.flavio.geradordeapertos.R;
import br.com.flavio.geradordeapertos.holder.ProcessoViewHolder;
import br.com.flavio.geradordeapertos.modelo.Processo;

/**
 * Adapter personalizado para que sejam exibidos os dados no RecyclerView
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ProcessoViewHolder> {
    private List<Processo> processos;
    
    public RecyclerViewAdapter(List<Processo> processos) {
        this.processos = processos;
    }
    
    @NonNull
    public ProcessoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.processo, parent, false);
        return new ProcessoViewHolder(view);
    }
    
    public void onBindViewHolder(@NonNull ProcessoViewHolder holder, final int position) {
        holder.getProcessoNome().setText(processos.get(position).getNome());
    }
    
    @Override
    public int getItemCount() {
        return processos.size();
    }
    
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
