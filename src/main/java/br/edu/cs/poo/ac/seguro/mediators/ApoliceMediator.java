package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.*;
import br.edu.cs.poo.ac.seguro.entidades.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

        if (dados == null) {
            return new RetornoInclusaoApolice(null,
                    "Dados do veículo devem ser informados");
        }

        if (dados.getCpfOuCnpj() == null || dados.getCpfOuCnpj().trim().isEmpty()) {
            return new RetornoInclusaoApolice(null,
                    "CPF ou CNPJ deve ser informado");
        }

        if (dados.getPlaca() == null || dados.getPlaca().trim().isEmpty()) {
            return new RetornoInclusaoApolice(null,
                    "Placa do veículo deve ser informada");
        }

        if (dados.getAno() < 2020 || dados.getAno() > 2025) {
            return new RetornoInclusaoApolice(null,
                    "Ano tem que estar entre 2020 e 2025, incluindo estes");
        }

        if (dados.getValorMaximoSegurado() == null) {
            return new RetornoInclusaoApolice(null,
                    "Valor máximo segurado deve ser informado");
        }

        if (dados.getCodigoCategoria() < 1 || dados.getCodigoCategoria() > 5) {
            return new RetornoInclusaoApolice(null,
                    "Categoria inválida");
        }

        if (dados.getCpfOuCnpj().length() == 11) {

            if (!ValidadorCpfCnpj.ehCpfValido(dados.getCpfOuCnpj())) {
                return new RetornoInclusaoApolice(null,
                        "CPF inválido");
            }

            if (daoSegPes.buscar(dados.getCpfOuCnpj()) == null) {
                return new RetornoInclusaoApolice(null,
                        "CPF inexistente no cadastro de pessoas");
            }
        } else if (dados.getCpfOuCnpj().length() == 14) {

            if (!ValidadorCpfCnpj.ehCnpjValido(dados.getCpfOuCnpj())) {
                return new RetornoInclusaoApolice(null,
                        "CNPJ inválido");
            }

            if (daoSegEmp.buscar(dados.getCpfOuCnpj()) == null) {
                return new RetornoInclusaoApolice(null,
                        "CNPJ inexistente no cadastro de empresas");
            }
        }

        CategoriaVeiculo categoria = CategoriaVeiculo.values()[dados.getCodigoCategoria()-1];
        for (PrecoAno p : categoria.getPrecosAnos()) {
            if (p.getAno() == dados.getAno()) {
                double precoBase = p.getPreco() * 100;
                BigDecimal percentualSegurado = dados.getValorMaximoSegurado()
                        .divide(BigDecimal.valueOf(precoBase), 2, RoundingMode.HALF_UP);

                if (percentualSegurado.compareTo(new BigDecimal("100")) > 0 ||
                        percentualSegurado.compareTo(new BigDecimal("75")) < 0) {
                    return new RetornoInclusaoApolice(
                            null,
                            "Valor máximo segurado deve estar entre 75% e 100% do valor do carro encontrado na categoria"
                    );
                }
                break;
            }
        }


        Veiculo veiculo = daoVel.buscar((dados.getPlaca()));
        String numeroApolice = dados.getCpfOuCnpj() + "000" +dados.getAno() + dados.getPlaca();
        Apolice apolice = daoApo.buscar(numeroApolice);
        if (apolice.getDataInicioVigencia().getYear() == dados.getAno())
        {
            return new RetornoInclusaoApolice(
                    null,
                    "Apólice já existente para ano atual e veículo"
                    );
        }

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
