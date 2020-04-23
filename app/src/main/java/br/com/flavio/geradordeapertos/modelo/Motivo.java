package br.com.flavio.geradordeapertos.modelo;

import androidx.annotation.NonNull;

public class Motivo {
    private String nome;
    private long id;
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }
    
    @NonNull
    @Override
    public String toString() {
        return getNome();
    }
}
