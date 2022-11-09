package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Exceptions.CalculadoraException;



public class CalculadoraTeste {
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		// cenário
		int a = 6, b = 3;

		// ação
		int resultado = calc.soma(a, b);

		// verificação8
		assertEquals(9, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		// cenário
		int a = 6, b = 3;

		// ação
		int resultado = calc.subtrai(a, b);

		// verificação8
		assertEquals(3, resultado);
	}
	
	@Test
	public void deveDividirirDoisValores() throws CalculadoraException {
		// cenário
		int a = 6, b = 3;

		// ação
		int resultado = calc.divide(a, b);

		// verificação8
		assertEquals(2, resultado);
	}
	
	@Test
	public void deveLançarExcecaoAoDividirPorZero() {
		int a = 6, b = 0;
		
		//ação
		try {
			int resultado = calc.divide(a, b);
			Assert.fail();
		}catch(CalculadoraException e) {
			assertThat(e.getMessage(), CoreMatchers.is("Divisão por zero!"));
		}

	}
	
	@Test
	public void deveDividirString() {
		//cenario
		String a = "6";
		String b = "3";
		
		//ação
		int resultado = calc.divideString(a, b);
		
		//verificação
		Assert.assertEquals(2, resultado);
	}
}
