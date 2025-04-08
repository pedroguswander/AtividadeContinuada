package br.edu.cs.poo.ac.seguro.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sinistro {
	private String numero;
	private Veiculo veiculo;
	private LocalDateTime dataHoraSinistro;
	private LocalDateTime dataHoraRegistro;
	private String usuarioRegistro;
	private BigDecimal valorSinistro;
	private TipoSinistro tipo;
	public Sinistro(String numero, Veiculo veiculo, LocalDateTime dataHoraSinistro, LocalDateTime dataHoraRegistro,
			String usuarioRegistro, BigDecimal valorSinistro, TipoSinistro tipo) {
		super();
		this.numero = numero;
		this.veiculo = veiculo;
		this.dataHoraSinistro = dataHoraSinistro;
		this.dataHoraRegistro = dataHoraRegistro;
		this.usuarioRegistro = usuarioRegistro;
		this.valorSinistro = valorSinistro;
		this.tipo = tipo;
	}
	
	
}
