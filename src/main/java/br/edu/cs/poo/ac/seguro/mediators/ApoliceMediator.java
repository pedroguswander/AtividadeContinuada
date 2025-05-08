package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.*;
import br.edu.cs.poo.ac.seguro.entidades.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;

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
        String numeroApolice = "";

        if (dados == null) {
            return new RetornoInclusaoApolice(null,
                    "Dados do veículo devem ser informados");
       }

        if (dados.getCodigoCategoria() > 5)
        {
            return new RetornoInclusaoApolice(null,
                    "Categoria inválida");
        }



        if (dados.getAno() < 2020 || dados.getAno() > 2025) {
            return new RetornoInclusaoApolice(null,
                    "Ano tem que estar entre 2020 e 2025, incluindo estes");
        }


        if (dados.getValorMaximoSegurado() == null) {
            return new RetornoInclusaoApolice(null,
                    "Valor máximo segurado deve ser informado");
        }
        else {
            CategoriaVeiculo categoria = CategoriaVeiculo.values()[dados.getCodigoCategoria()-1];
            for (PrecoAno p : categoria.getPrecosAnos()) {
                if (p.getAno() == dados.getAno()) {
                    double valorTotalVeiculo = p.getPreco(); // Este é o valor total do veículo (não percentual)
                    BigDecimal percentualSegurado = dados.getValorMaximoSegurado()
                            .divide(BigDecimal.valueOf(valorTotalVeiculo), 2, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)); // Multiplica por 100 para obter percentual// 2 casas decimais
                    System.out.println(percentualSegurado.doubleValue());
                    if (percentualSegurado.compareTo(new BigDecimal("100")) > 0 ||
                            percentualSegurado.compareTo(new BigDecimal("75")) < 0) {
                        // Fora do intervalo permitido (75% a 100%)
                        return new RetornoInclusaoApolice(
                                null,
                                "Valor máximo segurado deve estar entre 75% e 100% do valor do carro encontrado na categoria"
                        );
                    }
                }
            }
        }

        if (dados.getCpfOuCnpj() == null || dados.getCpfOuCnpj().trim().isEmpty()) {
            return new RetornoInclusaoApolice(null,
                    "CPF ou CNPJ deve ser informado");
        }
        if (dados.getPlaca() == null || dados.getPlaca().trim().isEmpty()) {
            return new RetornoInclusaoApolice(null,
                    "Placa do veículo deve ser informada");

        }
        if (dados.getCpfOuCnpj().length() == 11) {
            // CPF validation
            if (!ValidadorCpfCnpj.ehCpfValido(dados.getCpfOuCnpj())) {
                return new RetornoInclusaoApolice(null, "CPF inválido");
            }

            // Check if CPF exists in person registry
            SeguradoPessoa seguradoPessoa = daoSegPes.buscar(dados.getCpfOuCnpj());
            if (seguradoPessoa == null) {
                return new RetornoInclusaoApolice(null, "CPF inexistente no cadastro de pessoas");
            }

            // Generate policy number
            numeroApolice = LocalDate.now().getYear() + "000" + dados.getCpfOuCnpj() + dados.getPlaca();

            // Check for existing policy
            Apolice apoliceExistente = daoApo.buscar(numeroApolice);
            if (apoliceExistente != null) {
                return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
            }
        }
        else if (dados.getCpfOuCnpj().length() == 14) {
            // CNPJ validation
            if (!ValidadorCpfCnpj.ehCnpjValido(dados.getCpfOuCnpj())) {
                return new RetornoInclusaoApolice(null, "CNPJ inválido");
            }

            // Check if CNPJ exists in company registry
            SeguradoEmpresa seguradoEmpresa = daoSegEmp.buscar(dados.getCpfOuCnpj());
            if (seguradoEmpresa == null) {
                return new RetornoInclusaoApolice(null, "CNPJ inexistente no cadastro de empresas");
            }

            // Generate policy number
            numeroApolice = LocalDate.now().getYear() + dados.getCpfOuCnpj() + dados.getPlaca();

            // Check for existing policy
            Apolice apoliceExistente = daoApo.buscar(numeroApolice);
            if (apoliceExistente != null) {
                return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
            }
        }

