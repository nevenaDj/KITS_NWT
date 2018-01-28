package com.example.e2e.selenium;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CompanyDetailPage {
	private WebDriver driver;

	@FindBy(className = "delete-x")
	private WebElement delteElement;

	@FindBy(className = "border-desc")
	private List<WebElement> divElements;

	public CompanyDetailPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(delteElement));
	}

	public WebElement getDelteElement() {
		return delteElement;
	}

	public List<WebElement> getDivElements() {
		return divElements;
	}

}
