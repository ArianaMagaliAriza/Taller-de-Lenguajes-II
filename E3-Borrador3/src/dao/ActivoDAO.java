package dao;

import java.util.List;

import modelo.Activo;

public interface ActivoDAO {
	public boolean activoEnBD(int id);
	public void cargarActivo(Activo activo);
	public List<Activo> listarActivos();
	public void actualizarActivo(int idUsuario,int idMoneda,Double cantidad);
	public Activo obtenerActivo(int id);
}
