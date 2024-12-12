package modelo;

public class Usuario {
	private int id;
	private String email;
	private String password;
	private Boolean aceptaTerminos;
	
	public Usuario(int id,String email, String password, Boolean aceptaTerminos) {
		this.id=id;
		this.email = email;
		this.password = password;
		this.aceptaTerminos = aceptaTerminos;
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
