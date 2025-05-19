package br.edu.cs.poo.ac.seguro.entidades;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@EqualsAndHashCode
public class Veiculo implements Serializable {
	private String placa;
	private int ano;
	private Segurado propietario;
	//private SeguradoEmpresa proprietarioEmpresa;
	//private SeguradoPessoa proprietarioPessoa;
	private CategoriaVeiculo categoria;

	public Veiculo(String placa, int ano, Segurado segurado, CategoriaVeiculo categoria) {
		this.placa = placa;
		this.ano = ano;
		this.propietario = segurado;
		this.categoria = categoria;
	}

}
