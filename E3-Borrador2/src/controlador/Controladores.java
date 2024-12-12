package controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import modelo.*;
import servicios.Operaciones;
import vista.*;

public class Controladores {
	private Operaciones operaciones;
	private Usuario usuarioLogueado;
	
	public Controladores(Operaciones operaciones) {
		this.operaciones=operaciones;
	}
	// COTIZACIONES
	public boolean esActivo(Moneda moneda,List<Activo>activosUsuario) {
		for(Activo activo:activosUsuario) {
			Moneda monedaUser=devolverMoneda(activo.getIdMoneda());
			if(monedaUser.getNombre().equals(moneda.getNombre())) {
				return true;
			}
		}
		return false;
	}
	public List<Moneda> retornarMonedas() {
		List<Moneda> monedas=null;
		try {
			monedas = operaciones.listarMonedas(1);
		} catch (SQLException e) {
			System.out.println("Error de SQL: "+e.getMessage());
		}
		return monedas;
	}
	// MIS OPERACIONES
	public String listarOperaciones(int idUsuario){
		List<Transaccion>transacciones=operaciones.listarTransacciones(idUsuario);
		String str="";
		for(Transaccion transaccion:transacciones) {
			str+=">"+transaccion.getFecha()+" "+transaccion.getResumen()+"\n";
		}
		return str;
	}
	// MIS ACTIVOS
	public Persona devolverPersona(int id) {
		return operaciones.obtenerPersona(id);
	}
	public List<Activo> devolverActivosUsuario(int id){
		List<Activo> activos=null;
		List<Activo> activosUsuario=new ArrayList<Activo>();
		try {
			activos = operaciones.listarActivos(1);
			for(Activo activo:activos) {
				if(activo.getIdUsuario()==id)
					activosUsuario.add(activo);
			}
		} catch (SQLException e) {
			System.out.println("Error de SQL:"+e.getMessage());
		}
		return activosUsuario;
	}
	public Moneda devolverMoneda(int id) {
		return operaciones.obtenerMoneda(id);
	}
	public void generarDatosPrueba(int idUsuario) {
		try {
			operaciones.generarActivosPrueba(idUsuario);
		} catch (SQLException e) {
			System.out.println("Error de SQL:"+e.getMessage());
		}
	}
	public void generarArchivoCSV(List<Activo>activos) {
		BufferedWriter out;
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo CSV");

        // Establecer filtro para solo mostrar archivos .csv
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));

        // Mostrar dialogo para guardar archivo
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Obtener la ruta seleccionada por el usuario
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

        // Asegurarse de que el archivo tenga la extensión .csv
        if (!rutaArchivo.endsWith(".csv")) {
                rutaArchivo += ".csv";}
		try {
		out = new BufferedWriter (new FileWriter(rutaArchivo));
		
		String str = "Activo,Monto"+"\n";
		String strLine;
		
		for(Activo activo:activos) {
			Moneda moneda=devolverMoneda(activo.getIdMoneda());
			strLine=moneda.getNombre()+"("+moneda.getNomenclatura()+")"+","+activo.getCantidad();
			str += strLine+"\n";
		}
		System.out.println(str);
		out.write(str);
		out.close();
		} catch (IOException e) {
			System.out.println("Error IO:"+e.getMessage());
		}
	}}
	
	
	
	// LOGIN
	public void mostrarLogin(LoginGUI vistaLogin) {
		vistaLogin.setVisible(true);
	}
	public void ocultarLogin(LoginGUI vistaLogin) {
		vistaLogin.setVisible(false);
	}
	 // Método para manejar el inicio de sesión
    public void handleLogin(LoginGUI vistaLogin,String email,String password) {
  
        // validación
        if (operaciones.usuarioEnBD(email)) {
        	Usuario usuario = operaciones.obtenerUsuario(email);
        	if(usuario.getPassword().equals(password)) {
        		this.setUsuarioLogueado(usuario);
        		vistaLogin.mostrarMensaje("¡Inicio de sesión exitoso!");
        		vistaLogin.dispose();
        		abrirVentanaActivos();
        	}
        	else {
        		vistaLogin.mostrarError("Contraseña incorrecta!");
        	}
        } else {
        	vistaLogin.mostrarError("Email no encontrado, registrese para ingresar.");
        }
    }
    
    
    // REGISTRO
	public void mostrarRegistro(RegistroUserGUI vistaRegistro) {
		vistaRegistro.setVisible(true);
	}
	public void ocultarRegistro(RegistroUserGUI vistaRegistro) {
		vistaRegistro.setVisible(false);
	}
	// Método para manejar el registro
    public void handleRegistro(RegistroUserGUI vistaRegistro,String nombres,String apellidos,String email,String password,Boolean terminos) {
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || password.isEmpty()) {
            vistaRegistro.mostrarError("Por favor, complete todos los campos.");
            return;
        }
        
        if(operaciones.usuarioEnBD(email)) {
            vistaRegistro.mostrarError("Email asociado a otro usuario.");
            return;
        }
        if(!terminos) {
            vistaRegistro.mostrarError("Para registrarse debe aceptar los Términos y Condiciones.");
            return;	
        }
        // Guardar usuario en BD
        this.operaciones.cargarUsuario(nombres,apellidos,email, password, terminos);
        
        // Simulación de registro exitoso
        vistaRegistro.mostrarMensaje("Usuario registrado con éxito.");

    }

    // Método para ir a la ventana Compra
    public void abrirVentanaCompra(String cripto) {
        CompraGUI compraGUI = new CompraGUI(this,cripto);
        compraGUI.setVisible(true);
    }
    // Método para ir a la ventana Cotizaciones
    public void abrirVentanaCotizaciones() {
        CotizacionesGUI cotizacionesGUI = new CotizacionesGUI(this);
        cotizacionesGUI.setVisible(true);
    }
    // Método para ir a la ventana Mis Operaciones
    public void abrirVentanaOperaciones() {
        MisOperacionesGUI misOperacionesGUI = new MisOperacionesGUI(this);
        misOperacionesGUI.setVisible(true);
    }
    // Método para ir a la ventana Login
    public void abrirVentanaLogin() {
        LoginGUI loginGUI = new LoginGUI(this);
        loginGUI.setVisible(true);
    }
    // Método para ir la ventana de activos
    public void abrirVentanaActivos() {
 	   MisActivosGUI misActivosGUI = new MisActivosGUI(this);
       misActivosGUI.setVisible(true);
    }
    // Método para ir la ventana de registro
    public void abrirVentanaRegistro() {
    	   RegistroUserGUI registroGUI = new RegistroUserGUI(this);
           registroGUI.setVisible(true);
       }

	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}
	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
    
}
