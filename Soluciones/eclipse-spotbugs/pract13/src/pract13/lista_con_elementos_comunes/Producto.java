package pract13.lista_con_elementos_comunes;

/**
 * Clase producto.
 * 
 * @author Estructuras de Datos (UC) y Hector Gonzalez Iglesias
 * @version ene-2021
 */
public class Producto {
	private final int codigo;
	private final String descripcion;
	
	/**
	 * Construye un producto con los valores pasados como parametros.
	 * @param codigo codigo del producto
	 * @param descripcion descripcion del producto
	 */
	public Producto(int codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	/**
	 * Retorna el codigo del producto.
	 * @return el codigo del producto.
	 */
	public int codigo() {
		return codigo;
	}

	/**
	 * Retorna la descripcion del producto.
	 * @return la descripcion del producto.
	 */
	public String descripcion() {
		return descripcion;
	}

	@Override
	public String toString() {
		return "(" + codigo + ")";
	}
	
	// otros metodos necesarios para usar esta clase en el TAD elegido.
	@Override
	public boolean equals(Object producto) {

		if (producto == null) {
			return false;
		}
		
		if (!(producto instanceof Producto)) {
			return false;
		}
		
		if (producto == this) {
			return true;
		}
		Producto other = (Producto) producto;		
		return codigo == other.codigo();
	}
	
	@Override
	public int hashCode() {
		return ((Integer) codigo).hashCode();
	}
}
