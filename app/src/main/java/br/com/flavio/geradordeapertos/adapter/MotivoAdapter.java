package br.com.flavio.geradordeapertos.adapter;

import android.content.Context;
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
import br.com.flavio.geradordeapertos.dao.MotivoDAO;
import br.com.flavio.geradordeapertos.modelo.Motivo;

/**
 * Adapter personalizado para que sejam exibidos os dados no RecyclerView
 */
public class MotivoAdapter extends RecyclerView.Adapter<MotivoAdapter.MotivoViewHolder> {
    private List<Motivo> motivos;
    private Context contexto;
    
    public MotivoAdapter(List<Motivo> motivos, Context contexto) {
        this.motivos = motivos;
        this.contexto = contexto;
    }
    
    @NonNull
    @Override
    public MotivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_lista, parent, false);
        return new MotivoViewHolder(view, contexto);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MotivoViewHolder holder, int posicao) {
        holder.motivoNome.setText(motivos.get(posicao).getNome());
    }
    
    @Override
    public int getItemCount() {
        return motivos.size();
    }
    
    public class MotivoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView motivoNome;
        Context contexto;
        
        public MotivoViewHolder(@NonNull View itemView, Context contexto) {
            super(itemView);
            this.motivoNome = itemView.findViewById(R.id.tv_nome);
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
        
        MenuItem.OnMenuItemClickListener onClick = item -> {
            switch (item.getItemId()) {
                case 1:
                    alterarMotivo();
                    break;
                case 2:
                    removerMotivo();
                    break;
            }
            return true;
        };
        
        private void removerMotivo() {
            final int posicao = getAdapterPosition();
            final Motivo motivo = motivos.get(posicao);
            final MotivoDAO motivoDAO = new MotivoDAO(contexto);
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.remover_motivo)
                    .setMessage(R.string.remover_motivo_descricao)
                    .setPositiveButton(R.string.remover, (dialog, which) -> {
                        motivoDAO.deleta(motivo);
                        motivos.remove(posicao);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
            motivoDAO.close();
        }
        
        private void alterarMotivo() {
            final EditText et_alterar = new EditText(contexto);
            int posicao = getAdapterPosition();
            final Motivo motivo = motivos.get(posicao);
            final MotivoDAO motivoDAO = new MotivoDAO(contexto);
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.alterar_motivo)
                    .setMessage(R.string.alterar_motivo_descricao)
                    .setView(et_alterar)
                    .setPositiveButton(R.string.alterar, (dialog, which) -> {
                        motivo.setNome(et_alterar.getText().toString());
                        motivoDAO.altera(motivo);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
            motivoDAO.close();
        }
    }
}
