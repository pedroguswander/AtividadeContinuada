package br.edu.cs.poo.ac.seguro.testes;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.math.BigDecimal;

public class TesteApoliceDAO extends ApoliceDAO {
    private ApoliceDAO dao = new ApoliceDAO();

    protected Class getClasse() {
        return Apolice.class;
    }

    @Test
    public void teste01() {
        String numero = "01";
        cadastro.incluir((Serializable) new Apolice(numero, null, BigDecimal.ZERO,  BigDecimal.ZERO,
                BigDecimal.ZERO), numero);
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste02() {
        String numero = "02";
        cadastro.incluir((Serializable) new Apolice(numero, null, BigDecimal.ZERO,  BigDecimal.ZERO,
                BigDecimal.ZERO), numero);
        boolean ret = dao.excluir("03");
        Assertions.assertFalse(ret);
    }

    @Test void teste03() {
        String numero = "03";
        Apolice apolice = new Apolice(numero, null, BigDecimal.ZERO,  BigDecimal.ZERO, BigDecimal.ZERO);
        cadastro.incluir(apolice, numero);
        boolean ret = dao.incluir(apolice);
        Assertions.assertFalse(ret);
    }

    @Test void teste04() {
        String numero = "04";
        Apolice apolice = new Apolice(numero, null, BigDecimal.ZERO,  BigDecimal.ZERO, BigDecimal.ZERO);
        cadastro.incluir(apolice, numero);
        apolice = new Apolice(numero, null, BigDecimal.ONE,  BigDecimal.ZERO, BigDecimal.ZERO);
        boolean ret = dao.alterar(apolice);
        Assertions.assertTrue(ret);
    }

    @Test void teste05() {
        String numero = "05";
        Apolice apolice = new Apolice(numero, null, BigDecimal.ZERO,  BigDecimal.ZERO, BigDecimal.ZERO);
        cadastro.incluir(apolice, numero);
        Apolice apolice1 = dao.buscar(numero);
        Assertions.assertNotNull(apolice1);
    }

    @Test void teste06() {
        String numero = "06";
        Apolice apolice = new Apolice(numero, null, BigDecimal.ZERO,  BigDecimal.ZERO, BigDecimal.ZERO);
        cadastro.incluir(apolice, numero);
        Apolice apolice1 = dao.buscar("07");
        Assertions.assertNull(apolice1);
    }

    @Test void teste07() {
        String numero = "07";
        Apolice apolice = new Apolice(numero, null, BigDecimal.ZERO,  BigDecimal.ZERO, BigDecimal.ZERO);
        boolean ret = dao.alterar(apolice);
        Assertions.assertFalse(ret);
        Apolice apolice1 = dao.buscar(numero);
        Assertions.assertNull(apolice1);
    }

    @Test void teste08() {
        String numero = "08";
        Apolice apolice = new Apolice(numero, null, BigDecimal.ZERO,  BigDecimal.ZERO, BigDecimal.ZERO);
        cadastro.incluir(apolice, numero);
        boolean ret = dao.incluir(apolice);
        Assertions.assertTrue(ret);
        Apolice apolice1 = dao.buscar(numero);
        Assertions.assertNotNull(apolice1);

    }

}
