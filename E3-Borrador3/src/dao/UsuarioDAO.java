package dao;

import modelo.Usuario;

public interface UsuarioDAO {
	public void cargarUsuario(Usuario usuario);
	public boolean usuarioEnBD(String email);
	public Usuario obtenerUsuario(String email); 
}
