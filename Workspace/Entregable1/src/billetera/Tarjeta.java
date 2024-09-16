package billetera;

public class Tarjeta {
    private Criptomoneda tipo;
    private Double saldo;
    private Boolean terminos;
    private Boolean estado;
    
    /**
     * @param tipo es el tipo de criptomoneda que utiliza la tarjeta
     * @param saldo es la cantidad de dinero depositado en la tarjeta (en cripto)
     * @param terminos es el registro del estado de los terminos y condiciones, si se encuentran aceptados (true) o no (false) 
     * @param estado es el estado de la tarjeta, es decir, si la tarjeta está habilitada (true) o deshabilitada (false) 
     */
    
    public Tarjeta(Criptomoneda tipo, Double saldo, Boolean terminos, Boolean estado) {
        this.tipo = tipo;
        this.saldo = saldo;
        this.terminos = terminos;
        this.estado = estado;
    }

    public Criptomoneda getTipo() {
        return tipo;
    }
    public void setTipo(Criptomoneda tipo) {
        this.tipo = tipo;
    }
    public Double getSaldo() {
        return saldo;
    }
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
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
    
}
