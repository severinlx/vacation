package eu.fincon.Vakanzengrabber.FreelanceDE;

import com.relevantcodes.extentreports.LogStatus;
import eu.fincon.Datenverarbeitung.Testdatum;
import eu.fincon.Datenverarbeitung.Webseite;
import org.openqa.selenium.WebElement;

import static eu.fincon.Logging.ExtendetLogger.LogEntry;

public class FreelanceDESuchmaske extends FreelanceDEGrabben{
    //=====================================================================
    // XPaths - Suchmaske
    // =====================================================================
    String strXpathSuchfeld = "//*[@id='__search']//input[@name= '__search_freetext']";
    String strXpathSuchbutton = "//*[@id='__search']//input[@name= '__search_freetext']/..//button";
    public FreelanceDESuchmaske(FreelanceDEGrabben pSuperclass, Webseite wWebseite)
    {
        super(wWebseite);
        this.gObjWebDriver = pSuperclass.gObjWebDriver;
    }
    public void sucheAufMaskeDurchfuehren(Testdatum ptTestdatum) {
        boolean blnErgebnis=true;
        //=====================================================================
        // Das Webelement für das Suchfeld wird identifiziert und auf den Suchbegriff gesetzt
        //
        //=====================================================================
        WebElement suchfeld = webelementFinden(SelectorType.xpath, strXpathSuchfeld);
        blnErgebnis = webelementSetzen(suchfeld, ptTestdatum.strSuchbegriff);
        if (!blnErgebnis)
        {
            LogEntry(LogStatus.FAIL, "Fehler beim Setzen des Suchbegriffs.");
            assert (1==0);
        }
        //=====================================================================
        // Das Webelement für den Suchbutton wird identifiziert und geklickt
        //
        // =====================================================================
        WebElement suchButton = webelementFinden(SelectorType.xpath, strXpathSuchbutton);
        blnErgebnis = webelementKlicken(suchButton);
        if (!blnErgebnis)
        {
            LogEntry(LogStatus.FAIL, "Fehler beim Klicken auf den Suchbutton.");
            assert (1==0);
        }
        LogEntry(LogStatus.PASS, "Suche wurde durchgeführt");
        //=====================================================================
        // Die der WebElemente für die Suchergebnisse ermittelt und in einer Schleife durchlaufen
        //  //=====================================================================
    }
}
