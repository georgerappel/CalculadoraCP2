package com.testemedia.mediacp2.boletim;

public class Materias {

	private String nome;
	private int id;
	private float nota1, nota2, PreNota3, nota3, PrePFV, PFV, MA;

	// Construtor sem a nota3. Caso seja chamado, esse construtor considera que
	// nao houve nota 3 ainda e faz a estimativa da nota 3, colocando 0 para as
	// outras notas que ainda nao podem ser calculadas.
	public Materias(String nome, float nota1, float nota2) {
		this.nome = nome;
		this.nota1 = nota1;
		this.nota2 = nota2;
		this.nota3 = 0;
		if (nota2 == 0)
			this.PreNota3 = 0;
		else
			this.PreNota3 = Pre3Tri(nota1, nota2);
		this.MA = 0;
		this.PFV = 0;
		this.PrePFV = 0;
	}

	public Materias(String nome, float nota1, float nota2, float nota3) {
		this.nome = nome;
		this.nota1 = nota1;
		this.nota2 = nota2;
		this.nota3 = nota3;
		this.PreNota3 = Pre3Tri(nota1, nota2);
		this.PFV = 0;
		if (this.nota3 == 0) {
			this.MA = 0;
			this.PrePFV = 0;
		} else {
			this.MA = MA(nota1, nota2, nota3);
			if (this.MA < 7)
				this.PrePFV = PrePFV(nota1, nota2, nota3);
			else
				this.PrePFV = 0;
		}
	}

	public Materias(String nome, float nota1, float nota2, float nota3,
			float PFV) {
		this.nome = nome;
		this.nota1 = nota1;
		this.nota2 = nota2;
		this.nota3 = nota3;
		this.PFV = PFV;
		this.PreNota3 = Pre3Tri(nota1, nota2);
		if (nota3 == 0) {
			this.MA = 0;
			this.PrePFV = 0;
		} else {
			this.MA = MA(nota1, nota2, nota3);
			if (this.MA < 7)
				this.PrePFV = PrePFV(nota1, nota2, nota3);
			else
				this.PrePFV = 0;
		}
	}

	public Materias(int id, String nome, float nota1, float nota2, float nota3,
			float PFV) {
		this.id = id;
		this.nome = nome;
		this.nota1 = nota1;
		this.nota2 = nota2;
		this.nota3 = nota3;
		this.PFV = PFV;
		this.PreNota3 = Pre3Tri(nota1, nota2);
		if (nota3 == 0) {
			this.MA = 0;
			this.PrePFV = 0;
		} else {
			this.MA = MA(nota1, nota2, nota3);
			if (this.MA < 7)
				this.PrePFV = PrePFV(nota1, nota2, nota3);
			else
				this.PrePFV = 0;
		}
	}

	public Materias() {
		this.nome = "";
	}

	public float Pre3Tri(float nota1, float nota2) {
		return ((70 - ((nota1 * 3) + (nota2 * 3))) / 4);
	}

	public float PrePFV(float nota1, float nota2, float nota3) {
		return (25 - ((((nota1 * 3) + (nota2 * 3) + (nota3 * 4)) / 10) * 3)) / 2;

	}

	public float MA(float nota1, float nota2, float nota3) {
		return ((nota1 * 3) + (nota2 * 3) + (nota3 * 4)) / 10;
	}

	public String getNome() {
		return nome;
	}

	public float getNota1() {
		return nota1;
	}

	public float getNota2() {
		return nota2;
	}

	public float getNota3() {
		return nota3;
	}

	public float getPreNota3() {
		return PreNota3;
	}

	public float getMA() {
		return MA;
	}

	public float getPFV() {
		return PFV;
	}

	public float getPrePFV() {
		return PrePFV;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNota1(float nota1) {
		this.nota1 = nota1;
		this.MA = MA(nota1, nota2, nota3);
		if (this.MA < 7)
			this.PrePFV = PrePFV(nota1, nota2, nota3);
		else
			this.PrePFV = 0;
	}

	public void setNota2(float nota2) {
		this.nota2 = nota2;
		this.MA = MA(nota1, nota2, nota3);
		if (this.MA < 7)
			this.PrePFV = PrePFV(nota1, nota2, nota3);
		else
			this.PrePFV = 0;
	}

	public void setNota3(float nota3) {
		this.nota3 = nota3;
		if (nota3 == 0) {
			this.MA = 0;
			this.PrePFV = 0;
		} else {
			this.MA = MA(nota1, nota2, nota3);
			if (this.MA < 7)
				this.PrePFV = PrePFV(nota1, nota2, nota3);
		}
	}

	public void setPFV(float nota) {
		this.PFV = nota;
	}

}
