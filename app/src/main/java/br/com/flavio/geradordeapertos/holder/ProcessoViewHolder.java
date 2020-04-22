package br.com.flavio.geradordeapertos.holder;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import br.com.flavio.geradordeapertos.R;

public class ProcessoViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener {
    private CardView cv_processo;
    private TextView processoNome;
    
    public ProcessoViewHolder(@NonNull View itemView) {
        super(itemView);
        this.cv_processo = itemView.findViewById(R.id.cv_lista_processo);
        this.processoNome = itemView.findViewById(R.id.tv_processo_nome);
        itemView.setOnLongClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem alterar = menu.add(R.string.alterar);
        MenuItem remover = menu.add(R.string.remover);
        
        alterar.setOnMenuItemClickListener(onClick);
        remover.setOnMenuItemClickListener(onClick);
    }
    
    private final MenuItem.OnMenuItemClickListener onClick = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case 0:
                    item.setTitle("CARRRAIIIIII!");
                    break;
                case 1:
                    break;
            }
            return false;
        }
    };

    
    @Override
    public boolean onLongClick(View v) {
        
        
        return false;
    }
    
    public CardView getCv_processo() {
        return cv_processo;
    }
    
    public TextView getProcessoNome() {
        return processoNome;
    }
    
}