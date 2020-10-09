package eu.fincon.Vakanzengrabber.FreelanceDE;

import com.relevantcodes.extentreports.LogStatus;
import eu.fincon.Datenverarbeitung.Config;
import eu.fincon.Datenverarbeitung.Inserat;
import eu.fincon.Datenverarbeitung.InserateVerwalten;
import eu.fincon.Datenverarbeitung.Testdatum;
import eu.fincon.Vakanzengrabber.Base.VakanzenGrabber;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static eu.fincon.Logging.ExtendetLogger.LogEntry;

public class FreelanceDESuchlisteMaske extends VakanzenGrabber {
    //=====================================================================
    // XPaths - Suchliste
    // =====================================================================
    String strXpathSuchlisteneintrag = "(//*[contains(@id,\"project_link\")])[" + "[i]" + "]"; // [i] wird innerhalb der Schleife mit dem aktuellen Schleifenzähler ersetzt
    String strXPathnaechsteSeite = "//ul[@class=\"pagination\"]//span[@class=\"fa fa-angle-right\"]/..";
    String strXpathSuchlistenElemente = "//div[@class='project-list']/*";
    public FreelanceDESuchlisteMaske(FreelanceDEGrabben pSuperclass)
    {
        super();
        this.wWebseite = pSuperclass.wWebseite;
        this.gObjWebDriver = pSuperclass.gObjWebDriver;
    }
    public boolean suchlistebearbeiten()
    {
        boolean Ergebnis = false;
        List<WebElement> ListeSuchergebnisse = webelementeFinden(SelectorType.xpath, strXpathSuchlistenElemente);
        // TimeUnit.SECONDS.sleep(5);
        // Wenn keine Ergebnisse gefunden werden, wird die Funktion erfolgreich beendet
        if (ListeSuchergebnisse==null || ListeSuchergebnisse.size()==0)
            return true;
        try {
            Ergebnis = sucheinträgeSichern(ListeSuchergebnisse, Config.stSpeicherTyp);
        } catch (InterruptedException e) {
            LogEntry(LogStatus.INFO, "Exception:<br>" + e.getMessage());
        }
        return Ergebnis;
    }
    private boolean sucheinträgeSichern(List<WebElement> plisteSuchergebnisse, InserateVerwalten.SpeicherTypen pivstSpeichertyp) throws InterruptedException {
        //=====================================================================
        // Variablendeklaration
        // =====================================================================
        boolean blnErgebnis;
        boolean blnNeueSeiteButton = false;
        List<Inserat> lInserate = new ArrayList<Inserat>();
        List<String> lInseratURLs = new ArrayList<String>();
        InserateVerwalten InserateHandler;
        Inserat iTempInserat;
        String strLinkZiel;

        //=====================================================================
        // Schleife über alle Suchlisteneinträge (inklusive Seitenwechsel)
        // =====================================================================
        do {
            for (int i = 1; i <= plisteSuchergebnisse.size(); i++) {
                //=====================================================================
                // Inserat auf Basis des Indexes öffnen
                // Der Link zum Inserat wird zusätzlich gesichert
                // =====================================================================
                System.out.println();
                String strInseratXPath = strXpathSuchlisteneintrag.replace("[i]",String.valueOf(i));
                WebElement suchJobtitel = webelementFinden(SelectorType.xpath, strInseratXPath, 1);
                if (suchJobtitel != null) {
                    strLinkZiel = suchJobtitel.getAttribute("href");
                    LogEntry(LogStatus.INFO, "Inserat:" + strLinkZiel+" wird gesichert");
                    lInseratURLs.add(strLinkZiel);
                }
            }
            //=====================================================================
            // Webelement für die nächste Seite finden
            // Wenn das Webelement für die nächste Seite = null ist, wird die Schleife abgebrochen
            // Ansonsten wird das Webelement geklickt
            // =====================================================================
            // blnNeueSeiteButton = webelementVorhanden(SelectorType.xpath, strnaechsteSeiteXPath);
            WebElement naechsteSeiteButton = webelementFinden(SelectorType.xpath, strXPathnaechsteSeite, 0);
            if (naechsteSeiteButton != null)
            {
                LogEntry(LogStatus.INFO, "Es ist eine weitere Seite vorhanden - Das Webelement wird geklickt");
                webelementKlicken(naechsteSeiteButton);
                // Quick and Dirty !!!
                TimeUnit.SECONDS.sleep(1);
                plisteSuchergebnisse = webelementeFinden(SelectorType.xpath, strXpathSuchlistenElemente);
            }
            else
            {
                LogEntry(LogStatus.INFO, "Es ist keine weitere Seite mehr vorhanden - Die Schleife für die Seiten wird beendet");
                break;
            }
        }
        //=====================================================================
        // Endlosschleife bis das Webelement für die nächste Seite == null ist
        // =====================================================================
        while (1==1);
        LogEntry(LogStatus.PASS, "Es wurden " + lInseratURLs.size() + " Inserat-URLs gesammelt");


        //=====================================================================
        // Schleife über die Liste der gesicherten InseratURLs
        // =====================================================================
        for (int i = 0; i<lInseratURLs.size(); i++)
        {
            strLinkZiel = lInseratURLs.get(i);
            webseiteStarten(strLinkZiel, "");
            //=====================================================================
            // Inserat sichern
            // =====================================================================
            LogEntry(LogStatus.INFO, "Inserat wird gesichert...");
            iTempInserat = new FreelanceDEInseratMaske(this).inseratSichern(strLinkZiel);
            LogEntry(LogStatus.INFO, "Inserate wurde gesichert");
            if (iTempInserat != null)
                LogEntry(LogStatus.INFO, "Inserate wird in die Liste gesichert");
                lInserate.add(iTempInserat);
        }
        LogEntry(LogStatus.PASS, "Es wurden " + lInserate.size() + " Inserate gesammelt");


        //=====================================================================
        // Es wird eine Instanz der Klasse zum Speichern von Inseraten erzeugt
        // an die Instanz wird Liste der Inserate übergeben
        // im Anschluss wird die Funktion zum Sichern aufgerufen
        // =====================================================================
        LogEntry(LogStatus.INFO, "Die Inserateliste wird gesichert...");
        InserateHandler = new InserateVerwalten(lInserate);
        InserateHandler.inserateSichern(Config.stSpeicherTyp, "FreelanceDE");
        LogEntry(LogStatus.PASS, "Die Inserateliste wurde gesichert");
        return true;
    }

}
