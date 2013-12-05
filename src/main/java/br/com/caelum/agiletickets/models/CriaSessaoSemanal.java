package br.com.caelum.agiletickets.models;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Weeks;

public class CriaSessaoSemanal implements CriaSessoes {
	@Override
	public List<Sessao> retornaSessoes(LocalDate inicio, LocalDate fim, LocalTime horario, Espetaculo espetaculo) {
		int intervalo = 0;
		intervalo = Weeks.weeksBetween(inicio, fim).getWeeks();
		
		List<Sessao> sessoes = new ArrayList<Sessao>();
		for (int i = 0; i <= intervalo; i++) {
			DateTime dateTime = retornaDataDaSessao(inicio, horario, i);
			sessoes.add(new Sessao(espetaculo, dateTime));
		}
		return sessoes;
	}
	
	private DateTime retornaDataDaSessao(LocalDate inicio, LocalTime horario, int i) {
		DateTime dateTime;
			dateTime = inicio.toDateTime(horario).toDateTime().plusWeeks(i);
		return dateTime;
	}
}
