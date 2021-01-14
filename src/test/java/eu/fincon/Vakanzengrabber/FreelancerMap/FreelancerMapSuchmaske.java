package eu.fincon.Vakanzengrabber.FreelancerMap;

import com.relevantcodes.extentreports.LogStatus;
import eu.fincon.Datenverarbeitung.Testdatum;
import eu.fincon.Datenverarbeitung.Webseite;
import eu.fincon.Logging.ExtendetLogger;
import org.openqa.selenium.WebElement;

public class FreelancerMapSuchmaske extends FreelancerMapGrabben {
    //=====================================================================
    // XPaths - Suchmaske
    // =====================================================================
    String strXpathSuchfeld = "//input[@class= 'form-control']";
    String strXpathSuchbutton = "//i[@class='fa fa-search']//..";
    public FreelancerMapSuchmaske(FreelancerMapGrabben pSuperclass, Webseite wWebseite)
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
        ExtendetLogger.LogEntry(LogStatus.INFO, "Suchbegriff wird gesetzt...");
        WebElement suchfeld = webelementFinden(SelectorType.xpath, strXpathSuchfeld);
        blnErgebnis = webelementSetzen(suchfeld, ptTestdatum.strSuchbegriff);
        if (!blnErgebnis)
        {
            ExtendetLogger.LogEntry(LogStatus.FAIL, "Fehler beim Setzen des Suchbegriffs.\nSuchbegriff: "+ptTestdatum.strSuchbegriff);
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
            ExtendetLogger.LogEntry(LogStatus.FAIL, "Fehler beim Klicken des auf den Suchbutton.");
            assert (1==0);
        }
        ExtendetLogger.LogEntry(LogStatus.INFO, "Suchbegriff wurde gesetzt");
        //=====================================================================
        // Die der WebElemente für die Suchergebnisse ermittelt und in einer Schleife durchlaufen
        //  //=====================================================================
    }
}
