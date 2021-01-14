package eu.fincon.Vakanzengrabber.FreelancerMap;

import org.openqa.selenium.WebElement;

public class FreelancerMapAnmeldemaske extends FreelancerMapGrabben {
    //=====================================================================
    // XPaths - Anmeldemaske öffnen
    // =====================================================================
    String strXpathLoginBTN = "//a[text() = 'Mein Account']";
    //=====================================================================
    // XPaths - Anmeldemaske
    // =====================================================================
    String strXpathBenutzername = "//input[@id='login']";
    String strXpathPassword = "//input[@id='password']";
    String strXpathAnmeldenBTN = "//button[text() = 'Anmelden bei freelancermap']";

    String strXpathFehlermeldungBeiAnmeldung = "//div[@class='alert alert-danger']";

    String strXpathHeaderLogo = "//img[@class='img img-responsive']/..";

    //=====================================================================
    // XPath - Zum Hinweisfenster nach der Anmeldung
    // =====================================================================
    String strXpathHinweisFenster = "//div[@class='fancybox-wrap fancybox-desktop fancybox-type-inline fancybox-opened']//a[@class='fancybox-item fancybox-close']";

    public FreelancerMapAnmeldemaske(FreelancerMapGrabben pSuperclass)
    {
        super(pSuperclass.wWebseite);
        this.gObjWebDriver = pSuperclass.gObjWebDriver;
    }

    public boolean anmelden() {

        boolean blnErgebnis;
        //=====================================================================
        // Anmeldeseite öffnen
        //=====================================================================
        if (wWebseite.strBenutzername.contentEquals("") || wWebseite.strPasswort.contentEquals(""))
        {
            //=====================================================================
            // Wenn eine der Variablen "strBenutzername" oder "strPasswort leer sind
            // Wird die Anmeldung übersprungen
            // =====================================================================
            return true;
        }
        if (seitentitelHolen().contains("Freelancer & Projekte finden - freelancermap") == false)
        {
            return false;
        }
        //=====================================================================
        // Das Webelement für den Loginmaskenbutton wird ermittelt und geklickt
        //
        //=====================================================================
        WebElement loginoeffnenBTN = webelementFinden(SelectorType.xpath, strXpathLoginBTN);
        blnErgebnis = webelementKlicken(loginoeffnenBTN);

        //=====================================================================
        // Das Webelement für den Benutzername wird ermittelt und gesetzt
        //
        //=====================================================================
        WebElement benutzernamefeld = webelementFinden(SelectorType.xpath, strXpathBenutzername);
        blnErgebnis = webelementSetzen(benutzernamefeld, wWebseite.strBenutzername);
        //=====================================================================
        // Das Webelement für das Passwort wird ermittelt und gesetzt
        //
        //=====================================================================
        WebElement passwortfeld = webelementFinden(SelectorType.xpath, strXpathPassword);
        blnErgebnis = webelementSetzen(passwortfeld, wWebseite.strPasswort);
        //=====================================================================
        // Das Webelement für den Anmeldebutton wird ermittelt und geklickt
        //
        //=====================================================================
        WebElement anmeldenBTN = webelementFinden(SelectorType.xpath, strXpathAnmeldenBTN);
        blnErgebnis = webelementKlicken(anmeldenBTN);

        //=====================================================================
        // Prüft das keine Fehlermeldung (Auf der Anmeldeseite angezeigt wird) und das ein WelcomePanel angezeigt wird
        // Wenn Ja -> Anmeldung war erfolgreich
        //      Nein -> Anmeldung war nicht erfolgreich
        //=====================================================================
        if (webelementFinden(SelectorType.xpath, strXpathFehlermeldungBeiAnmeldung, 1)==null /*&& webelementFinden(SelectorType.xpath, getStrXpathWelcomePanel, 1)!=null*/)
        {
            System.out.println("Anmeldung war erfolgreich");
            blnErgebnis = true;
        }
        else
        {
            System.out.println("Anmeldung war Fehlerhaft");
            blnErgebnis = false;
        }
        WebElement weHinweisFenster = webelementFinden(SelectorType.xpath, strXpathHinweisFenster, 2);
        if (weHinweisFenster!=null)
        {
            webelementKlicken(weHinweisFenster);
        }
        WebElement webHeaderLogo = webelementFinden(SelectorType.xpath, strXpathHeaderLogo);
        webelementKlicken(webHeaderLogo);
        return blnErgebnis;
    }
}
