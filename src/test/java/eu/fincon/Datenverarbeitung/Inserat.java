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
    public String strDauer;
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
        strDauer = Standardwert;
        strEndeDatum = Standardwert;
        strOrt = Standardwert;
        strBezahlung = Standardwert;
        strLetztesUpdate = Standardwert;
        strReferenzNummer = Standardwert;
        strProjektbeschreibung = Standardwert;
        strKategorienUndSkills = Standardwert;
    }
    //=====================================================================
    // Eine statische Funktion kann ohne Instanz über die Klasse aufgerufen werden
    // =====================================================================
    public static String getSQLiteCreateTable(String pstrTabellenName) {
        return "CREATE TABLE IF NOT EXISTS " + pstrTabellenName + " (\n"
                + "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " URL text,\n"
                + "	Titel text,\n"
                + "	Start text,\n"
                + "	Dauer text,\n"
                + "	Ende text,\n"
                + "	Ort text,\n"
                + " Bezahlung text,\n"
                + "	Letztes_Update text,\n"
                + "	Remote text,\n"
                + "	Referenz_Nummer text,\n"
                + "	Projektbeschreibung text,\n"
                + "	Kategorien_und_Skills text,\n"
                + "	Kontaktdaten text\n"
                + ");";
    }
    public static String getSQLiteSpalten()
    {
        return "URL,Titel,Start,Dauer,Ende,Ort,Bezahlung,Letztes_Update,Remote,Referenz_Nummer,Projektbeschreibung,Kategorien_und_Skills,Kontaktdaten";
    }
    public String getInseratStringSQLite() {

        //=====================================================================
        // Alle Werte des Inserates werden Semikolon-separiet zurückgegeben.
        // URL;Titel;StartDatum;EndeDatum;Ort;Bezahlung;LetztesUpdate;ReferenzNummer;Projektbeschreibung
        // =====================================================================
        return "\"" + strURL + "\",\"" + strTitel + "\",\"" + strStartDatum+ "\",\"" + strDauer + "\",\"" + strEndeDatum + "\",\"" + strOrt + "\",\"" + strBezahlung + "\",\"" + strLetztesUpdate + "\",\"" + strRemote + "\",\"" + strReferenzNummer + "\",\"" + strProjektbeschreibung + "\",\"" + strKategorienUndSkills + "\",\"" + strKontaktdaten + "\"";
    }
    public String getInseratStringCSV() {

        //=====================================================================
        // Alle Werte des Inserates werden Semikolon-separiet zurückgegeben.
        // URL;Titel;StartDatum;EndeDatum;Ort;Bezahlung;LetztesUpdate;ReferenzNummer;Projektbeschreibung
        // =====================================================================
        return strURL + ";" + strTitel + ";" + strStartDatum+ ";" + strDauer + ";" + strEndeDatum + ";" + strOrt + ";" + strBezahlung + ";" + strLetztesUpdate + ";" + strRemote + ";" + strReferenzNummer + ";" + strProjektbeschreibung + ";" + strKategorienUndSkills + ";" + strKontaktdaten;
    }
    //=====================================================================
    // Eine statische Funktion kann ohne Instanz über die Klasse aufgerufen werden
    // =====================================================================
    public static String getCSVSpalten()
    {
        return "URL;Titel;Start;Dauer;Ende;Ort;Bezahlung;Letztes Update;Remote;Referenz Nummer;Projektbeschreibung;Kategorien und Skills;Kontaktdaten";
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
