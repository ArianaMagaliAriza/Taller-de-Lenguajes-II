package dao;

import java.util.List;

import modelo.Moneda;

public interface MonedaDAO {
public boolean monedaEnBD(String nomenclatura);
public void cargarMoneda(Moneda moneda);
public List<Moneda> listarMonedas();
public void crearStock(String moneda,Double stock);
//public List<Moneda> listarStocks();
public Moneda obtenerMoneda(int id);
public void actualizarStock(String moneda,Double stock);
}
