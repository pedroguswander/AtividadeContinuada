package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.entidades.Sinistro;

import java.util.Comparator;

public class ComparadorSinistroSequencial implements Comparator<Sinistro> {
    @Override
    public int compare(Sinistro o1, Sinistro o2) {
        return Integer.compare(o2.getSequencial(), o1.getSequencial());
    }
}
