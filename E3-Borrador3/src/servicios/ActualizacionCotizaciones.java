package servicios;

import java.util.List;
import java.util.Timer;

import vista.CotizacionesGUI;

public class ActualizacionCotizaciones {
    private Timer timer;
    private ConsultarPrecioCripto consultarPrecioCripto;
    private CotizacionesGUI cotizacionesGUI;
    private Controladores controlador;
    			
    public ActualizacionCotizaciones(CotizacionesGUI cotizacionesGUI,Controladores controlador) {
        this.cotizacionesGUI = cotizacionesGUI;
        this.controlador=controlador;
        this.consultarPrecioCripto = new ConsultarPrecioCripto(this);
        this.timer = new Timer();
        
        // Programar la tarea para ejecutarse cada 1 minuto
        timer.schedule(consultarPrecioCripto, 0,60000);
        
    }
    public void detenerActualizacion() {
        timer.cancel(); // Detiene el timer
    }
    public void actualizarPrecios(List<Double>precios) {
    	// Actualizar cotizaciones en la vista
    	cotizacionesGUI.actualizarCotizaciones(precios.get(0),precios.get(1),precios.get(2),precios.get(3),precios.get(4));
    	// Actualizar cotizaciones en la BD
    	controlador.actualizarCotizaciones(precios);
    }
}
