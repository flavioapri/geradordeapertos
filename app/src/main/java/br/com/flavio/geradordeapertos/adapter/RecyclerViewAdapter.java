package br.com.flavio.geradordeapertos.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.flavio.geradordeapertos.R;
import br.com.flavio.geradordeapertos.dao.ProcessoDAO;
import br.com.flavio.geradordeapertos.modelo.Processo;

/**
 * Adapter personalizado para que sejam exibidos os dados no RecyclerView
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ProcessoViewHolder> {
    private List<Processo> processos;
    private Context contexto;
    
    public RecyclerViewAdapter(List<Processo> processos, Context contexto) {
        this.processos = processos;
        this.contexto = contexto;
        
    }
    
    @NonNull
    @Override
    public ProcessoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.processo, parent, false);
        return new ProcessoViewHolder(view, contexto);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ProcessoViewHolder holder, int posicao) {
        holder.processoNome.setText(processos.get(posicao).getNome());
    }
    
    @Override
    public int getItemCount() {
        return processos.size();
    }
    
    public class ProcessoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView processoNome;
        Context contexto;
        
        
        public ProcessoViewHolder(@NonNull View itemView, Context contexto) {
            super(itemView);
            this.processoNome = itemView.findViewById(R.id.tv_processo_nome);
            this.contexto = contexto;
            
            itemView.setOnCreateContextMenuListener(this);
        }
    
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem alterar = menu.add(Menu.NONE, 1, 1, R.string.alterar);
            MenuItem remover = menu.add(Menu.NONE, 2, 2, R.string.remover);
            
            alterar.setOnMenuItemClickListener(onClick);
            remover.setOnMenuItemClickListener(onClick);
        }
        
        MenuItem.OnMenuItemClickListener onClick = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int posicao = getAdapterPosition();
                Processo processo = processos.get(posicao);
                ProcessoDAO processoDAO = new ProcessoDAO(contexto);
                switch (item.getItemId()){
                    case 1:
                        break;
                    case 2:
                        processoDAO.deleta(processo);
                        break;
                }
                processoDAO.close();
                return true;
            }
        };
    }
}
