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
        if (buscar(entidade.getIdUnico()) != null) {
            return false;
        }
        cadastro.incluir(entidade, entidade.getIdUnico());
        return true;
    }

    public boolean alterar(D entidade) {
        if (buscar(entidade.getIdUnico()) == null) {
            return false;
        }
        cadastro.alterar(entidade, entidade.getIdUnico());
        return true;
    }

    public D buscar(String idUnico)
    {
        return (D) cadastro.buscar(idUnico);
    }

    public boolean excluir(String idUnico)
    {
        if (buscar(idUnico) == null) {
            return false;
        }
        cadastro.excluir(idUnico);
        return true;
    }

    public Registro[] buscarTodos()
    {
        return (Registro[]) cadastro.buscarTodos();
    }
}
