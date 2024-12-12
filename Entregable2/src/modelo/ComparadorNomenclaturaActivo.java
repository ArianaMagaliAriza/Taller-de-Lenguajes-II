package modelo;

import java.util.Comparator;

public class ComparadorNomenclaturaActivo implements Comparator<Activo>{
	public int compare(Activo a1,Activo a2) {return 1;}
	/*public int compare(Activo a1,Activo a2) {
		return a1.getNomenclatura().compareTo(a2.getNomenclatura());
	}*/
}
