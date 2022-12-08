package pract09.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import pract09.modelo.*;

/**
 * Test de la clase Tienda.
 * 
 * @author Estructuras de Datos (UC)
 * @version nov-2020
 */
public class TiendaTest {

	@Test
	void anhadeProductoTest() {
		System.out.println("anhadeProductoTest");
		Tienda tienda = new Tienda();

		int cod1 = tienda.anhadeProducto(new Producto("P1", 1));
		int cod2 = tienda.anhadeProducto(new Producto("P2", 2));
		int cod3 = tienda.anhadeProducto(new Producto("P3", 3));

		assertEquals(3, tienda.stockProducto(cod3));			
		assertEquals(1, tienda.stockProducto(cod1));	
		assertEquals(2, tienda.stockProducto(cod2));
	}

	@Test
	void anhadeClienteTest() {
		System.out.println("anhadeClienteTest");
		Tienda tienda = new Tienda();

		// anhade clientes
		tienda.anhadeCliente(new Cliente("11111111A", "D1"));
		tienda.anhadeCliente(new Cliente("22222222B", "D2"));

		// anhade un producto para poder hacer compras
		int codProd = tienda.anhadeProducto(new Producto("P1", 100));

		// comprueba que los clientes pueden comprar
		tienda.nuevaVenta(codProd, "22222222B", 2);
		tienda.nuevaVenta(codProd, "11111111A", 3);
	}

	@Test
	void anhadeClienteExistenteTest() {
		System.out.println("anhadeClienteExistenteTest");
		Tienda tienda = new Tienda();

		// anhade clientes
		tienda.anhadeCliente(new Cliente("11111111A", "D1"));
		tienda.anhadeCliente(new Cliente("22222222B", "D2"));

		// anhade cliente con DNI repetido
		try {
			tienda.anhadeCliente(new Cliente("11111111A", "DXXXX"));
			fail("Deberia haberse lanzado la excepcion");
		} catch (Tienda.ClienteYaExiste e) {
			// Lo correcto es que se lance la excepcion
		}

		// anhade cliente
		tienda.anhadeCliente(new Cliente("33333333C", "D1"));

		// anhade clientes con DNI repetido
		try {
			tienda.anhadeCliente(new Cliente("33333333C", "DXXXX"));
			fail("Deberia haberse lanzado la excepcion");
		} catch (Tienda.ClienteYaExiste e) {
			// Lo correcto es que se lance la excepcion
		}
		try {
			tienda.anhadeCliente(new Cliente("22222222B", "DXXXX"));
			fail("Deberia haberse lanzado la excepcion");
		} catch (Tienda.ClienteYaExiste e) {
			// Lo correcto es que se lance la excepcion
		}
	}

	@Test
	void ventasStockNoReservasTest() {
		System.out.println("ventasStockNoReservasTest");
		Tienda tienda = new Tienda();

		// anhade clientes
		tienda.anhadeCliente(new Cliente("11111111A", "D1"));
		tienda.anhadeCliente(new Cliente("22222222B", "D2"));

		int codProc = tienda.anhadeProducto(new Producto("P1", 10));
		tienda.anhadeProducto(new Producto("P2", 2));

		// hace ventas y comprueba stock		
		assertEquals(10, tienda.stockProducto(codProc));
		tienda.nuevaVenta(codProc, "22222222B", 2);	
		assertEquals(8, tienda.stockProducto(codProc));
		tienda.nuevaVenta(codProc, "11111111A", 3);	
		assertEquals(5, tienda.stockProducto(codProc));
		tienda.nuevaVenta(codProc, "11111111A", 1);	
		assertEquals(4, tienda.stockProducto(codProc));
		tienda.nuevaVenta(codProc, "22222222B", 4);	
		assertEquals(0, tienda.stockProducto(codProc));
	}

