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
	private WebElement newTenantElement;

	@FindBy(className = "small-btn")
	private WebElement addOwnerElement;
	
	@FindBy(className = "tenant")
	private WebElement tenantElement;

	public ApartmentDetailPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(delteElement));

	}

	public void ensureIsDisplayedAddOwnerButton() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(addOwnerElement));
	}
	
	public void ensureIsDisplayedTenant() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(tenantElement));

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

	public WebElement getNewTenantElement() {
		return newTenantElement;
	}

	public WebElement getAddOwnerElement() {
		return addOwnerElement;
	}

	public WebElement getTenantElement() {
		return tenantElement;
	}
	

}
