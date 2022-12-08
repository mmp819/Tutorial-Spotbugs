
package pract13.cuenta_repeticiones;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Contabiliza las veces que se repite cada una de las palabras existentes
 * en un fichero de texto.
 * 
 * @author Estructuras de Datos (UC) y Hector Gonzalez Iglesias
 * @version ene-2021
 */
public class CuentaRepeticionesEficiente {

	private HashMap<String, RepeticionesPalabra> cuentaRepeticiones = new HashMap<>();
	
	private TreeSet<RepeticionesPalabra> ranking = null;
	// creada la primera vez que se llama al metodo palabraEnRanking()

	/**
	 * Lanzada por palabraEnRanking cuando se pasa una posicion no valida.
	 */
	@SuppressWarnings("serial")
	public static class PosicionIncorrecta extends RuntimeException {
	}

	/**
	 * Contabiliza las veces que se repite cada una de las palabras existentes
	 * en un fichero de texto y lo almacena en una estructura de datos interna para
	 * su posterior consulta con el metodo repeticiones.
	 * @param nomFich nombre del fichero en el que contabilizar las palabras.
	 * @throws FileNotFoundException si no existe el fichero.
	 */
	public CuentaRepeticionesEficiente(String nomFich) throws FileNotFoundException {
		String str = "";
		try (Scanner in = new Scanner(new FileReader(nomFich))) {
			while (in.hasNext()) {
				str = in.next();

				// convierte a minusculas
				str = str.toLowerCase();

				// elimina los caracteres que no sean letras
				str = str.replaceAll("[^a-zñáéíóúü]", "");

				// acumula ocurrencia
				if (!str.equals("")) {
					RepeticionesPalabra palabra = cuentaRepeticiones.get(str);
					
					// Si la palabra ya ha aparecido previamente le suma una repeticion
					if (palabra != null) {
						palabra.sumaRepeticion();
					
					// Si no, la registra para sumarle repeticiones en el futuro
					} else {
						RepeticionesPalabra nuevaPalabra = new RepeticionesPalabra(str);
						cuentaRepeticiones.put(str, nuevaPalabra);
					}
				}
			}
		}
	}

	/**
	 * Retorna las veces que se repite la palabra en el fichero que fue
	 * pasada como parámetro al constructor.
	 * @param palabra palabra de la que se quiere saber cuantas veces se
	 * encuentra repetida en el fichero.
	 * @return numero de repeticiones de la palabra en el fichero.
	 */
	public int repeticionesPalabra(String palabra) {
		/*
		 * Busca la palabra. Si ya ha aparecido en el texto, retorna las repeticiones, 0 en caso
		 * contrario
		 */
		RepeticionesPalabra palabraRep = cuentaRepeticiones.get(palabra);
		if (palabraRep != null) {
			return palabraRep.repeticiones();
		} 
		return 0;
	}
	
	/**
	 * Retorna la palabra que ocupa la posicion indicada en el ranking de palabras
	 * mas repetidas.
	 * La posicion 1 corresponde a la palabra mas repetida.
	 * @param pos posicion en el ranking.
	 * @return palabra que ocupa la posicion indicada en el ranking.
	 * @throws PosicionIncorrecta si la posicion es menor o igual que 0
	 * o mayor que el numero de palabras en el fichero.
	 */
	public RepeticionesPalabra palabraEnRanking(int pos) throws PosicionIncorrecta {
		
		// Si la posicion es menor que 0, lanza la excepcion
		if (pos - 1 < 0) {
			throw new PosicionIncorrecta();
		}
		
		// Si el ranking no contiene ningun valor, anhade las repeticiones de todas las palabras
		if (ranking == null) {
			ranking = new TreeSet<RepeticionesPalabra>();
			for (RepeticionesPalabra repeticionesPalabra : cuentaRepeticiones.values()) {
				ranking.add(repeticionesPalabra);
			}
		}
		
		/*
		 *  Recorre las posiciones en el ranking hasta que encuentra la palabra que ocupa esa
		 *  posicion.
		 */
		int i = 1;
		for (RepeticionesPalabra repeticionesPalabra : ranking) {
			if (i == pos) {
				return repeticionesPalabra;
			}
			i++;
		}
		return new RepeticionesPalabra("Numero de palabras inferior al umbral");
	}
}
