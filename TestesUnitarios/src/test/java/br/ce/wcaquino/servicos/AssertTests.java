package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTests {

	@Test
	public void teste() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		Assert.assertEquals(1, 1);

		Assert.assertEquals(0.52, 0.51, 0.1);
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));

		Usuario u1 = new Usuario("Usuario 01");	
		Usuario u2 = new Usuario("Usuario 01");
		Usuario u3 = u2;
		
		Assert.assertEquals(u1, u2);
		Assert.assertSame(u3, u2);


	}

}
