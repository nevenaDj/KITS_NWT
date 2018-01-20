package com.example.e2e.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;

	@FindBy(name = "username")
	private WebElement inputUsername;

	@FindBy(name = "password")
	private WebElement inputPassword;

	@FindBy(xpath = "//button[1]")
	private WebElement okButton;

	public LoginPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
	}

	public WebElement getInputUsername() {
		return inputUsername;
	}

	public void setInputUsername(String value) {
		WebElement el = getInputUsername();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getInputPassword() {
		return inputPassword;
	}

	public void setInputPassword(String value) {
		WebElement el = getInputPassword();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getOkButton() {
		return okButton;
	}

}
