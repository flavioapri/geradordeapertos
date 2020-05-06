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

import br.com.flavio.geradordeapertos.CadastroPrograma;
import br.com.flavio.geradordeapertos.R;
import br.com.flavio.geradordeapertos.dao.ProgramaDAO;
import br.com.flavio.geradordeapertos.modelo.Programa;

/**
 * Adapter personalizado para que sejam exibidos os dados no RecyclerView
 */
public class ProgramaAdapter extends RecyclerView.Adapter<ProgramaAdapter.ProgramaViewHolder> {
    private List<Programa> programas;
    private Context contexto;
    
    public ProgramaAdapter(List<Programa> programas, Context contexto) {
        this.programas = programas;
        this.contexto = contexto;
    }
    
    @NonNull
    @Override
    public ProgramaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_relacao_programas, parent, false);
        return new ProgramaViewHolder(view, contexto);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ProgramaViewHolder holder, int posicao) {
        holder.nome.setText(programas.get(posicao).getNome());
        holder.processo.setText("Processo: " + programas.get(posicao).getProcesso().getNome());
        holder.ciclos.setText("Ciclos: " + programas.get(posicao).getCiclos());
        holder.angulo.setText("Ângulo: " + programas.get(posicao).getAngulo());
        holder.valor.setText("Valor: " + programas.get(posicao).getValorNominal());
    }
    
    @Override
    public int getItemCount() {
        return programas.size();
    }
    
    public class ProgramaViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView nome;
        TextView processo;
        TextView ciclos;
        TextView angulo;
        TextView valor;
        Context contexto;
        
        public ProgramaViewHolder(@NonNull View itemView, Context contexto) {
            super(itemView);
            this.nome = itemView.findViewById(R.id.tv_lista_programas_nome);
            this.processo = itemView.findViewById(R.id.tv_lista_programas_processo);
            this.ciclos = itemView.findViewById(R.id.tv_lista_programas_ciclos);
            this.angulo = itemView.findViewById(R.id.tv_lista_programas_valor);
            this.valor = itemView.findViewById(R.id.tv_lista_programas_angulo);
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
                    alterarPrograma();
                    break;
                case 2:
                    removerPrograma();
                    break;
            }
            return true;
        };
        
        private void removerPrograma() {
            final int posicao = getAdapterPosition();
            final Programa programa = programas.get(posicao);
            final ProgramaDAO programaDAO = new ProgramaDAO(contexto);
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.remover_programa)
                    .setMessage(R.string.remover_programa_descricao)
                    .setPositiveButton(R.string.remover, (dialog, which) -> {
                        programaDAO.deleta(programa);
                        programas.remove(posicao);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
            programaDAO.close();
        }
        
        private void alterarPrograma() {
            final Programa programa = programas.get(getAdapterPosition());
            
            new AlertDialog.Builder(contexto)
                    .setTitle(R.string.alterar_programa)
                    .setMessage(R.string.alterar_programa_descricao)
                    .setPositiveButton(R.string.alterar, (dialog, which) -> {
                        Intent vaiParaCadastro = new Intent(contexto, CadastroPrograma.class);
                        vaiParaCadastro.putExtra("programa", programa);
                        contexto.startActivity(vaiParaCadastro);
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
        }
    }
}
