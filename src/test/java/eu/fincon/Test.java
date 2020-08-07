package eu.fincon;
import Datenverarbeitung.Config;
import Datenverarbeitung.Datentreiber;
import Datenverarbeitung.Testdatum;
import Vakanzengrabber.FreelanceDE.FreelanceDEGrabben;

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
        Config.setBrowser();
        //=====================================================================
        // Es wird eine Schleif über alle Einträge des Testdatentreibers gelaufen
        // =====================================================================
        for (Testdatum tTestdatum : lTestdatumListe)
        {
            System.out.println("--------\nTestdaten für Lauf\n--------\nURL" + tTestdatum.strURL + "\nSuchbegriff" + tTestdatum.strSuchbegriff+"\n--------");
            //=====================================================================
            // Je nach URL aus dem Testdatum wird die entsprechende Funktion aufgerufen.
            // =====================================================================
            switch (tTestdatum.eSeite)
            {
                case FreelanceDE:
                    FreelanceDEGrabben FreelanceDE = new FreelanceDEGrabben();
                    FreelanceDE.browserVorbereiten(tTestdatum);
                    FreelanceDE.seiteOeffnen(tTestdatum);
                    FreelanceDE.benutzerAnmelden(tTestdatum);
                    FreelanceDE.sucheDurchfuehren(tTestdatum);
                    FreelanceDE.suchlisteSichern(tTestdatum);
                    FreelanceDE.seiteSchließen();
                    break;
            }
        }
    }
}
