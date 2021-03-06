package com.example.e2e.selenium;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotificationPage {
	private WebDriver driver;

	@FindBy(className = "small-btn")
	private WebElement newElement;
	
	@FindBy(className = "notifications")
	private List<WebElement> notificationsDetailElement;

	public NotificationPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(newElement));
	}

	public WebElement getNewElement() {
		return newElement;
	}

	public List<WebElement> getNotificationsDetailElement() {
		return notificationsDetailElement;
	}	

}
