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

    @Test //excluir um Sinistro correto
    public void teste01() {
        String numero = "01";
        cadastro.incluir((Serializable) new Sinistro(numero, null, null,
                null, null, BigDecimal.ZERO, TipoSinistro.COLISAO), numero);
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test //excluir um Sinistro que não existe
    public void teste02() {
        String numero = "02";
        cadastro.incluir((Serializable) new Sinistro(numero, null, null,
                null, null, BigDecimal.ZERO, TipoSinistro.COLISAO), numero);
        boolean ret = dao.excluir("03");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste03() {//inserir um Sinistro que já existe
        String numero = "03";
        Sinistro sinistro = new Sinistro(numero, null, null, null,
                null, BigDecimal.ZERO, TipoSinistro.COLISAO);
        cadastro.incluir(sinistro, numero);
        boolean ret = dao.incluir(sinistro);
        Assertions.assertFalse(ret);
    }

    @Test //alterar um Sinistro que já existe
    public void teste04() {
        String numero = "04";
        Sinistro sinistro = new Sinistro(numero, null, null, null,
                null, BigDecimal.ZERO, TipoSinistro.COLISAO);
        cadastro.incluir(sinistro, numero);
        boolean ret = dao.alterar(sinistro);
        Assertions.assertTrue(ret);
    }

    @Test //buscando um Sinistro que já existe
    public void teste05() {
        String numero = "05";
        Sinistro sinistro = new Sinistro(numero, null, null, null,
                null, BigDecimal.ZERO, TipoSinistro.COLISAO);
        cadastro.incluir(sinistro, numero);
        Sinistro ret = dao.buscar(sinistro.getNumero());
        Assertions.assertNotNull(ret);
    }

    @Test //buscando um Sinistro que não existe
    public void teste06() {
        String numero = "06";
        Sinistro sinistro = new Sinistro(numero, null, null, null,
                null, BigDecimal.ZERO, TipoSinistro.COLISAO);
        cadastro.incluir(sinistro, numero);
        Sinistro ret = dao.buscar("07");
        Assertions.assertNull(ret);
    }

    @Test //alterando um Sinistro que não existe
    public void teste07() {
        String numero = "07";
        Sinistro sinistro = new Sinistro(numero, null, null, null,
                null, BigDecimal.ZERO, TipoSinistro.COLISAO);
        boolean ret = dao.alterar(sinistro);
        Assertions.assertFalse(ret);
        Sinistro sinistro1 = dao.buscar(numero);
        Assertions.assertNull(sinistro1);
    }

    @Test //inserir um Sinistro que não existe
    public void teste08() {
        String numero = "08";
        Sinistro sinistro = new Sinistro(numero, null, null, null,
                null, BigDecimal.ZERO, TipoSinistro.COLISAO);
        boolean ret = dao.incluir(sinistro);
        Assertions.assertTrue(ret);
        Sinistro sinistro1 = dao.buscar(numero);
        Assertions.assertNotNull(sinistro1);

    }
}
