package billetera;

public class DeFi {
	private Cripto tipo;
    private String protocolo;
    private Double monto;
    private Double interes;
    

    /**
     * @param tipo es el tipo de criptomoneda en el que se realiza el DeFi
     * @param protocolo es el protocolo del DeFi
     * @param monto es el monto en cripto depositado en el DeFi
     * @param interes es el interes generado por el DeFi
     * 
     */
    
	
	public DeFi(Cripto tipo, String protocolo, Double monto, Double interes) {
		this.tipo = tipo;
		this.protocolo = protocolo;
		this.monto = monto;
		this.interes = interes;
	}
	
	 
    public Cripto getTipo() {
		return tipo;
    }

	public void setTipo(Cripto tipo) {
		this.tipo = tipo;
	}
	
	public String getProtocolo() {
		return protocolo;
	}
	
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	
	public Double getMonto() {
		return monto;
	}
	
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	public Double getInteres() {
		return interes;
	}
	
	public void setInteres(Double interes) {
		this.interes = interes;
	}

    
}