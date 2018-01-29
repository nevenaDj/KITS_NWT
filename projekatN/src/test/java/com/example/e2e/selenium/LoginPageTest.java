package com.example.e2e.selenium;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest {
	private WebDriver browser;

	LoginPage loginPage;
	BuildingPage buildingPage;

	@BeforeMethod
	public void setupSelenium() {
		// instantiate browser
		System.setProperty("webdriver.chrome.driver", "/Users/sabinomi/Downloads/chromedriver");
		browser = new ChromeDriver();
		// maximize window
		browser.manage().window().maximize();
		// navigate
		browser.navigate().to("https://localhost:8443");

		loginPage = PageFactory.initElements(browser, LoginPage.class);
		buildingPage = PageFactory.initElements(browser, BuildingPage.class);

	}

	@Test
	public void testLogin() {
		assertEquals("https://localhost:8443/#/login", browser.getCurrentUrl());

		loginPage.ensureIsDisplayed();

		assertTrue(loginPage.getInputUsername().isDisplayed());
		assertTrue(loginPage.getInputPassword().isDisplayed());

		loginPage.setInputUsername("admin");
		loginPage.setInputPassword("admin");

		loginPage.getOkButton().click();

		buildingPage.ensureIsDisplayed();

		assertEquals("https://localhost:8443/#/buildings", browser.getCurrentUrl());

	}

	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}

}
