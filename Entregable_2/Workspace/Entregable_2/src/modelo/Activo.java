package modelo;

public class Activo {
	private Double cantidad;
	private Moneda moneda;

	public Activo(Double cantidad, Moneda moneda) {
		this.cantidad = cantidad;
		this.moneda = moneda;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	
	public void crearMoneda(String nombre){
		Moneda m= new Moneda(nombre);
	}

}
