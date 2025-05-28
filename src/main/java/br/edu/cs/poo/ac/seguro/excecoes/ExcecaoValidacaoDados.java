package br.edu.cs.poo.ac.seguro.excecoes;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExcecaoValidacaoDados extends Exception {
    List<String> mensagens = new ArrayList<String>();
    public ExcecaoValidacaoDados(String message) {
        super(message);
        mensagens.add(message);
    }

}
