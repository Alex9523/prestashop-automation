import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy (xpath = "//span[@class='expand-more _gray-darker hidden-sm-down']")
    WebElement searchCurency;

    @FindBy(xpath = "//a[@title='Доллар США']")
    WebElement selectCurency;

    @FindBy(xpath = "//span[@class='expand-more _gray-darker hidden-sm-down']")
    WebElement dropDownMenuCurency;

    @FindBy(name = "s")
    WebElement searchInputField;

    @FindBy(xpath = "//div[@class='col-md-6 hidden-sm-down total-products']/p")
    WebElement x;

    @FindBy(xpath = "//a[@class='select-title']")
    WebElement dropDownMenuSort;

    @FindBy(xpath = "//div/a[@class='select-list js-search-link'][4]")
    WebElement fromHightToLow;

    public MainPage(WebDriver driver){
        webDriver = driver;
        wait = new WebDriverWait(webDriver , 30);

        PageFactory.initElements(webDriver, this);
    }

    public String getTextSearchCurency(){
        return searchCurency.getText();
    }

    public void clickOnSelectCurency(){
        selectCurency.click();
    }

    public void clickOnDropDownMenuCurency(){
        dropDownMenuCurency.click();
    }

    public void searchFor(String text){
        searchInputField.clear();
        searchInputField.sendKeys(text+ Keys.ENTER);
    }

    public String q(){
        return x.getText();
    }

    public void clickDropDownMenu(){
        dropDownMenuSort.click();
    }

    public void selectFromHighToLow(){
        fromHightToLow.click();
    }



    public double[] sort(double[]dabl){//sort
        double temp;
        for (int i = 0; i < dabl.length; i++) {
            for (int k = i + 1; k < dabl.length; k++) {
                if (dabl[i] < dabl[k]) {
                    temp = dabl[i];
                    dabl[i] = dabl[k];
                    dabl[k] = temp;
                }
            }
        }
        return dabl;
    }

    public double[] fromStringToDouble(String []str){ //converts String[] to double[]
        double[] dbl = new double[7];
        for (int i = 0; i < 7; i++) {
            str[i]=str[i].replace(',' , '.');
            dbl[i] = Double.valueOf(str[i]);
        }
        return dbl;
    }

    public String[] listCurrentPrices() { // catch all current prices and lodge them into masive
        String [] allElements = new String [7] ;
        boolean bool;
        for (int x=1; x<=7; x++) {
            try {
                WebElement element = webDriver.findElement(By.xpath("//article["+ x +"]//span[@class='regular-price']"));
                bool = true;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                bool = false;
            }
            if(bool == true)
                allElements[x-1] = webDriver.findElement(By.xpath("//article["+ x +"]//span[@class='regular-price']")).getText();
            else
                allElements[x-1] = webDriver.findElement(By.xpath("//article["+ x +"]//span[@class='price']")).getText();
            String [] y;
            y = allElements[x-1].split(" ");
            allElements[x-1]=y[0];
        }
        return allElements;
    }

    public void compareSort(double[] first, double[] second){ //compare correct sort and current sort
        String[] x1, y1;
        boolean bool=true;
        for(int i=0;i<first.length;i++){
            if(first[i]==second[i])
                bool = true;
            else {
                bool = false;
                break;
            }
        }
        if (bool == true)
            System.out.println("Sort correct");
        else  System.out.println("Sort wrong");
    }

    public boolean check(double x , double y, int percent){//check correct formula
        double z = 100-((y*100)/x);
        boolean bool=true;
        int result = (int)Math.round(z);
        try{
            Assert.assertTrue(result==percent);
            bool = true;
        }
        catch (AssertionError e){
            bool = false;
        }
        return bool;
    }

    public void fromStringToDouble(String x, String y, String z){ //change type for price, current-price and discount
        String[] x1, y1;
        double x2,y2;
        int z2;
        x1=x.split(" ");
        y1=y.split(" ");
        x=x1[0].replace(',' , '.');
        y=y1[0].replace(',' , '.');
        z=z.substring(1, z.length() - 1);
        x2=Double.valueOf(x);
        y2=Double.valueOf(y);
        z2=Integer.valueOf(z);

        if(check(x2,y2,z2)==true){
            System.out.println("Discount percent correct");
        }
        else System.out.println("Discount percent wrong");

    }

    public void checkDiscount() { //catch price, curent-price and discount
        boolean bool;
        for (int x=1; x<=7; x++) {
            try {
                WebElement element = webDriver.findElement(By.xpath("//article["+ x +"]//span[@class='regular-price']"));
                bool = true;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                bool = false;
            }
            if(bool == true){
                String x1 = webDriver.findElement(By.xpath("//article["+ x +"]//span[@class='regular-price']")).getText();
                String y = webDriver.findElement(By.xpath("//article["+ x +"]//span[@class='price']")).getText();
                String z = webDriver.findElement(By.xpath("//article["+ x +"]//span[@class='discount-percentage']")).getText();
                fromStringToDouble(x1,y,z);
            }
        }

    }
}
