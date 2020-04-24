package br.com.flavio.geradordeapertos.modelo;

public class Programa {
    private int id;
    private int IdProcesso;
    private String nome;
    private int ciclos;
    private float valorNominal;
    private int angulo;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdProcesso() {
        return IdProcesso;
    }
    
    public void setIdProcesso(int idProcesso) {
        IdProcesso = idProcesso;
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
    
    public int getAngulo() {
        return angulo;
    }
    
    public void setAngulo(int angulo) {
        this.angulo = angulo;
    }
}
