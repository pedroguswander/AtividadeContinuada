package br.edu.cs.poo.ac.seguro.mediators;

public class StringUtils {
    private StringUtils() {}
    public static boolean ehNuloOuBranco(String str) {
        if (str == null || str.isBlank())
        {
            return true;
        }
        return false;
    }
    public static boolean temSomenteNumeros(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) < 48 || input.charAt(i) > 57) {
                return false;
            }
        }
        return true;
    }
}
