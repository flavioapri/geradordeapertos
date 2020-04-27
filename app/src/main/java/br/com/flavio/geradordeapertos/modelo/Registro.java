package br.com.flavio.geradordeapertos.modelo;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;

public class Registro {
    private int idRegistro;
    private String data; //
    private int idPrograma;//
    private int np;//
    private int idMotivo;//
    private int ciclo;//
    private double valor;
    
    public int getIdRegistro() {
        return idRegistro;
    }
    
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        String[] dataSeparada = data.split("/");
        this.data = dataSeparada[2] +
                dataSeparada[1] +
                dataSeparada[0];
    }
    
    public int getIdPrograma() {
        return idPrograma;
    }
    
    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }
    
    public int getNP() {
        return np;
    }
    
    public void setNP(int np) {
        this.np = np;
    }
    
    public void setNP(String np) {
        np = np.replace(".", "").replace("/", "");
        this.np = Integer.parseInt(np);
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
    
    public void setCiclo(String ciclo) {
        this.ciclo = Integer.parseInt(ciclo);
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        // Arredonda as casas decimais para duas
        DecimalFormat formatador = new DecimalFormat("000.00");
        String valorFormatado = formatador.format(valor);
        valorFormatado = valorFormatado.replace(",", ".");
        this.valor = Double.parseDouble(valorFormatado);
    }
    
    @NonNull
    @Override
    public String toString() {
        return "\n"
                + "ID Programa: " + idPrograma + "\n"
                + "NP: " + np + "\n"
                + "ID Motivo: " + idMotivo + "\n"
                + "Ciclo: " + ciclo + "\n"
                + "Torque: " + valor + "\n"
                + "Data: " + data;
    }
}
