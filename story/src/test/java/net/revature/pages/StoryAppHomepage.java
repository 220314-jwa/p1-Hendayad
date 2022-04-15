package net.revature.pages;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
// setup selinum,added depency to pom file and added chromedriver.exe file
public class StoryAppHomepage {
    WebDriver driver;
	
	// before we have interaction we have tell selenuim how to find this element
	
	@FindBy(id="usernameLogin")
	WebElement usernameInput;
	@FindBy(id="passwordLogin")
	WebElement passwordInput;
	@FindBy(id="logInBtn")
	WebElement logInBtn;
	// TODO
	WebElement messageBox;
	//web driver implementation for for brower we want to implement 
	public StoryAppHomepage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	// we go to our front end page from here
	public void navigateTo() {
		driver.get("C:\\Users\\16099\\Documents\\scripts\\index.html");
	}
	// interaction method we have in frontend
	public void inputUsername(String username) {
		usernameInput.sendKeys(username);
	}
	
	public void inputPassword(String password) {
		passwordInput.sendKeys(password);
	}
	
	public void submitLogin() {
		logInBtn.click();
	}
	
	public void logOut() {
		if (logInBtn.getText().equals("Log Out")) {
			logInBtn.click();
		}
	}
	
	public String getMessageBoxText() {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions
				.textToBePresentInElement(messageBox, "i"));
		
		return messageBox.getText();
	}
	
	public String getNavText() {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions
				.numberOfElementsToBe(By.id("nameDisplay"), 1));
		
		return driver.findElement(By.id("nameDisplay")).getText();
	}


}
