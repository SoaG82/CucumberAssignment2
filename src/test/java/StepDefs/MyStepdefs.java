package StepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MyStepdefs {

    private static WebDriver driver;
    private int randomNumber;

    @Given("I am on the website for the form and I use {string}")
    public void iAmOnTheWebsiteForTheFormAndIUse(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Browser \"" + browser + "\" not supported");
        }

        driver.manage().window().maximize();
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }


    @And("I enter my first name {string}")
    public void iEnterMyFirstName(String firstName) throws InterruptedException {
        WebElement DOB = driver.findElement(By.cssSelector("#dp"));
        Thread.sleep(1000);
        DOB.click();
        DOB.sendKeys("31/12/1984");

        Thread.sleep(1000);
        WebElement firstNameField = driver.findElement(By.cssSelector("#member_firstname"));
        firstNameField.click();
        firstNameField.sendKeys(firstName);

    }

    @And("Next I enter my last name {string}")
    public void nextIEnterMyLastName(String lastName) throws InterruptedException {
        Thread.sleep(1000);
        WebElement surName = driver.findElement(By.cssSelector("#member_lastname"));
        surName.click();
        surName.sendKeys(lastName);
        Thread.sleep(1000);

        // filling out email in this step as well
        Random rand = new Random();
        randomNumber = rand.nextInt(100000);
        WebElement eMail1 = driver.findElement(By.cssSelector("#member_emailaddress"));
        String email = "Test" + randomNumber + "@hamstermail.com";
        eMail1.sendKeys(email);

        Thread.sleep(1000);
        WebElement eMail2 = driver.findElement(By.cssSelector("#member_confirmemailaddress"));
        eMail2.sendKeys(email);
        Thread.sleep(1000);
    }


    @And("Then I enter my password {string}")
    public void thenIEnterMyPassword(String password) throws InterruptedException {
        WebElement pass1 = driver.findElement(By.cssSelector("#signupunlicenced_password"));
        pass1.sendKeys(password);
        Thread.sleep(1000);
    }

    @And("Then I enter the password confirmation {string}")
    public void iEnterThePassConfirm(String passConfirm) throws InterruptedException {
        WebElement pass2 = driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword"));
        pass2.sendKeys(passConfirm);
        Thread.sleep(1000);
    }

    @And("I enter my the role best suited for me which is {string}")
    public void iEnterMyRole(String role) {
        if (role.equals("player")) {
            WebElement box1 = driver.findElement(By.cssSelector("#signup_form > div:nth-child(11) > div > div > div:nth-child(18) > div > label > span.box"));
            box1.click();
        } else if (role.equals("coach")) {
            WebElement box2 = driver.findElement(By.cssSelector("#signup_form > div:nth-child(11) > div > div > div:nth-child(9) > div > label > span.box"));
            box2.click();
        } else if (role.equals("fan")) {
            WebElement box3 = driver.findElement(By.cssSelector("#signup_form > div:nth-child(11) > div > div > div:nth-child(12) > div > label > span.box"));
            box3.click();
        }
    }

    @And("I accept the {string} for the form")
    public void iAcceptTheTACForTheForm(String termsAndConditions) throws InterruptedException {
        if (termsAndConditions.equals("accept")) {
            WebElement tAndA1 = driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div:nth-child(1) > label > span.box"));
            tAndA1.click();
        } else if (termsAndConditions.equals("do not accept")) {
            Thread.sleep(1000);
        }
        // I also check the box that I am over the age of 18 or have parental responsibility and I check the code of conduct
        WebElement age = driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div.md-checkbox.margin-top-10 > label > span.box"));
        age.click();
        Thread.sleep(1000);
        WebElement codeOfConduct = driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(7) > label > span.box"));
        codeOfConduct.click();
        Thread.sleep(1500);
    }

    @When("I click submit")
    public void iClickSubmit() {
        clickSubmitButton();
    }

    // Private method using the Explicit Wait method which was obligatory for the assignment
    private void clickSubmitButton() {
        By submitSelector = By.cssSelector("#signup_form > div.form-actions.noborder > input");
        click(submitSelector);
    }

    @Then("I should get a {string} or be redirected to a confirmation webpage")
    public void iShouldGetAMessage(String message) throws InterruptedException {
        if (message.equals("confirmation")) {
            WebElement title = driver.findElement(By.cssSelector("body > div > div.page-content-wrapper > div > h2"));

            String actual;
            actual = title.getText();
            String expected = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";

            assertEquals(expected, actual);

        } else if (message.equals("Missing last name")) {
            WebElement error1 = driver.findElement(By.cssSelector("#signup_form > div:nth-child(6) > div:nth-child(2) > div > span > span"));

            String actual = error1.getText();
            String expected = "Last Name is required";

            assertEquals(expected, actual);

        } else if (message.equals("Password did not match")) {
            WebElement error2 = driver.findElement(By.cssSelector("#signup_form > div:nth-child(9) > div > div.row > div:nth-child(2) > div > span > span"));

            String actual = error2.getText();
            String expected = "Password did not match";

            assertEquals(expected, actual);
        } else if (message.equals("Didn't accept terms and conditions")) {
            WebElement error3 = driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div:nth-child(1) > span > span"));

            String actual = error3.getText();
            String expected = "You must confirm that you have read and accepted our Terms and Conditions";

            assertEquals(expected, actual);
        }

        Thread.sleep(5000);
        driver.close();
    }

    // Explicit Wait that was obligatory for the assignment
    public static void click(By by) {

        (new WebDriverWait(driver, Duration.ofSeconds(3))).until(ExpectedConditions.elementToBeClickable(by));

        driver.findElement(by).click();
    }
}