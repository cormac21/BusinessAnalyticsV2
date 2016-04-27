package cormacx.prova;

public class Row {

	public float valor;
	public float nivel;
	public float tendencia;
	public float componenteTemporal;
	public float erroMedio;
	public float erroMedioPercentual;
	
	public Row(Float aux) {
		this.valor = aux;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getNivel() {
		return nivel;
	}

	public void setNivel(float nivel) {
		this.nivel = nivel;
	}

	public float getTendencia() {
		return tendencia;
	}

	public void setTendencia(float tendencia) {
		this.tendencia = tendencia;
	}

	public float getErroMedio() {
		return erroMedio;
	}

	public void setErroMedio(float erroMedio) {
		this.erroMedio = erroMedio;
	}

	public float getErroMedioPercentual() {
		return erroMedioPercentual;
	}

	public void setErroMedioPercentual(float erroMedioPercentual) {
		this.erroMedioPercentual = erroMedioPercentual;
	}

	public float getComponenteTemporal() {
		return componenteTemporal;
	}

	public void setComponenteTemporal(float componenteTemporal) {
		this.componenteTemporal = componenteTemporal;
	}
}
