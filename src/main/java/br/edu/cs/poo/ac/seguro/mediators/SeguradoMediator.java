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
        if (StringUtils.ehNuloOuBranco(nome))
        {
            return "Nome deve ser informado";
        }
        if (nome.length() > 100)
        {
            return"Tamanho do nome deve ser no máximo 100 caracteres";
        }
        return null;
    }
    public String validarEndereco(Endereco endereco) {
        if (endereco == null)
        {
            return "Endereço deve ser informado";
        }
        else if (endereco.getLogradouro() == null || endereco.getLogradouro().isBlank())
        {
            return "Logradouro deve ser informado";
        }
        else if (endereco.getCep() == null || endereco.getCep().isBlank()) {
            return "CEP deve ser informado";
        }
        else if (endereco.getCep().length() != 8)
        {
            return "Tamanho do CEP deve ser 8 caracteres";
        }
        else if (!StringUtils.temSomenteNumeros(endereco.getCep()))
        {
            return "CEP deve ter formato NNNNNNNN";
        }
        else if (endereco.getCidade() == null || endereco.getCidade().isBlank())
        {
            return "Cidade deve ser informada";
        }
        else if(endereco.getCidade().length() > 100)
        {
            return "Tamanho da cidade deve ser no máximo 100 caracteres";
        }
        else if (endereco.getEstado() == null || endereco.getEstado().isBlank())
        {
            return "Sigla do estado deve ser informada";
        }
        else if (endereco.getEstado().length() != 2)
        {
            return "Tamanho da sigla do estado deve ser 2 caracteres";
        }
        else if (endereco.getPais() == null || endereco.getPais().isBlank()) {
            return "País deve ser informado";
        }
        else if (endereco.getPais().length() > 40)
        {
            return "Tamanho do país deve ser no máximo 40 caracteres";
        }
        else if (endereco.getNumero() == null || endereco.getNumero().isBlank() || endereco.getNumero().length() <= 2) {

        }
        else if (endereco.getNumero().length() > 20)
        {
            return "Tamanho do número deve ser no máximo 20 caracteres";
        }
        else if (endereco.getComplemento().length() > 30)
        {
            return "Tamanho do complemento deve ser no máximo 30 caracteres";
        }
        return null;
    }
    public String validarDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null)
        {
            return "Data da criação deve ser informada";
        }
        else if (dataCriacao.isAfter(LocalDate.now()))
        {
            return "Data da criação deve ser menor ou igual à data atual";
        }

        return null;
    }
    public BigDecimal ajustarDebitoBonus(BigDecimal bonus, BigDecimal valorDebito) {
        if (bonus.compareTo(valorDebito) < 0)
        {
            return bonus;
        }
        else {
            return valorDebito;
        }
    }
}
