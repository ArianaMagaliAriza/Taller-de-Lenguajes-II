package controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import excepciones.*;
import modelo.*;
import servicios.Operaciones;
import vista.*;

public class Controladores {
	private Operaciones operaciones;
	private Usuario usuarioLogueado;
	
	public Controladores(Operaciones operaciones) {
		this.operaciones=operaciones;
	}
	
	
	//SWAP
	public void cargarDatosSwap(SwapGUI vista,String cripto, String precio) {
	    Usuario usuario = getUsuarioLogueado();
        Persona persona = devolverPersona(usuario.getId());

        // Obtener lista de criptos del usuario
        List<Activo> activosUsuario = devolverActivosUsuario(usuario.getId());
        List<Moneda> monedasCripto = new ArrayList<>();
        for (Activo activo:activosUsuario) {
        	Moneda moneda=devolverMoneda(activo.getIdMoneda());
            if ("cripto".equalsIgnoreCase(moneda.getTipo())) {
            	monedasCripto.add(moneda);
            }
        }
        // Configurar vista
        vista.cargarUserLabel(persona);
        vista.cargarMonedas(monedasCripto);
        vista.cargarStockPrecio(cripto,precio);
	}
    public void convertirMontoCripto(SwapGUI vista,double monto,Moneda criptoSeleccionada,String precio) {
    	try {
            if (criptoSeleccionada == null) {
                vista.mostrarMensaje("Seleccione una moneda válida antes de convertir.");
                return;
            }
            double cotizacionCripto = Double.parseDouble(precio);
        	// Calcular valor equivalente en cripto 
    		double resultado = (monto * criptoSeleccionada.getValorEnDolar()) / cotizacionCripto;

            vista.cargarResultadoLabel(String.format("Equivale a: %.6f", resultado));
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("Ingrese un número válido en el campo de monto.");
        }
    }
    public void realizarSwap(SwapGUI vista,String precio,double monto,double cantidadCripto,Moneda criptoSeleccionada,Moneda criptoEsperada) throws OperacionException {
		List<Activo> activosUsuario = devolverActivosUsuario(this.getUsuarioLogueado().getId());
    	// Si la cripto aún no es un activo lo creo
		if(!esActivo(criptoEsperada,activosUsuario)) {
			vista.mostrarMensaje(criptoSeleccionada.getNomenclatura() + " será agregada a sus activos");
			int idCripto=operaciones.obtenerId(criptoSeleccionada.getNomenclatura());
			operaciones.cargarActivo(this.getUsuarioLogueado().getId(),idCripto,0.0);
		}
		boolean swapExitoso=false;
		try {
			swapExitoso = operaciones.swap(this.getUsuarioLogueado().getId(),
					criptoSeleccionada, monto,criptoEsperada,cantidadCripto,activosUsuario);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(StockInsuficienteException e) {
			vista.mostrarMensaje("El stock de "+criptoEsperada.getNomenclatura()+" es insuficiente para realizar el swap.");
		} catch(SaldoInsuficienteException e) {
			vista.mostrarMensaje("No tienes saldo suficiente en " + criptoSeleccionada.getNomenclatura() + " para realizar el swap.");
		}
		
        if(swapExitoso)
        	vista.mostrarMensaje("Swap realizado con éxito!");
        else
        	throw new OperacionException("Error en la operación Swap.");
		
}
    
	
	//COMPRA
    public void cargarDatosCompra(CompraGUI vista,String cripto, String precio) {
        Usuario usuario = getUsuarioLogueado();
        Persona persona = devolverPersona(usuario.getId());

        // Obtener lista de Fiat del usuario
        List<Activo> activosUsuario = devolverActivosUsuario(usuario.getId());
        List<Moneda> monedasFiat = new ArrayList<>();
        for (Activo activo:activosUsuario) {
        	Moneda moneda=devolverMoneda(activo.getIdMoneda());
            if ("fiat".equalsIgnoreCase(moneda.getTipo())) {
            	monedasFiat.add(moneda);
            }
        }
        // Configurar vista
        vista.cargarUserLabel(persona);
        vista.cargarMonedas(monedasFiat);
        vista.cargarStockPrecio(cripto,precio);
    }
    public void convertirMonto(CompraGUI vista,double monto,Moneda monedaSeleccionada,String precio) {
    	try {
            if (monedaSeleccionada == null) {
                vista.mostrarMensaje("Seleccione una moneda válida antes de convertir.");
                return;
            }
            double valorEnDolar = monedaSeleccionada.getValorEnDolar();
            double cotizacionCripto = Double.parseDouble(precio);
            double resultado = (monto / valorEnDolar) / cotizacionCripto;
            vista.cargarResultadoLabel(String.format("Equivale a: %.6f", resultado));
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("Ingrese un número válido en el campo de monto.");
        }
    }
    public void realizarCompra(CompraGUI vista,String precio,double monto,double cantidadCripto,Moneda fiatSeleccionada,Moneda criptoSeleccionada) throws OperacionException {
    		List<Activo> activosUsuario = devolverActivosUsuario(this.getUsuarioLogueado().getId());
        	// Si la cripto aún no es un activo lo creo
			if(!esActivo(criptoSeleccionada,activosUsuario)) {
				vista.mostrarMensaje(criptoSeleccionada.getNomenclatura() + " será agregada a sus activos");
				int idCripto=operaciones.obtenerId(criptoSeleccionada.getNomenclatura());
				operaciones.cargarActivo(this.getUsuarioLogueado().getId(),idCripto,0.0);
			}
    		boolean compraExitosa=false;
			try {
				compraExitosa = operaciones.compra(this.getUsuarioLogueado().getId(),
						criptoSeleccionada,fiatSeleccionada, monto,cantidadCripto,activosUsuario);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch(StockInsuficienteException e) {
				vista.mostrarMensaje("El stock de "+criptoSeleccionada.getNomenclatura()+" es insuficiente para realizar la compra");
			} catch(SaldoInsuficienteException e) {
				vista.mostrarMensaje("No tienes saldo suficiente en " + fiatSeleccionada.getNomenclatura() + " para esta compra.");
			}
    		
            if(compraExitosa)
            	vista.mostrarMensaje("Compra realizada con éxito!");
            else
            	throw new OperacionException("Error en la operación Compra.");
    		
    }

	// COTIZACIONES
	//Chequea si la moneda está entre los activos del usuario por el nombre
	public boolean esActivo(Moneda moneda,List<Activo>activosUsuario) {
		return operaciones.esActivo(moneda, activosUsuario);
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
	 // Método para manejar el inicio de sesión
    public void handleLogin(LoginGUI vistaLogin,String email,String password) throws LoginException {
  
        // validación
        if (email.isEmpty() || password.isEmpty()) {
            throw new LoginException("Por favor, complete todos los campos.");
            
        }
        else {
        if (operaciones.usuarioEnBD(email)) {
        	Usuario usuario = operaciones.obtenerUsuario(email);
        	if(usuario.getPassword().equals(password)) {
        		this.setUsuarioLogueado(usuario);
        	}
        	else {
        		throw new LoginException("Contraseña incorrecta!");
        	}
        } else {
        	throw new LoginException("Email no encontrado, registrese para ingresar.");
        }
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
    public void handleRegistro(RegistroUserGUI vistaRegistro,String nombres,String apellidos,String email,String password,Boolean terminos) throws RegistroException {
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new RegistroException("Por favor, complete todos los campos.");
        }
        if(operaciones.usuarioEnBD(email)) {
            throw new RegistroException("Email asociado a otro usuario.");
        }
        if(!terminos) {
        	throw new RegistroException("Para registrarse debe aceptar los Términos y Condiciones.");
        }
        // Guardar usuario en BD
        this.operaciones.cargarUsuario(nombres,apellidos,email, password, terminos);
       
    }

    // Método para ir a la ventana Swap
    public void abrirVentanaSwap(String cripto,String precio) {
        SwapGUI swapGUI = new SwapGUI(this,cripto,precio);
        swapGUI.setVisible(true);
    }
    // Método para ir a la ventana Compra
    public void abrirVentanaCompra(String cripto,String precio) {
        CompraGUI compraGUI = new CompraGUI(this,cripto,precio);
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
