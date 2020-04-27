package br.com.flavio.geradordeapertos.modelo;

import androidx.annotation.NonNull;

public class Programa {
    private int id;
    private int idProcesso;
    private String nome;
    private int ciclos;
    private float valorNominal;
    private int angulo;
    
    public Programa() {
        this.idProcesso = 0;
        this.nome = "";
        this.ciclos = 0;
        this.valorNominal = 0;
        this.angulo = 0;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdprocesso() {
        return idProcesso;
    }
    
    public void setIdprocesso(int idprocesso) {
        this.idProcesso = idprocesso;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getCiclos() {
        return ciclos;
    }
    
    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
    }
    
    public float getValorNominal() {
        return valorNominal;
    }
    
    public void setValorNominal(float valorNominal) {
        this.valorNominal = valorNominal;
    }
    
    public void setValorNominal(String valorNominal) {
        try {
            this.valorNominal = Float.parseFloat(valorNominal);
        } catch (NumberFormatException e) {
        }
    }
    
    public int getAngulo() {
        return angulo;
    }
    
    public void setAngulo(int angulo) {
        this.angulo = angulo;
    }
    
    public void setAngulo(String angulo) {
        try {
            this.angulo = Integer.parseInt(angulo);
        } catch (NumberFormatException e) {
        }
    }
    
    public boolean isPreenchido() {
        return (idProcesso > 0 & nome.length() > 0 & ciclos > 0 & valorNominal > 0);
    }
    
    @NonNull
    @Override
    public String toString() {
        return nome;
    }
}
