package modelo;

public class Transaccion {
	private String resumen;
	private String fecha;
	
	public Transaccion(String resumen, String fecha) {
		this.resumen = resumen;
		this.fecha = fecha;
	}
	
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
