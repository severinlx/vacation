package eu.fincon.Datenverarbeitung;

public class Inserat {
    private String strURL; // Kann nur über GET/SET zugegriffen werden.
    //=====================================================================
    // Variablendeklaration für Titel und Firmenname
    // =====================================================================
    public String strTitel;
    public String strFirmenname; // noch offen, da kein PremiumKonto vorhanden
    //=====================================================================
    // Variablendeklaration für die Metadaten
    // =====================================================================
    public String strStartDatum;
    public String strEndeDatum;
    public String strOrt;
    public String strBezahlung;
    public String strRemote;
    public String strLetztesUpdate;
    public String strReferenzNummer;
    //=====================================================================
    // Variablendeklaration für die Projektbeschreibung
    // =====================================================================
    public String strProjektbeschreibung;
    //=====================================================================
    // Variablendeklaration für die Kategorien und Skills
    // =====================================================================
    public String strKategorienUndSkills;
    //=====================================================================
    // Variablendeklaration für die Kontaktdaten
    // =====================================================================
    public String strKontaktdaten;

    String Standardwert = "N/A";
    public Inserat(String pstrURL)
    {
        strURL = pstrURL;
        //=====================================================================
        // Sobald eine Instanz erzeugt wird, wird jede Variable mit dem Standardwert vorbelegt
        // =====================================================================
        strTitel = Standardwert;
        strStartDatum = Standardwert;
        strEndeDatum = Standardwert;
        strOrt = Standardwert;
        strBezahlung = Standardwert;
        strLetztesUpdate = Standardwert;
        strReferenzNummer = Standardwert;
        strProjektbeschreibung = Standardwert;
        strKategorienUndSkills = Standardwert;
    }
    public String getInseratStringCSV() {

        //=====================================================================
        // Alle Werte des Inserates werden Semikolon-separiet zurückgegeben.
        // URL;Titel;StartDatum;EndeDatum;Ort;Bezahlung;LetztesUpdate;ReferenzNummer;Projektbeschreibung
        // =====================================================================
        return strURL + ";" + strTitel + ";" + strStartDatum + ";" + strEndeDatum + ";" + strOrt + ";" + strBezahlung + ";" + strLetztesUpdate + ";" + strRemote + ";" + strReferenzNummer + ";" + strProjektbeschreibung + ";" + strKategorienUndSkills + ";" + strKontaktdaten;
    }

    //=====================================================================
    // Eine statische Funktion kann ohne Instanz über die Klasse aufgerufen werden
    // =====================================================================
    public static String getCSVSpalten()
    {
        return "URL;Titel;Start;Ende;Ort;Bezahlung;Letztes Update;Remote;Referenz Nummer;Projektbeschreibung;Kategorien und Skills;Kontaktdaten";
    }
    public String getURL()
    {
        return strURL;
    }
    public void setURL(String pstrURL)
    {
        strURL = pstrURL;
    }
}
