package eu.fincon.Vakanzengrabber.FreelancerMap;

import eu.fincon.Datenverarbeitung.Testdatum;
import eu.fincon.Datenverarbeitung.Webseite;
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
        WebElement suchfeld = webelementFinden(SelectorType.xpath, strXpathSuchfeld);
        blnErgebnis = webelementSetzen(suchfeld, ptTestdatum.strSuchbegriff);
        if (!blnErgebnis)
        {
            System.out.println("Fehler beim Setzen des Suchbegriffs.");
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
            System.out.println("Fehler beim Klicken auf den Suchbutton.");
            assert (1==0);
        }
        //=====================================================================
        // Die der WebElemente für die Suchergebnisse ermittelt und in einer Schleife durchlaufen
        //  //=====================================================================
    }
}
