package br.ce.wcaquino.matchers;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DataMatcher extends TypeSafeMatcher<Date> {
	
	private Integer diff;
	private Date hoje = new Date();
	
	public DataMatcher (Integer diff) {
		this.diff = diff;
	}

	public void describeTo(Description description) {
		description.appendText(adicionarDias(hoje, diff).toString());
		
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return isMesmaData(data, adicionarDias(hoje, diff));
	}

}
