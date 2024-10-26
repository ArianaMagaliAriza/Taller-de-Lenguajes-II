package modelo;

public abstract class Moneda {
	private String nombre;
	
	public Moneda(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
