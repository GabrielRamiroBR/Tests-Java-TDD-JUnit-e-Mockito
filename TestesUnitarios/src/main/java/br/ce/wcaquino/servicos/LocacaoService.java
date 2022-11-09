package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import Exceptions.FilmeSemEstoqueException;
import Exceptions.LocadoraException;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import br.ce.wcaquinodaos.LocacaoDAO;

public class LocacaoService {
	private LocacaoDAO dao;
	
	private double somaValorLocacao = 0;	
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		
		if (usuario == null)
			throw new LocadoraException("Usuario vazio");
		
		if (filmes == null || filmes.isEmpty())
			throw new LocadoraException("Filme vazio");
		
		for (Filme f : filmes) {
			if (f.getEstoque() == 0)
				throw new FilmeSemEstoqueException("Filme sem Estoque");
		}
	
		
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		int contador = 1;
		for (Filme f : filmes) {
			switch (contador) {
			case 3:
				somaValorLocacao += f.getPrecoLocacao()*(1 - 0.25);
				break;	
			case 4:
				somaValorLocacao += f.getPrecoLocacao()*(1 - 0.5);
				break;
			case 5:
				somaValorLocacao += f.getPrecoLocacao()*(1 - 0.75);
				break;
			case 6:
				somaValorLocacao += f.getPrecoLocacao()*(1 - 1);
				break;
			default:
				somaValorLocacao += f.getPrecoLocacao();
			}
			contador++;
		}
		locacao.setValor(somaValorLocacao);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY))
			dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		dao.salvar(locacao);
		
		return locacao;
	}
	
	public void setLocacaoDAO(LocacaoDAO dao) {
		this.dao = dao;
	}

}