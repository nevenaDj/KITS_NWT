package com.example.e2e.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApartmentDetailPage {
	private WebDriver driver;

	@FindBy(id = "description")
	private WebElement divDescription;

	@FindBy(id = "number")
	private WebElement divNumber;

	@FindBy(className = "delete-x")
	private WebElement delteElement;

	@FindBy(id = "new")
	private WebElement newApartmentElement;

	public ApartmentDetailPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(delteElement));

	}

	public WebElement getDivDescription() {
		return divDescription;
	}

	public WebElement getDivNumber() {
		return divNumber;
	}

	public WebElement getDelteElement() {
		return delteElement;
	}

	public WebElement getNewApartmentElement() {
		return newApartmentElement;
	}

}
