package modelo;

public class Transaccion {
	private String resumen;
	private String fecha;
	private int idUsuario;
	
	public Transaccion(String resumen, String fecha,int idUsuario) {
		this.resumen = resumen;
		this.fecha = fecha;
		this.idUsuario=idUsuario;
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

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
