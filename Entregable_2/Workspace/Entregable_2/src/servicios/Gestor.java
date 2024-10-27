package servicios;

import java.sql.SQLException;
import java.util.List;

import dao.MonedaDAO;
import modelo.Moneda;

public class Gestor {
	private MonedaDAO monedaDAO;
	
	public Operaciones(MonedaDAO monedaDAO) {
		this.monedaDAO=monedaDAO;
	}
	
	public void cargarMoneda(String tipo, String nombre,String nomenclatura, double valorEnDolar, double volatilidad, double stock) throws SQLException{
		if(!tipo.equals("Cripto") && !tipo.equals("Fiat")) {
			System.out.print("Error, debe ingresar 'Cripto' o 'Fiat'");
			return;
		}
		else {
			Moneda moneda=new Moneda(tipo,nombre,nomenclatura,valorEnDolar,volatilidad,stock);
			monedaDAO.cargarMoneda(moneda);
			System.out.println("Moneda agregada exitosamente.");
		}
	}
	public void listarMonedas() throws SQLException{
			List<Moneda> monedas = monedaDAO.listarMonedas();
			for(Moneda moneda: monedas){
				System.out.println(moneda.getNombre()+"("+moneda.getNomenclatura()+")"+" Valor:"+moneda.getValorEnDolar()+" USD"+ " Volatilidad:"+moneda.getVolatilidad()+" Stock disponible:" +moneda.getStock());
			}
	}
	public void generarStock()throws SQLException{
		
	}
}
