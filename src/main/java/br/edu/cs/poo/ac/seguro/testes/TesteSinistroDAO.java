package br.edu.cs.poo.ac.seguro.testes;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TesteSinistroDAO extends TesteDAO{
    private SinistroDAO dao = new SinistroDAO();

    protected Class getClasse() {
        return Sinistro.class;
    }

    @Test //excluir um Sinistro errado
    public void teste01() {
        String numero = "01";
        cadastro.incluir((Serializable) new Sinistro(numero, null, null,
                null, null, BigDecimal.ZERO, TipoSinistro.COLISAO), numero);
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

}
