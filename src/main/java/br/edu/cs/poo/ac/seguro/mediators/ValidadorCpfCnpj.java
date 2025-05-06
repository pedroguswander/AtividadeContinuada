package br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {

    public static boolean ehCnpjValido(String cnpj) {
        return cnpj.length() == 14 && StringUtils.temSomenteNumeros(cnpj);
    }

    public static boolean ehCpfValido(String cpf) {
        return cpf.length() == 11 && StringUtils.temSomenteNumeros(cpf) && validarDigitosVerificadores(cpf);
    }

    private static boolean validarDigitosVerificadores(String cpf) {
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

            return dv1 == digito10 && dv2 == digito11;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
