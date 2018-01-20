package com.example.e2e.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddApartmentPage {
	private WebDriver driver;

	@FindBy(id = "description")
	private WebElement inputDescription;

	@FindBy(id = "number")
	private WebElement inputNumber;

	@FindBy(className = "save-button-text")
	private WebElement okButton;

	public AddApartmentPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("description")));

	}

	public WebElement getInputDescription() {
		return inputDescription;
	}

	public void setInputDescription(String value) {
		WebElement el = getInputDescription();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getInputNumber() {
		return inputNumber;
	}

	public void setInputNumber(String value) {
		WebElement el = getInputNumber();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getOkButton() {
		return okButton;
	}

}
