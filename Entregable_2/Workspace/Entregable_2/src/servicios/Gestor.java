package servicios;

public class Gestor {

	Scanner scanner = new Scanner(System.in);
    Connection connection = null;

	public void bajaUsuario(){

	}
	
	public void altaUsuario() {

	}
	
	public void iniciarTransaccion() {

        try {
            connection = DBConnection.connect();
            creacionTablas.createTables(connection);

    

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.disconnect(connection);
            scanner.close();
        }
    }
}
