package br.com.caelum.agiletickets.models;

import static org.junit.Assert.*;

import javax.persistence.Temporal;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class SessaoTest {

	@Test
	public void deveVender1ingressoSeHa2vagas() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);

		assertTrue(sessao.podeReservar(1));
	}

	@Test
	public void naoDeveVender3ingressoSeHa2vagas() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);

		assertFalse(sessao.podeReservar(3));
	}

	@Test
	public void deveVender1ingressoSeHa1vaga() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(1);

		assertTrue(sessao.podeReservar(1));
	}

	@Test
	public void reservarIngressosDeveDiminuirONumeroDeIngressosDisponiveis()
			throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(5);

		sessao.reserva(3);
		assertEquals(2, sessao.getIngressosDisponiveis().intValue());
	}
<<<<<<< HEAD
	
	@Test
	public void deveVender1ingressoSeHa1vaga() throws Exception {
		Sessao sessao = new Sessao();
        sessao.setTotalIngressos(1);

        Assert.assertTrue(sessao.podeReservar(1));
	}
	
	@Test
	public void naoDeveVender0ingressoSeHa0vaga() throws Exception {
		Sessao sessao = new Sessao();
        sessao.setTotalIngressos(0);

        Assert.assertFalse(sessao.podeReservar(0));
	}
=======

	@Test
	public void naoDeveVenderIngressoSeNaoHaVagas() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(0);
		assertFalse(sessao.podeReservar(0));
	}

>>>>>>> b2925d2aa649a2918703bc2218ed040a3304da29
}
