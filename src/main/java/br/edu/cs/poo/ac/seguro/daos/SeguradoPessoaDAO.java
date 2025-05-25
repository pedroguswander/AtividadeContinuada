package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Segurado;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

/*
 * As classes Segurado e SeguradoPessoa devem implementar Serializable.
 */
public class SeguradoPessoaDAO extends SeguradoDAO {
    public SeguradoPessoaDAO() {
        super();
    }

    public SeguradoPessoa buscar(String numero)
    {
        return (SeguradoPessoa) super.buscar(numero);
    }


}