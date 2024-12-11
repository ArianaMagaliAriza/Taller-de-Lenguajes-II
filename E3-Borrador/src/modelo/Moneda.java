package modelo;

public class Moneda {
	private int id;
	private String tipo;
	private String nombre;
	private String nomenclatura;
	private double valorEnDolar;
	private double volatilidad;
	private double stock;	//cantidad que hay en la billetera
	
	
	public Moneda(String tipo, String nombre,String nomenclatura, double valorEnDolar, double volatilidad, double stock) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.nomenclatura = nomenclatura;
		this.valorEnDolar = valorEnDolar;
		this.volatilidad = volatilidad;
		this.stock = stock;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNomenclatura() {
		return nomenclatura;
	}
	public void setNomenclatura(String nomenclatura) {
		this.nomenclatura = nomenclatura;
	}
	public double getValorEnDolar() {
		return valorEnDolar;
	}
	public void setValorEnDolar(double valorEnDolar) {
		this.valorEnDolar = valorEnDolar;
	}
	public double getVolatilidad() {
		return volatilidad;
	}
	public void setVolatilidad(double volatilidad) {
		this.volatilidad = volatilidad;
	}
	public double getStock() {
		return stock;
	}
	public void setStock(double stock) {
		this.stock = stock;
	}

	

	
}
