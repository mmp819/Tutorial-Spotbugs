package pract09.modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Clase que representa un producto ofertado por la tienda.
 * Todo producto se identifica por un codigo numerico que se asigna
 * automaticamente en su creacion.
 * Un producto puede tener una serie de reservas, de manera, que cuando
 * se actualice el stock del producto, sea utilizado para suplir dichas 
 * reservas (en orden de antiguedad).
 * 
 * @author Estructuras de Datos (UC) y Hector Gonzalez Iglesias
 * @version nov-2020
 *
 */
public class Producto {

	private final int codigo;
	private final String descripcion;
	private int unidadesStock = 0;
	private Queue<Reserva> reservas = new LinkedList<>();

	// para generar un codigo diferente para cada producto
	private static int contadorProductos = 0;

	/**
	 * Constructor del producto. Asigna un identificador unico
	 * a cada producto.
	 * @param descripcion descripcion del producto.
	 * @param stockInicial numero de unidades disponibles del producto.
	 */
	public Producto(String descripcion, int stockInicial) {
		this.unidadesStock = stockInicial;
		this.descripcion = descripcion;
		codigo = generaCodigoProducto();
	}

	/**
	 * Simula la generacion de un codigo diferente para cada producto.
	 * @return codigo generado.
	 */
	private static int generaCodigoProducto() {
		contadorProductos++;
		Random random = new Random();
		int number = random.nextInt(900 + contadorProductos);
		
		return number;
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
	 * @return descripcion del producto.
	 */
	public String descripcion() {
		return descripcion;
	}

	/**
	 * Retorna el numero de unidades disponibles del producto.
	 * @return numero de unidades disponibles del producto.
	 */
	public int unidadesStock() {
		return unidadesStock;
	}

	/**
	 * Actualiza el stock del producto en la cantidad indicada, 
	 * que puede ser negativa o positiva.
	 * @param cantidad numero de unidades que se anhaden/eliminan.
	 * @return lista de reservas cubiertas con el nuevo stock.
	 */
	public List<Reserva> actualizaStock(int cantidad) {

		List<Reserva> reservasCubiertas = new ArrayList<>();
		unidadesStock += cantidad;

		while (reservas.size() > 0 && unidadesStock >= reservas.peek().unidades()) {
			Reserva reserva = reservas.peek();
			reservasCubiertas.add(reserva);
			Reserva reservaEliminada = reservas.poll();
			if (reservaEliminada != null) {
				unidadesStock -= reserva.unidades();
			}
		}


		return reservasCubiertas;
	}

	/** 
	 * Almacena una nueva reserva para el producto.
	 * @param reserva reserva a almacenar.
	 */
	public boolean anhadeReserva(Reserva reserva) {
		return reservas.add(reserva);
	}

	@Override
	public String toString() {
		return "(cod=" + codigo + ", des=" + descripcion + ", stock=" + unidadesStock + ")";
	}

	// Otros metodos
	/**
	 * Retorna las reservas sin atender.
	 * @return las reservas sin atender.
	 */
	public Queue<Reserva> reservas() {
		Queue<Reserva> reservations = new LinkedList<>();
		reservations.addAll(reservas);
		return reservations;
	}
}
