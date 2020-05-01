package br.com.flavio.geradordeapertos.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.flavio.geradordeapertos.R;
import br.com.flavio.geradordeapertos.dao.RegistroDAO;
import br.com.flavio.geradordeapertos.modelo.Registro;

/**
 * Adapter personalizado para que sejam exibidos os dados no RecyclerView
 */
public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder> {
    private List<Registro> registros;
    private List<Registro> unificados;
    private List<String> valores;
    private Context contexto;
    
    public RegistroAdapter(List<Registro> registros, Context contexto) {
        this.registros = registros;
        this.unificados = unificaRegistros();
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
        holder.np.setText(unificados.get(posicao).getNPComMascara());
        System.out.println(holder.np.getText());
        holder.data.setText(unificados.get(posicao).getDataComMascara());
        holder.processo.setText("Processo: " + unificados.get(posicao).getPrograma().getProcesso().getNome());
        holder.programa.setText("Programa: " + unificados.get(posicao).getPrograma().getNome());
        holder.motivo.setText("Motivo: " + unificados.get(posicao).getMotivo().getNome());
        holder.angulo.setText("Ângulo: " + String.valueOf(unificados.get(posicao).getPrograma().getAngulo()));
        holder.ciclos_apertos.setText(valores.get(posicao));
    }
    
    /**
     * Unifica os registro para que sejam exibidos em um único card.
     *
     * @return Lista com os registros unificados
     */
    private List<Registro> unificaRegistros() {
        List<Registro> unificados = new ArrayList<>();
        valores = new ArrayList<>();
        String valor = "";
        
        int i = 0;
        for (int j = i; j < registros.size(); j++) {
            if (registros.get(i).equals(registros.get(j))) { // Comparação feita por equals sobrescrito em Registro
                valor += "Ciclo " + registros.get(j).getCiclo() + ": " + registros.get(j).getValor() + "\n";
            }
            if (!registros.get(i).equals(registros.get(j)) || (j + 1) == registros.size()) {
                unificados.add(registros.get(i));
                i = j;
                valores.add(valor);
                valor = "";
                if ((j + 1) == registros.size())
                    break;
                j--;
            }
        }
        return unificados;
    }
    
    @Override
    public int getItemCount() {
        return unificados.size();
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
            this.np = itemView.findViewById(R.id.tv_np);
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
            MenuItem enviar = menu.add(Menu.NONE, 1, 1, R.string.enviar);
            MenuItem remover = menu.add(Menu.NONE, 2, 2, R.string.remover);
            
            enviar.setOnMenuItemClickListener(onClick);
            remover.setOnMenuItemClickListener(onClick);
        }
        
        MenuItem.OnMenuItemClickListener onClick = item -> {
            switch (item.getItemId()) {
                case 1:
                    envia();
                    break;
                case 2:
                    remove();
                    break;
            }
            return true;
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
        
        public void envia() {
            PackageManager pm = contexto.getPackageManager();
            Registro registro = unificados.get(getAdapterPosition());
            String msg = registro.toString();
            
            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                
                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                intent.setPackage("com.whatsapp");
                
                intent.putExtra(Intent.EXTRA_TEXT, msg);
                contexto.startActivity(intent);
                
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(contexto, "WhatsApp não instalado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}