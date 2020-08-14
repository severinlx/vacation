package eu.fincon.Datenverarbeitung;

import eu.fincon.Vakanzengrabber.Base.VakanzenGrabber;

public class Testdatum
{
    //=====================================================================
    // Ein Testdatum besteht aus den folgenden Variablen
    // =====================================================================
    public VakanzenGrabber.Seiten eSeite;
    public String strURL;
    public String strSuchbegriff;
    public InserateVerwalten.SpeicherTypen ivstSpeichertyp;
    public String strBenutzername;
    public String strPasswort;

    public Testdatum(String pstrSeitenname, String pstrSuchbegriff)
    {
        //=====================================================================
        // Bei der Instanziierung müssen Parameter übergeben werden, diese werden in die Variablen gesichert
        // ====================================================================
        initTestdatum(pstrSeitenname, pstrSuchbegriff, InserateVerwalten.SpeicherTypen.csv);
    }
    public Testdatum(String pstrSeitenname, String pstrSuchbegriff, InserateVerwalten.SpeicherTypen pivstSpeichertyp)
    {
        //=====================================================================
        // Bei der Instanziierung müssen Parameter übergeben werden, diese werden in die Variablen gesichert
        // =====================================================================
        initTestdatum(pstrSeitenname, pstrSuchbegriff, pivstSpeichertyp);
    }
    private void initTestdatum(String pstrSeitenname, String pstrSuchbegriff, InserateVerwalten.SpeicherTypen pivstSpeichertyp)
    {
        strSuchbegriff = pstrSuchbegriff;
        ivstSpeichertyp = pivstSpeichertyp;
        strURL = Config.getURL(pstrSeitenname);
        strBenutzername = Config.getBenutzername(pstrSeitenname);
        strPasswort = Config.getPasswort(pstrSeitenname);
        setSeite(pstrSeitenname);

        //=====================================================================
        // Loggingausgabe Testdatum
        // =====================================================================
        logTestdatum(pstrSeitenname);
    }

    private void logTestdatum(String pstrSeitenname) {
        System.out.println("----------Testdatum--------------");
        System.out.println("Suchbegriff"+strSuchbegriff);
        System.out.println(pstrSeitenname);
        System.out.println("Seite"+ eSeite.toString());
        System.out.println("URL"+strURL);
        System.out.println("Benutzer"+strBenutzername);
        System.out.println("Passwort"+strPasswort);
        System.out.println("---------------------------------");
    }

    private void setSeite(String pstrSeitenname) {
        switch(pstrSeitenname.toLowerCase())
        {
            case "freelancede":
                eSeite = VakanzenGrabber.Seiten.FreelanceDE;
                break;
            case "freelancermap":
                eSeite = VakanzenGrabber.Seiten.FreelancerMap;
                break;
            default:
                System.out.println(pstrSeitenname.toLowerCase());
        }
    }
}
