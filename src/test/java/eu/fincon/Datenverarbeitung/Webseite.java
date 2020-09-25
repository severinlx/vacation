package eu.fincon.Datenverarbeitung;

import com.relevantcodes.extentreports.LogStatus;
import eu.fincon.Logging.ExtendetLogger;
import eu.fincon.Vakanzengrabber.Base.VakanzenGrabber;
import eu.fincon.Vakanzengrabber.FreelanceDE.FreelanceDEGrabben;
import eu.fincon.Vakanzengrabber.FreelancerMap.FreelancerMapGrabben;

public class Webseite {
    //=====================================================================
    // Eine Instanz von Webseite besteht aus den folgenden Variablen
    // =====================================================================
    public VakanzenGrabber.Seiten eSeite;
    public String strURL;
    public String strBenutzername;
    public String strPasswort;
    public VakanzenGrabber VakanzenObject;
    public Webseite(String pstrSeitenname)
    {
        ExtendetLogger.LogEntry(LogStatus.INFO, "Seite " + pstrSeitenname + " wird vorbereitet...");
        strURL = Config.getURL(pstrSeitenname);
        strBenutzername = Config.getBenutzername(pstrSeitenname);
        strPasswort = Config.getPasswort(pstrSeitenname);
        setSeite(pstrSeitenname);
        ExtendetLogger.LogEntry(LogStatus.PASS, "Seite " + pstrSeitenname + " wurde vorbereitet");
    }
    private void setSeite(String pstrSeitenname) {
        switch (pstrSeitenname.toLowerCase()) {
            case "freelancede":
                eSeite = VakanzenGrabber.Seiten.FreelanceDE;
                ExtendetLogger.LogEntry(LogStatus.INFO, "Seite - " + eSeite.toString() + " - wird instanziiert");
                VakanzenObject = new FreelanceDEGrabben(this);
                break;
            case "freelancermap":
                eSeite = VakanzenGrabber.Seiten.FreelancerMap;
                ExtendetLogger.LogEntry(LogStatus.INFO, "Seite - " + eSeite.toString() + " - wird instanziiert");
                VakanzenObject = new FreelancerMapGrabben(this);
                break;
            default:
                ExtendetLogger.LogEntry(LogStatus.WARNING, "Seite wurde nicht erkannt - " + pstrSeitenname);
        }
    }
}
