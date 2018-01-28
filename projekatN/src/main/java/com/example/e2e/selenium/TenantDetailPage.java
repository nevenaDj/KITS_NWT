package com.example.e2e.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TenantDetailPage {
	private WebDriver driver;

	@FindBy(className = "delete-x")
	private WebElement deleteElement;

	public TenantDetailPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(deleteElement));

	}

	public WebElement getDeleteElement() {
		return deleteElement;
	}

}
