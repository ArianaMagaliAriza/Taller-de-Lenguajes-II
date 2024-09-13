package billetera;
/**
 * @moneda es el tipo de criptomoneda en el que se realiza el DeFi
 * @protocolo es el protocolo del DeFi
 * @monto es el monto en cripto depositado en el DeFi
 * @interes es el interes generado por el DeFi
 * 
 */
public class DeFi {
    private Criptomoneda moneda;
    private String protocolo;
    private Double monto;
    private Double interes;

    public DeFi(Criptomoneda moneda, String protocolo, Double monto, Double interes) {
        this.moneda = moneda;
        this.protocolo = protocolo;
        this.monto = monto;
        this.interes = interes;
    }

    public Criptomoneda getMoneda() {
        return moneda;
    }
    public void setMoneda(Criptomoneda moneda) {
        this.moneda = moneda;
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