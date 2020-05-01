package br.com.flavio.geradordeapertos.modelo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    
    /**
     * Retorna a data formatada
     *
     * @return Data com mascara dd/mm/yyyy
     */
    public String getDataComMascara() {
        return this.data.substring(0, 2) + "/" + this.data.substring(2, 4) + "/" + this.data.substring(4, 8);
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
    
    /**
     * Devolve o NP formatado
     *
     * @return NP formatado com mascara 00.0000000/0
     */
    public String getNPComMascara() {
        String np = String.valueOf(this.np);
        return np.substring(0, 2) + "." + np.substring(2, 8) + "/" + np.substring(8, 9);
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
    
    public double
    getValor() {
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
        String angulo = "";
        if (programa.getAngulo() > 0) // Se houver ângulo exibe
            angulo += "Ângulo: " + this.programa.getAngulo();
        return "\n"
                + "Processo: " + programa.getProcesso().getNome() + "\n"
                + "Programa: " + programa.getNome() + "\n"
                + "NP " + getNPComMascara() + "\n"
                + "Motivo: " + motivo.getNome() + "\n"
                + "Data: " + getDataComMascara() + "\n"
                + angulo + "\n";
    }
    
    /**
     * Sobrescrito para que seja a feita comparação necessária para a unificação dos registros. Os critérios de verificação são pelo NP e
     * Programa.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Registro))
            return false;
        Registro registro = (Registro) obj;
        if (!(this.np == registro.np) || !(this.getPrograma().getId() == registro.getPrograma().getId()))
            return false;
        return true;
    }
    
    /**
     * Sobrescrito para que seja a feita comparação necessária para a unificação dos registros. Sempre que equals() for sobrescrito
     * hashCode() deve ser sobrescrito também. Aqui deve se informar algum código que devolva um número qualquer.
     *
     * @param obj
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + np;
        return result;
    }
}
