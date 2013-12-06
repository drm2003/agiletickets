package br.com.caelum.agiletickets;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.joda.time.DateTime;

import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Estabelecimento;
import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;
import br.com.caelum.vraptor.util.jpa.EntityManagerFactoryCreator;

public class PreencheBanco {
	private static final int TOTAL_INGRESSOS = 10;
	private static final int DURACAO_EM_MINUTOS = 60 * 3;
	static EntityManagerFactoryCreator creator;
	static EntityManagerCreator managerCreator;
	
 public static void main(String[] args) {
        EntityManagerFactoryCreator creator = new EntityManagerFactoryCreator();
        creator.create();
        EntityManagerCreator managerCreator = new EntityManagerCreator(creator.getInstance());
        managerCreator.create();
        EntityManager manager = managerCreator.getInstance();

        manager.getTransaction().begin();
        manager.createQuery("delete from Sessao").executeUpdate();
        manager.createQuery("delete from Espetaculo").executeUpdate();
        manager.createQuery("delete from Estabelecimento").executeUpdate();
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Casa de shows");
        estabelecimento.setEndereco("Rua dos Silveiras, 12345");

        Espetaculo espetaculo = new Espetaculo();
        espetaculo.setEstabelecimento(estabelecimento);
        espetaculo.setNome("Depeche Mode");
        espetaculo.setTipo(TipoDeEspetaculo.SHOW);

        manager.persist(estabelecimento);
        manager.persist(espetaculo);

        for (int i = 0; i < 10; i++) {
                Sessao sessao = new Sessao();
                sessao.setEspetaculo(espetaculo);
                sessao.setInicio(new DateTime().plusDays(7+i));
                sessao.setDuracaoEmMinutos(60 * 3);
                sessao.setTotalIngressos(10);
                sessao.setIngressosReservados(10 - i);
                sessao.setPreco(new BigDecimal("12.34"));
                manager.persist(sessao);
        }

        manager.getTransaction().commit();
        manager.close();
}

	private static Sessao criaSessao(Espetaculo espetaculo, int i) {
		Sessao sessao = new Sessao();
		sessao.setEspetaculo(espetaculo);
		sessao.setInicio(new DateTime().plusDays(7+i));
		sessao.setDuracaoEmMinutos(DURACAO_EM_MINUTOS);
		sessao.setTotalIngressos(TOTAL_INGRESSOS);
		sessao.setIngressosReservados(10 - i);
		return sessao;
	}

	private static Espetaculo criaEspetaculo(Estabelecimento estabelecimento) {
		Espetaculo espetaculo = new Espetaculo();
		espetaculo.setEstabelecimento(estabelecimento);
		espetaculo.setNome("Depeche Mode");
		espetaculo.setTipo(TipoDeEspetaculo.SHOW);
		return espetaculo;
	}

	private static Estabelecimento criaEstabelecimento() {
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setNome("Casa de shows");
		estabelecimento.setEndereco("Rua dos Silveiras, 12345");
		return estabelecimento;
	}

	private static void limpaTabelasSessaoEspetaculoEstabelecimento(
			EntityManager manager) {
		manager.createQuery("delete from Sessao").executeUpdate();
		manager.createQuery("delete from Espetaculo").executeUpdate();
		manager.createQuery("delete from Estabelecimento").executeUpdate();
	}

	private static EntityManager conexao() {
		creator = new EntityManagerFactoryCreator();
		creator.create();
		managerCreator = new EntityManagerCreator(creator.getInstance());
		managerCreator.create();
		return managerCreator.getInstance();
	}
	
	private static EntityManager criaFabricas(){
		creator = new EntityManagerFactoryCreator();
		creator.create();
		
		managerCreator = new EntityManagerCreator(creator.getInstance());
		managerCreator.create();
		
		return managerCreator.getInstance();
	}
}
