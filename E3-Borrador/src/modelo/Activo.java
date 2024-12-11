package modelo;

public class Activo {
	private int id;
	private Double cantidad; //representa la cantidad que el usuario posee
	private String nomenclatura;
	private int idMoneda;
	private int idUser;

	public Activo(Double cantidad, String nomenclatura, int idMoneda, int idUser) {
		this.cantidad = cantidad;
		this.nomenclatura = nomenclatura;
		this.idMoneda = idMoneda;
		this.idUser = idUser;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(int idMoneda) {
		this.idMoneda = idMoneda;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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
