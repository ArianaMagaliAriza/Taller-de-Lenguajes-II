package billetera;

public class Transaccion {
	private Double monto;
	private Cripto moneda;
		

	/**
     * @param monto es la cantidad de cripto utilizada en la transaccion 
     * @param moneda es el tipo de cripto en el que se realiza la transaccion 
     */
	
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
