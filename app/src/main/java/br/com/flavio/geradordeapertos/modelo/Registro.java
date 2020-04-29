package br.com.flavio.geradordeapertos.modelo;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;

public class Registro {
    private int id;
    private int np;
    private String data;
    private Programa programa;
    private int ciclo;
    private double valor;
    private Motivo motivo;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        String dia;
        String mes;
        String ano;
        if (data.contains("/")) {
            String[] dataSeparada = data.split("/");
            dia = dataSeparada[2];
            mes = dataSeparada[1];
            ano = dataSeparada[0];
        } else {
            dia = data.substring(6);
            mes = data.substring(4, 6);
            ano = data.substring(0, 4);
        }
        this.data = dia + mes + ano;
    }
    
    public Programa getPrograma() {
        return programa;
    }
    
    public void setPrograma(Programa programa) {
        this.programa = programa;
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
    
    public Motivo getMotivo() {
        return motivo;
    }
    
    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
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
                + "Programa: " + programa.getNome() + "\n"
                + "NP: " + np + "\n"
                + "Motivo: " + motivo.getNome() + "\n"
                + "Ciclo: " + ciclo + "\n"
                + "Torque: " + valor + "\n"
                + "Data: " + data;
    }
}
