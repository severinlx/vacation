package eu.fincon.Vakanzengrabber.Base;

import eu.fincon.Datenverarbeitung.Config;
import eu.fincon.Datenverarbeitung.Testdatum;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By; //Identifikationstyp xpath, oder class name, id  etc.
import org.openqa.selenium.WebElement; //das identifizierte Element funktionen ausführen zB klick
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

//=====================================================================
// Basis Klasse
// Enthält alle Selenium Funktionen
// =====================================================================
public class VakanzenGrabber {
    public WebDriver gObjWebDriver;

    public enum SelectorType {
        xpath,
        tagName
    }
    public enum Seiten{
        FreelanceDE
    }
    public enum Browser{
        Chrome
    }
    public VakanzenGrabber()
    {

    }
    public int intGlobalTimeout = 15; // Wartezeite bis Timeout in Sekunden
    //=====================================================================
    // Selenium Funktionen - allgemein
    // =====================================================================


    protected boolean webseiteStarten(String pstrUrl, String pstrExpTitle) {
        //=====================================================================
        // Declare local Variables
        //=====================================================================
        String strActualTitle;
        //=====================================================================
        // Init
        //=====================================================================
        // Navigate to the URL
        //=====================================================================
        urlOeffnen(pstrUrl);
        //=====================================================================
        // Get the actual Title and compare it with the expected Title
        //=====================================================================
        strActualTitle = seitentitelHolen();
        if (pstrExpTitle == "" || strActualTitle.contentEquals(pstrExpTitle)) {
            return true;
        } else {
            System.out.println("Seitentitel ist abweichend");
            return false;
        }
    }

