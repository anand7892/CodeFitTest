package CodeFit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.*;

public class TestProgramForCodaFit {

    @Test(description = "Write the automation test using selenium to verify contact page")
    public void contactPage(){
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        WebDriver driver=new ChromeDriver(chromeOptions);
        driver.get("https://codafit.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        //region From the home page make sure the links to contact page is working.
        Map<String, String> expectedContactLink=new HashMap<>();
        expectedContactLink.put("Contact","(//span[text()='Contact'])[1]//ancestor::a");
        expectedContactLink.put("Get In touch","//a//span[contains(text(),'Get in Touch')]//ancestor::a");
        expectedContactLink.put("Contact US","//span[text()='Contact Us']//ancestor::a");
        expectedContactLink.put("Learn More","(//span[text()='Learn More'])[last()]//ancestor::a");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(expectedContactLink.get("Contact"))));
        Set<String> actualContactLink=new HashSet<>();
        for(String contactLink:expectedContactLink.keySet()) {
            if(driver.findElement(By.xpath(expectedContactLink.get(contactLink))).getAttribute("href").equals("http://codafit.com/contact/"))
                actualContactLink.add(contactLink);
            else if(driver.findElement(By.xpath(expectedContactLink.get(contactLink))).getAttribute("href").equals("https://codafit.com/contact/"))
                actualContactLink.add(contactLink);
        }
        System.out.print("actual contact links found");
        for (String contactLink:actualContactLink)
            System.out.print(contactLink+",");
        Assert.assertEquals(actualContactLink,expectedContactLink.keySet(), "few links are missing with proper contact information");

        //endregion

        //region  Check the title of contact page is “Let’s talk business”
        driver.findElement(By.xpath("(//span[text()='Contact'])[1]//ancestor::a")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(expectedContactLink.get("Contact"))));
        Assert.assertEquals(driver.getTitle(), "Let’s talk business", "contact page title is not matching");
        //endregion

        driver.quit();

    }


}
