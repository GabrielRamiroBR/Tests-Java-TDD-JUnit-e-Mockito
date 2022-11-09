package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {
	

	public static int contador = 0;
	@Test
	public void inicio() {
		contador = 1;
		
	}

	@Test
	public void verifica() {
		assertThat(contador, is(1));
		
	}


}
