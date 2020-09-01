package eu.fincon.Vakanzengrabber.FreelancerMap;

import eu.fincon.Datenverarbeitung.Inserat;
import eu.fincon.Vakanzengrabber.Base.VakanzenGrabber;
import org.openqa.selenium.WebElement;

public class FreelancerMapInseratMaske extends VakanzenGrabber {
    //=====================================================================
    // XPaths - Inserat
    // =====================================================================
    String strXpathTitel = "//div[@class='container']//h1[@class='top_seo']";

    String strXpathStartDatum = "//div[@class='project-detail-title' and text() = 'Start:']/../div[@class='project-detail-description']";
    String strXpathDauer = "//div[@class='project-detail-title' and text() = 'Dauer:']/../div[@class='project-detail-description']";
    String strXpathOrt = "//div[@class='project-detail-title' and text() = 'Ort:']/../div[@class='project-detail-description']";
    String strXpathRemote = "//div[@class='project-detail-title' and text() = 'Vertragsart:']/../div[@class='project-detail-description']";
    String strXpathReferenzNummer = "//div[@class='project-detail-title' and text() = 'Projekt-ID:']/../div[@class='project-detail-description']";

    String strXpathProjektbeschreibung = "//div[@class='projectcontent']";

    String strXpathKategorienUndSkills = "//div[@class='project-categories']";


    String strXpathOpenKontaktdaten = "//a[@class='btn btn-default btn-sm']";
    String strXpathKontaktdaten = "//div[@class='col-md-8 col-sm-8 text_medium m-t-1 ']";
    public FreelancerMapInseratMaske(FreelancerMapSuchlisteMaske pSuperclass)
    {
        super();
        this.wWebseite=pSuperclass.wWebseite;
        this.gObjWebDriver = pSuperclass.gObjWebDriver;
    }
    public Inserat inseratSichern(String pstrLinkZiel)
    {
        Inserat iAktuellesInserat = new Inserat(pstrLinkZiel);
        //=====================================================================
        // Das Webelement für den Titel wird ermittelt
        // =====================================================================
        WebElement webTitel = webelementFinden(SelectorType.xpath, strXpathTitel);

        //=====================================================================
        // Webelemente zu den Metadaten werden ermittelt
        // =====================================================================
        WebElement webStartDatum = webelementFinden(SelectorType.xpath, strXpathStartDatum, 0);
        WebElement webDauer = webelementFinden(SelectorType.xpath, strXpathDauer, 0);
        WebElement webOrt = webelementFinden(SelectorType.xpath, strXpathOrt, 0);
        WebElement webRemote = webelementFinden(SelectorType.xpath, strXpathRemote, 0);
        WebElement webReferenzNummer = webelementFinden(SelectorType.xpath, strXpathReferenzNummer, 0);

        //=====================================================================
        // Webelement zur Projektbeschreibung wird ermittelt
        // =====================================================================
        WebElement webProjektbeschreibung = webelementFinden(SelectorType.xpath, strXpathProjektbeschreibung);

        //=====================================================================
        // Webelement zur Projektbeschreibung wird ermittelt
        // =====================================================================
        WebElement webKategorienUndSkills = webelementFinden(SelectorType.xpath, strXpathKategorienUndSkills, 0);

        //=====================================================================
        // Die Inhalte werden aus den Webelementen geholt und in einer Instanz
        // der Klasse Inserate gesichert
        // =====================================================================
        iAktuellesInserat.strTitel = webelementTextauslesen(webTitel);

        iAktuellesInserat.strStartDatum = webelementTextauslesen(webStartDatum);
        iAktuellesInserat.strDauer = webelementTextauslesen(webDauer);
        iAktuellesInserat.strOrt = webelementTextauslesen(webOrt);
        iAktuellesInserat.strRemote = webelementTextauslesen(webRemote);
        iAktuellesInserat.strReferenzNummer = webelementTextauslesen(webReferenzNummer);

        iAktuellesInserat.strProjektbeschreibung = webelementTextauslesen(webProjektbeschreibung);

        iAktuellesInserat.strKategorienUndSkills = webelementTextauslesen(webKategorienUndSkills);


        //=====================================================================
        // Maske für Kontaktdaten öffnen und
        // Webelement zur Kontaktdaten wird ermittelt
        // =====================================================================
        WebElement webOpenKontaktdaten = webelementFinden(SelectorType.xpath, strXpathOpenKontaktdaten, 0);
        webelementKlicken(webOpenKontaktdaten);
        WebElement webKontaktdaten = webelementFinden(SelectorType.xpath, strXpathKontaktdaten, 0);
        iAktuellesInserat.strKontaktdaten = webelementTextauslesen(webKontaktdaten);


        System.out.println("Das Inserat " + iAktuellesInserat.strTitel + " wurde gesichert");
        return iAktuellesInserat;
    }
}