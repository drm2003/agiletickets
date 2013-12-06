package br.com.caelum.agiletickets.controllers;

import static br.com.caelum.vraptor.view.Results.status;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.caelum.agiletickets.domain.Agenda;
import br.com.caelum.agiletickets.domain.DiretorioDeEstabelecimentos;
import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Periodicidade;
import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;

import com.google.common.base.Strings;

@Resource
public class EspetaculosController {

	private NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

	private final Agenda agenda;
	private Validator validator;
	private Result result;

	private final DiretorioDeEstabelecimentos estabelecimentos;

	public EspetaculosController(Agenda agenda, DiretorioDeEstabelecimentos estabelecimentos, Validator validator, Result result) {
		this.agenda = agenda;
		this.estabelecimentos = estabelecimentos;
		this.validator = validator;
		this.result = result;
	}

	@Get @Path("/espetaculos")
	public List<Espetaculo> lista() {
		// inclui a lista de estabelecimentos
		result.include("estabelecimentos", estabelecimentos.todos());
		return agenda.espetaculos();
	}

	@Post @Path("/espetaculos")
	public void adicionaEspetaculo(Espetaculo espetaculo) {
		// aqui eh onde fazemos as varias validacoes
		// se nao tiver nome, avisa o usuario
		// se nao tiver descricao, avisa o usuario
		validaNomeOuDescricaoNulaOuVazia(espetaculo);

		agenda.cadastra(espetaculo);
		result.redirectTo(this).lista();
	}

	private void validaNomeOuDescricaoNulaOuVazia(Espetaculo espetaculo) {
		if (Strings.isNullOrEmpty(espetaculo.getNome())) {
			mensagemValidacao("Nome do espetáculo nao pode estar em branco","");
		}
		if (Strings.isNullOrEmpty(espetaculo.getDescricao())) {
			mensagemValidacao("Descricao do espetaculo nao pode estar em branco", "");
		}
		validator.onErrorRedirectTo(this).lista();
	}

	private void mensagemValidacao(String mensagem,String categoria) {
		validator.add(new ValidationMessage(mensagem, categoria));
	}

	@Get @Path("/sessao/{id}")
	public void sessao(Long id) {
		Sessao sessao = agenda.sessao(id);
		if (isSessaoNull(sessao)) {
			result.notFound();
		}

		result.include("sessao", sessao);
	}

	@Post @Path("/sessao/{sessaoId}/reserva")
	public void reserva(Long sessaoId, final Integer quantidade) {
		Sessao sessao = agenda.sessao(sessaoId);
		if (isSessaoNull(sessao)) {
			result.notFound();
			return;
		}

		validaQuantidadeMenorQueUmESeNaoPodeReservar(quantidade, sessao);

		sessao.reserva(quantidade);
		result.include("message", "Sessao reservada com sucesso");

		result.redirectTo(IndexController.class).index();
	}

	private void validaQuantidadeMenorQueUmESeNaoPodeReservar(
			final Integer quantidade, Sessao sessao) {
		if (quantidade < 1) {
			mensagemValidacao("Voce deve escolher um lugar ou mais", "");
		}

		if (!sessao.podeReservar(quantidade)) {
			mensagemValidacao("Nao existem ingressos disponíeis", "");
		}

		// em caso de erro, redireciona para a lista de sessao
		validator.onErrorRedirectTo(this).sessao(sessao.getId());
	}

	private boolean isSessaoNull(Sessao sessao) {
		return sessao == null;
	}

	@Get @Path("/espetaculo/{espetaculoId}/sessoes")
	public void sessoes(Long espetaculoId) {
		Espetaculo espetaculo = carregaEspetaculo(espetaculoId);

		result.include("espetaculo", espetaculo);
	}


	@Post @Path("/espetaculo/{espetaculoId}/sessoes")
	public void cadastraSessoes(Long espetaculoId, LocalDate inicio, LocalDate fim, String xxx, Periodicidade periodicidade) {
		Espetaculo espetaculo = carregaEspetaculo(espetaculoId);

		// aqui faz a magica!
		// cria sessoes baseado no periodo de inicio e fim passados pelo usuario
		LocalTime time = new LocalTime(xxx);
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, time, periodicidade);

		agenda.agende(sessoes);

		result.include("message", sessoes.size() + " sessoes criadas com sucesso");
		result.redirectTo(this).lista();
	}
	private Espetaculo carregaEspetaculo(Long espetaculoId) {
		Espetaculo espetaculo;
		try{
			espetaculo = agenda.consultaEspetaculoPorId(espetaculoId);
		}catch (Exception e){
			return null;
		}
		if (espetaculo == null) {
			mensagemValidacao("", "");
		}
		//validator.onErrorUse(status()).notFound();
		return espetaculo;
	}
}
