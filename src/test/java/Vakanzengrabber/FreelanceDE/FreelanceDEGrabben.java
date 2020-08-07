package Vakanzengrabber.FreelanceDE;
import Datenverarbeitung.Testdatum;
import Vakanzengrabber.Base.VakanzenGrabber;
import org.openqa.selenium.WebElement;

//=====================================================================
// Enthält FreelanceDe spezifische Informationen
// =====================================================================
public class FreelanceDEGrabben extends VakanzenGrabber {
    String strXPathCookieDiv = "//div[@id='CybotCookiebotDialog']";
    String StrXPathCookieAccept = "//a[@id='CybotCookiebotDialogBodyButtonAccept']";
    //=====================================================================
    // Basis Methode für FreelanceDE
    // =====================================================================
    public FreelanceDEGrabben()
    {
        //=====================================================================
        // Ruft den Constructor der Basisklasse auf
        // =====================================================================
        super();
    }
    public void cookieHandler()
    {
        WebElement weCookieDivElement = webelementFinden(SelectorType.xpath, strXPathCookieDiv, 0);
        if (weCookieDivElement!=null)
        {
            System.out.println("Cookie Div ist vorhanden!\nAnzeige wird geschlossen!");
            WebElement weCookieDivAkzeptierenElement = webelementFinden(SelectorType.xpath, StrXPathCookieAccept);
            webelementKlicken(weCookieDivAkzeptierenElement);
        }
    }
    //=====================================================================
    // Override Methoden die die Basisklasse überschreiben
    // =====================================================================
    @Override
    public void seiteOeffnen(Testdatum ptTestdatum)
    {
        //=====================================================================
        // Akzeptiert den Cookies Popup
        // =====================================================================
        cookieHandler();
        //=====================================================================
        // Ruft die Methode "webseiteStarten" aus der Basis-Klasse auf
        // =====================================================================
        if (!webseiteStarten(ptTestdatum.strURL, "Projekte und Aufträge für Freelancer, Freiberufler und Selbstständige"))
        {
            System.out.println("Fehler beim Starten der Webseite");
            assert (1==0);
        }
    }
    @Override
    public void benutzerAnmelden(Testdatum ptTestdatum)
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
        if (!new FreelanceDEAnmeldemaske(this).anmelden(ptTestdatum))
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
        new FreelanceDESuchmaske(this).sucheAufMaskeDurchfuehren(ptTestdatum);
    }
    @Override
    public void suchlisteSichern(Testdatum ptTestdatum){
        //=====================================================================
        // Erzeugt eine Instanz der Sub-Klasse "FreelanceDESuchlisteMaske" und übergibt das Object der aktuellen
        // Instanz an den Constructor
        // Im Anschluss wird die Methode "suchlistebearbeiten" aus dem Object ausgeführt
        // =====================================================================
        if (!new FreelanceDESuchlisteMaske(this).suchlistebearbeiten(ptTestdatum))
        {
            System.out.println("Fehler beim Starten der Webseite");
            assert (1==0);
        }
    }
    @Override
    public void browserVorbereiten(Testdatum ptTestdatum) {
        //=====================================================================
        // Ruft die Methode "WebDriverInitiieren" aus der Basis-Klasse auf
        // =====================================================================
        if (!WebDriverInitiieren(ptTestdatum))
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