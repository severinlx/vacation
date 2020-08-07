package eu.fincon;

import eu.fincon.Datenverarbeitung.Datensatz;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Automation {
    enum Browsers{
        Chrome,
        Firefox
    }
    enum IdentifierType{
        Xpath,
        Link_Text,
        Id
    }
    static String ausgabe_pfad = "export.csv";
    public static WebDriver webdriver_Anlegen(Browsers Browser) {
        WebDriver driver;
        switch (Browser) {
            case Chrome:
                ChromeOptions choptions;
                WebDriverManager.chromedriver().setup();
                String binarypath = "";
                choptions = new ChromeOptions();
                choptions.setBinary(binarypath);

                driver = new ChromeDriver(choptions);
                return driver;
            case Firefox:
                FirefoxOptions ffoptions;
                WebDriverManager.firefoxdriver().setup();
                binarypath = "";
                ffoptions = new FirefoxOptions();
                ffoptions.setBinary(binarypath);

                driver = new FirefoxDriver(ffoptions);
                return driver;
            default:
                return null;
        }
    }
    public static void seite_Oeffnen(WebDriver driver, String url){
        driver.navigate().to(url);
    }
    public static WebElement element_Holen(WebDriver driver, IdentifierType type, String Identifier) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement element = null;
        try {
            switch (type) {
                case Xpath:
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Identifier)));
                    new Actions(driver).moveToElement(element).perform();
                    return element;
                default:
                    return null;
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public static List<WebElement> elemente_Holen(WebDriver driver, IdentifierType type, String Identifier) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement parent_element = null;
        List<WebElement> elements = null;
        switch (type) {
            case Xpath:
                parent_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Identifier)));
                elements = driver.findElements(By.xpath(Identifier));
                new Actions(driver).moveToElement(parent_element).perform();
                return elements;
            default:
                return null;
        }
    }
    public static WebElement element_Holen(WebDriver driver, WebElement element, IdentifierType type, String Identifier) {
        WebElement temp_element = null;
        warte_auf_element(element);
        switch (type) {
            case Xpath:
                temp_element = element.findElement(By.xpath("." + Identifier));
                new Actions(driver).moveToElement(temp_element).perform();
                return temp_element;
            default:
                return null;
        }
    }

    public static void vorherige_Seite(WebDriver driver)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.history.go(-1)");
    }
    public static void element_Setzen(WebElement element, String Text){
        if (!warte_auf_element(element))
            return;
        try {
            element.clear();
            element.sendKeys(Text);
        }
        catch (Exception e)
        {
            System.out.print("Fehler beim setzen des Elements\n" + e.getMessage());
            return;
        }
    }
    public static void element_Klicken(WebDriver driver, WebElement element){
        if (!warte_auf_element(element))
            return;
        WebDriverWait wait = new WebDriverWait(driver, 5000l);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    public static boolean Element_pruefen(WebElement element, String Wert){
        if (element.getText() == Wert) {
            return true;
        }
        else return false;
    }

    // wartet bis eine ELement sichtbar und aktiviert ist
    static boolean warte_auf_element(WebElement element)
    {
        int waittime = 2000;
        int time = 0;
        int maxwaittime = waittime * 10;
        try {
            do {
                try {
                    Thread.sleep(waittime);
                    time += waittime;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while ((element.isDisplayed() == false && element.isEnabled() == false) || time >= maxwaittime);
        }
        catch (Exception e){
            System.out.print("Fehler beim holen des Elements\n" + e.getMessage());
            return false;
        }
        return true;
    }
    //
    // Funktionen zum Bearbeiten der Seite Freelance.de
    //
    //
    // freelancede_Sichern ist die Hauptfunktion die parametriesiert aufgerufen wird
    public static void freelancede_Sichern(WebDriver driver, String Suchkriterium)
    {
        // Suche durchführen
        WebElement searchField = element_Holen(driver, Automation.IdentifierType.Xpath, "/html/body/div[2]/div[1]/section[1]/div[1]/article/div/form/div/input[3]");
        Automation.element_Setzen(searchField, Suchkriterium);
        WebElement searchButton = element_Holen(driver, Automation.IdentifierType.Xpath, "/html/body/div[2]/div[1]/section[1]/div[1]/article/div/form/div/span/button");

        element_Klicken(driver, searchButton);
        // Durchläuft alle erhaltenen Sucheinträge und speichert diese in Eine CSV
        freelance_suchliste_Durchlaufen(driver);
        // Am Ende des Testfalls den Webdriver schließen
    }
    public static void freelance_suchliste_Durchlaufen(WebDriver driver)
    {
        // Element für den Button zum Wechseln auf die nächste Seite (Für Schleifen Kriterium)
        WebElement nextPageButton = null;
        // Zählt die Seiten (dient nur zum berrechnen des Listenindexes für die CSV Datei)
        int pagecount = 0;
        // Schleife über alle Seiten der Suchergebnisse
        do {
            List<WebElement> Sucheinträge = elemente_Holen(driver, Automation.IdentifierType.Xpath, "//div[@class='project-list']/*");
            // Speichert die Anzahl der Suchlisteneinträge für die aktuell angezeigte Seite
            // dient zum identifizieren, ob die Seite über den Klick auf "Next Page" aktualisiert wurde
            String pageIdentifier = element_Holen(driver, Automation.IdentifierType.Xpath, "//div[@id='pagination']/p").getText();
            sucheinträge_Bearbeiten(driver, Sucheinträge, pagecount);
            // Button für die nächste Seite ermitteln
            nextPageButton = element_Holen(driver, Automation.IdentifierType.Xpath, "//a[@aria-label='Next']");
            // Wenn nextPageButton == null ist, wird am Ende aus der Schleife gegangen
            // für den ersten durchlauf kann es sein, das bereits nur 1 Seite an Ergebnissen erzeugt wurde
            if (nextPageButton != null)
            {
                Automation.element_Klicken(driver, nextPageButton);
                // Seiten zählens
                pagecount += 1;
                int waitcount = 0;
                // Warten bis das Label mit der Anzahl der Suchergebnisse sich ändert
                // könnte/sollte in Funktion extrahiert werden.
                do {
                    try {
                        Thread.sleep(1000);
                        waitcount += 1000;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while (pageIdentifier == Automation.element_Holen(driver, Automation.IdentifierType.Xpath, "//div[@id='pagination']/p").getText() || waitcount > (20*1000));
            }
        }
        // Prüfen ob das Element zum Wechseln auf die nächste Seite noch vorhanden ist
        while (nextPageButton != null);
    }




    // Eine Liste von Suchergebnissen wird abgearbeitet
    public static void sucheinträge_Bearbeiten(WebDriver driver, List<WebElement> sucheinträge, int pagecount) {
        WebElement we;
        int count = 0;
        while (count < sucheinträge.size())
        {
            we = sucheinträge.get(count);
            // Webelement öffnen
            WebElement überschrift = Automation.element_Holen(driver, we, Automation.IdentifierType.Xpath,"//h3/a");
            Automation.element_Klicken(driver, überschrift);
            // Daten aus Inserat speichern
            Datensatz Eintrag = inserat_Sichern(driver);
            Eintrag.Write_file((count+1) + (pagecount * 20), ausgabe_pfad);
            // Aus dem geöffneten Inserat zurück gehen
            Automation.vorherige_Seite(driver);
            count ++;
            // aufgrund des wechseln zur vorherigen Seite muss die Liste neu erstellt werden
            sucheinträge = Automation.elemente_Holen(driver, Automation.IdentifierType.Xpath, "//div[@class='project-list']/*");
        }
    }
    // Ein geöffnetes Inseret wird in der Klasse "Datensatz" gespeichert
    private static Datensatz inserat_Sichern(WebDriver driver) {
        String Überschrift = "";
        String Firmenname = "";
        String DatumVon = "";
        String DatumBis = "";
        String Ort = "";
        String Bezahlung = "";
        String Beschreibung = "";
        //Überschrift
        WebElement ÜberschritftElement = Automation.element_Holen(driver, Automation.IdentifierType.Xpath, "//h1/b");
        if (ÜberschritftElement != null) {
            Überschrift = ÜberschritftElement.getText();
        }
        //Firmenname
        WebElement FirmennameElement = Automation.element_Holen(driver, Automation.IdentifierType.Xpath, "//div[@class='panel-body project-header panel-white']/p[1]");
        if (FirmennameElement != null) {
            Firmenname = FirmennameElement.getText();
        }
        //Datum Von
        WebElement DatumVonElement = Automation.element_Holen(driver, Automation.IdentifierType.Xpath, "//div[@class='project-right-content']//div[@class='col-md-6 details-box'][1]/p[1]");
        if (DatumVonElement != null) {
            DatumVon = DatumVonElement.getText();
        }
        //Datum Bis
        WebElement DatumBisElement = Automation.element_Holen(driver, Automation.IdentifierType.Xpath, "//div[@class='project-right-content']//div[@class='col-md-6 details-box'][1]/p[2]");
        if (DatumBisElement != null) {
            DatumBis = DatumBisElement.getText();
        }
        //Ort
        WebElement OrtElement = Automation.element_Holen(driver, Automation.IdentifierType.Xpath, "//div[@class='project-right-content']//div[@class='col-md-6 details-box'][1]/p[3]");
        if (OrtElement != null) {
            Ort = OrtElement.getText();
        }
        //Bezahlung
        WebElement BezahlungElement = Automation.element_Holen(driver, Automation.IdentifierType.Xpath, "//div[@class='project-right-content']//div[@class='col-md-6 details-box'][2]/p[1]");
        if (BezahlungElement != null) {
            Bezahlung = BezahlungElement.getText();
        }
        // Beschreibung
        WebElement BeschreibungElement = Automation.element_Holen(driver, Automation.IdentifierType.Xpath, "//div[@class='panel-body']");
        if (BeschreibungElement != null) {
            Beschreibung = BeschreibungElement.getText();
        }
        // Datensatz Element erzeugen
        Datensatz Eintrag = new Datensatz(Überschrift, Firmenname, DatumVon, DatumBis, Ort, Bezahlung, Beschreibung);
        return Eintrag;
    }
}
