package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builder.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builder.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builder.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.caiEm;
import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import Exceptions.FilmeSemEstoqueException;
import Exceptions.LocadoraException;
import br.ce.wcaquino.builder.FilmeBuilder;
import br.ce.wcaquino.builder.UsuarioBuilder;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.matchers.DataMatcher;
import br.ce.wcaquino.matchers.DiaSemanaMatcher;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;
import br.ce.wcaquinodaos.LocacaoDAOFake;
import buildermaster.BuilderMaster;
import br.ce.wcaquinodaos.LocacaoDAO;

public class LocacaoServiceTest {

	private LocacaoService servico;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		servico = new LocacaoService();
		LocacaoDAO dao = new LocacaoDAOFake();
		servico.setLocacaoDAO(dao);
	}

	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenário
		Usuario usuario = umUsuario().agora();
		Filme filme1 = umFilme().agora();
		Filme filme2 = umFilme().agora();
		ArrayList<Filme> filmes = new ArrayList<Filme>();
		filmes.add(filme1);
		filmes.add(filme2);

		// Ação

		Locacao locacao = servico.alugarFilme(usuario, filmes);

		// validação
		error.checkThat(locacao.getValor(), is(equalTo(12.0)));
		/*
		 * error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),
		 * is(true));
		 */
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		/*
		 * error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(),
		 * DataUtils.obterDataComDiferencaDias(1)), is(true));
		 */
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));

	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		// cenário
		Usuario usuario = umUsuario().agora();
		ArrayList<Filme> filmes = new ArrayList<Filme>();
		Filme filme1 = umFilmeSemEstoque().agora();
		Filme filme2 = umFilmeSemEstoque().agora();
		Filme filme3 = umFilmeSemEstoque().agora();
		filmes.add(filme1);
		filmes.add(filme2);
		filmes.add(filme3);

		// Ação

		servico.alugarFilme(usuario, filmes);
	}

	@Test
	public void teste_filmeSemEstoque2() {
		// cenário
		Usuario usuario = umUsuario().agora();
		ArrayList<Filme> filmes = new ArrayList<Filme>();
		Filme filme1 = umFilme().semEstoque().agora();
		Filme filme2 = umFilme().semEstoque().agora();
		Filme filme3 = umFilme().semEstoque().agora();
		filmes.add(filme1);
		filmes.add(filme2);
		filmes.add(filme3);

		// Ação

		try {
			servico.alugarFilme(usuario, filmes);
			Assert.fail();
		} catch (Exception e) {
			assertEquals(e.getClass(), FilmeSemEstoqueException.class);
		}
	}

	@Test
	public void teste_filmeSemEstoque3() throws Exception {

		// cenário
		Usuario usuario = umUsuario().agora();
		ArrayList<Filme> filmes = new ArrayList<Filme>();
		Filme filme1 = umFilme().semEstoque().agora();
		Filme filme2 = umFilme().semEstoque().agora();
		Filme filme3 = umFilme().semEstoque().agora();
		filmes.add(filme1);
		filmes.add(filme2);
		filmes.add(filme3);

		exception.expect(FilmeSemEstoqueException.class);
		exception.expectMessage("Filme sem Estoque");

		// Ação

		Locacao locacao = servico.alugarFilme(usuario, filmes);
	}

	@Test
	public void naoDeveAlugarFilmeSemUSuario() throws FilmeSemEstoqueException {
		Usuario usuario = umUsuario().agora();
		ArrayList<Filme> filmes = new ArrayList<Filme>();
		Filme filme1 = umFilme().semEstoque().agora();
		Filme filme2 = umFilme().semEstoque().agora();
		Filme filme3 = umFilme().semEstoque().agora();
		filmes.add(filme1);
		filmes.add(filme2);
		filmes.add(filme3);

		// ação

		try {
			servico.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));

		}
	}

	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = umUsuario().agora();
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		// ação
		servico.alugarFilme(usuario, null);
	}

	@Test
	public void deveDevolverNaSegundaSeAlugaSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		Usuario usuario = umUsuario().agora();
		ArrayList<Filme> filmes = new ArrayList<Filme>();
		/* List<Filme> filmes = Arrays.asList(new Filme ("X-men", 1, 6.0)); */

		Filme filme1 = umFilme().agora();
		filmes.add(filme1);


		// ação
		Locacao locacao = servico.alugarFilme(usuario, filmes);

		// verificação
		
		  Assert.assertThat(locacao.getDataRetorno(), new DiaSemanaMatcher(Calendar.SUNDAY));
		 
		/* assertThat(locacao.getDataRetorno(), caiNumaSegunda()); */
	}
	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Locacao.class);
	}

}
