package eu.fincon;
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
        ExtendetLogger.setup();
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
        ExtendetLogger.LogEntry(ExtendetLogger.LogTypes.info,"Es werden "+Config.strarrayWebseitenListe.length+" Webseiten geprüft");
        ExtendetLogger.LogEntry(ExtendetLogger.LogTypes.info,"Es werden "+lTestdatumListe.size()+" Testdaten pro Seite geprüft");
        //=====================================================================
        // Es wird eine Schleif über alle Einträge des Testdatentreibers gelaufen
        // ====================================================================
        for (Webseite wWebseite : Config.strarrayWebseitenListe) {
            for (Testdatum tTestdatum : lTestdatumListe) {
                ExtendetLogger.LogEntry(ExtendetLogger.LogTypes.info,"Webseite - "+wWebseite.strURL+
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
            }
        }
    }
}
