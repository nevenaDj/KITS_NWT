package com.example.e2e.selenium;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddCompanyPageTest {
	private WebDriver browser;

	LoginPage loginPage;
	BuildingPage buildingPage;
	CompanyPage companyPage;
	AddCompanyPage addCompnayPage;
	CompanyDetailPage companyDetailPage;

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
		companyPage = PageFactory.initElements(browser, CompanyPage.class);
		addCompnayPage = PageFactory.initElements(browser, AddCompanyPage.class);
		companyDetailPage = PageFactory.initElements(browser, CompanyDetailPage.class);

	}

	@Test
	public void testAddBuilding() {
		assertEquals("https://localhost:8443/#/login", browser.getCurrentUrl());

		loginPage.ensureIsDisplayed();

		assertTrue(loginPage.getInputUsername().isDisplayed());
		assertTrue(loginPage.getInputPassword().isDisplayed());

		loginPage.setInputUsername("admin");
		loginPage.setInputPassword("admin");

		loginPage.getOkButton().click();

		buildingPage.ensureIsDisplayed();

		assertEquals("https://localhost:8443/#/buildings", browser.getCurrentUrl());

		buildingPage.getCompaniesElement().click();

		assertEquals("https://localhost:8443/#/companies", browser.getCurrentUrl());

		companyPage.ensureIsDisplayed();

		int elements = companyPage.getCompaniesElement().size();

		companyPage.getNewElement().click();

		addCompnayPage.ensureIsDisplayed();

		assertEquals("https://localhost:8443/#/addCompany", browser.getCurrentUrl());

		assertTrue(addCompnayPage.getInputUsername().isDisplayed());
		assertTrue(addCompnayPage.getInputPhoneNo().isDisplayed());
		assertTrue(addCompnayPage.getInputEmail().isDisplayed());
		assertTrue(addCompnayPage.getInputStreet().isDisplayed());
		assertTrue(addCompnayPage.getInputNumber().isDisplayed());
		assertTrue(addCompnayPage.getInputCity().isDisplayed());
		assertTrue(addCompnayPage.getInputZipCode().isDisplayed());

		addCompnayPage.setInputUsername("company");
		addCompnayPage.setInputPhoneNo("123456");
		addCompnayPage.setInputEmail("company@gmail.com");
		addCompnayPage.setInputCity("new city");
		addCompnayPage.setInputZipCode("15000");
		addCompnayPage.setInputStreet("new street");
		addCompnayPage.setInputNumber("1");

		addCompnayPage.getOkButton().click();

		companyPage.ensureIsDisplayed();
		assertEquals(companyPage.getCompaniesElement().size(), elements + 1);
		int elementsNum = companyPage.getCompaniesDetailElement().size();
		companyPage.getCompaniesDetailElement().get(elementsNum - 1).click();

		companyDetailPage.ensureIsDisplayed();
		assertEquals(companyDetailPage.getDivElements().get(0).getText(), "company");
		assertEquals(companyDetailPage.getDivElements().get(1).getText(), "123456");
		assertEquals(companyDetailPage.getDivElements().get(2).getText(), "company@gmail.com");
		assertEquals(companyDetailPage.getDivElements().get(3).getText(), "new city");
		assertEquals(companyDetailPage.getDivElements().get(4).getText(), "15000");
		assertEquals(companyDetailPage.getDivElements().get(5).getText(), "new street");
		assertEquals(companyDetailPage.getDivElements().get(6).getText(), "1");
		companyDetailPage.getDelteElement().click();

		companyPage.ensureIsDisplayed();
		assertEquals(companyPage.getCompaniesElement().size(), elements);

	}

	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}

}
