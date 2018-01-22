package com.example.e2e.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddOwnerPage {
	private WebDriver driver;

	@FindBy(id = "username")
	private WebElement inputUsername;

	@FindBy(id = "phoneNo")
	private WebElement inputPhoneNo;

	@FindBy(id = "email")
	private WebElement inputEmail;

	@FindBy(id = "save-owner")
	private WebElement okButton;

	public AddOwnerPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
	}

	public WebElement getInputUsername() {
		return inputUsername;
	}

	public void setInputUsername(String value) {
		WebElement el = getInputUsername();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getInputPhoneNo() {
		return inputPhoneNo;
	}

	public void setInputPhoneNo(String value) {
		WebElement el = getInputPhoneNo();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getInputEmail() {
		return inputEmail;
	}

	public void setInputEmail(String value) {
		WebElement el = getInputEmail();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getOkButton() {
		return okButton;
	}

}
