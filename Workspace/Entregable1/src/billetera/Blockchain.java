package billetera;

public class Blockchain {
    private String comision;
    private int tiempo;
    
    /**
     * @param comision es la comisión/tarifa de las transacciones
     * @param tiempo es el tiempo que tarda una transacción en ser verificada
     * 
     */
    
    public Blockchain(String comision, int tiempo) {
        this.comision = comision;
        this.tiempo = tiempo;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
}