package br.com.flavio.geradordeapertos;

public enum OpcoesGeracaoApertos  {
    PROGRAMA_INDIVIDUAL(1), PROGRAMA_LOTE(2);
    
    private final int valor;
    
    OpcoesGeracaoApertos(int valorOpcao){
        valor = valorOpcao;
    }
}
