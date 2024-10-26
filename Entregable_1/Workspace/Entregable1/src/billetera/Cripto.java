package billetera;

public abstract class Cripto {//extiende de la clase Moneda
	private String etiqueta;
	private int direccion;
	
	/**
	 * @param nombre es el nombre de la criptomoneda (ej:Bitcoin,Etherium,etc)
	 * @param etiqueta son las siglas que identifican a la criptomoneda (ej:BTC,ETH,etc)
	 * @param volatilidad es el porcentaje de fluctación del precio de la criptomoneda
	 * 
	 */
	
	
	public Cripto(String etiqueta, int direccion) {
		//recibe también el parámetro nombre (atributo de la clase padre Moneda) 
		this.etiqueta = etiqueta;
		this.direccion = direccion;
	}
	
	

	public String getEtiqueta() {
		return etiqueta;
	}


	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}


	public int getDireccion() {
		return direccion;
	}


	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

}
