package br.com.caelum.agiletickets.models;

public enum Periodicidade {
	DIARIA(new CriaSessaoDiaria()), SEMANAL(new CriaSessaoSemanal());
	
	private CriaSessoes criaSessoes;
	Periodicidade (CriaSessoes criaSessoes){
		
		this.criaSessoes = criaSessoes;
	}
	
	public CriaSessoes getCriaSessoes(){
		return this.criaSessoes ;
	}
	
}
