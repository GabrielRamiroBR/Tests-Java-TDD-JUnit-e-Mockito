package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builder.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builder.UsuarioBuilder.umUsuario;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import Exceptions.FilmeSemEstoqueException;
import Exceptions.LocadoraException;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquinodaos.LocacaoDAO;
import br.ce.wcaquinodaos.LocacaoDAOFake;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTeste {
	private LocacaoService servico;
	@Parameter
	public List<Filme>filmes;
	@Parameter(value = 1)
	public Double valorLocacao;
	@Parameter(value = 2)
	public String string;

	
	@Before
	public void setup() {
		servico = new LocacaoService();
		LocacaoDAO dao = new LocacaoDAOFake();
		servico.setLocacaoDAO(dao);
		}
	private static Filme filme1 = umFilme().agora();
	private static Filme filme2 = umFilme().agora();
	private static Filme filme3 = umFilme().agora();
	private static Filme filme4 = umFilme().agora();
	private static Filme filme5 = umFilme().agora();
	private static Filme filme6 = umFilme().agora();
	
	@Parameters(name = "Teste {2} - valor {1}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2, filme3), 16.5, "3 filmes 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 19.5, "4 filmes 40%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 21.0, "5 filmes 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 21.0, "6 filmes 100%"}
		});
	}
	
	@Test
	public void deveCalcularValorDaLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = umUsuario().agora();

		
		//ação
		Locacao locacao = servico.alugarFilme(usuario, filmes);
		
		//validação
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(valorLocacao));
	}

}
