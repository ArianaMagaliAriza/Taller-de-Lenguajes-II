package modelo;

import java.util.Comparator;

public class ComparadorNomenclaturaMoneda implements Comparator<Moneda>{
	public int compare(Moneda m1,Moneda m2) {
		return m1.getNomenclatura().compareTo(m2.getNomenclatura());
	}
}
