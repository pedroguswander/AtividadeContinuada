package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SeguradoMediator {
    static private SeguradoMediator instancia;

    private SeguradoMediator()
    {

    }

    static public SeguradoMediator getInstancia()
    {
        if (instancia == null)
        {
            instancia = new SeguradoMediator();
        }
        return instancia;
    }

    public String validarNome(String nome) {
        return null;
    }
    public String validarEndereco(Endereco endereco) {
        return null;
    }
    public String validarDataCriacao(LocalDate dataCriacao) {
        return null;
    }
    public BigDecimal ajustarDebitoBonus(BigDecimal bonus, BigDecimal valorDebito) {
        return null;
    }
}
