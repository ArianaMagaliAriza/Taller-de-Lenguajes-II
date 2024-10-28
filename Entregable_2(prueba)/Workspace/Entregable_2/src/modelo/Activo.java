package modelo;

public class Activo {
	private Double cantidad; //representa la cantidad que el usuario posee
	private String nomenclatura;

	public Activo(Double cantidad, String nomenclatura) {
		this.cantidad = cantidad;
		this.nomenclatura = nomenclatura;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getNomenclatura() {
		return nomenclatura;
	}

	public void setNomenclatura(String nomenclatura) {
		this.nomenclatura = nomenclatura;
	}
}
