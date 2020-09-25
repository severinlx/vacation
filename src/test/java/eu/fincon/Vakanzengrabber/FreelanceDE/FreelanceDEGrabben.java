package eu.fincon.Vakanzengrabber.FreelanceDE;
import com.relevantcodes.extentreports.LogStatus;
import eu.fincon.Datenverarbeitung.Testdatum;
import eu.fincon.Datenverarbeitung.Webseite;
import eu.fincon.Vakanzengrabber.Base.VakanzenGrabber;
import org.openqa.selenium.WebElement;

import static eu.fincon.Logging.ExtendetLogger.LogEntry;

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
        LogEntry(LogStatus.INFO, "FreelanceDEGrabben wird instanziiert...");
        wWebseite = pwWebseite;
        LogEntry(LogStatus.INFO, "FreelanceDEGrabben wurde instanziiert");
    }
    public void cookieHandler()
    {
        LogEntry(LogStatus.INFO, "Cookie Div wird geprüft...");
        WebElement weCookieDivAkzeptierenElement = webelementFinden(SelectorType.xpath, StrXPathCookieAccept, 1);
        if (weCookieDivAkzeptierenElement!=null)
        {
            LogEntry(LogStatus.INFO, "Cookie Div ist vorhanden!<br>Anzeige wird geschlossen!");
            webelementKlicken(weCookieDivAkzeptierenElement);
            LogEntry(LogStatus.PASS, "Cookie Div wurde geschlossen!");
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
            LogEntry(LogStatus.FAIL, "Fehler beim Starten der Webseite");
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
            LogEntry(LogStatus.FAIL, "Fehler beim anmelden des Benutzers");
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
            LogEntry(LogStatus.FAIL, "Fehler beim Starten der Webseite");
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
            LogEntry(LogStatus.FAIL, "Fehler beim initiieren des Webdrivers");
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
            LogEntry(LogStatus.FAIL, "Fehler beim schließen des Browsers");
            assert (1==0);
        }
    }
}