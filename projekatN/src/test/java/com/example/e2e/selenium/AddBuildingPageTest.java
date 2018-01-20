package com.example.e2e.selenium;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddBuildingPageTest {
	private WebDriver browser;

	LoginPage loginPage;
	BuildingPage buildingPage;
	AddBuildingPage addBuildingPage;
	BuildingDetailPage buildigDetailPage;

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
		addBuildingPage = PageFactory.initElements(browser, AddBuildingPage.class);
		buildigDetailPage = PageFactory.initElements(browser, BuildingDetailPage.class);

	}

	@Test
	public void testAddBuilding() {
		assertEquals("https://localhost:8443/login", browser.getCurrentUrl());

		loginPage.ensureIsDisplayed();

		assertTrue(loginPage.getInputUsername().isDisplayed());
		assertTrue(loginPage.getInputPassword().isDisplayed());

		loginPage.setInputUsername("admin");
		loginPage.setInputPassword("admin");

		loginPage.getOkButton().click();

		buildingPage.ensureIsDisplayed();

		assertEquals("https://localhost:8443/buildings", browser.getCurrentUrl());

		int elements = buildingPage.getBuildingsElement().size();

		buildingPage.getNewElement().click();

		assertEquals("https://localhost:8443/addBuilding", browser.getCurrentUrl());

		assertTrue(addBuildingPage.getInputStreet().isDisplayed());
		assertTrue(addBuildingPage.getInputNumber().isDisplayed());
		assertTrue(addBuildingPage.getInputCity().isDisplayed());
		assertTrue(addBuildingPage.getInputZipCode().isDisplayed());

		addBuildingPage.setInputStreet("new street");
		addBuildingPage.setInputNumber("1");
		addBuildingPage.setInputCity("new city");
		addBuildingPage.setInputZipCode("15000");

		addBuildingPage.getOkButton().click();

		buildigDetailPage.ensureIsDisplayed();

		assertTrue(buildigDetailPage.getDivStreet().isDisplayed());
		assertTrue(buildigDetailPage.getDivNumber().isDisplayed());
		assertTrue(buildigDetailPage.getDivCity().isDisplayed());
		assertTrue(buildigDetailPage.getDivZipCode().isDisplayed());

		assertEquals(buildigDetailPage.getDivStreet().getText(), "new street");
		assertEquals(buildigDetailPage.getDivNumber().getText(), "1");
		assertEquals(buildigDetailPage.getDivCity().getText(), "new city");
		assertEquals(buildigDetailPage.getDivZipCode().getText(), "15000");

		buildigDetailPage.getDelteElement().click();

		buildingPage.ensureIsDisplayed();

		assertEquals(buildingPage.getBuildingsElement().size(), elements);

	}

	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}

}
