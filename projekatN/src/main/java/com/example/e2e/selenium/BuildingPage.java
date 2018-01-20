package com.example.e2e.selenium;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BuildingPage {
	private WebDriver driver;

	@FindBy(id = "new")
	private WebElement newElement;

	@FindBy(className = "weather")
	private List<WebElement> buildingsElement;

	public BuildingPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(newElement));
	}

	public WebElement getNewElement() {
		return newElement;
	}

	public List<WebElement> getBuildingsElement() {
		return buildingsElement;
	}
}
