package br.com.flavio.geradordeapertos.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
public class RecyclerViewAdapter extends RecyclerView.Adapter<ProcessoViewHolder> implements View.OnCreateContextMenuListener {
    private List<Processo> processos;
    private Context contexto;
    
    
    public RecyclerViewAdapter(List<Processo> processos, Context contexto) {
        this.processos = processos;
        this.contexto = contexto;
    }
    
    @NonNull
    public ProcessoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.processo, parent, false);
        return new ProcessoViewHolder(view);
    }
    
    public void onBindViewHolder(@NonNull ProcessoViewHolder holder, final int position) {
        holder.getProcessoNome().setText(processos.get(position).getNome());
        holder.getProcessoNome().setOnCreateContextMenuListener(this);
    }
    
    @Override
    public int getItemCount() {
        return processos.size();
    }
    
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem alterar = menu.add(Menu.NONE, 1, 1, R.string.alterar);
        MenuItem remover = menu.add(Menu.NONE, 2, 2, R.string.remover);
        
        alterar.setOnMenuItemClickListener(onClick);
        remover.setOnMenuItemClickListener(onClick);
    }
    
    private final MenuItem.OnMenuItemClickListener onClick = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case 1:
                    
                    break;
                case 2:
                    
                    break;
            }
            
            return true;
        }
    };
}
