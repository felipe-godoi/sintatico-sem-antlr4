package com.lexer.iftm;

public class Valor {
    private enum TipoValor {
        INTEIRO, DECIMAL, IDENTIFICADOR
    }

    private int valorInteiro;
    private double valorDecimal;
    private String valorIdentificador;
    private TipoValor tipo;

    public Valor(int valorInteiro) {
        this.valorInteiro = valorInteiro;
        tipo = TipoValor.INTEIRO;
    }

    public Valor(String valorIdentificador) {
        this.valorIdentificador = valorIdentificador;
        tipo = TipoValor.IDENTIFICADOR;
    }

    public Valor(double valorDecimal) {
        this.valorDecimal = valorDecimal;
        tipo = TipoValor.DECIMAL;
    }


    public int getvalorInteiro() {
        return valorInteiro;
    }

    public void setvalorInteiro(int valorInteiro) {
        this.valorInteiro = valorInteiro;
        tipo = TipoValor.INTEIRO;
    }

    public double getValorDecimal() {
        return valorDecimal;
    }

    public void setValorDecimal(double valorDecimal) {
        this.valorDecimal = valorDecimal;
        tipo = TipoValor.DECIMAL;
    }

    public String getValorIdentificador() {
        return valorIdentificador;
    }

    public void setValorIdentificador(String valorIdentificador) {
        this.valorIdentificador = valorIdentificador;
        tipo = TipoValor.IDENTIFICADOR;
    }

    @Override
    public String toString() {
        if (tipo == TipoValor.INTEIRO) {
            return "ValorInteiro: " + valorInteiro;
        } else if (tipo == TipoValor.DECIMAL) {
            return "ValorDecimal: " + valorDecimal;
        } else {
            return "ValorIdentificador: " + valorIdentificador;
        }
    }

    
}
