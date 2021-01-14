package eu.fincon.Vakanzengrabber.FreelancerMap;

import eu.fincon.Datenverarbeitung.Config;
import eu.fincon.Datenverarbeitung.Inserat;
import eu.fincon.Datenverarbeitung.InserateVerwalten;
import eu.fincon.Vakanzengrabber.Base.VakanzenGrabber;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FreelancerMapSuchlisteMaske extends VakanzenGrabber {
    //=====================================================================
    // XPaths - Suchliste
    // =====================================================================
    String strXpathSuchlisteneintrag = "//li[@class='project-row']";
    String strXPathnaechsteSeite = "//a[@class='next' and contains(text(), '>>')]";
    String strXpathSuchlistenElemente = "//h3[@class='title']/a";
    public FreelancerMapSuchlisteMaske(FreelancerMapGrabben pSuperclass)
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
        try {
            Ergebnis = sucheinträgeSichern(ListeSuchergebnisse, Config.stSpeicherTyp);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            for (int i = 0; i < plisteSuchergebnisse.size(); i++) {
                //=====================================================================
                // Inserat auf Basis des Indexes öffnen
                // Der Link zum Inserat wird zusätzlich gesichert
                // =====================================================================
                if (plisteSuchergebnisse.get(i) != null) {
                    strLinkZiel = plisteSuchergebnisse.get(i).getAttribute("href");
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
                System.out.println("Es ist eine weitere Seite vorhanden - Das Webelement wird geklickt");
                webelementKlicken(naechsteSeiteButton);
                // Quick and Dirty !!!
                TimeUnit.SECONDS.sleep(1);
                plisteSuchergebnisse = webelementeFinden(SelectorType.xpath, strXpathSuchlistenElemente);
            }
            else
            {
                System.out.println("Es ist keine weitere Seite mehr vorhanden - Die Schleife für die Seiten wird beendet");
                break;
            }
        }
        //=====================================================================
        // Endlosschleife bis das Webelement für die nächste Seite == null ist
        // =====================================================================
        while (1==1);


        //=====================================================================
        // Schleife über die Liste der gesicherten InseratURLs
        // =====================================================================
        for (String lInseratURL : lInseratURLs) {
            strLinkZiel = lInseratURL;
            webseiteStarten(strLinkZiel, "");
            //=====================================================================
            // Inserat sichern
            // =====================================================================
            iTempInserat = new FreelancerMapInseratMaske(this).inseratSichern(strLinkZiel);
            if (iTempInserat != null)
                lInserate.add(iTempInserat);
        }


        //=====================================================================
        // Es wird eine Instanz der Klasse zum Speichern von Inseraten erzeugt
        // an die Instanz wird Liste der Inserate übergeben
        // im Anschluss wird die Funktion zum Sichern aufgerufen
        // =====================================================================
        InserateHandler = new InserateVerwalten(lInserate);
        InserateHandler.inserateSichern(Config.stSpeicherTyp, "FreelancerMap");
        System.out.println("Die Inserateliste wurde gesichert");
        return true;
    }

}
