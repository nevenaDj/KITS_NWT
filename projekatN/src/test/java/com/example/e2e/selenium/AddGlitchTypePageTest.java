package com.example.e2e.selenium;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddGlitchTypePageTest {
	private WebDriver browser;

	LoginPage loginPage;
	BuildingPage buildingPage;
	GlitchTypePage glitchTypePage;
	AddGlitchTypePage addGlitchTypePage;

	@BeforeMethod
	public void setupSelenium() {
		// instantiate browser
		System.setProperty("webdriver.chrome.driver", "C:/javatools/selenium-chrome-driver/chromedriver.exe");
		browser = new ChromeDriver();
		// maximize window
		browser.manage().window().maximize();
		// navigate
		browser.navigate().to("https://localhost:8443");

		loginPage = PageFactory.initElements(browser, LoginPage.class);
		buildingPage = PageFactory.initElements(browser, BuildingPage.class);
		glitchTypePage = PageFactory.initElements(browser, GlitchTypePage.class);
		addGlitchTypePage = PageFactory.initElements(browser, AddGlitchTypePage.class);

	}

	@Test
	public void testAddBuilding() {
		assertEquals("https://localhost:8443/#/login", browser.getCurrentUrl());

		//login
		loginPage.ensureIsDisplayed();
		assertTrue(loginPage.getInputUsername().isDisplayed());
		assertTrue(loginPage.getInputPassword().isDisplayed());
		loginPage.setInputUsername("admin");
		loginPage.setInputPassword("admin");
		loginPage.getOkButton().click();

		//admin home page
		buildingPage.ensureIsDisplayed();
		assertEquals("https://localhost:8443/#/buildings", browser.getCurrentUrl());
		
		//go to glitch types
		buildingPage.getGlitchTypesElement().click();

		//glitch types
		assertEquals("https://localhost:8443/#/glitchTypes", browser.getCurrentUrl());
		glitchTypePage.ensureIsDisplayed();
		int numGlitchTypes = glitchTypePage.getGlitchTypeTableSize();
		glitchTypePage.getNewElement().click();
		
		//add new glitch type
		addGlitchTypePage.ensureIsDisplayed();
		assertEquals("https://localhost:8443/#/addGlitchType", browser.getCurrentUrl());
		assertTrue(addGlitchTypePage.getInputType().isDisplayed());
		addGlitchTypePage.setInputType("new type");
		addGlitchTypePage.getOkButton().click();
		
		//glitch types
		glitchTypePage.ensureIsDisplayed();
		assertEquals(numGlitchTypes + 1, glitchTypePage.getGlitchTypeTableSize());
		int num = glitchTypePage.getDeleteElements().size();

		//delete glitch type
		glitchTypePage.getDeleteElements().get(num - 1).click();
		browser.navigate().to("https://localhost:8443/#/glitchTypes");
		glitchTypePage.ensureIsDisplayed();
		assertEquals(numGlitchTypes, glitchTypePage.getGlitchTypeTableSize());

	}

	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}

}
