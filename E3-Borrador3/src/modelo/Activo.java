package modelo;

public class Activo {
	private int idUsuario;
	private int idMoneda;
	private Double cantidad; //representa la cantidad que el usuario posee

	public Activo(int idUsuario,int idMoneda,Double cantidad) {
		this.idUsuario=idUsuario;
		this.idMoneda=idMoneda;
		this.cantidad = cantidad;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(int idMoneda) {
		this.idMoneda = idMoneda;
	}
}
