package com.example.e2e.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddGlitchTypePage {
	private WebDriver driver;

	@FindBy(id = "type")
	private WebElement inputType;

	@FindBy(className = "save-button-text")
	private WebElement okButton;

	public AddGlitchTypePage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("type")));
	}

	public WebElement getInputType() {
		return inputType;
	}

	public void setInputType(String value) {
		WebElement el = getInputType();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getOkButton() {
		return okButton;
	}

}
