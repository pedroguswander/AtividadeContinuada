package br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {

    public static boolean ehCnpjValido(String cnpj) {
        if (cnpj.length() == 14 && StringUtils.temSomenteNumeros(cnpj)) {
            return true;
        }
        return false;
    }
    public static boolean ehCpfValido(String cpf) {
        if (cpf.length() == 11 && StringUtils.temSomenteNumeros(cpf)) {
            return true;
        }
        return false;
    }
}
