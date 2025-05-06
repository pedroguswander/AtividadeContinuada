package br.edu.cs.poo.ac.seguro.mediators;

public class ValidadorCpfCnpj {

    public static boolean ehCnpjValido(String cnpj) {
        if (cnpj == null || cnpj.length() != 14 || !StringUtils.temSomenteNumeros(cnpj)) {
            return false;
        }

        try {
            int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma1 = 0, soma2 = 0;
            for (int i = 0; i < 12; i++) {
                int digito = Character.getNumericValue(cnpj.charAt(i));
                soma1 += digito * pesos1[i];
                soma2 += digito * pesos2[i];
            }

            int dv1 = soma1 % 11;
            dv1 = dv1 < 2 ? 0 : 11 - dv1;

            soma2 += dv1 * pesos2[12];
            int dv2 = soma2 % 11;
            dv2 = dv2 < 2 ? 0 : 11 - dv2;

            return dv1 == Character.getNumericValue(cnpj.charAt(12)) &&
                    dv2 == Character.getNumericValue(cnpj.charAt(13));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean ehCpfValido(String cpf) {
        if (cpf == null || cpf.length() != 11 || !StringUtils.temSomenteNumeros(cpf)) {
            return false;
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

            return dv1 == Character.getNumericValue(cpf.charAt(9)) &&
                    dv2 == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
