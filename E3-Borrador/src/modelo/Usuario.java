package modelo;

public class Usuario{
	private int id;
	private int idPer;
	private String email;
	private String password;
	private Boolean aceptaTerminos;
	
	public Usuario(int idPer, String email, String password, Boolean aceptaTerminos) {
		this.idPer = idPer;
		this.email = email;
		this.password = password;
		this.aceptaTerminos = aceptaTerminos;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPer() {
		return idPer;
	}

	public void setIdPer(int idPer) {
		this.idPer = idPer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAceptaTerminos() {
		return aceptaTerminos;
	}

	public void setAceptaTerminos(Boolean aceptaTerminos) {
		this.aceptaTerminos = aceptaTerminos;
	}
	
	
}
