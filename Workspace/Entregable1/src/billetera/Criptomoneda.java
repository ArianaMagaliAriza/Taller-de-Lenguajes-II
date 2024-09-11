package billetera;
/**
 * 
 * @nombre es el nombre de la criptomoneda (ej:Bitcoin,Etherium,etc)
 * @etiqueta son las siglas que identifican a la criptomoneda (ej:BTC,ETH,etc)
 * @volatilidad es el porcentaje de fluctación del precio de la criptomoneda
 * @stock es la cantidad de la criptomoneda que se encuentra disponible en el mercado
 * 
 */
public class Criptomoneda {
	private String nombre;
	private String etiqueta;
	private Double volatilidad;
	private Double stock;
	
	
	public Criptomoneda(String nombre, String etiqueta, Double volatilidad, Double stock) {
		this.nombre = nombre;
		this.etiqueta = etiqueta;
		this.volatilidad = volatilidad;
		this.stock = stock;
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
	
	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

}
