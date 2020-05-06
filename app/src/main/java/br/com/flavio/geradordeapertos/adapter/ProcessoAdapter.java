package br.com.flavio.geradordeapertos.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.flavio.geradordeapertos.R;
import br.com.flavio.geradordeapertos.dao.ProcessoDAO;
import br.com.flavio.geradordeapertos.modelo.Processo;

/**
 * Adapter personalizado para que sejam exibidos os dados no RecyclerView
 */
public class ProcessoAdapter extends RecyclerView.Adapter<ProcessoAdapter.ProcessoViewHolder> {
    private List<Processo> processos;
    private Context contexto;
    
    public ProcessoAdapter(List<Processo> processos, Context contexto) {
        this.processos = processos;
        this.contexto = contexto;
    }
    
    @NonNull
    @Override
    public ProcessoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_lista, parent, false);
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
            this.processoNome = itemView.findViewById(R.id.tv_nome);
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
                switch (item.getItemId()) {
                    case 1:
                        alterarProcesso();
                        break;
                    case 2:
                        removerProcesso();
                        break;
                }
                return true;
            }
        };
        
        private void removerProcesso() {
            final int posicao = getAdapterPosition();
            final Processo processo = processos.get(posicao);
            final ProcessoDAO processoDAO = new ProcessoDAO(contexto);
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.remover_processo)
                    .setMessage(R.string.remover_processo_descricao)
                    .setPositiveButton(R.string.remover, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            processoDAO.deleta(processo);
                            processos.remove(posicao);
                            notifyDataSetChanged();
                            //TODO remover todos os programas relacionados
                        }
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
            processoDAO.close();
        }
        
        private void alterarProcesso() {
            final EditText et_alterar = new EditText(contexto);
            int posicao = getAdapterPosition();
            final Processo processo = processos.get(posicao);
            final ProcessoDAO processoDAO = new ProcessoDAO(contexto);
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.alterar_processo)
                    .setMessage(R.string.alterar_processo_descricao)
                    .setView(et_alterar)
                    .setPositiveButton(R.string.alterar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            processo.setNome(et_alterar.getText().toString());
                            processoDAO.altera(processo);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
            processoDAO.close();
        }
    }
}
