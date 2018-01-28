package com.example.e2e.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddNotificationPage {
	private WebDriver driver;

	@FindBy(id = "text")
	private WebElement inputText;

	@FindBy(className = "save-button-text")
	private WebElement okButton;

	public AddNotificationPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("text")));
	}

	public WebElement getInputText() {
		return inputText;
	}

	public void setInputText(String value) {
		WebElement el = getInputText();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getOkButton() {
		return okButton;
	}

}
