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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.flavio.geradordeapertos.R;
import br.com.flavio.geradordeapertos.dao.RegistroDAO;
import br.com.flavio.geradordeapertos.modelo.Registro;

/**
 * Adapter personalizado para que sejam exibidos os dados no RecyclerView
 */
public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder> {
    private List<Registro> registros;
    private Context contexto;
    
    public RegistroAdapter(List<Registro> registros, Context contexto) {
        this.registros = registros;
        this.contexto = contexto;
    }
    
    @NonNull
    @Override
    public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_registro_main, parent, false);
        return new RegistroViewHolder(view, contexto);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int posicao) {
        //O método setText deve sempre receber String
        holder.np.setText(String.valueOf(registros.get(posicao).getNP()));
        holder.data.setText(registros.get(posicao).getData());
        holder.processo.setText(registros.get(posicao).getPrograma().getProcesso().getNome());
        holder.programa.setText(registros.get(posicao).getPrograma().getNome());
        holder.motivo.setText(registros.get(posicao).getMotivo().getNome());
        holder.angulo.setText(String.valueOf(registros.get(posicao).getPrograma().getAngulo()));
        //TODO exibir todos os apertos em um unico card
        holder.ciclos_apertos.setText(String.valueOf(registros.get(posicao).getValor()));
    }
    
    @Override
    public int getItemCount() {
        return registros.size();
    }
    
    public class RegistroViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView np;
        TextView data;
        TextView processo;
        TextView programa;
        TextView motivo;
        TextView angulo;
        TextView ciclos_apertos;
        Context contexto;
        
        public RegistroViewHolder(@NonNull View itemView, Context contexto) {
            super(itemView);
            this.np = itemView.findViewById(R.id.tv_lista_registro_np);
            this.data = itemView.findViewById(R.id.tv_data);
            this.processo = itemView.findViewById(R.id.tv_processo);
            this.programa = itemView.findViewById(R.id.tv_programa);
            this.motivo = itemView.findViewById(R.id.tv_motivo);
            this.angulo = itemView.findViewById(R.id.tv_angulo);
            this.ciclos_apertos = itemView.findViewById(R.id.tv_ciclos_apertos);
            this.contexto = contexto;
            
            itemView.setOnCreateContextMenuListener(this);
        }
        
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem enviar = menu.add(Menu.NONE, 1, 1, R.string.alterar);
            MenuItem remover = menu.add(Menu.NONE, 2, 2, R.string.remover);
    
            enviar.setOnMenuItemClickListener(onClick);
            remover.setOnMenuItemClickListener(onClick);
        }
        
        MenuItem.OnMenuItemClickListener onClick = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        envia();
                        break;
                    case 2:
                        remove();
                        break;
                }
                return true;
            }
        };
        
        private void remove() {
            final int posicao = getAdapterPosition();
            final Registro registro = registros.get(posicao);
            final RegistroDAO registroDAO = new RegistroDAO(contexto);
            
            new AlertDialog.Builder(contexto, R.style.AlertDialog)
                    .setTitle(R.string.remover_registro)
                    .setMessage(R.string.remover_registro_descricao)
                    .setPositiveButton(R.string.remover, (dialog, which) -> {
                        registroDAO.deleta(registro);
                        registros.remove(posicao);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
            registroDAO.close();
        }
        
        private void envia() {
           //TODO implementar
        }
    }
}