    protected boolean chromebrowserEinrichten()
    {
        try {
            WebDriverManager.chromedriver().setup();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        //=====================================================================
        // Webdriver initialisieren:
        //=====================================================================
        try {
            gObjWebDriver = new ChromeDriver();
        } catch (Exception e) {
            System.out.println("Webbrowser konnte nicht maximiert werden");
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }
    protected boolean WebDriverInitiieren(Testdatum ptTestdatum) {
        //=====================================================================
        // WebDriverManger setup
        //=====================================================================
        switch (Config.bBrowser)
        {
            case Chrome:
                chromebrowserEinrichten();
        }
        //=====================================================================
        // Browser wird maximiert
        //=====================================================================
        try {
            gObjWebDriver.manage().window().maximize();
        } catch (Exception e) {
            System.out.println("Webbrowser konnte nicht maximiert werden");
            System.out.println("Exception: " + e.getMessage());
        }
        //=====================================================================
        // Return
        //=====================================================================
        return true;
    }

    public boolean webbrowserZurueckNavigieren() {
        try {
            gObjWebDriver.navigate().back();
        } catch (Exception e) {
            System.out.println("Es konnte nicht zurück navigiert werden");
            return false;
        }
        System.out.println("Es wurde zurück navigiert");
        return true;
    }

    protected boolean browserSchließen() {
        try {
            gObjWebDriver.close();
        } catch (Exception e) {
            System.out.println("Es konnte nicht geschlossen werden");
            return false;
        }
        System.out.println("Es wurde geschlossen");
        return true;
    }
    //=====================================================================
    // Selenium Funktionen - Webelemente bezogen
    // =====================================================================

    protected WebElement webelementFinden(SelectorType pselectorType, String pstrIdentifikator) {
        return webelementFinden(pselectorType, pstrIdentifikator, -1);
    }
    protected WebElement webelementFinden(SelectorType pselectorType, String pstrIdentifikator, int pintCustomTimeout) {
        //=====================================================================
        // Declare (and local Variables
        //=====================================================================
        WebElement element = null;
        int intWaittime = intGlobalTimeout;
        if (pintCustomTimeout>=0)
            intWaittime = pintCustomTimeout;
        WebDriverWait wait = new WebDriverWait(gObjWebDriver, intWaittime);

        //=====================================================================
        // SelectorTyp identifizieren und das Element auf Basis des Identifikators
        // finden und zuweisen.
        //=====================================================================
        try {
            switch (pselectorType) {
                case xpath:
                    element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(pstrIdentifikator)));
                    break;
                case tagName:
                    element = wait.until(ExpectedConditions.elementToBeClickable(By.tagName(pstrIdentifikator)));
                    break;
                default:
                    // Errorhandling
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            element = null;
        }
        if (element != null) {
            System.out.println("Das Element " + pstrIdentifikator + " wurde mittels " + pselectorType.toString() + " selektiuert");
        } else {
            System.out.println("Das Element " + pstrIdentifikator + " wurde mittels " + pselectorType.toString() + " nicht gefunden");
        }
        return element;
    }

    protected List<WebElement> webelementeFinden(SelectorType pselectorType, String pstrIdentifikator) {
        //=====================================================================
        // Declare (and local Variables
        //=====================================================================
        List<WebElement> elementListe = null;
        WebDriverWait wait = new WebDriverWait(gObjWebDriver, intGlobalTimeout);
        //=====================================================================
        // SelectorTyp identifizieren und das Element auf Basis des Identifikators
        // finden und zuweisen.
        //=====================================================================
        try {
            switch (pselectorType) {
                case xpath:
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(pstrIdentifikator)));
                    elementListe = gObjWebDriver.findElements(By.xpath(pstrIdentifikator));
                    break;
                case tagName:
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(pstrIdentifikator)));
                    elementListe = gObjWebDriver.findElements(By.tagName(pstrIdentifikator));
                    break;
                default:
                    // Errorhandling
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        if (elementListe != null && elementListe.size() > 0) {
            String Anzahl = String.valueOf(elementListe.size());
            System.out.println("Es wurden " + Anzahl + " Elemente mittel (" + pselectorType.toString() + " - " + pstrIdentifikator + ") identifiziert.");
        } else {
            System.out.println("Es wurden keine Elemente mittel (" + pselectorType.toString() + " - " + pstrIdentifikator + ") identifiziert.");
        }
        return elementListe;
    }

    protected boolean webelementVorhanden(SelectorType pselectorType, String pstrIdentifikator) {
        WebElement element = webelementFinden(pselectorType, pstrIdentifikator);

        if (element == null)
            return false;
        return true;
    }
    protected boolean webelementSetzen(WebElement element, String Wert) {
        try {
            element.sendKeys(Wert);
        } catch (Exception e) {
            System.out.println("Das Element" + element.getTagName() + " konnte nicht auf den Wert \"" + Wert + "\" gesetzt werden.");
            return false;
        }
        System.out.println("Das Element " + element.getTagName() + " wurde auf den Wert " + Wert + " gesetzt");
        return true;
    }

    protected boolean webelementKlicken(WebElement element) {
        WebDriverWait wait = new WebDriverWait(gObjWebDriver, intGlobalTimeout);
        if (element == null)
            return false;
        String strTagName = element.getTagName();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            System.out.println("Das Element" + strTagName + " ist nicht klickbar.");
            return false;
        }
        System.out.println("Das Element " + strTagName + " wurde geklickt.");
        return true;
    }
    protected String webelementTextauslesen(WebElement element)
    {
        //=====================================================================
        // Declare (and local Variables)
        // =====================================================================
        String strElementNichtGefunden = "N/A"; // Wenn das Webelement nicht gefunden wurde
        String strElementNichtAuslesbar = "-"; // Wenn das Webelement nicht gefunden wurde
        String strTextAusElement;

        //=====================================================================
        // Prüfen ob das übergebene Element null ist
        // Wenn ja wird die Funktion abgebrochen und ein Standardwert zurückgegeben
        // =====================================================================
        if (element == null)
        {
            System.out.println("Das element konnte nicht ausgelesen werden da es \"null\" ist");
            return strElementNichtGefunden;
        }
        try{
            //=====================================================================
            // Der Text des Elementes wird versucht auszulesen
            // =====================================================================
            strTextAusElement = element.getText();
        }
        catch(Exception e)
        {
            //=====================================================================
            // Wenn der Text nicht innerhalb des Try ausgelesen werden kann, wird
            // ein Standardtext zurückgegeben
            // =====================================================================
            return strElementNichtAuslesbar;
        }
        System.out.println("Das Element ("+element.toString()+") wurde ausgelesen (Wert - " + strTextAusElement + ")");
        return strTextAusElement;
    }
    //=====================================================================
    // Ermittelt den Seitentitel der  Instanz "gObjWebDriver" und gibt diesen zurück
    // =====================================================================
    protected String seitentitelHolen() {
        String strActualTitle = "";
        try {
            strActualTitle = gObjWebDriver.getTitle();
        } catch (Exception e) {
            System.out.println("Der Seitentitel konnte nicht ermittelt werden");
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("Die Seite hat den Titel " + strActualTitle);
        return strActualTitle;
    }
    //=====================================================================
    // Navigiert den Webbrowser der Instanz "gObjWebDriver" zu einer übergebenen URL
    // =====================================================================
    private void urlOeffnen(String pstrUrl) {
        try {
            gObjWebDriver.navigate().to(pstrUrl);
        } catch (Exception e) {
            System.out.println("Webbrowser konnte nicht zu der URL " + pstrUrl + " navigiert werden");
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("Webbrowser hat die URL " + pstrUrl + " geöffnet");
    }

    //=====================================================================
    // Public Methoden die von der vererbten Klasse überschreiben werden.
    // =====================================================================
    public void seiteOeffnen(Testdatum ptTestdatum){}
    public void benutzerAnmelden(Testdatum ptTestdatum){}
    public void sucheDurchfuehren(Testdatum ptTestdatum){}
    public void suchlisteSichern(Testdatum ptTestdatum){}
    public void browserVorbereiten(Testdatum ptTestdatum){}
    public void seiteSchließen(){}
}