package com.example.e2e.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BuildingDetailPage {
	private WebDriver driver;

	@FindBy(id = "street")
	private WebElement divStreet;

	@FindBy(id = "number")
	private WebElement divNumber;

	@FindBy(id = "city")
	private WebElement divCity;

	@FindBy(id = "zipCode")
	private WebElement divZipCode;

	@FindBy(className = "delete-x")
	private WebElement delteElement;

	@FindBy(id = "new")
	private WebElement newApartmentElement;

	public BuildingDetailPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		/*
		 * (new WebDriverWait(driver,
		 * 10)).until(ExpectedConditions.textToBe(By.id("street"),
		 * "new street")); (new WebDriverWait(driver,
		 * 10)).until(ExpectedConditions.textToBe(By.id("city"), "new city"));
		 * (new WebDriverWait(driver,
		 * 10)).until(ExpectedConditions.textToBe(By.id("number"), "1")); (new
		 * WebDriverWait(driver,
		 * 10)).until(ExpectedConditions.textToBe(By.id("zipCode"), "15000"));
		 */
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(delteElement));

	}

	public WebElement getDivStreet() {
		return divStreet;
	}

	public WebElement getDivNumber() {
		return divNumber;
	}

	public WebElement getDivCity() {
		return divCity;
	}

	public WebElement getDivZipCode() {
		return divZipCode;
	}

	public WebElement getDelteElement() {
		return delteElement;
	}

	public WebElement getNewApartmentElement() {
		return newApartmentElement;
	}

}
