package eu.fincon.Datenverarbeitung;

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
        strURL = Config.getURL(pstrSeitenname);
        strBenutzername = Config.getBenutzername(pstrSeitenname);
        strPasswort = Config.getPasswort(pstrSeitenname);
        setSeite(pstrSeitenname);
    }
    private void setSeite(String pstrSeitenname) {
        switch (pstrSeitenname.toLowerCase()) {
            case "freelancede":
                eSeite = VakanzenGrabber.Seiten.FreelanceDE;
                VakanzenObject = new FreelanceDEGrabben(this);
                break;
            case "freelancermap":
                eSeite = VakanzenGrabber.Seiten.FreelancerMap;
                VakanzenObject = new FreelancerMapGrabben(this);
                break;
            default:
                System.out.println(pstrSeitenname.toLowerCase());
        }
    }
}
