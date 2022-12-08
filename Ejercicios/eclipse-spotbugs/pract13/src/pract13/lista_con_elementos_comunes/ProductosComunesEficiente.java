package pract13.lista_con_elementos_comunes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementa el algoritmo que permite dejar en una lista los
 * productos que tambien se encuentran en la otra.
 *  
 * @author Estructuras de Datos (UC) y Hector Gonzalez Iglesias
 * @version ene-2021
 */
public class ProductosComunesEficiente {

	/**
	 * Deja en la lista1 unicamente aquellos productos que tambien estan
	 * en la lista2.
	 * @param lista1 lista en la que se dejan unicamente aquellos productos
	 * que tambien estan en la lista2.
	 * @param lista2 lista en la que esta los productos a conservar.
	 */
	public static void dejaElementosComunes(ArrayList<Producto> lista1,
			ArrayList<Producto> lista2) {
		
		// O(n2)
		Set<Producto> s2 = new HashSet<Producto>();
		for (Producto producto : lista2) {
			s2.add(producto);
		}
		
		// O(n1)
		Iterator<Producto> iterador = lista1.iterator();
		while (iterador.hasNext()) {
			Producto producto1 = iterador.next();
			if (!s2.contains(producto1)) {
				iterador.remove();
			}
		}
	}
}
