package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Registro;

public abstract class DAOGenerico<D extends Registro> {
    private CadastroObjetos cadastro;

    public DAOGenerico() {
        this.cadastro =  new CadastroObjetos(getClasseEntidade());
    }

    public abstract Class<D> getClasseEntidade();

    public boolean incluir(D entidade) {
        if (buscar(entidade) != null) {
            return false;
        }
        cadastro.incluir(entidade, entidade.getIdUnico());
        return true;
    }

    public boolean alterar(D entidade) {
        if (buscar(entidade) == null) {
            return false;
        }
        cadastro.alterar(entidade, entidade.getIdUnico());
        return true;
    }

    public D buscar(D entidade)
    {
        return (D) cadastro.buscar(entidade.getIdUnico());
    }

    public boolean excluir(D entidade)
    {
        if (buscar(entidade) == null) {
            return false;
        }
        cadastro.excluir(entidade.getIdUnico());
        return true;
    }

    public Registro[] buscarTodos()
    {
        return (Registro[]) cadastro.buscarTodos();
    }
}
