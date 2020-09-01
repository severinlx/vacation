package eu.fincon.Vakanzengrabber.FreelancerMap;
import eu.fincon.Datenverarbeitung.Testdatum;
import eu.fincon.Datenverarbeitung.Webseite;
import eu.fincon.Vakanzengrabber.Base.VakanzenGrabber;
import org.openqa.selenium.WebElement;

//=====================================================================
// Enthält FreelanceDe spezifische Informationen
// =====================================================================
public class FreelancerMapGrabben extends VakanzenGrabber {
    String StrXPathCookieWindow = "//div[@class='cc_banner cc_container cc_container--open']";
    String StrXPathCookieAccept = "//div[@class='cc_banner cc_container cc_container--open']/a";
    //=====================================================================
    // Basis Methode für FreelanceDE
    // =====================================================================
    public FreelancerMapGrabben(Webseite pwWebseite)
    {
        //==========================4===========================================
        // Ruft den Constructor der Basisklasse auf
        // =====================================================================
        super();
        wWebseite = pwWebseite;
    }
    public void cookieHandler()
    {
        WebElement weCookieWindowElement = webelementFinden(SelectorType.xpath, StrXPathCookieWindow, 1);
        if (weCookieWindowElement!=null)
        {
            WebElement weCookieDivAkzeptierenElement = webelementFinden(SelectorType.xpath, StrXPathCookieAccept, 1);
            System.out.println("Cookie Div ist vorhanden!\nAnzeige wird geschlossen!");
            webelementKlicken(weCookieDivAkzeptierenElement);
        }
    }
    //=====================================================================
    // Override Methoden die die Basisklasse überschreiben
    // =====================================================================
    @Override
    public void seiteOeffnen()
    {
        //=====================================================================
        // Akzeptiert den Cookies Popup
        // =====================================================================
        cookieHandler();
        //=====================================================================
        // Ruft die Methode "webseiteStarten" aus der Basis-Klasse auf
        // =====================================================================
        if (!webseiteStarten(wWebseite.strURL, "Freelancer & Projekte finden - freelancermap"))
        {
            System.out.println("Fehler beim Starten der Webseite");
            assert (1==0);
        }
    }
    @Override
    public void benutzerAnmelden()
    {
        //=====================================================================
        // Akzeptiert den Cookies Popup
        // =====================================================================
        cookieHandler();
        //=====================================================================
        // Erzeugt eine Instanz der Sub-Klasse "FreelanceDEAnmeldeMaske" und übergibt das Object der aktuellen
        // Instanz an den Constructor
        // Im Anschluss wird die Methode "anmelden" aus dem Object ausgeführt
        // =====================================================================
        if (!new FreelancerMapAnmeldemaske(this).anmelden())
        {
            System.out.println("Fehler beim anmelden des Benutzers");
            assert (1==0);
        }
    }
    @Override
    public void sucheDurchfuehren(Testdatum ptTestdatum) {
        //=====================================================================
        // Erzeugt eine Instanz der Sub-Klasse "FreelanceDESuchmaske" und übergibt das Object der aktuellen
        // Instanz an den Constructor
        // Im Anschluss wird die Methode "sucheDurchfuehren" aus dem Object ausgeführt
        // =====================================================================
        new FreelancerMapSuchmaske(this, wWebseite).sucheAufMaskeDurchfuehren(ptTestdatum);
    }
    @Override
    public void suchlisteSichern(){
        //=====================================================================
        // Erzeugt eine Instanz der Sub-Klasse "FreelanceDESuchlisteMaske" und übergibt das Object der aktuellen
        // Instanz an den Constructor
        // Im Anschluss wird die Methode "suchlistebearbeiten" aus dem Object ausgeführt
        // =====================================================================
        if (!new FreelancerMapSuchlisteMaske(this).suchlistebearbeiten())
        {
            System.out.println("Fehler beim Starten der Webseite");
            assert (1==0);
        }
    }
    @Override
    public void browserVorbereiten() {
        //=====================================================================
        // Ruft die Methode "WebDriverInitiieren" aus der Basis-Klasse auf
        // =====================================================================
        if (!WebDriverInitiieren())
        {
            System.out.println("Fehler beim initiieren des Webdrivers");
            assert (1==0);
        }
    }
    @Override
    public void seiteSchließen() {
        //=====================================================================
        // Ruft die Methode "browserSchließen" aus der Basis-Klasse auf
        // =====================================================================
        if (!browserSchließen())
        {
            System.out.println("Fehler beim schließen des Browsers");
            assert (1==0);
        }
    }
}