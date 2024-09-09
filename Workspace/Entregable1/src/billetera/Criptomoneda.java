package billetera;
/**
 * 
 * @nombre nombre es el nombre de la criptomoneda (ej:Bitcoin,Etherium,etc)
 * @etiqueta etiqueta son las siglas que identifican a la criptomoneda (ej:BTC,ETH,etc)
 * @volatilidad volatilidad es el porcentaje de fluctación del precio de la criptomoneda
 * 
 */
public class Criptomoneda {
	private String nombre;
	private String etiqueta;
	private Double volatilidad;
	
	public Criptomoneda(String nombre, String etiqueta, Double volatilidad) {
		this.nombre = nombre;
		this.etiqueta = etiqueta;
		this.volatilidad = volatilidad;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEtiqueta() {
		return etiqueta;
	}
	
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	
	public Double getVolatilidad() {
		return volatilidad;
	}
	
	public void setVolatilidad(Double volatilidad) {
		this.volatilidad = volatilidad;
	}

}
