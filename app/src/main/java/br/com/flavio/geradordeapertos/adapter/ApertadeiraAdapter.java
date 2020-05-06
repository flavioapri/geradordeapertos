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
import br.com.flavio.geradordeapertos.dao.ApertadeiraDAO;
import br.com.flavio.geradordeapertos.modelo.Apertadeira;

/**
 * Adapter personalizado para que sejam exibidos os dados no RecyclerView
 */
public class ApertadeiraAdapter extends RecyclerView.Adapter<ApertadeiraAdapter.ApertadeiraViewHolder> {
    private List<Apertadeira> apertadeiras;
    private Context contexto;
    
    public ApertadeiraAdapter(List<Apertadeira> apertadeiras, Context contexto) {
        this.apertadeiras = apertadeiras;
        this.contexto = contexto;
    }
    
    @NonNull
    @Override
    public ApertadeiraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_lista, parent, false);
        return new ApertadeiraViewHolder(view, contexto);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ApertadeiraViewHolder holder, int posicao) {
        holder.apertadeiraNome.setText(apertadeiras.get(posicao).getNome());
    }
    
    @Override
    public int getItemCount() {
        return apertadeiras.size();
    }
    
    public class ApertadeiraViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView apertadeiraNome;
        Context contexto;
        
        public ApertadeiraViewHolder(@NonNull View itemView, Context contexto) {
            super(itemView);
            this.apertadeiraNome = itemView.findViewById(R.id.tv_nome);
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
                        alterarApertadeira();
                        break;
                    case 2:
                        removerApertadeira();
                        break;
                }
                return true;
            }
        };
        
        private void removerApertadeira() {
            final int posicao = getAdapterPosition();
            final Apertadeira apertadeira = apertadeiras.get(posicao);
            final ApertadeiraDAO apertadeiraDAO = new ApertadeiraDAO(contexto);
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.ad_titulo_remover_apertadeira)
                    .setMessage(R.string.ad_msg_remover_apertadeira)
                    .setPositiveButton(R.string.remover, (dialog, which) -> {
                        apertadeiraDAO.deleta(apertadeira);
                        apertadeiras.remove(posicao);
                        notifyDataSetChanged();
                        //TODO remover todos os processos relacionados
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
            apertadeiraDAO.close();
        }
        
        private void alterarApertadeira() {
            final EditText et_alterar = new EditText(contexto);
            int posicao = getAdapterPosition();
            final Apertadeira apertadeira = apertadeiras.get(posicao);
            final ApertadeiraDAO apertadeiraDAO = new ApertadeiraDAO(contexto);
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.ad_titulo_alterar_apertadeira)
                    .setMessage(R.string.ad_msg_alterar_apertadeira)
                    .setView(et_alterar)
                    .setPositiveButton(R.string.alterar, (dialog, which) -> {
                        apertadeira.setNome(et_alterar.getText().toString());
                        apertadeiraDAO.altera(apertadeira);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
            apertadeiraDAO.close();
        }
    }
}
