package br.com.flavio.geradordeapertos.modelo;

import androidx.annotation.NonNull;

public class Motivo {
    private String nome;
    private int id;
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    @NonNull
    @Override
    public String toString() {
        return getNome();
    }
}
