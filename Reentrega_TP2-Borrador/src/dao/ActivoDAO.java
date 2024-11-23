package dao;

import java.util.List;

import modelo.Activo;

public interface ActivoDAO {
	public boolean activoEnBD(String nomenclatura);
	public void cargarActivo(Activo activo);
	public List<Activo> listarActivos();
	public void actualizarActivo(String nomenclatura,Double cantidad);
	public Activo obtenerActivo(String nomenclatura);
}
