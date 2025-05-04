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
        }else if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
            return "CPF deve ter 11 caracteres";
        }
        try {
            int soma1 = 0, soma2 = 0;
            for (int i = 0; i < 9; i++) {
                int digito = Character.getNumericValue(cpf.charAt(i));
                soma1 += digito * (10 - i);
                soma2 += digito * (11 - i);
            }

            int dv1 = soma1 % 11;
            dv1 = dv1 < 2 ? 0 : 11 - dv1;

            soma2 += dv1 * 2;
            int dv2 = soma2 % 11;
            dv2 = dv2 < 2 ? 0 : 11 - dv2;

            int digito10 = Character.getNumericValue(cpf.charAt(9));
            int digito11 = Character.getNumericValue(cpf.charAt(10));

            if (dv1 != digito10 || dv2 != digito11) {
                return "CPF com dígito inválido";
            }

        } catch (NumberFormatException e) {
            return "CPF com dígito inválido";
        }
        return null;
    }
    public String validarRenda(double renda) {
        if (renda <= 0) {
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
        return null;
    }
    public String excluirSeguradoPessoa(String cpf) {
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
            return erroCpf;  // pode ser: "CPF deve ser informado" ou "CPF com dígito inválido"
        }

        if (seg.getRenda() < 0) {
            return "Renda deve ser maior ou igual à zero";
        }

        return null;
    }
}
