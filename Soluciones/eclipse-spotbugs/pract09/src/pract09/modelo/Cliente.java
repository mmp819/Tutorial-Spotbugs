package pract09.modelo;

/**
 * Clase que representa un cliente registrado en la tienda.
 * Por cada cliente se almacena su nombre y dni (que lo identifica).
 * 
 * @author Estructuras de Datos (UC)
 * @version nov-2020
 *
 */
public class Cliente {

	private final String nombre;

	private final String dni;
	
	/**
	 * Constructor de la clase Cliente.
	 * @param nombre Nombre del cliente.
	 * @param dni DNI del cliente.
	 */
	public Cliente(String dni, String nombre) {
		this.dni = dni; 
		this.nombre = nombre;
	}

	/**
	 * Retorna el dni del cliente.
	 * @return DNI del cliente.
	 */
	public String dni() {
		return dni;
	}

	/**
	 * Retorna el nombre del cliente.
	 * @return Nombre del cliente.
	 */
	public String nombre() {
		return nombre;
	}
}
