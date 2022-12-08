package pract08;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Implementacion de mapa basado en tabla de dispersion abierta.
 *
 * @param <K> clase de elementos usados como llaves del mapa
 * @param <V> clase de elementos usados como valores del mapa
 * 
 * @author Estructuras de Datos (UC) y Hector Gonzalez Iglesias
 * @version nov-2020
 */
public class MapaDispersionAbierta<K, V> implements IMapa<K, V> {
	// tabla con las listas de entradas
	private List<Entrada<K, V>>[] tabla;

	// numero de entradas en el mapa
	private int numEntradas = 0;

	// entrada en el mapa
	private static class Entrada<K, V> {
		private K llave;
		private V valor;

		public Entrada(K llave, V valor) {
			this.llave = llave;
			this.valor = valor;
		}

		@Override
		public String toString() {
			return "(k:" + llave + ",v:" + valor + ")";
		}
	}

	/**
	 * Constructor al que se le pasa la longitud de la tabla.
	 * @param longTabla longitud de la tabla de listas de entradas.
	 */
	@SuppressWarnings("unchecked")
	public MapaDispersionAbierta(int longTabla) {
		tabla = new List[longTabla]; // el compilador pone un warning, pero esta bien
		for (int i = 0; i < longTabla; i++) {
			tabla[i] = new LinkedList<Entrada<K, V>>();
		}
	}
	//XXX Complejidad temporal: O(n)

	/**
	 * Si NO existe la entrada para la llave, anhade la entrada (llave,valor) al mapa.
	 * Si existe la entrada para la llave, pone valor como nuevo valor asociado con la llave.
	 * 
	 * @param llave llave con la que asociar el valor.
	 * @param valor valor asociado a la llave.
	 */
	@Override
	public void anhade(K llave, V valor) {
		int cod = hash(llave);

		Entrada<K, V> entrada = buscaEnLista(tabla[cod], llave);
		if (entrada == null) {
			entrada = new Entrada<K, V>(llave, valor);
			tabla[cod].add(entrada);
			numEntradas++;

		} else {
			entrada.valor = valor;
		}
	}
	//XXX Complejidad temporal: O(n)

	/**
	 * Si existe la entrada para la llave, la elimina.
	 * 
	 * @param llave llave de la entrada a eliminar.
	 */
	@Override
	public void elimina(K llave) {
		int cod = hash(llave);

		boolean eliminado = eliminaDeLista(tabla[cod], llave);
		if (eliminado) {
			numEntradas--;
		}
	}
	//XXX Complejidad temporal: O(n)

	/**
	 * Retorna el valor asociado con la llave.
	 * 
	 * @param llave llave de la entrada buscada.
	 * @return valor asociado con la llave o null si no hay ninguna
	 * entrada para la llave indicada.
	 */
	@Override
	public V busca(K llave) {
		V v = null;
		int cod = hash(llave);

		Entrada<K, V> entrada = buscaEnLista(tabla[cod], llave);
		if (entrada != null) {
			v = entrada.valor;
		}

		return v;
	}
	//XXX Complejidad temporal: O(1)

	/**
	 * Retorna el numero de entradas en el mapa.
	 *	
	 * @return numero de entradas en el mapa.
	 */
	@Override
	public int tamanho() {
		return numEntradas;
	}
	//XXX Complejidad temporal: O(1)

	/**
	 * Vacia el mapa (pasa a tener 0 entradas).
	 */
	@Override
	public void haceVacio() {
		numEntradas = 0;
		for (int i = 0; i < tabla.length; i++) {
			tabla[i].clear();
		}
	}
	//XXX Complejidad temporal: O(n)

	/**
	 * Retorna una lista con todas las llaves de las
	 * entradas existentes en el mapa. 
	 * @return lista con todas las llaves de las
	 * entradas existentes en el mapa.
	 */
	@Override
	public LinkedList<K> llaves() {
		LinkedList<K> llaves = new LinkedList<>();

		for (List<Entrada<K, V>> lista: tabla) {
			Iterator<Entrada<K, V>> iterador = lista.iterator();
			while (iterador.hasNext()) {
				Entrada<K, V> entrada = iterador.next();
				llaves.add(entrada.llave);
			}
		}
		return llaves;
	}
	//XXX Complejidad temporal: O(n)
	
	/**
	 * Retorna una lista con todos los valores de las
	 * entradas existentes en el mapa. 
	 * @return lista con todos los valores de las
	 * entradas existentes en el mapa.
	 */
	@Override
	public LinkedList<V> valores() {
		LinkedList<V> valores = new LinkedList<>();

		for (List<Entrada<K, V>> lista: tabla) {
			Iterator<Entrada<K, V>> iterador = lista.iterator();
			while (iterador.hasNext()) {
				Entrada<K, V> entrada = iterador.next();
				valores.add(entrada.valor);
			}
		}
		return valores;
	}
	//XXX Complejidad temporal: O(n)

	@Override
	public String toString() {
		return Arrays.toString(tabla);
	}
	//XXX Complejidad temporal: O(1)

	// Otros metodos
	int hash(K llave) {
		return Math.abs(llave.hashCode()) % tabla.length;
	}
	//XXX Complejidad temporal: O(1)
	
	/**
	 * Busca la entrada con llave K en la lista correspondiente a su codigo de dispersion.
	 * @param lista la lista con el codigo de dispersion correspondiente
	 * @param llave la llave K que corresponde al elemento
	 * @return la entrada con llave K en la lista correspondiente a su codigo de dispersion
	 */
	private Entrada<K, V> buscaEnLista(List<Entrada<K, V>> lista, K llave) {
		Iterator<Entrada<K, V>> iterador = lista.iterator();
		while (iterador.hasNext()) {
			Entrada<K, V> entrada = iterador.next();
			if (entrada.llave.equals(llave)) {
				return entrada;
			}
		}
		return null;
	}
	//XXX Complejidad temporal: O(n)
	
	/**
	 * Elimina de la lista correspondiente la entrada con llave K (si es que existe).
	 * @param lista la lista con el codigo de dispersion correspondiente
	 * @param llave la llave K que corresponde al elemento
	 * @return true si se ha encontrado la entrada, false en caso contrario
	 */
	private boolean eliminaDeLista(List<Entrada<K, V>> lista, K llave) {
		Iterator<Entrada<K, V>> iterador = lista.iterator();
		while (iterador.hasNext()) {
			Entrada<K, V> entrada = iterador.next();
			if (entrada.llave.equals(llave)) {
				iterador.remove();
				return true;
			}
		}
		return false;
	}
	//XXX Complejidad temporal: O(n)
}
