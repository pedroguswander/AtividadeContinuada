package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.*;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;

import java.io.Serializable;
import java.math.BigDecimal;

public class ApoliceMediator {
    private SeguradoPessoaDAO daoSegPes;
    private SeguradoEmpresaDAO daoSegEmp;
    private VeiculoDAO daoVel;
    private ApoliceDAO daoApo;
    private SinistroDAO daoSin;
    private static ApoliceMediator instancia;

    static public ApoliceMediator getInstancia()
    {
        if (instancia == null)
        {
            return new ApoliceMediator();
        }
        return instancia;
    }

    public ApoliceMediator() {
        this.daoSegPes = new SeguradoPessoaDAO();
        this.daoSegEmp = new SeguradoEmpresaDAO();
        this.daoVel = new VeiculoDAO();
        this.daoApo = new ApoliceDAO();
        this.daoSin = new SinistroDAO();
    }

    public RetornoInclusaoApolice incluirApolice(DadosVeiculo dados) {
        return null;
    }

    public Apolice buscarApolice(String numero) {
        return daoApo.buscar(numero);
    }

    public String excluirApolice(String numero) {
        if (numero == null || numero.isBlank()) {
            return "Número deve ser informado";
        }
        if (!daoApo.excluir(numero))
        {
            return "Apólice inexistente";
        }

        Serializable[] sinistros = daoSin.buscarTodos();
        for (Serializable sinistro : sinistros)
        {

        }
        return "Existe sinistro cadastrado para o veículo em questão e no mesmo ano da apólice";
    }


    private String validarTodosDadosVeiculo(DadosVeiculo dados) {
        return null;
    }
    private String validarCpfCnpjValorMaximo(DadosVeiculo dados) {
        return null;
    }
    private BigDecimal obterValorMaximoPermitido(int ano, int codigoCat) {
        return null;
    }





}