// Handle vehicle creation/update
        Veiculo veiculo = daoVel.buscar(dados.getPlaca());
        CategoriaVeiculo categoria = Arrays.stream(CategoriaVeiculo.values())
                .filter(cat -> cat.getCodigo() == dados.getCodigoCategoria())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de categoria inválido: " + dados.getCodigoCategoria()));

        if (veiculo == null) {
            // Create new vehicle
            SeguradoPessoa seguradoPessoa = dados.getCpfOuCnpj().length() == 11 ?
                    daoSegPes.buscar(dados.getCpfOuCnpj()) : null;
            SeguradoEmpresa seguradoEmpresa = dados.getCpfOuCnpj().length() == 14 ?
                    daoSegEmp.buscar(dados.getCpfOuCnpj()) : null;

            veiculo = new Veiculo(
                    dados.getPlaca(),
                    dados.getAno(),
                    seguradoEmpresa,
                    seguradoPessoa,
                    categoria
            );
            daoVel.incluir(veiculo);
        } else {
            // Update existing vehicle's owner
            if (dados.getCpfOuCnpj().length() == 11) {
                veiculo.setProprietarioPessoa(daoSegPes.buscar(dados.getCpfOuCnpj()));
                veiculo.setProprietarioEmpresa(null);


            } else {
                veiculo.setProprietarioEmpresa(daoSegEmp.buscar(dados.getCpfOuCnpj()));
                veiculo.setProprietarioPessoa(null);
            }
            daoVel.alterar(veiculo);
        }

// Create and return the new policy


        Apolice novaApolice = new Apolice(
                numeroApolice,
                veiculo,
                new BigDecimal("2223.00"),  // These values should probably come from somewhere
                new BigDecimal("1710.00"),  // Maybe calculate based on vehicle category
                new BigDecimal("57000.00"), // This should come from DadosVeiculo
                LocalDate.now()
        );

        /*Sinistro[] sinistros = (Sinistro[]) daoSin.buscarTodos();

        for (Sinistro sinistro : sinistros) {
            Veiculo vel = sinistro.getVeiculo();
            if (vel.equals(novaApolice.getVeiculo()))
            {
                int anoSinistro = sinistro.getDataHoraRegistro().getYear();
                int anoApolice = novaApolice.getDataInicioVigencia().getYear() -1;
                if (anoSinistro != anoApolice)
                {
                    BigDecimal bonus = novaApolice.getValorPremio().multiply(new BigDecimal("0.30"));
                    if (dados.getCpfOuCnpj().length() == 11)
                    {
                        SeguradoPessoa segurado = daoSegPes.buscar(dados.getCpfOuCnpj());
                        segurado.creditarBonus(segurado.getBonus().add(bonus));
                        daoSegPes.alterar(segurado);
                    }
                    else if (dados.getCpfOuCnpj().length() == 14)
                    {
                        SeguradoEmpresa segurado = daoSegEmp.buscar(dados.getCpfOuCnpj());
                        segurado.creditarBonus(segurado.getBonus().add(bonus));
                        daoSegEmp.alterar(segurado);
                    }

                }
            }


        }*/

        daoApo.incluir(novaApolice);

        return new RetornoInclusaoApolice(numeroApolice, null);
    }

    public Apolice buscarApolice(String numero) {
        return daoApo.buscar(numero);
    }

    public String excluirApolice(String numero) {
        if (numero == null || numero.isBlank()) {
            return "Número deve ser informado";
        }

        Apolice apolice = daoApo.buscar(numero);
        if (apolice == null) {
            return "Apólice inexistente";
        }

        // Corrigindo o problema do cast
        Serializable[] serializables = daoSin.buscarTodos();
        for (Serializable serializable : serializables) {
            if (serializable instanceof Sinistro) {
                Sinistro sinistro = (Sinistro) serializable;

                // Compara os veículos
                if (sinistro.getVeiculo().equals(apolice.getVeiculo())) {
                    // Compara os anos
                    int anoSinistro = sinistro.getDataHoraRegistro().getYear();
                    int anoApolice = apolice.getDataInicioVigencia().getYear();

                    if (anoSinistro == anoApolice) {
                        return "Existe sinistro cadastrado para o veículo em questão e no mesmo ano da apólice";
                    }
                }
            }
        }

        if (!daoApo.excluir(numero)) {
            return "Erro ao excluir apólice";
        }

        return null;
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
