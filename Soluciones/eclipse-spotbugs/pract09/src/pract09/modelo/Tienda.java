package pract09.modelo;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import pract09.mapas.*;

/**
 * Clase que representa una tienda que oferta una serie de productos.
 * La clase gestiona el stock de cada producto y las reservas que los clientes
 * pueden realizar cuando el stock actual disponible de un producto no es 
 * suficiente para suplir su demanda. 
 * 
 * @author Estructuras de Datos (UC) y Hector Gonzalez Iglesias
 * @version nov-2020
 *
 */
public class Tienda {
	private MapaDispersionAbierta<String, Cliente> clientes;
	private TreeMap<Integer, Producto> productos;

	/**
	 * Lanzada por anhadeCliente si el cliente ya esta registrado en la tienda.
	 */
	@SuppressWarnings("serial")
	public static class ClienteYaExiste extends RuntimeException {
	}

	/**
	 * Lanzada si el cliente indicado no esta registrado en la tienda.
	 */
	@SuppressWarnings("serial")
	public static class ClienteNoExiste extends RuntimeException {
	}

	/**
	 * Lanzada si el producto indicado no es ofertado por la tienda.
	 */
	@SuppressWarnings("serial")
	public static class ProductoNoExiste extends RuntimeException {
	}
	
	/**
	 * Constructor de la tienda.
	 */
	public Tienda() {
		clientes = new MapaDispersionAbierta<>(5);
		productos = new TreeMap<>();
	}

	/**
	 * Anhade un nuevo producto a la tienda.
	 * @param producto Producto.
	 * @return Identificador del producto.
	 */
	public int anhadeProducto(Producto producto) {
		productos.put(producto.codigo(), producto);
		return producto.codigo();
	}
	
	/**
	 * Registra un nuevo cliente en la tienda.
	 * @param cliente Nuevo cliente.
	 * @throws ClienteYaExiste si ya existe un cliente con ese DNI.
	 */
	public void anhadeCliente(Cliente cliente) 
			throws ClienteYaExiste {
		Cliente clienteBuscado = clientes.busca(cliente.dni());
		if (clienteBuscado != null) {
			throw new ClienteYaExiste();
		}

		clientes.anhade(cliente.dni(), cliente);
	}

	/**
	 * Retorna el stock del producto.
	 * @param codProducto codigo del producto.
	 * @return el stock del producto.
	 * @throws ProductoNoExiste si el codigo no corresponde a ningun
	 * producto de la tienda.
	 */
	public int stockProducto(int codProducto) throws ProductoNoExiste {
		Producto productoBuscado = productos.get(codProducto);
		if (productoBuscado == null) {
			throw new ProductoNoExiste();
		}
		return productoBuscado.unidadesStock();
	}
	
	/**
	 * Actualiza el stock de un producto.
	 * @param codProducto Identificador del producto cuyo stock se actualiza.
	 * @param cantidad Numero de unidades en que se actualiza el stock.
	 * @return Lista de reservas del producto que se han suplido con el
	 * nuevo stock.
	 * @throws ProductoNoExiste si el codigo no corresponde a ningun
	 * producto de la tienda.
	 */
	public List<Reserva> incrementaStock(int codProducto, int cantidad) throws ProductoNoExiste {
		Producto productoBuscado = buscaProducto(codProducto);
		if (productoBuscado == null) {
			throw new ProductoNoExiste();
		}
		return productoBuscado.actualizaStock(cantidad);
	}

	/**
	 * Realiza una venta de un producto.
	 * @param codProducto Identificador del producto cuyo stock se actualiza.
	 * @param dniCliente DNI del cliente que realiza la compra.
	 * @param cantidad Numero de unidades en que se actualiza el stock
	 * @return true si se ha realizado la venta (habia stock suficiente).
	 * 	       false si se ha generado una reserva (no habia stock suficiente).
	 * @throws ClienteNoExiste Cuando dniCliente no corresponde a ningun
	 * cliente registrado.
	 * @throws ProductoNoExiste Cuando codProducto no corresponde a ningun
	 * producto de la tienda.
	 */
	public boolean nuevaVenta(int codProducto, String dniCliente, int cantidad) 
			throws ClienteNoExiste, ProductoNoExiste {

		Cliente clienteBuscado = clientes.busca(dniCliente);
		if (clienteBuscado == null) {
			throw new ClienteNoExiste();
		}

		Producto productoBuscado = buscaProducto(codProducto);
		if (productoBuscado == null) {
			throw new ProductoNoExiste();
		}

		if (cantidad > productoBuscado.unidadesStock()) {
			productoBuscado.anhadeReserva(new Reserva(cantidad, clienteBuscado));
			return false;
		}

		productoBuscado.actualizaStock(-cantidad);
		return true;
	}
	
	/**
	 * Retorna los productos con codigos en el rango [codIni, codFin].
	 * Los productos retornados se encuentran ordenados de menor a mayor codigo.
	 * @param codIni codigo inicial del rango.
	 * @param codFin codigo final del rango.
	 * @return los productos con codigos en el rango [codIni, codFin].
	 */
	public Collection<Producto> productosEnRango(int codIni, int codFin) {
		return productos.subMap(codIni, codFin).values();
	}

	// Metodos privados
	private Producto buscaProducto(int codProducto) {
		return productos.get(codProducto);
	}
}
