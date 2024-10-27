package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import modelo.Moneda;

public class MonedaDAO {
	private Connection con;
	
	public MonedaDAO(Connection con) {
		this.con=con;
	}
	
	public void cargarMoneda(Moneda moneda)throws SQLException{
		try {
			String query = "INSERT INTO MONEDA(tipo,nombre,nomenclatura,valor_dolar,volatilidad,stock) VALUES(?,?,?,?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.clearParameters();
			st.setString(1, moneda.getTipo());
			st.setString(2, moneda.getNombre());
			st.setString(3, moneda.getNomenclatura());
			st.setDouble(4, moneda.getValorEnDolar());
			st.setDouble(5, moneda.getVolatilidad());
			st.setDouble(6, moneda.getStock());
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error de SQL(cargarMoneda()): " + e.getMessage());
		}
	}
	
	public List<Moneda> listarMonedas() throws SQLException{
		List<Moneda> monedas = new LinkedList<>();
		try {
			Statement st = con.createStatement();
			String query = "SELECT * FROM moneda ORDER BY valor_dolar";
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				Moneda moneda = new Moneda(res.getString("tipo"),res.getString("nombre"),res.getString("nomenclatura"),res.getDouble("valor_dolar"),res.getDouble("volatilidad"),res.getDouble("stock"));
				monedas.add(moneda);
			}
			st.close();
			res.close();
		}catch (SQLException e) {
			System.out.println("Error de SQL: " + e.getMessage());
		}
		return monedas;
	}
	
	public void generarStock()throws SQLException{
		try {
			String query = "SELECT * FROM moneda";
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				
			}
		}catch(SQLException e) {
			System.out.print("Error de SQL:"+e.getMessage());
		}
	}
}
