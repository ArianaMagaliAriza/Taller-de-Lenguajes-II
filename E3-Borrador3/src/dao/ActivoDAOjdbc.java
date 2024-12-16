package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import modelo.Activo;

public class ActivoDAOjdbc implements ActivoDAO{
	private Connection con;
	
	public ActivoDAOjdbc(Connection con) {
		this.con = con; 
	}
	
	//chequea si el activo está en la base de datos
	public boolean activoEnBD(int idMoneda) {
		boolean existe=false;
		try{
			String query = "SELECT * FROM WHERE id_moneda=?";
			PreparedStatement st = con.prepareStatement(query);
			st.clearParameters();
			st.setInt(1, idMoneda);
			ResultSet res = st.executeQuery();
			if(res.next())
				existe=true;
		}catch(SQLException e) {
			System.out.print("Error de SQL: "+e.getMessage());
		}
		return existe;
	}
	
	public void cargarActivo(Activo activo) {
		try {
			String query = "INSERT INTO activo(id_usuario,id_moneda,cantidad) VALUES(?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.clearParameters();
			st.setInt(1, activo.getIdUsuario());
			st.setInt(2, activo.getIdMoneda());
			st.setDouble(3, activo.getCantidad());
			st.executeUpdate();
			st.close();
		}catch(SQLException e) {
			System.out.print("Error de SQL: "+e.getMessage());
		}
	}
	public List<Activo> listarActivos(){
		List<Activo> activos = new LinkedList<>();
		try {
			String query = "SELECT * FROM activo ORDER BY cantidad DESC";
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				Activo activo = new Activo(res.getInt("ID_USUARIO"),res.getInt("ID_MONEDA"),res.getDouble("cantidad"));
				activos.add(activo);
			}
			st.close();
			res.close();
		}catch(SQLException e) {
			System.out.print("Error de SQL: "+e.getMessage());
		}
		return activos;
	}
	public void actualizarActivo(int idUsuario,int idMoneda,Double cantidad) {
		try {
			String query = "UPDATE activo SET cantidad = cantidad + ? WHERE id_usuario=? AND id_moneda=?";
			PreparedStatement st = con.prepareStatement(query);
			st.setDouble(1,cantidad);
			st.setInt(2,idUsuario);
			st.setInt(3,idMoneda);
			st.executeUpdate();
			st.close();
		}catch(SQLException e) {
			System.out.print("Error de SQL: "+e.getMessage());
		}
	}
	public Activo obtenerActivo(int id) {
		Activo activo=null;
		try {
			String query = "SELECT * FROM activo";
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				if(res.getInt("ID_MONEDA")==id) {
					activo = new Activo(res.getInt("ID_USUARIO"),res.getInt("ID_MONEDA"),res.getDouble("cantidad"));
				}
			}
			st.close();
		} catch (SQLException e) {
            System.out.print("Error de SQL:"+e.getMessage());
        }
		return activo;
	}
	public void cargarStockActivo(int idUsuario){
        try{
       	// Valores de prueba
           	String queryActualizar = "INSERT INTO activo (id_usuario,id_moneda,cantidad) VALUES(?, '1', '100')";
           	PreparedStatement statement = con.prepareStatement(queryActualizar);
           	statement.setInt(1, idUsuario);
           	statement.executeUpdate();
           	queryActualizar = "INSERT INTO activo (id_usuario,id_moneda,cantidad) VALUES(?, '2', '5000')";
           	statement = con.prepareStatement(queryActualizar);
           	statement.setInt(1, idUsuario);
           	statement.executeUpdate();
           	queryActualizar = "INSERT INTO activo (id_usuario,id_moneda,cantidad) VALUES(?, '3', '15000')";
           	statement = con.prepareStatement(queryActualizar);
           	statement.setInt(1, idUsuario);
           	statement.executeUpdate();
       	statement.close();
       } catch (SQLException e) {
           System.out.print("Error de SQL:"+e.getMessage());
       }
	}
}
