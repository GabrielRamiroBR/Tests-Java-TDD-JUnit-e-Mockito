package br.ce.wcaquino.suites;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.CalculadoraTeste;
import br.ce.wcaquino.servicos.CalculoValorLocacaoTeste;
import br.ce.wcaquino.servicos.LocacaoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTeste.class,
	LocacaoServiceTest.class,
	CalculoValorLocacaoTeste.class
})
public class SuiteExecucao {
	// remova se puder
	/*
	 * @BeforeClass public static void beforeClass() {
	 * System.out.println("BeforeClass"); }
	 * 
	 * @Before public static void before() { System.out.println("1"); }
	 * 
	 * @After public static void after() { System.out.println("2"); }
	 * 
	 * @AfterClass public static void afterClass() {
	 * System.out.println("AfterClass"); }
	 */
	
}
