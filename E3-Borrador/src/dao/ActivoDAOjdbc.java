package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import modelo.Activo;
import modelo.Usuario;
import servicios.Conexion;

public class ActivoDAOjdbc implements ActivoDAO{
	private Connection con;
	
	public ActivoDAOjdbc() {
		this.con = Conexion.getCon(); 
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
			String query = "INSERT INTO activo(cantidad,nomenclatura,id_usuario, id_moneda) VALUES(?,?,?,?)";
			PreparedStatement st = con.prepareStatement(query);
			st.clearParameters();
			st.setDouble(1, activo.getCantidad());
			st.setString(2, activo.getNomenclatura());
			st.setInt(3, activo.getIdUser());
			st.setInt(4, activo.getIdMoneda());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
	            int generatedId = rs.getInt(1);
	            Activo a = new Activo(activo.getCantidad(),activo.getNomenclatura(), activo.getIdUser(), activo.getIdMoneda());
	            a.setId(generatedId);
			}
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
				Activo activo = new Activo(res.getDouble("cantidad"),res.getString("nomenclatura"),res.getInt("id_user"),res.getInt("id_moneda"));
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
					activo = new Activo(res.getDouble("cantidad"),res.getString("nomenclatura"),res.getInt("id_user"),res.getInt("id_moneda"));
				}
			}
			st.close();
		} catch (SQLException e) {
            System.out.print("Error de SQL:"+e.getMessage());
        }
		return activo;
	}
}
