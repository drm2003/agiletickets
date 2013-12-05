package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.testng.Assert;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}


	@Test
	public void deveRetornarUmaSessaoParaSessaoDiariaComInicioEFimNoMesmoDia() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate();
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				Periodicidade.DIARIA);
		// Verifica se Sessão criada
		Assert.assertEquals(sessoes.size(), 1);
		// Confere data horário
		Assert.assertEquals(inicio.toDateTime(horario).toDateTime(), sessoes.get(0).getInicio());
	}
	
	@Test
	public void deveRetornarDuasSessoesParaSessaoDiariaComInicioEFimEmDoisDias(){
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().plusDays(1);
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				Periodicidade.DIARIA);
		// Verifica se Sessão criada
		Assert.assertEquals(sessoes.size(), 2);
		// Confere data horário
		Assert.assertEquals(inicio.toDateTime(horario).toDateTime(), sessoes.get(0).getInicio());
		Assert.assertEquals(inicio.toDateTime(horario).toDateTime().plusDays(1), sessoes.get(1).getInicio());
	}
	
	@Test
	public void deveRetornarNuloSeDiaDeFimForMenorQueDiaDeInicio(){
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().minusDays(1);
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				Periodicidade.DIARIA);
		// Verifica não criou sessão
		Assert.assertNull(sessoes);
	}
	
	@Test
	public void deverRetornarNuloSeDataInicioOuDataFimOuHorarioVazios(){
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = null;
		LocalDate fim = new LocalDate();
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				Periodicidade.DIARIA);
		// Verifica não criou sessão
		Assert.assertNull(sessoes);
		//
		inicio = new LocalDate();
		fim = null;
		horario = new LocalTime();
		sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				Periodicidade.DIARIA);
		// Verifica não criou sessão
		Assert.assertNull(sessoes);
		//
		inicio = new LocalDate();
		inicio = null;
		fim = new LocalDate();
		horario = null;
		sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				Periodicidade.DIARIA);
		// Verifica não criou sessão
		Assert.assertNull(sessoes);
	}
	
	@Test
	public void deverRetornarNuloSeDataInicioForAnteriorADataAtual(){
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate().minusDays(1);
		LocalDate fim = new LocalDate();
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				Periodicidade.DIARIA);
		// Verifica não criou sessão
		Assert.assertNull(sessoes);
		Assert.assertTrue(false);
	}
	
	@Test
	public void deveRetornarDuasSessoesSeIntervaloDeQuinzeDias() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().plusDays(14);
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				Periodicidade.SEMANAL);
		Assert.assertEquals(sessoes.size(), 3);
		// Confere data horário
		Assert.assertEquals(inicio.toDateTime(horario).toDateTime(), sessoes.get(0).getInicio());
		Assert.assertEquals(inicio.toDateTime(horario).toDateTime().plusDays(7), sessoes.get(1).getInicio());
		Assert.assertEquals(inicio.toDateTime(horario).toDateTime().plusDays(14), sessoes.get(2).getInicio());
	}
}
