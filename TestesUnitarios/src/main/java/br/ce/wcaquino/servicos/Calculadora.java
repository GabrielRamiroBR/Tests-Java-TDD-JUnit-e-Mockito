package br.ce.wcaquino.servicos;

import Exceptions.CalculadoraException;

public class Calculadora {

	public int soma(int a, int b) {
		// TODO Auto-generated method stub
		return a + b;
	}

	public int subtrai(int a, int b) {
		// TODO Auto-generated method stub
		return a - b;
	}

	public int divide(int a, int b) throws CalculadoraException {
		// TODO Auto-generated method stub
		if (b == 0)
			throw new CalculadoraException("Divisão por zero!");
		return a/b;
	}
	
	public int divideString(String a, String b) {
		return Integer.valueOf(a)/Integer.valueOf(b);
	}

}
