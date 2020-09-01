package eu.fincon;
import eu.fincon.Datenverarbeitung.Config;
import eu.fincon.Datenverarbeitung.Datentreiber;
import eu.fincon.Datenverarbeitung.Testdatum;
import eu.fincon.Datenverarbeitung.Webseite;
import eu.fincon.Vakanzengrabber.FreelanceDE.FreelanceDEGrabben;

import java.util.List;

public class Test {
    //=====================================================================
    // Test Methode
    // =====================================================================
    @org.testng.annotations.Test // Ausführbarkeit von der Funktion als Test
    public void vakanzenGrabben() throws InterruptedException {
        //=====================================================================
        // Eine Liste (Typ Testdatum) wird aus der übergebenen Datei erstellt
        // =====================================================================
        List<Testdatum> lTestdatumListe = Datentreiber.getTestdatenEXCEL();

        //=====================================================================
        // Config Laden
        // =====================================================================
        Config.setBrowser();
        Config.WebseitenlisteLaden();
        //=====================================================================
        // Es wird eine Schleif über alle Einträge des Testdatentreibers gelaufen
        // =====================================================================
        for (Webseite wWebseite : Config.strarrayWebseitenListe) {
            for (Testdatum tTestdatum : lTestdatumListe) {

                System.out.println("--------\nTestdaten für Lauf\n--------\nURL" + wWebseite.strURL + "\nSuchbegriff" + tTestdatum.strSuchbegriff + "\n--------");

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
