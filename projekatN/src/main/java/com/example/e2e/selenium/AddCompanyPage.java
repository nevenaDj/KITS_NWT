package com.example.e2e.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddCompanyPage {
	private WebDriver driver;

	@FindBy(id = "username")
	private WebElement inputUsername;

	@FindBy(id = "phoneNo")
	private WebElement inputPhoneNo;

	@FindBy(id = "email")
	private WebElement inputEmail;

	@FindBy(id = "street")
	private WebElement inputStreet;

	@FindBy(id = "number")
	private WebElement inputNumber;

	@FindBy(id = "city")
	private WebElement inputCity;

	@FindBy(id = "zipCode")
	private WebElement inputZipCode;

	@FindBy(className = "save-button-text")
	private WebElement okButton;

	public AddCompanyPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("street")));
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

	public WebElement getInputStreet() {
		return inputStreet;
	}

	public void setInputStreet(String value) {
		WebElement el = getInputStreet();
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

	public WebElement getInputCity() {
		return inputCity;
	}

	public void setInputCity(String value) {
		WebElement el = getInputCity();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getInputZipCode() {
		return inputZipCode;
	}

	public void setInputZipCode(String value) {
		WebElement el = getInputZipCode();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getOkButton() {
		return okButton;
	}

}
