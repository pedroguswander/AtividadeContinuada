package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaMediator {
    private SeguradoMediator seguradoMediator;
    private SeguradoPessoaDAO seguradoPessoaDAO;
    static private SeguradoPessoaMediator instancia;

    public SeguradoPessoaMediator() {
        this.seguradoPessoaDAO = new SeguradoPessoaDAO();
    }



    static public SeguradoPessoaMediator getInstancia()
    {
        if (instancia == null)
        {
            instancia = new SeguradoPessoaMediator();
        }
        return instancia;
    }

    public String validarCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return "CPF deve ser informado";
        } else if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
            if (cpf.length() != 11) {
                return "CPF deve ter 11 caracteres";
            }
            return "CPF com dígito inválido";
        }
        return null;
    }

    public String validarRenda(double renda) {
        if (renda < 0) {
            return "Renda deve ser maior ou igual à zero";
        }
        return null;
    }
    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String erro = validarSeguradoPessoa(seg);
        if (erro != null) {
            return erro;
        }

        boolean sucesso = seguradoPessoaDAO.incluir(seg);
        if (!sucesso) {
            return "CPF do segurado pessoa já existente";
        }
        return null;
    }
    public String alterarSeguradoPessoa(SeguradoPessoa seg) {
        String erro = validarSeguradoPessoa(seg);
        if (erro != null) {
            return erro;
        }

        boolean sucesso = seguradoPessoaDAO.alterar(seg);
        if (!sucesso) {
            return "CPF do segurado pessoa não existente";
        }
        return null;
    }
    public String excluirSeguradoPessoa(String cpf) {
        String erro = validarCpf(cpf);
        if (erro != null) {
            return erro;
        }

        boolean sucesso = seguradoPessoaDAO.excluir(cpf);
        if (!sucesso) {
            return "CPF do segurado pessoa não existente";
        }
        return null;
    }
    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
        String erro = validarCpf(cpf);
        if (erro != null) {
            return null;
        }

        return seguradoPessoaDAO.buscar(cpf);
    }
    public String validarSeguradoPessoa(SeguradoPessoa seg) {
        if (seg == null) {
            return "Segurado inválido";
        }

        if (seg.getNome() == null || seg.getNome().trim().isEmpty()) {
            return "Nome deve ser informado";
        }

        if (seg.getEndereco() == null) {
            return "Endereço deve ser informado";
        }

        if (seg.getDataNascimento() == null) {
            return "Data do nascimento deve ser informada";
        }

        String erroCpf = validarCpf(seg.getCpf());
        if (erroCpf != null) {
            return erroCpf;
        }

        if (seg.getRenda() < 0) {
            return "Renda deve ser maior ou igual à zero";
        }

        return null;
    }
}
