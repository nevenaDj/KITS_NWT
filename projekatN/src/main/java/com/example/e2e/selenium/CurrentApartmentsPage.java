package com.example.e2e.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CurrentApartmentsPage {
	private WebDriver driver;

	@FindBy(className = "weather2")
	private WebElement apartmentElement;

	@FindBy(id = "notifications")
	private WebElement notificationsElement;

	public CurrentApartmentsPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("weather2")));
	}

	public WebElement getNotificationsElement() {
		return notificationsElement;
	}

}
