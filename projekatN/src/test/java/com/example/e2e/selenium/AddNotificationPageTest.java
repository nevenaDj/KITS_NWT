package com.example.e2e.selenium;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddNotificationPageTest {
	private WebDriver browser;

	LoginPage loginPage;
	CurrentApartmentsPage currentApartmentsPage;
	NotificationPage notificationPage;
	AddNotificationPage addNotificationPage;

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
		currentApartmentsPage = PageFactory.initElements(browser, CurrentApartmentsPage.class);
		notificationPage = PageFactory.initElements(browser, NotificationPage.class);
		addNotificationPage = PageFactory.initElements(browser, AddNotificationPage.class);

	}

	@Test
	public void testAddNotification() {
		assertEquals("https://localhost:8443/#/login", browser.getCurrentUrl());

		loginPage.ensureIsDisplayed();

		assertTrue(loginPage.getInputUsername().isDisplayed());
		assertTrue(loginPage.getInputPassword().isDisplayed());

		loginPage.setInputUsername("user");
		loginPage.setInputPassword("user");

		loginPage.getOkButton().click();

		currentApartmentsPage.ensureIsDisplayed();

		assertEquals("https://localhost:8443/#/tenant/apartments", browser.getCurrentUrl());

		currentApartmentsPage.getNotificationsElement().click();

		notificationPage.ensureIsDisplayed();

		assertEquals("https://localhost:8443/#/tenant/notifications", browser.getCurrentUrl());
		
		notificationPage.getNewElement().click();

		assertTrue(addNotificationPage.getInputText().isDisplayed());

		addNotificationPage.setInputText("new notification");

		addNotificationPage.getOkButton().click();
		
		notificationPage.ensureIsDisplayed();
		assertEquals("https://localhost:8443/#/tenant/notifications", browser.getCurrentUrl());
		

	}

	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}

}
