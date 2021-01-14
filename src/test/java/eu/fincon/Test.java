package eu.fincon;
import com.relevantcodes.extentreports.LogStatus;
import eu.fincon.Datenverarbeitung.Config;
import eu.fincon.Datenverarbeitung.Datentreiber;
import eu.fincon.Datenverarbeitung.Testdatum;
import eu.fincon.Datenverarbeitung.Webseite;
import eu.fincon.Logging.ExtendetLogger;
import eu.fincon.Vakanzengrabber.FreelanceDE.FreelanceDEGrabben;

import java.util.List;
import java.util.logging.Level;

public class Test {
    //=====================================================================
    // Test Methode
    // =====================================================================
    @org.testng.annotations.Test // Ausführbarkeit von der Funktion als Test
    public void vakanzenGrabben() throws InterruptedException {
        //=====================================================================
        // Logger setup
        // =====================================================================
        ExtendetLogger.setup("Vakanzengrabber");
        //=====================================================================
        // Eine Liste (Typ Testdatum) wird aus der übergebenen Datei erstellt
        // =====================================================================
        List<Testdatum> lTestdatumListe = Datentreiber.getTestdatenEXCEL();
        //=====================================================================
        // Config Laden
        // =====================================================================
        Config.init();
        //=====================================================================
        // Tests werden ermittelt und geloggt
        // =====================================================================
        ExtendetLogger.LogEntry(LogStatus.INFO,"Es werden "+Config.strarrayWebseitenListe.length+" Webseiten geprüft");
        ExtendetLogger.LogEntry(LogStatus.INFO,"Es werden "+lTestdatumListe.size()+" Testdaten pro Seite geprüft");
        //=====================================================================
        // Es wird eine Schleif über alle Einträge des Testdatentreibers gelaufen
        // ====================================================================
        for (Webseite wWebseite : Config.strarrayWebseitenListe) {
            for (Testdatum tTestdatum : lTestdatumListe) {
                ExtendetLogger.CreateChild(wWebseite.eSeite.toString() + "_" + tTestdatum.strSuchbegriff);
                ExtendetLogger.LogEntry(LogStatus.INFO,"Webseite - "+wWebseite.strURL+
                        " mit dem Suchbegriff -"+tTestdatum.strSuchbegriff+" wird ausgeführt...");
                //=====================================================================
                // Die Seitenspezifischen Methoden aus der jeweiligen Klasse werden aufgerufen
                // ====================================================================
                wWebseite.VakanzenObject.browserVorbereiten();
                wWebseite.VakanzenObject.seiteOeffnen();
                wWebseite.VakanzenObject.benutzerAnmelden();
                wWebseite.VakanzenObject.sucheDurchfuehren(tTestdatum);
                wWebseite.VakanzenObject.suchlisteSichern();
                wWebseite.VakanzenObject.seiteSchließen();
                ExtendetLogger.AppendChild();
            }
        }
        //=====================================================================
        // Logger finish
        // =====================================================================
        ExtendetLogger.finish();
    }
}
