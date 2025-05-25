package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;

import java.io.Serializable;
import java.util.List;

public class SinistroDAO extends DAOGenerico<Sinistro> {
    public SinistroDAO() {
        super();
    }

    @Override
    public Class<Sinistro> getClasseEntidade() {
        return Sinistro.class;
    }

    /*public Serializable[] buscarTodos()
    {
        return cadastro.buscarTodos(Sinistro.class);
    }*/
}
