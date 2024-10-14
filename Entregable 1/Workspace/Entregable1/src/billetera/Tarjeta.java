package billetera;

public abstract class Tarjeta {

    private Cripto tipo;
	private int numero;
    private int codigoSeguridad;
    private Boolean terminos;
    private Boolean estado;
    private String fechaVencimiento;
    
    /**
     * @param tipo es el tipo de criptomoneda que se utiliza en la tarjeta
     * @param numero es el numero que identifica a la tarjeta
     * @param codigoSeguridad son los 3 digitos de seguridad de la tarjeta
     * @param terminos es el registro del estado de los terminos y condiciones, si se encuentran aceptados (true) o no (false) 
     * @param estado es el estado de la tarjeta, es decir, si la tarjeta está habilitada (true) o deshabilitada (false) 
     * @param fechaVencimiento es la fecha de vencimiento de la tarjeta
     */
    
    public Tarjeta(Cripto tipo, int numero, int codigoSeguridad, Boolean terminos, Boolean estado,
			String fechaVencimiento) {
		super();
		this.tipo = tipo;
		this.numero = numero;
		this.codigoSeguridad = codigoSeguridad;
		this.terminos = terminos;
		this.estado = estado;
		this.fechaVencimiento = fechaVencimiento;
	}
    

	public Cripto getTipo() {
		return tipo;
	}

	public void setTipo(Cripto tipo) {
		this.tipo = tipo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCodigoSeguridad() {
		return codigoSeguridad;
	}

	public void setCodigoSeguridad(int codigoSeguridad) {
		this.codigoSeguridad = codigoSeguridad;
	}

	public Boolean getTerminos() {
		return terminos;
	}

	public void setTerminos(Boolean terminos) {
		this.terminos = terminos;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
   
    
    
   
    
}
