package modelo;

public class Transaccion {
	private int id;
	private String resumen;
	private String fecha;
	private int idUser;
	
	public Transaccion(String resumen, String fecha, int idUser) {
		this.resumen = resumen;
		this.fecha = fecha;
		this.idUser = idUser;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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
