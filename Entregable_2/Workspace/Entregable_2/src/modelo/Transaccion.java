package modelo;

public abstract class Transaccion {
	private Double monto;
	private Cripto moneda;
	
	public Transaccion(Double monto, Cripto moneda) {
		this.monto = monto;
		this.moneda = moneda;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Cripto getMoneda() {
		return moneda;
	}

	public void setMoneda(Cripto moneda) {
		this.moneda = moneda;
	}


}
