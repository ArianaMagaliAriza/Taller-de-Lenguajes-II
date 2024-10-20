package modelo;

public abstract class Cripto extends Moneda{
	private String etiqueta;
	private int direccion;
	

	public Cripto(String nombre, String etiqueta, int direccion) {
		super(nombre);
		this.etiqueta = etiqueta;
		this.direccion = direccion;
	}


	public String getEtiqueta() {
		return etiqueta;
	}


	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}


	public int getDireccion() {
		return direccion;
	}


	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

	public abstract Double getStock();

	public abstract Double getCotizacion();

}
