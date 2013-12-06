package br.com.caelum.agiletickets.integration;

import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ReservaTest {
	public static String BASE_URL = "http://localhost:8080";
	private static WebDriver browser;

	@BeforeClass
	public static void inicializa() {
		browser = new FirefoxDriver();
	}

	@Test
	public void deveReservarUmIngressoMeiaEntrada() {
		browser.get(BASE_URL);
		WebElement ul = browser.findElement(By.id("sessoes"));
		List<WebElement> listaDeLis = ul.findElements(By.tagName("li"));
		listaDeLis.get(2).findElement(By.tagName("a")).click();
		WebElement qtde = browser.findElement(By.id("qtde"));
		qtde.sendKeys("1");
		qtde.submit();
		WebElement mensagem = browser.findElement(By.id("message"));
		Assert.assertTrue(mensagem.getText().contains("12,34"));
	}

}
