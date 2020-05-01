package br.com.flavio.geradordeapertos.modelo;

class Aperto {
    private int id;
    private int id_registro;
    private double valor;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }
}
