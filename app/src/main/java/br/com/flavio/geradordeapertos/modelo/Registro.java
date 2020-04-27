package br.com.flavio.geradordeapertos.modelo;

public class Registro {
    private int idRegistro;
    private int data;
    private int idPrograma;
    private int idCabina;
    private int idMotivo;
    private int ciclo;
    private float valor;
    
    public int getIdRegistro() {
        return idRegistro;
    }
    
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }
    
    public int getData() {
        return data;
    }
    
    public void setData(int data) {
        this.data = data;
    }
    
    public int getIdPrograma() {
        return idPrograma;
    }
    
    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }
    
    public int getIdCabina() {
        return idCabina;
    }
    
    public void setIdCabina(int idCabina) {
        this.idCabina = idCabina;
    }
    
    public int getIdMotivo() {
        return idMotivo;
    }
    
    public void setIdMotivo(int idMotivo) {
        this.idMotivo = idMotivo;
    }
    
    public int getCiclo() {
        return ciclo;
    }
    
    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }
    
    public float getValor() {
        return valor;
    }
    
    public void setValor(float valor) {
        this.valor = valor;
    }
    
}
