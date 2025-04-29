package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class VeiculoDAO extends DAOGenerico {
    public VeiculoDAO() {
        cadastro = new CadastroObjetos(Veiculo.class);
    }

    public Veiculo buscar(String placa) {
        return (Veiculo)cadastro.buscar(placa);
    }
    public boolean incluir(Veiculo veiculo) {
        if (cadastro.buscar(veiculo.getPlaca()) != null) {
            return false;
        }
        cadastro.incluir(veiculo, veiculo.getPlaca());
        return true;
    }
    public boolean alterar(Veiculo veiculo) {
        if (cadastro.buscar(veiculo.getPlaca()) == null) {
            return false;
        }
        else {
            cadastro.alterar(veiculo, veiculo.getPlaca());
            return true;
        }
    }
    public boolean excluir(String placa) {
        if (cadastro.buscar(placa) == null) {
            return false;
        }
        else {
            cadastro.excluir(placa);
            return true;
        }
    }
}