	@Test
	void ventasIncrementaStockNoReservasTest() {
		System.out.println("ventasIncrementaStockNoReservasTest");
		Tienda tienda = new Tienda();

		// anhade clientes
		tienda.anhadeCliente(new Cliente("11111111A", "D1"));
		tienda.anhadeCliente(new Cliente("22222222B", "D2"));

		int codProc = tienda.anhadeProducto(new Producto("P1", 10));
		tienda.anhadeProducto(new Producto("P2", 2));

		// hace ventas y comprueba stock		
		assertEquals(10, tienda.stockProducto(codProc));
		tienda.nuevaVenta(codProc, "22222222B", 2);	
		assertEquals(8, tienda.stockProducto(codProc));
		tienda.nuevaVenta(codProc, "11111111A", 3);	
		assertEquals(5, tienda.stockProducto(codProc));

		// incrementa el stock	
		tienda.incrementaStock(codProc, 3);
		assertEquals(8, tienda.stockProducto(codProc));	

		// hace ventas y comprueba stock	
		tienda.nuevaVenta(codProc, "11111111A", 1);	
		assertEquals(7, tienda.stockProducto(codProc));
		tienda.nuevaVenta(codProc, "22222222B", 7);	
		assertEquals(0, tienda.stockProducto(codProc));	

		// incrementa el stock	
		tienda.incrementaStock(codProc, 2);
		assertEquals(2, tienda.stockProducto(codProc));
	}

	@Test
	void creaReservasTest() {
		System.out.println("creaReservasTest");
		Tienda tienda = new Tienda();

		// anhade clientes
		tienda.anhadeCliente(new Cliente("11111111A", "D1"));
		tienda.anhadeCliente(new Cliente("22222222B", "D2"));

		// anhade productos
		Producto proc1 = new Producto("P1", 1);
		Producto proc2 = new Producto("P2", 2);
		Producto proc3 = new Producto("P3", 3);
		int cod1 = tienda.anhadeProducto(proc1);
		int cod2 = tienda.anhadeProducto(proc2);
		int cod3 = tienda.anhadeProducto(proc3);

		// ventas que crean reservas
		tienda.nuevaVenta(cod2, "22222222B", 3);
		tienda.nuevaVenta(cod3, "11111111A", 30);

		// comprueba que el stock no ha cambiado	
		assertEquals(1, tienda.stockProducto(cod1));	
		assertEquals(2, tienda.stockProducto(cod2));	
		assertEquals(3, tienda.stockProducto(cod3));
	}

	@Test
	void noAtiendeReservasTest() {
		System.out.println("noAtiendeReservasTest");
		Tienda tienda = new Tienda();

		// anhade clientes
		tienda.anhadeCliente(new Cliente("11111111A", "D1"));
		tienda.anhadeCliente(new Cliente("22222222B", "D2"));

		// anhade productos
		Producto proc1 = new Producto("P1", 1);
		Producto proc2 = new Producto("P2", 2);
		Producto proc3 = new Producto("P3", 3);
		int cod1 = tienda.anhadeProducto(proc1);
		int cod2 = tienda.anhadeProducto(proc2);
		int cod3 = tienda.anhadeProducto(proc3);

		// ventas que crean reservas
		tienda.nuevaVenta(cod2, "22222222B", 20);
		tienda.nuevaVenta(cod2, "22222222B", 5);
		tienda.nuevaVenta(cod3, "11111111A", 30);
		tienda.nuevaVenta(cod3, "22222222B", 5);

		// comprueba que el stock no ha cambiado	
		assertEquals(1, tienda.stockProducto(cod1));	
		assertEquals(2, tienda.stockProducto(cod2));	
		assertEquals(3, tienda.stockProducto(cod3));

		// incrementa stock y comprueba que no se ha atendido ninguna reserva
		List<Reserva> reservasAtendidas;
		reservasAtendidas = tienda.incrementaStock(cod2, 5);
		assertEquals(0, reservasAtendidas.size(), "La lista deberia estar vacia");

		reservasAtendidas = tienda.incrementaStock(cod3, 5);
		assertEquals(0, reservasAtendidas.size(), "La lista deberia estar vacia");

		// comprueba que el stock ha aumentado	
		assertEquals(1, tienda.stockProducto(cod1));	
		assertEquals(7, tienda.stockProducto(cod2));	
		assertEquals(8, tienda.stockProducto(cod3));
	}

