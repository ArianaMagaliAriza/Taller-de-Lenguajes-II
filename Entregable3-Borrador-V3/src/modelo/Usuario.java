package modelo;

public class Usuario {
	private String email;
	private String password;
	private Boolean aceptaTerminos;
	
	public Usuario(String email, String password, Boolean aceptaTerminos) {
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
	
	
}
