package com.example.e2e.selenium;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddOwnerPageTest {
	private WebDriver browser;

	LoginPage loginPage;
	BuildingPage buildingPage;
	AddBuildingPage addBuildingPage;
	BuildingDetailPage buildigDetailPage;
	AddApartmentPage addApartmentPage;
	ApartmentDetailPage apartmentDetailPage;
	AddOwnerPage addOwnerPage;

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
		addApartmentPage = PageFactory.initElements(browser, AddApartmentPage.class);
		apartmentDetailPage = PageFactory.initElements(browser, ApartmentDetailPage.class);
		addOwnerPage = PageFactory.initElements(browser, AddOwnerPage.class);

	}

	@Test
	public void testAddOwner() {
		assertEquals("https://localhost:8443/#/login", browser.getCurrentUrl());

		// login
		loginPage.ensureIsDisplayed();

		assertTrue(loginPage.getInputUsername().isDisplayed());
		assertTrue(loginPage.getInputPassword().isDisplayed());

		loginPage.setInputUsername("admin");
		loginPage.setInputPassword("admin");

		loginPage.getOkButton().click();

		// home page for admin
		buildingPage.ensureIsDisplayed();

		assertEquals("https://localhost:8443/#/buildings", browser.getCurrentUrl());

		int elements = buildingPage.getBuildingsElement().size();

		// add new building
		buildingPage.getNewElement().click();

		assertEquals("https://localhost:8443/#/addBuilding", browser.getCurrentUrl());

		assertTrue(addBuildingPage.getInputStreet().isDisplayed());
		assertTrue(addBuildingPage.getInputNumber().isDisplayed());
		assertTrue(addBuildingPage.getInputCity().isDisplayed());
		assertTrue(addBuildingPage.getInputZipCode().isDisplayed());

		addBuildingPage.setInputStreet("new street");
		addBuildingPage.setInputNumber("1");
		addBuildingPage.setInputCity("new city");
		addBuildingPage.setInputZipCode("15000");

		addBuildingPage.getOkButton().click();

		// building detail
		buildigDetailPage.ensureIsDisplayed();

		assertTrue(buildigDetailPage.getDivStreet().isDisplayed());
		assertTrue(buildigDetailPage.getDivNumber().isDisplayed());
		assertTrue(buildigDetailPage.getDivCity().isDisplayed());
		assertTrue(buildigDetailPage.getDivZipCode().isDisplayed());

		assertEquals(buildigDetailPage.getDivStreet().getText(), "new street");
		assertEquals(buildigDetailPage.getDivNumber().getText(), "1");
		assertEquals(buildigDetailPage.getDivCity().getText(), "new city");
		assertEquals(buildigDetailPage.getDivZipCode().getText(), "15000");

		buildigDetailPage.getNewApartmentElement().click();

		// add new apartment
		addApartmentPage.ensureIsDisplayed();

		assertTrue(addApartmentPage.getInputDescription().isDisplayed());
		assertTrue(addApartmentPage.getInputNumber().isDisplayed());

		addApartmentPage.setInputDescription("new apartment");
		addApartmentPage.setInputNumber("15");

		addApartmentPage.getOkButton().click();

		apartmentDetailPage.ensureIsDisplayed();

		assertTrue(apartmentDetailPage.getDivDescription().isDisplayed());
		assertTrue(apartmentDetailPage.getDivNumber().isDisplayed());

		assertEquals(apartmentDetailPage.getDivDescription().getText(), "new apartment");
		assertEquals(apartmentDetailPage.getDivNumber().getText(), "15");

		apartmentDetailPage.ensureIsDisplayedAddOwnerButton();

		apartmentDetailPage.getAddOwnerElement().click();

		// add new owner
		addOwnerPage.ensureIsDisplayed();

		assertTrue(addOwnerPage.getInputUsername().isDisplayed());
		assertTrue(addOwnerPage.getInputPhoneNo().isDisplayed());
		assertTrue(addOwnerPage.getInputEmail().isDisplayed());

		addOwnerPage.setInputUsername("new owner");
		addOwnerPage.setInputEmail("owner@gmail.com");
		addOwnerPage.setInputPhoneNo("123456");

		addOwnerPage.getOkButton().click();

		apartmentDetailPage.ensureIsDisplayed();

		((JavascriptExecutor) browser).executeScript("window.scrollTo(0, 10)");

		// delete apartment
		apartmentDetailPage.getDelteElement().click();
		buildigDetailPage.ensureIsDisplayed();

		// delete building
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
