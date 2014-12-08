package pcrf.betha.test.compras.lista;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;

import pcrf.betha.compras.entity.lista.Lista;
import pcrf.betha.compras.fcd.lista.ListaFcd;

public class ListaTest {

	@Inject
	private ListaFcd listaFcd;
	
	@Test
	public void testObter() {
		long id = 1;
		
		Lista lista = listaFcd.obter(id);
		
		assertNotNull(lista);
		
		assertEquals(id, (long)lista.getIdlista());
	}

	@Test
	public void testObterTodos() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSalvar() {
		fail("Not yet implemented"); // TODO
	}

}
