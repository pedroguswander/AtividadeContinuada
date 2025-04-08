package br.edu.cs.poo.ac.seguro.entidades;

public enum TipoSinistro {
	COLISAO(1,"Colisão"),
	INCENDIO(2,"Incêndio"),
	FURTO(3, "Furto"),
	ENCHENTE(4, "Enchente"),
	DEPREDACAO(5, "Depredação");
	
	private int codigo;
	private String nome;
	
	private TipoSinistro(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public static TipoSinistro getTipoSinistro(int codigo)
	{
		switch (codigo) {
		case 1:
			return TipoSinistro.COLISAO;
		case 2:
			return TipoSinistro.INCENDIO;
		case 3:
			return TipoSinistro.FURTO;
		case 4:
			return TipoSinistro.ENCHENTE;
		case 5:
			return TipoSinistro.DEPREDACAO;
		default:
			return TipoSinistro.COLISAO;
		}
	}
}
