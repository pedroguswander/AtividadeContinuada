package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Segurado;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoEmpresaDAO extends SeguradoDAO {
    public SeguradoEmpresaDAO() {
        super();
    }

    public SeguradoEmpresa buscar(String numero)
    {
        return (SeguradoEmpresa) super.buscar(numero);
    }

}