	@Test
	void atiendeReservasTest() {
		System.out.println("atiendeReservasTest");
		Tienda tienda = new Tienda();

		// anhade clientes
		Cliente cli1 = new Cliente("11111111A", "D1");
		Cliente cli2 = new Cliente("22222222B", "D2");
		tienda.anhadeCliente(cli1);
		tienda.anhadeCliente(cli2);

		// anhade productos
		Producto proc1 = new Producto("P1", 1);
		Producto proc2 = new Producto("P2", 1);
		Producto proc3 = new Producto("P3", 1);
		int cod1 = tienda.anhadeProducto(proc1);
		int cod2 = tienda.anhadeProducto(proc2);
		int cod3 = tienda.anhadeProducto(proc3);

		// ventas que crean reservas
		tienda.nuevaVenta(cod2, "22222222B", 2);
		tienda.nuevaVenta(cod2, "11111111A", 5);
		tienda.nuevaVenta(cod3, "11111111A", 4);
		tienda.nuevaVenta(cod3, "22222222B", 5);

		// comprueba que el stock no ha cambiado	
		assertEquals(1, tienda.stockProducto(cod1));	
		assertEquals(1, tienda.stockProducto(cod2));	
		assertEquals(1, tienda.stockProducto(cod3));

		// incrementa stock del proc2 y comprueba las reservas atendidas
		//  proc2.unidadas = 1
		//  proc2.reservas = [(cli2, 2), (cli1, 5)]
		List<Reserva> reservasAtendidas;
		reservasAtendidas = tienda.incrementaStock(cod2, 6);
		assertEquals(2, reservasAtendidas.size());
		assertEquals(cli2, reservasAtendidas.get(0).cliente());
		assertEquals(2, reservasAtendidas.get(0).unidades());
		assertEquals(cli1, reservasAtendidas.get(1).cliente());
		assertEquals(5, reservasAtendidas.get(1).unidades());
		assertEquals(0, tienda.stockProducto(cod2));

		// incrementa stock del proc3 y comprueba las reservas atendidas
		//  proc2.unidadas = 1
		//  proc2.reservas = [(cli1, 4), (cli2, 5)]
		reservasAtendidas = tienda.incrementaStock(cod3, 5);
		assertEquals(1, reservasAtendidas.size());
		assertEquals(cli1, reservasAtendidas.get(0).cliente());
		assertEquals(4, reservasAtendidas.get(0).unidades());
		assertEquals(2, tienda.stockProducto(cod3));
	}

	@Test
	void productosEnRangoTest() {
		System.out.println("productosEnRangoTest");
		Tienda tienda = new Tienda();

		SortedMap<Integer, Producto> oraculo = new TreeMap<>();
		Collection<Producto> procsEsperados;
		Collection<Producto> procsInOrden;

		// anhade productos
		Producto[] prods = {
		  new Producto("P1", 1),
		  new Producto("P2", 1),
		  new Producto("P3", 1),
		  new Producto("P4", 12),
		  new Producto("P5", 2),
		  new Producto("P6", 2)};
		for (Producto p: prods) {
			tienda.anhadeProducto(p);
			oraculo.put(p.codigo(), p);
		}

		// ningun producto
		procsEsperados = oraculo.subMap(0, 0).values();
		procsInOrden = tienda.productosEnRango(0, 0);
		assertArrayEquals(procsEsperados.toArray(), procsInOrden.toArray());

		// todos los productos
		procsEsperados = oraculo.subMap(-1, 100000).values();
		procsInOrden = tienda.productosEnRango(-1, 100000);
		System.out.println("Esperados:" + procsEsperados);
		assertArrayEquals(procsEsperados.toArray(), procsInOrden.toArray());

		// subrangos
		int codIni = Integer.min(prods[0].codigo(), prods[1].codigo());
		int codFin = Integer.max(prods[0].codigo(), prods[1].codigo());
		procsEsperados = oraculo.subMap(codIni, codFin).values();
		procsInOrden = tienda.productosEnRango(codIni, codFin);
		System.out.println("Esperados:" + procsEsperados);
		assertArrayEquals(procsEsperados.toArray(), procsInOrden.toArray());

		codIni = Integer.min(prods[2].codigo(), prods[4].codigo());
		codFin = Integer.max(prods[2].codigo(), prods[4].codigo());
		procsEsperados = oraculo.subMap(codIni, codFin).values();
		procsInOrden = tienda.productosEnRango(codIni, codFin);
		System.out.println("Esperados:" + procsEsperados);
		assertArrayEquals(procsEsperados.toArray(), procsInOrden.toArray());
	}
}