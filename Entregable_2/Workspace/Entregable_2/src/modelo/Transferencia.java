package modelo;

public class Transferencia extends Transaccion{
	private int direccion;

	public Transferencia(Double monto, Cripto moneda, int direccion) {
		super(monto, moneda);
		this.direccion = direccion;
	}

	@Override
	public void accion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void consultarBlockchain() {
		// TODO Auto-generated method stub
		
	}

	

}
