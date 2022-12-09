package pract09.gui;

import java.util.Collection;
import java.util.List;

import fundamentos.*;
import pract09.modelo.*;

/**
 * Interfaz Grafica de Usuario (GUI) del programa de gestion de  las compras
 * y reposiciones de productos en una tienda.
 * 
 * @author Estructuras de Datos (UC)
 * @version nov-2020
 */
public class GUIGestionTienda {

	/**
	 * Programa principal basado en menu.
	 * @param args argumentos del main (no utilizados)
	 */
	public static void main(String[] args) {
		// opciones del menu
		final int ANHADE_PRODUCTO = 0;
		final int ANHADE_CLIENTE = 1;
		final int NUEVA_VENTA = 2;
		final int ACTUALIZA_STOCK = 3;
		final int PRODUCTOS_EN_RANGO = 4;

		// variables auxiliares
		String dni = "12345678A";
		Lectura lect;
		int unidades;
		int codigo = 0;

		// crea el registro con las tarjetas
		Tienda tienda = new Tienda();

		// crea la ventana de menu
		Menu menu = new Menu("Tienda");
		menu.insertaOpcion("Anhade producto", ANHADE_PRODUCTO);
		menu.insertaOpcion("Anhade cliente", ANHADE_CLIENTE);
		menu.insertaOpcion("Nueva venta", NUEVA_VENTA);
		menu.insertaOpcion("Actualiza stock", ACTUALIZA_STOCK);
		menu.insertaOpcion("Productos en rango", PRODUCTOS_EN_RANGO);

		int opcion;

		// lazo de espera de comandos del usuario
		while (true) {
			opcion = menu.leeOpcion();

			// realiza las acciones dependiendo de la opción elegida
			switch (opcion) {
			case ANHADE_PRODUCTO:
				lect = new Lectura("Datos producto");
				lect.creaEntrada("Descripcion", "Gafas");
				lect.creaEntrada("Unidades", 1);
				lect.esperaYCierra();
				String descripcion = lect.leeString("Descripcion");
				unidades = lect.leeInt("Unidades");

				codigo = tienda.anhadeProducto(new Producto(descripcion, unidades));
				mensaje("Producto", "Codigo: " + codigo);

			case ANHADE_CLIENTE:
				lect = new Lectura("Datos cliente");
				lect.creaEntrada("Nombre", "Lolo");
				lect.creaEntrada("DNI", dni);
				lect.esperaYCierra();
				dni = lect.leeString("DNI");
				String nombre = lect.leeString("Nombre");

				try  {
					tienda.anhadeCliente(new Cliente(nombre, dni));
				} catch (Tienda.ClienteYaExiste e) {
					mensaje("Error", "Ya existe un cliente con el DNI indicado");
				}
				
			case NUEVA_VENTA:
				lect = new Lectura("Datos venta");
				lect.creaEntrada("DNI cliente", dni);
				lect.creaEntrada("Codigo producto", codigo);
				lect.creaEntrada("Unidades", 1);
				lect.esperaYCierra();
				dni = lect.leeString("DNI cliente");
				codigo = lect.leeInt("Codigo producto");
				unidades = lect.leeInt("Unidades");

				try  {
					boolean ventaRealizada = tienda.nuevaVenta(codigo, dni, unidades);
					if (ventaRealizada) {
						mensaje("Venta realizada",
								"Cod. producto:" + codigo +
								"\nUnidades vendidas:" + unidades +
								"\nDNI cliente:" + dni);
					} else {
						mensaje("Creada reserva",
								"Cod. producto:" + codigo +
								"\nUnidades reservadas:" + unidades +
								"\nDNI cliente:" + dni);

					}
				} catch (Tienda.ClienteNoExiste e) {
					mensaje("Error", "No existe un cliente con el DNI indicado");	
				} catch (Tienda.ProductoNoExiste p) {
					mensaje("Error", "No existe el producto con el codigo indicado");	
				}

			case ACTUALIZA_STOCK:
				lect = new Lectura("Datos stock");
				lect.creaEntrada("Codigo producto", codigo);
				lect.creaEntrada("Unidades", 1);
				lect.esperaYCierra();
				codigo = lect.leeInt("Codigo producto");
				unidades = lect.leeInt("Unidades");

				try  {
					List<Reserva> reservas = tienda.incrementaStock(codigo, unidades);
					String reservasString = "";
					for (Reserva r: reservas) {
						reservasString = reservasString +
								"Cliente: " + r.cliente().nombre() +
								"  Unidades: " + r.unidades() + "\n"; 
					}
					mensaje("Reservas atendidas", reservasString);

				} catch (Tienda.ClienteNoExiste e) {
					mensaje("Error", "No existe un cliente con el DNI indicado");	
				} catch (Tienda.ProductoNoExiste p) {
					mensaje("Error", "No existe el producto con el codigo indicado");	
				}

			case PRODUCTOS_EN_RANGO:
				lect = new Lectura("Rango productos");
				lect.creaEntrada("Codigo inicial", 0);
				lect.creaEntrada("Codigo final", 100000);
				lect.esperaYCierra();
				int codIni = lect.leeInt("Codigo inicial");
				int codFin = lect.leeInt("Codigo final");
				Collection<Producto> productos = tienda.productosEnRango(codIni, codFin);
				String productosString = "";
				for (Producto p: productos) {
					productosString = productosString +
							"  Cod: " + p.codigo() +
							"  Des: " + p.descripcion() +
							"  Stock: " + p.unidadesStock() + "\n"; 
				}
				mensaje("Productos entre " + codIni + " y " + codFin, 
						productosString);
				
			default:
				throw new AssertionError("Opcion no esperada");
			}
		}

	}

	/**
	 * Metodo auxiliar que muestra un ventana de mensaje.
	 * @param titulo titulo de la ventana
	 * @param txt texto contenido en la ventana
	 */
	private static void mensaje(String titulo, String txt) {
		Mensaje msj = new Mensaje(titulo);
		msj.escribe(txt);

	}

}
