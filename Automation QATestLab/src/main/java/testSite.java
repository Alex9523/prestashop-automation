import org.openqa.selenium.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class testSite {
    WebDriver driver;
    MainPage mainPage ;

       @Before
        public void set(){
            System.setProperty("webdriver.chrome.driver", "C:/Users/Alex/Downloads/chromedriver_win32/chromedriver.exe");
            driver = new ChromeDriver();
            mainPage = new MainPage(driver);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            System.out.println("Step 1: Open web page - " + driver.getCurrentUrl());
            driver.get("http://prestashop-automation.qatestlab.com.ua/ru/");
            System.out.println("We open web page: " + driver.getCurrentUrl());
        }

        @Test
        public void testWebPage(){//step 2
            System.out.println("Step 2: Check curency for all products.");
            checkPriceAndCurency();

            System.out.println("Step 3: Change curency to USD.");
            changeCurrencyToUSD();
            System.out.println("Step 4: Search 'dress'.");
            searchDress();
            System.out.println("Step 5: The search result contains 'Товаров: Х' .");
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

        public void checkPriceAndCurency(){ //Step 2
            String valuta = mainPage.getTextSearchCurency();
            boolean bool=true;
            for(int i=1;i<=7;i++) {
                try {
                    Assert.assertTrue(driver.findElement(By.xpath("//div/article[" + i + "]//span[@class='price']")).getText().contains(valuta.substring(valuta.length() - 1)));
                }
                catch (AssertionError e){
                   bool = false;
                   break;
                }
            }
            if(bool = true)
                System.out.println("Checking passed");
            else  System.out.println("Checking false");
        }

        public void changeCurrencyToUSD(){//step 3
            mainPage.clickOnDropDownMenuCurency();
            mainPage.clickOnSelectCurency();
            System.out.println("Currency are changed to USD success");
        }

        public void searchDress(){//step 4
            mainPage.searchFor("dress");
            System.out.println("We search 'dress' in search field");
        }

        public void checkArticle(){//step 5
            String article = mainPage.q();
            int count = driver.findElements(By.xpath("//div/article")).size();
            try{
            Assert.assertTrue(article.contains("Товаров: "+count+"."));
                System.out.println("The step passed");
            }
            catch (AssertionError e){
                System.out.println("The step failed");
            }
        }


        public void selectSort(){//step 7
            mainPage.clickDropDownMenu();
            mainPage.selectFromHighToLow();
            System.out.println("Select sort by descending");
        }

        public void checkSort(){ // Step 8
            String[] st = mainPage.listCurrentPrices(); //
            double [] dbl = mainPage.fromStringToDouble(st);
            double [] corect = mainPage.sort(dbl);
            mainPage.compareSort(corect,dbl);
            }

        public void checkPercent(){ //Step 9-10
            mainPage.checkDiscount();
        }

        @After
        public void cleanUp(){
            if(driver != null)
                driver.quit();
        }
}
