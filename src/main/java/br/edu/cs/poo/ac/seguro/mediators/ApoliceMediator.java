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
        double valorTotalVeiculo;

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
                    valorTotalVeiculo = p.getPreco();
                    BigDecimal percentualSegurado = dados.getValorMaximoSegurado()
                            .divide(BigDecimal.valueOf(valorTotalVeiculo), 2, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    System.out.println(percentualSegurado.doubleValue());
                    if (percentualSegurado.compareTo(new BigDecimal("100")) > 0 ||
                            percentualSegurado.compareTo(new BigDecimal("75")) < 0) {
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
            if (!ValidadorCpfCnpj.ehCpfValido(dados.getCpfOuCnpj())) {
                return new RetornoInclusaoApolice(null, "CPF inválido");
            }

            SeguradoPessoa seguradoPessoa = daoSegPes.buscar(dados.getCpfOuCnpj());
            if (seguradoPessoa == null) {
                return new RetornoInclusaoApolice(null, "CPF inexistente no cadastro de pessoas");
            }

            numeroApolice = LocalDate.now().getYear() + "000" + dados.getCpfOuCnpj() + dados.getPlaca();

            Apolice apoliceExistente = daoApo.buscar(numeroApolice);
            if (apoliceExistente != null) {
                return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
            }
        }
        else if (dados.getCpfOuCnpj().length() == 14) {
            if (!ValidadorCpfCnpj.ehCnpjValido(dados.getCpfOuCnpj())) {
                return new RetornoInclusaoApolice(null, "CNPJ inválido");
            }

            SeguradoEmpresa seguradoEmpresa = daoSegEmp.buscar(dados.getCpfOuCnpj());
            if (seguradoEmpresa == null) {
                return new RetornoInclusaoApolice(null, "CNPJ inexistente no cadastro de empresas");
            }

            numeroApolice = LocalDate.now().getYear() + dados.getCpfOuCnpj() + dados.getPlaca();

            Apolice apoliceExistente = daoApo.buscar(numeroApolice);
            if (apoliceExistente != null) {
                return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
            }
        }

        Veiculo veiculo = daoVel.buscar(dados.getPlaca());
        CategoriaVeiculo categoria = Arrays.stream(CategoriaVeiculo.values())
                .filter(cat -> cat.getCodigo() == dados.getCodigoCategoria())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de categoria inválido: " + dados.getCodigoCategoria()));

        Segurado propietario = null;
        if (veiculo == null) {
            if (dados.getCpfOuCnpj().length() == 11)
            {
                propietario = daoSegPes.buscar(dados.getCpfOuCnpj());
            }
            else if (dados.getCpfOuCnpj().length() == 14)
            {
                propietario = daoSegPes.buscar(dados.getCpfOuCnpj());
            }

            veiculo = new Veiculo(
                    dados.getPlaca(),
                    dados.getAno(),
                    propietario,
                    categoria
            );
            daoVel.incluir(veiculo);
        } else {
            if (dados.getCpfOuCnpj().length() == 11) {
                veiculo.setPropietario(daoSegPes.buscar(dados.getCpfOuCnpj()));

            } else {
                veiculo.setPropietario(daoSegEmp.buscar(dados.getCpfOuCnpj()));
            }

            daoVel.alterar(veiculo);
        }

        Apolice novaApolice = new Apolice(
                numeroApolice,
                veiculo,
                new BigDecimal("2223.00"),
                new BigDecimal("1710.00"),
                new BigDecimal("57000.00"),
                LocalDate.now()
        );

// (A) VPA = 3% do valor máximo segurado
        BigDecimal vpa = dados.getValorMaximoSegurado()
                .multiply(new BigDecimal("0.03"));

// (B) VPB = 1.2 * VPA se for empresa locadora; senão, VPB = VPA
        BigDecimal vpb;
        Segurado seg;
        if (dados.getCpfOuCnpj().length() == 14) {
            SeguradoEmpresa segEmp = daoSegEmp.buscar(dados.getCpfOuCnpj());
            seg = segEmp;

            if (segEmp.isEhLocadoraDeVeiculos()) {
                vpb = vpa.multiply(new BigDecimal("1.2"));
            } else {
                vpb = vpa;
            }
        } else {
            seg = daoSegPes.buscar(dados.getCpfOuCnpj());
            vpb = vpa;
        }

// (C) VPC = VPB - (bônus / 10)
        BigDecimal bonusDividido = seg.getBonus()
                .divide(new BigDecimal("10"), 2, RoundingMode.HALF_UP);
        BigDecimal vpc = vpb.subtract(bonusDividido);

// (D) Prêmio = VPC se VPC > 0; senão 0
        BigDecimal premio = vpc.compareTo(BigDecimal.ZERO) > 0 ? vpc : BigDecimal.ZERO;

// Franquia = 130% do VPB
        BigDecimal valorFranquia = vpb.multiply(new BigDecimal("1.3"));

        daoApo.incluir(novaApolice);

        // Bonificação do segurado, se for o caso
        int anoAnterior = novaApolice.getDataInicioVigencia().getYear() - 1;
        boolean teveSinistroAnterior = false;

        Serializable[] sinistros = daoSin.buscarTodos();
        for (Serializable s : sinistros) {
            if (s instanceof Sinistro) {
                Sinistro sinistro = (Sinistro) s;
                Veiculo veiculoSinistro = sinistro.getVeiculo();
                int anoSinistro = sinistro.getDataHoraRegistro().getYear();

                if (anoSinistro == anoAnterior) {
                    if (!seg.isEmpresa()) {
                        if (veiculoSinistro.getPropietario().getCpf().equals(((SeguradoPessoa) seg).getCpf())) {
                            teveSinistroAnterior = true;
                            break;
                        }
                    }
                    if (seg.isEmpresa()) {
                        if (veiculoSinistro.getPropietario().getCnpj().equals(((SeguradoEmpresa) seg).getCnpj())) {
                            teveSinistroAnterior = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!teveSinistroAnterior) {
            BigDecimal bonusAdicional = premio.multiply(new BigDecimal("0.30"));

            seg.creditarBonus(bonusAdicional);

            if (!seg.isEmpresa()) {
                daoSegPes.alterar((SeguradoPessoa) seg);
            } else if (seg.isEmpresa()) {
                daoSegEmp.alterar((SeguradoEmpresa) seg);
            }
        }

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

        Serializable[] serializables = daoSin.buscarTodos();
        for (Serializable serializable : serializables) {
            if (serializable instanceof Sinistro) {
                Sinistro sinistro = (Sinistro) serializable;

                if (sinistro.getVeiculo().equals(apolice.getVeiculo())) {
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