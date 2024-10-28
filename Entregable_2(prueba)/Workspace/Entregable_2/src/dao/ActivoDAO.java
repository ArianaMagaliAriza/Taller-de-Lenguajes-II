package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import modelo.Activo;

public class ActivoDAO {
	private Connection con;
	
	public ActivoDAO(Connection con) {
		this.con = con; 
	}
	
	//chequea si el activo está en la base de datos
	public boolean activoEnBD(String nomenclatura) {
		boolean existe=false;
		try{
			String query = "SELECT * FROM activo";
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
	
	public void cargarActivo(Activo activo) {
		try {
			String query = "INSERT INTO activo(nomenclatura,cantidad) VALUES(?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.clearParameters();
			st.setString(1, activo.getNomenclatura());
			st.setDouble(2, activo.getCantidad());
			st.executeUpdate();
			st.close();
		}catch(SQLException e) {
			System.out.print("Error de SQL: "+e.getMessage());
		}
	}
	public List<Activo> listarActivos(){
		List<Activo> activos = new LinkedList<>();
		try {
			String query = "SELECT * FROM activo";
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				Activo activo = new Activo(res.getDouble("cantidad"),res.getString("nomenclatura"));
				activos.add(activo);
			}
			st.close();
			res.close();
		}catch(SQLException e) {
			System.out.print("Error de SQL: "+e.getMessage());
		}
		return activos;
	}
	public void actualizarActivo(String nomenclatura,Double cantidad) {
		try {
			String query = "UPDATE activo SET cantidad = cantidad + ? WHERE nomenclatura = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setDouble(1,cantidad);
			st.setString(2,nomenclatura);
			st.executeUpdate();
			st.close();
		}catch(SQLException e) {
			System.out.print("Error de SQL: "+e.getMessage());
		}
	}
	public Activo obtenerActivo(String nomenclatura) {
		Activo activo=null;
		try {
			String query = "SELECT * FROM activo";
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				if(res.getString("nomenclatura").equals(nomenclatura)) {
					activo = new Activo(res.getDouble("cantidad"),res.getString("nomenclatura"));
				}
			}
			st.close();
		} catch (SQLException e) {
            System.out.print("Error de SQL:"+e.getMessage());
        }
		return activo;
	}
}
