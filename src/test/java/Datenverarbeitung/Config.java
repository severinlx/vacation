package Datenverarbeitung;

import Vakanzengrabber.Base.VakanzenGrabber;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class Config {
    public static VakanzenGrabber.Browser bBrowser;
    public Config()
    {

    }
    public static String strConfigFilePath = "config.xml";
    public static String getBenutzername(String pstrSeite)
    {
        return getWertAusConfig(pstrSeite, "benutzername");
    }
    public static String getPasswort(String pstrSeite)
    {
        return getWertAusConfig(pstrSeite, "passwort");
    }
    public static String getURL(String pstrSeite)
    {
        return getWertAusConfig(pstrSeite, "url");
    }
    public static void setBrowser() {

        //=====================================================================
        // Holt den Wert für das Element Browser aus der Config und setzt
        // die statische Variable bBrowser auf den entsprechenden Wert
        // =====================================================================
        String strBrowserAusConfig = getWertAusConfig("", "Browser");
        switch (strBrowserAusConfig.toLowerCase()) {
            case "chrome":
                bBrowser = VakanzenGrabber.Browser.Chrome;
                break;
            default:
                bBrowser = VakanzenGrabber.Browser.Chrome;
        }
    }

    //=====================================================================
    // Öffnet eine XML Datei aus dem Pfad der Globalen Variablen ""
    // Und gibt einen Wert für ein Attribut zurück der übergeben Parameter "name" zurück
    // =====================================================================
    private static String getWertAusConfig(String pstrSeite, String pstrAttributName)
    {
        //=====================================================================
        // Prüfen ob die Config-Datei vorhanden ist
        // =====================================================================
        if (new File(strConfigFilePath).exists() == false)
        {
            System.out.println("Datei gefunden");
            return "";
        }
        //=====================================================================
        // XML auslesen
        // =====================================================================
        String strWert = "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return "";
        }
        Document document = null;
        try {
            //=====================================================================
            // Parsed die Datei in dem übergebenen Pfad zu XML
            // =====================================================================
            document = builder.parse(new InputSource(strConfigFilePath));
        } catch (SAXException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        //=====================================================================
        // Selektiert das überste Element der XML
        // =====================================================================
        Element rootElement = document.getDocumentElement();
        if(!pstrSeite.equalsIgnoreCase("")) {

            //=====================================================================
            // Ändert die Übergabeparameter in Kleinbuchstaben zur besser vergleichbarkeit
            // =====================================================================
            pstrSeite = pstrSeite.toLowerCase();
            pstrAttributName = pstrAttributName.toLowerCase();

            //=====================================================================
            // "selektiert" alle Elemente vom Typ "Seite"
            // =====================================================================
            NodeList eSeitenElemente = rootElement.getElementsByTagName("Seite");

            //=====================================================================
            // Läuft durch alle "Seite"-Elemente und "sucht" die Webseite im Attribut "name"
            // Wenn das Element gefunden wurde, wird der Werte für das übergebene Attribut als Rückgabewert gesetzt
            // =====================================================================
            for (int i = 0; i < eSeitenElemente.getLength(); i++) {

                //=====================================================================
                // Sucht das Element "Seite" mit dem Attribut "name" = pstrSeite
                // =====================================================================
                if (eSeitenElemente.item(i).getAttributes().getNamedItem("name").getNodeValue().toLowerCase().contentEquals(pstrSeite)) {
                    String strValue = "";
                    //=====================================================================
                    // Sucht das übergebene Attribut in "Seite" mit dem Attribut "name" = pstrSeite
                    // =====================================================================
                    for (int j = 0; j < eSeitenElemente.item(i).getAttributes().getLength(); j++) {
                        if (eSeitenElemente.item(i).getAttributes().item(j).getNodeName().toLowerCase().contentEquals(pstrAttributName)) {
                            strValue = eSeitenElemente.item(i).getAttributes().item(j).getNodeValue();
                            break;
                        }
                    }
                    return strValue;
                }
            }
        }
        else
        {
            //=====================================================================
            // "selektiert" das Element welches als Attribut übergeben wurde (nur den ersten Eintrag, sofern mehrere
            // gefunden wurden
            // ====================================================================
            strWert = rootElement.getElementsByTagName(pstrAttributName).item(0).getTextContent();
        }
        document = null;
        return strWert;
    }
}
