package billetera;

public class DeFi {

    private String protocolo;
    private Double monto;
    private Double interes;
    
   
    /**
     * @param protocolo es el protocolo del DeFi
     * @param monto es el monto en cripto depositado en el DeFi
     * @param interes es el interes generado por el DeFi
     * 
     */

    public DeFi(String protocolo, Double monto, Double interes) {

        this.protocolo = protocolo;
        this.monto = monto;
        this.interes = interes;
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