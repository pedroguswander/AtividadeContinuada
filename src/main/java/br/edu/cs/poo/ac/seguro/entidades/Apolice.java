package br.edu.cs.poo.ac.seguro.entidades;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Apolice {
	private Veiculo veiculo;
	private BigDecimal valorFranquia;
	private BigDecimal valorPremio;
	private BigDecimal valorMaximoSegurado;
	
	public Apolice(Veiculo veiculo, BigDecimal valorFranquia, BigDecimal valorPremio, BigDecimal valorMaximoSegurado) {
		super();
		this.veiculo = veiculo;
		this.valorFranquia = valorFranquia;
		this.valorPremio = valorPremio;
		this.valorMaximoSegurado = valorMaximoSegurado;
	}
	
	
}
