
package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Registro;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;

public class SinistroMediator {

    private VeiculoDAO daoVeiculo = new VeiculoDAO();
    private ApoliceDAO daoApolice = new ApoliceDAO();
    private SinistroDAO daoSinistro = new SinistroDAO();
    private static SinistroMediator instancia;
    public static SinistroMediator getInstancia() {
        if (instancia == null)
            instancia = new SinistroMediator();
        return instancia;
    }
    private SinistroMediator() {}

    public String incluirSinistro(DadosSinistro dados, LocalDateTime dataHoraAtual) throws ExcecaoValidacaoDados {
        List<String> erros = new ArrayList<>();
        Sinistro sinistroInstanciado = null;
        String numero = null;

        // Validações básicas
        if (dados == null) {
            erros.add("Dados do sinistro devem ser informados");
            throw new ExcecaoValidacaoDados(erros);
        }

        // Validações de dados obrigatórios
        if (dados.getDataHoraSinistro() == null) {
            erros.add("Data/hora do sinistro deve ser informada");
        } else if (dados.getDataHoraSinistro().isAfter(LocalDateTime.now())) {
            erros.add("Data/hora do sinistro deve ser menor que a data/hora atual");
        }

        if (dados.getPlaca() == null || dados.getPlaca().isBlank()) {
            erros.add("Placa do Ve�culo deve ser informada");
        } else if (daoVeiculo.buscar(dados.getPlaca()) == null) {
            erros.add("Ve�culo n�o cadastrado");
        }

        if (dados.getUsuarioRegistro() == null || dados.getUsuarioRegistro().isBlank()) {
            erros.add("Usu�rio do registro de sinistro deve ser informado");
        }

        if (dados.getValorSinistro() <= 0.0) {
            erros.add("Valor do sinistro deve ser maior que zero");
        }

        if (TipoSinistro.getTipoSinistro(dados.getCodigoTipoSinistro()) == null) {
            erros.add("C�digo do tipo de sinistro inv�lido");
        }

        // Se houver erros nas validações básicas, não continua
        if (!erros.isEmpty()) {
            throw new ExcecaoValidacaoDados(erros);
        }

        // Validações de negócio (apólice)
        Registro[] apolices = daoApolice.buscarTodos();
        boolean apoliceEncontrada = false;
        Apolice apoVigente = null;

        for (Registro apolice : apolices) {
            Apolice apo = daoApolice.buscar(apolice.getIdUnico());
            if (Objects.equals(apo.getVeiculo().getPlaca(), dados.getPlaca()) &&
                    Period.between(apo.getDataInicioVigencia(), LocalDate.now()).getYears() < 1) {

                apoVigente = apo;
                apoliceEncontrada = true;

                if (apo.getValorMaximoSegurado().compareTo(BigDecimal.valueOf(dados.getValorSinistro())) < 0) {
                    erros.add("Valor do sinistro n�o pode ultrapassar o valor m�ximo segurado constante na ap�lice");
                }
                break; // Encontrou uma apólice válida
            }
        }

        if (!apoliceEncontrada) {
            erros.add("N�o existe ap�lice vigente para o ve�culo");
        }
        else {
            String sequencial = ""; // Defina o sequencial apropriadamente

            boolean v1 = true;
            boolean v2 = false;
            String comp = "";
            ArrayList<Sinistro> sins = new ArrayList<Sinistro>();

            Registro[] sinistros = daoSinistro.buscarTodos();
            for (Registro sinistro : sinistros) {
                Sinistro sin = daoSinistro.buscar(sinistro.getIdUnico());
                sins.add(sin);
                if (sin.getNumero().compareTo(apoVigente.getNumero()) == 0) {
                    v1 = false;
                    v2 = true;
                }
            }

            sins.sort(new ComparadorSinistroSequencial());
            for (Sinistro si : sins) {
                System.out.println(si.getSequencial());
            }

            if (v1) sequencial = "1"; comp = "00";
            if (v2)
            {
                sequencial = String.valueOf(sins.get(0).getSequencial()+1);
                if (sins.get(0).getSequencial()+1 < 100) comp = "00";
            }

            numero = "S" + apoVigente.getIdUnico() + comp + sequencial;

            sinistroInstanciado = new Sinistro(numero, daoVeiculo.buscar(dados.getPlaca()), dados.getDataHoraSinistro(),
                    dataHoraAtual, dados.getUsuarioRegistro(), BigDecimal.valueOf(dados.getValorSinistro()),
                    TipoSinistro.getTipoSinistro(dados.getCodigoTipoSinistro()));
            sinistroInstanciado.setSequencial(Integer.parseInt(sequencial));

            daoSinistro.incluir(sinistroInstanciado);
        }

        // Se houver erros nas validações de negócio, não continua
        if (!erros.isEmpty()) {
            throw new ExcecaoValidacaoDados(erros);
        }

        return numero;
    }
}
