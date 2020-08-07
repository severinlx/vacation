package Vakanzengrabber.FreelanceDE;

import Datenverarbeitung.Inserat;
import Vakanzengrabber.Base.VakanzenGrabber;
import org.openqa.selenium.WebElement;

public class FreelanceDEInseratMaske extends VakanzenGrabber {
    //=====================================================================
    // XPaths - Inserat
    // =====================================================================
    String strXpathTitel = "//div[@class='panel-body project-header panel-white']//h1";

    String strXpathStartDatum = "//div[@class='col-md-6 details-box']//i[@class='fa fa-sign-in']/..";
    String strXpathEndeDatum = "//div[@class='col-md-6 details-box']//i[@class='fa fa-sign-out']/..";
    String strXpathOrt = "//div[@class='col-md-6 details-box']//i[@class='fa fa-globe']/..";
    String strXpathBezahlung = "//div[@class='col-md-6 details-box']//i[@class='fa fa-money']/..";
    String strXpathRemote = "//div[@class='col-md-6 details-box']//i[@class='fa fa-home']/..";
    String strXpathLetztesUpdate = "//div[@class='col-md-6 details-box']//i[@class='fa fa-refresh']/..";
    String strXpathReferenzNummer = "//div[@class='col-md-6 details-box']//i[@class='fa fa-home']/..";

    String strXpathProjektbeschreibung = "//div[@class=\"panel-body\"]";

    String strXpathKategorienUndSkills = "//h3[text() = 'Kategorien und Skills']/../..//div[@class='panel-body']";


    String strXpathKontaktdaten = "//h3[text() = 'Kontaktdaten']/../..//div[@class='panel-body']";
    public FreelanceDEInseratMaske(FreelanceDEGrabben pSuperclass)
    {
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
        WebElement webEndeDatum = webelementFinden(SelectorType.xpath, strXpathEndeDatum, 0);
        WebElement webOrt = webelementFinden(SelectorType.xpath, strXpathOrt, 0);
        WebElement webBezahlung = webelementFinden(SelectorType.xpath, strXpathBezahlung, 0);
        WebElement webRemote = webelementFinden(SelectorType.xpath, strXpathRemote, 0);
        WebElement webLetztesUpdate = webelementFinden(SelectorType.xpath, strXpathLetztesUpdate, 0);
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
        // Webelement zur Kontaktdaten wird ermittelt
        // =====================================================================
        WebElement webKontaktdaten = webelementFinden(SelectorType.xpath, strXpathKontaktdaten, 0);

        //=====================================================================
        // Die Inhalte werden aus den Webelementen geholt und in einer Instanz
        // der Klasse Inserate gesichert
        // =====================================================================
        iAktuellesInserat.strTitel = webelementTextauslesen(webTitel);

        iAktuellesInserat.strStartDatum = webelementTextauslesen(webStartDatum);
        iAktuellesInserat.strEndeDatum = webelementTextauslesen(webEndeDatum);
        iAktuellesInserat.strOrt = webelementTextauslesen(webOrt);
        iAktuellesInserat.strBezahlung = webelementTextauslesen(webBezahlung);
        iAktuellesInserat.strRemote = webelementTextauslesen(webRemote);
        iAktuellesInserat.strLetztesUpdate = webelementTextauslesen(webLetztesUpdate);
        iAktuellesInserat.strReferenzNummer = webelementTextauslesen(webReferenzNummer);

        iAktuellesInserat.strProjektbeschreibung = webelementTextauslesen(webProjektbeschreibung);

        iAktuellesInserat.strKategorienUndSkills = webelementTextauslesen(webKategorienUndSkills);


        iAktuellesInserat.strKontaktdaten = webelementTextauslesen(webKontaktdaten);


        System.out.println("Das Inserat " + iAktuellesInserat.strTitel + " wurde gesichert");
        return iAktuellesInserat;
    }
}
