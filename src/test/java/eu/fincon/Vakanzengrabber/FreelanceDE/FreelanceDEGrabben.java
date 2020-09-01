package eu.fincon.Vakanzengrabber.FreelanceDE;
import eu.fincon.Datenverarbeitung.Testdatum;
import eu.fincon.Datenverarbeitung.Webseite;
import eu.fincon.Vakanzengrabber.Base.VakanzenGrabber;
import org.openqa.selenium.WebElement;

//=====================================================================
// Enthält FreelanceDe spezifische Informationen
// =====================================================================
public class FreelanceDEGrabben extends VakanzenGrabber {
    String StrXPathCookieAccept = "//a[@id='CybotCookiebotDialogBodyLevelButtonLevelOptinAllowallSelection']";
    //=====================================================================
    // Basis Methode für FreelanceDE
    // =====================================================================
    public FreelanceDEGrabben(Webseite pwWebseite)
    {
        //==========================4===========================================
        // Ruft den Constructor der Basisklasse auf
        // =====================================================================
        super();
        wWebseite = pwWebseite;
    }
    public void cookieHandler()
    {
        WebElement weCookieDivAkzeptierenElement = webelementFinden(SelectorType.xpath, StrXPathCookieAccept, 1);
        if (weCookieDivAkzeptierenElement!=null)
        {
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
        if (!webseiteStarten(wWebseite.strURL, "Freelancer, Freiberufler und"))
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
        if (!new FreelanceDEAnmeldemaske(this).anmelden())
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
        new FreelanceDESuchmaske(this, wWebseite).sucheAufMaskeDurchfuehren(ptTestdatum);
    }
    @Override
    public void suchlisteSichern(){
        //=====================================================================
        // Erzeugt eine Instanz der Sub-Klasse "FreelanceDESuchlisteMaske" und übergibt das Object der aktuellen
        // Instanz an den Constructor
        // Im Anschluss wird die Methode "suchlistebearbeiten" aus dem Object ausgeführt
        // =====================================================================
        if (!new FreelanceDESuchlisteMaske(this).suchlistebearbeiten())
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