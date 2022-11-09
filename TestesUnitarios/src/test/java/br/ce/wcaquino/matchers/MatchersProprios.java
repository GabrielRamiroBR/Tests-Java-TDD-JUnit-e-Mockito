package br.ce.wcaquino.matchers;

import java.util.Calendar;

public class MatchersProprios {
	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}

	public static DataMatcher ehHojeComDiferencaDias(Integer diff) {
		return new DataMatcher(diff);
	}
	
	public static DataMatcher ehHoje() {
		return new DataMatcher(0);
	}
}
