package com.example.e2e.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GlitchTypePage {
	private WebDriver driver;

	@FindBy(className = "small-btn")
	private WebElement newElement;

	@FindBy(className = "btn")
	private List<WebElement> deleteElements;

	public GlitchTypePage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public void ensureIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(newElement));

	}

	public WebElement getNewElement() {
		return newElement;
	}

	public int getGlitchTypeTableSize() {
		return driver.findElements(By.cssSelector("tr")).size();
	}

	public List<WebElement> getDeleteElements() {
		return deleteElements;
	}

}
