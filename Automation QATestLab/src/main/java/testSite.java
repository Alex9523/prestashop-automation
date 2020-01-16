import org.openqa.selenium.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Driver;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class testSite {
    WebDriver driver;
    MainPage mainPage;

        @Before
        public void set(){
            System.setProperty("webdriver.chrome.driver", "C:/Users/Alex/Downloads/chromedriver_win32/chromedriver.exe");
            driver = new ChromeDriver();
            mainPage = new MainPage(driver);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            System.out.println("Step 1: Open web page - " + driver.getCurrentUrl());
            driver.get("http://prestashop-automation.qatestlab.com.ua/ru/");
        }

        @Test
        public void testWebPage(){//step 2
            System.out.println("Step 2: Check curency for all products.");
            checkPriceAndCurency();

            System.out.println("Step 3: Change curency to USD.");
            changeCurrencyToUSD();
            System.out.println("Step 4: Search 'dress'.");
            searchDress();
            System.out.println("Step 5: Check title 'Товаров: Ч' .");
            checkArticle();
            System.out.println("Step 6: Check price. ");
            checkPriceAndCurency();
            System.out.println("Step 7: Select sort. ");
            selectSort();
            System.out.println("Step 8: Check sort");
            checkSort();
            System.out.println("Step 9-10: Check discount");
            checkPercent();
        }

        public void checkPriceAndCurency(){ //Step 2,3
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            String valuta = driver.findElement(By.xpath("//span[@class='expand-more _gray-darker hidden-sm-down']")).getText();
            for(int i=1;i<=7;i++) {
                Assert.assertTrue(driver.findElement(By.xpath("//div/article["+i+"]//span[@class='price']")).getText().contains(valuta.substring(valuta.length() - 1)));
            }
        }

        public void changeCurrencyToUSD(){//step 3
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            mainPage.clickOnDropDownMenuCurency();
            mainPage.clickOnSelectCurency();
        }

        public void searchDress(){//step 4
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            mainPage.searchFor("dress");
        }

        public void checkArticle(){//step 5
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            String article = mainPage.q();
            int count = driver.findElements(By.xpath("//div/article")).size();
            Assert.assertTrue(article.contains("Товаров: "+count+"."));
        }

        public void selectSort(){//step 7
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            mainPage.clickDropDownMenu();
            mainPage.selectFromHighToLow();
        }

        public void checkSort(){ // Step 8
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            String[] st = mainPage.listCurentPrices(); //
            double [] dbl = mainPage.fromStringToDouble(st);
            double [] corect = mainPage.sort(dbl);
            boolean q = mainPage.compareSort(corect,dbl);
            }

        public void checkPercent(){
            mainPage.cathDiscount();
        }

        /*@After
        public void cleanUp(){
            if(driver != null)
                driver.quit();
        }*/
}
