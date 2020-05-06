package br.com.flavio.geradordeapertos.adapter;

import android.content.Context;
import android.content.Intent;
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

import br.com.flavio.geradordeapertos.CadastroProcesso;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_processos, parent, false);
        return new ProcessoViewHolder(view, contexto);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ProcessoViewHolder holder, int posicao) {
        holder.nome.setText(processos.get(posicao).getNome());
        holder.apertadeira.setText("Apertadeira: " + processos.get(posicao).getApertadeira().getNome());
        holder.ciclos.setText("Ciclos: " + processos.get(posicao).getCiclos());
        holder.angulo.setText("Ângulo: " + processos.get(posicao).getAngulo());
        holder.valor.setText("Valor: " + processos.get(posicao).getValorNominal());
    }
    
    @Override
    public int getItemCount() {
        return processos.size();
    }
    
    public class ProcessoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView nome;
        TextView apertadeira;
        TextView ciclos;
        TextView angulo;
        TextView valor;
        Context contexto;
        
        public ProcessoViewHolder(@NonNull View itemView, Context contexto) {
            super(itemView);
            this.nome = itemView.findViewById(R.id.tv_lista_processos_nome);
            this.apertadeira = itemView.findViewById(R.id.tv_lista_processos_apertadeira);
            this.ciclos = itemView.findViewById(R.id.tv_lista_processos_ciclos);
            this.angulo = itemView.findViewById(R.id.tv_lista_processos_valor);
            this.valor = itemView.findViewById(R.id.tv_lista_processos_angulo);
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
                    alterarProcesso();
                    break;
                case 2:
                    removerProcesso();
                    break;
            }
            return true;
        };
        
        private void removerProcesso() {
            final int posicao = getAdapterPosition();
            final Processo processo = processos.get(posicao);
            final ProcessoDAO processoDAO = new ProcessoDAO(contexto);
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.ad_titulo_remover_processo)
                    .setMessage(R.string.ad_msg_remover_processo)
                    .setPositiveButton(R.string.remover, (dialog, which) -> {
                        processoDAO.deleta(processo);
                        processos.remove(posicao);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
            processoDAO.close();
        }
        
        private void alterarProcesso() {
            final Processo processo = processos.get(getAdapterPosition());
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.ad_titulo_alterar_processo)
                    .setMessage(R.string.ad_msg_alterar_processo)
                    .setPositiveButton(R.string.alterar, (dialog, which) -> {
                        Intent vaiParaCadastro = new Intent(contexto, CadastroProcesso.class);
                        vaiParaCadastro.putExtra("processo", processo);
                        contexto.startActivity(vaiParaCadastro);
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
        }
    }
}
