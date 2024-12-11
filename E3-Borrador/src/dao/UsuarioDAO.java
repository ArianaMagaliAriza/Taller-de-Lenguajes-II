package dao;

import modelo.Usuario;

public interface UsuarioDAO {
public boolean usuarioEnBD(String email, String password);
public void cargarUsuario(Usuario usuario);
public Usuario obtenerUsuario(String email);
}
