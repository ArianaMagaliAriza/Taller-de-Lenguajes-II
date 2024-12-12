package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import modelo.Moneda;

public class MonedaDAOjdbc implements MonedaDAO{
	private Connection con;
	
	public MonedaDAOjdbc(Connection con) {
		this.con=con;
	}
	
	//chequea si la moneda está en la base de datos
		public boolean monedaEnBD(String nomenclatura) {
			boolean existe=false;
			try{
				String query = "SELECT * FROM moneda";
				Statement st = con.createStatement();
				ResultSet res = st.executeQuery(query);
				while(res.next()) {
					if(res.getString("nomenclatura").equals(nomenclatura)) {
						existe=true;
					}
				}
			}catch(SQLException e) {
				System.out.print("Error de SQL: "+e.getMessage());
			}
			return existe;
		}
		
	public void cargarMoneda(Moneda moneda){
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
	
	public List<Moneda> listarMonedas(){
		List<Moneda> monedas = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			String query = "SELECT * FROM moneda";
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				Moneda moneda = new Moneda(res.getString("tipo"),res.getString("nombre"),res.getString("nomenclatura"),res.getDouble("valor_dolar"),res.getDouble("volatilidad"),res.getDouble("stock"),res.getString("nombre_icono"));
				monedas.add(moneda);
			}
			st.close();
			res.close();
		}catch (SQLException e) {
			System.out.println("Error de SQL(listarMoneda()): " + e.getMessage());
		}
		return monedas;
	}
	
	public void crearStock(String moneda,Double stock){
         try{
        	// Actualizar el stock en la tabla de monedas
        	String queryActualizar = "UPDATE moneda SET stock = ? WHERE nomenclatura = ?";
        	PreparedStatement statement = con.prepareStatement(queryActualizar);
        	statement.setDouble(1, stock);
        	statement.setString(2, moneda);
        	statement.executeUpdate();
        	statement.close();
        } catch (SQLException e) {
            System.out.print("Error de SQL:"+e.getMessage());
        }
	}/*
	public List<Moneda> listarStocks() {
		List<Moneda> monedas = new LinkedList<>();
		try {
			String query = "SELECT * FROM moneda ORDER BY stock DESC";
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				Moneda moneda = new Moneda(res.getString("tipo"),res.getString("nombre"),res.getString("nomenclatura"),res.getDouble("valor_dolar"),res.getDouble("volatilidad"),res.getDouble("stock"));
				monedas.add(moneda);
			}
			st.close();
		} catch (SQLException e) {
            System.out.print("Error de SQL:"+e.getMessage());
        }
		return monedas;
	}*/
	/*
	public Moneda obtenerMoneda(String nomenclatura) {
		Moneda moneda=null;
		try {
			String query = "SELECT * FROM moneda";
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				if(res.getString("nomenclatura").equals(nomenclatura)) {
					moneda = new Moneda(res.getString("tipo"),res.getString("nombre"),res.getString("nomenclatura"),res.getDouble("valor_dolar"),res.getDouble("volatilidad"),res.getDouble("stock"));
					
				}
			}
			st.close();
		} catch (SQLException e) {
            System.out.print("Error de SQL:"+e.getMessage());
        }
		return moneda;
	}
*/
	public Moneda obtenerMoneda(int id) {
		Moneda moneda=null;
			try {
				String query = "SELECT * FROM MONEDA WHERE ID=?";
				PreparedStatement st = con.prepareStatement(query);
				st.setInt(1,id);
				ResultSet res = st.executeQuery();
				moneda = new Moneda(res.getString("TIPO"),res.getString("NOMBRE"),res.getString("NOMENCLATURA"),res.getDouble("VALOR_DOLAR"),res.getDouble("VOLATILIDAD"),res.getDouble("STOCK"),res.getString("NOMBRE_ICONO"));

				st.close();
			} catch (SQLException e) {
	            System.out.print("Error de SQL(obtenerMoneda):"+e.getMessage());
	        }
			return moneda;
	}
	public void actualizarStock(String moneda,Double stock){
        try{
        	// Actualizar el stock del activo en la tabla monedas
        	String queryActualizar = "UPDATE moneda SET stock = stock + ? WHERE nomenclatura = ?";
        	PreparedStatement statement = con.prepareStatement(queryActualizar);
        	statement.setDouble(1, stock);
        	statement.setString(2, moneda);
        	statement.executeUpdate();
        	statement.close();
       } catch (SQLException e) {
    	   	System.out.print("Error de SQL:"+e.getMessage());
       }
	}
}