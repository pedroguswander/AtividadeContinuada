package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {
    private SeguradoEmpresaDAO seguradoEmpresaDAO;
    private static SeguradoEmpresaMediator instancia;

    private SeguradoEmpresaMediator() {
        seguradoEmpresaDAO = new SeguradoEmpresaDAO();
    }

    static public SeguradoEmpresaMediator getInstancia()
    {
        if (instancia == null)
        {
            instancia = new SeguradoEmpresaMediator();
        }
        return instancia;
    }

    public String validarCnpj(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return "CNPJ deve ser informado";
        } else if (cnpj.length() != 14) {
            return "CNPJ deve ter 14 caracteres";
        } else if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
            return "CNPJ com dígito inválido";
        } else if (!cnpj.substring(12, 14).equals("74")) {
            return "CNPJ com dígito inválido";
        }
        return null;
    }


    public String validarFaturamento(double faturamento) {
        if (faturamento <= 0) {
            return "Faturamento deve ser maior que zero";
        }
        return null;
    }
    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String erro = validarSeguradoEmpresa(seg);
        if (erro != null) {
            return erro;
        }

        boolean sucesso = seguradoEmpresaDAO.incluir(seg);
        if (!sucesso) {
            return "CNPJ do segurado empresa já existente";
        }
        return null;
    }
    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String erro = validarSeguradoEmpresa(seg);
        if (erro != null) {
            return erro;
        }

        boolean sucesso = seguradoEmpresaDAO.alterar(seg);
        if (!sucesso) {
            return "CNPJ do segurado empresa não existente";
        }
        return null;
    }
    public String excluirSeguradoEmpresa(String cnpj) {
        String erro = validarCnpj(cnpj);
        if (erro != null) {
            return erro;
        }

        boolean sucesso = seguradoEmpresaDAO.excluir(cnpj);
        if (!sucesso) {
            return "CNPJ do segurado empresa não existente";
        }
        return null;
    }
    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {

        return seguradoEmpresaDAO.buscar(cnpj);
    }
    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        if (seg == null) {
            return "Segurado inválido";
        }

        if (seg.getNome() == null || seg.getNome().trim().isEmpty()) {
            return "Nome deve ser informado";
        }

        if (seg.getEndereco() == null) {
            return "Endereço deve ser informado";
        }

        if (seg.getDataAbertura() == null) {
            return "Data da abertura deve ser informada";
        }

        String erro = validarCnpj(seg.getCnpj());
        if (erro != null) {
            return erro;
        }

        if (seg.getFaturamento() <= 0.0) {
            return "Faturamento deve ser maior que zero";
        }

        return null;
    }

}
