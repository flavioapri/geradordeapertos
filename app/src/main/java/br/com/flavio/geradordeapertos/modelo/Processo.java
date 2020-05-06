package br.com.flavio.geradordeapertos.modelo;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Processo implements Serializable {
    private int id;
    private Apertadeira apertadeira;
    private String nome;
    private int ciclos;
    private double valorNominal;
    private int angulo;
    
    public Processo() {
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
    
    public Apertadeira getApertadeira() {
        return apertadeira;
    }
    
    public void setApertadeira(Apertadeira apertadeira) {
        this.apertadeira = apertadeira;
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
    
    public double getValorNominal() {
        return valorNominal;
    }
    
    public void setValorNominal(float valorNominal) {
        this.valorNominal = valorNominal;
    }
    
    public void setValorNominal(String valorNominal) {
        try {
            this.valorNominal = Double.parseDouble(valorNominal);
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
        return (nome.length() > 0 & ciclos > 0 & valorNominal > 0);
    }
    
    @NonNull
    @Override
    public String toString() {
        return nome;
    }
}
