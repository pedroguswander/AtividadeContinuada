
package br.edu.cs.poo.ac.seguro.mediators;

import java.time.LocalDateTime;
import java.util.*;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
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

        if (dados == null) erros.add("Dados do sinistro devem ser informados");

        else {
            if (dados.getDataHoraSinistro() == null) erros.add("Data/hora do sinistro deve ser informada");
            if (dados.getPlaca() == null || dados.getPlaca().isBlank()) erros.add("Placa do Ve�culo deve ser informada");
            if (dados.getUsuarioRegistro() == null || dados.getUsuarioRegistro().isBlank())  erros.add("Usu�rio do registro de sinistro deve ser informado");
            if (dados.getValorSinistro() <= 0.0) erros.add("Valor do sinistro deve ser maior que zero");
            if (TipoSinistro.getTipoSinistro(dados.getCodigoTipoSinistro()) == null) erros.add("C�digo do tipo de sinistro inv�lido");

        }



        if (!erros.isEmpty()) throw new ExcecaoValidacaoDados(erros);

        return null;
    }
}
