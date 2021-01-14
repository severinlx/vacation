package eu.fincon.Datenverarbeitung;

import com.relevantcodes.extentreports.LogStatus;
import eu.fincon.Logging.ExtendetLogger;

import java.io.*;
// SQL Imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
// Time Imports
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
// List Import
import java.util.List;

public class InserateVerwalten {
    public enum SpeicherTypen{
        csv,
        txt,
        sqllite
    }
    //=====================================================================
    // Der Text des Elementes wird versucht auszulesen
    // =====================================================================
    public String ExportPfad = "export";
    List<Inserat> lInserate;

    // Database Functions
    private Connection connectToSQLLiteDatabase()
    {
        // SQLite connection string
        String url = Config.strDatabasePfad + Config.strDatabaseName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            ExtendetLogger.LogEntry(LogStatus.ERROR, "Failed to connect to Database - " + url);
        }
        return conn;
    }
    private void dropExistingTable(Connection conn, String pstrTabellenName)
    {
        String sql = "DROP TABLE IF EXISTS " + pstrTabellenName;
        try
        {
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            ExtendetLogger.LogEntry(LogStatus.INFO, sql);
            conn.commit();
        } catch (SQLException e) {
            ExtendetLogger.LogEntry(LogStatus.ERROR, "Failed to Drop Table - " + sql);
            ExtendetLogger.LogEntry(LogStatus.ERROR, e.getMessage());
        }
    }
    private String createNewTable(Connection conn, String pstrTabellenName)
    {
        String url = Config.strDatabasePfad + Config.strDatabaseName;
        String strTabellenName = pstrTabellenName + LocalDateTime.now().format(DateTimeFormatter.ofPattern("_yyyy_MM_dd"));
        dropExistingTable(conn, strTabellenName);
        // SQL statement for creating a new table
        String sql = Inserat.getSQLiteCreateTable(strTabellenName);

        try
        {
             Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            ExtendetLogger.LogEntry(LogStatus.INFO, sql);
            conn.commit();
        } catch (SQLException e) {
            ExtendetLogger.LogEntry(LogStatus.ERROR, "Failed to create Table - " + sql);
            ExtendetLogger.LogEntry(LogStatus.ERROR, e.getMessage());
        }
        return strTabellenName;
    }
    public void insertIntoSQLite(Inserat piInserat, Connection pConnection, String pstrTabellenname, int pintID) {
        Statement stmt = null;
        try {
            stmt = pConnection.createStatement();
        } catch (SQLException e) {
            ExtendetLogger.LogEntry(LogStatus.ERROR, "Failed to create Statement for Database");
        }
        String sql = "INSERT INTO "+ pstrTabellenname +" (" + Inserat.getSQLiteSpalten() + ") VALUES(" + piInserat.getInseratStringSQLite() + ")";
        try {
            stmt.execute(sql);
            ExtendetLogger.LogEntry(LogStatus.INFO, sql);
            pConnection.commit();
        } catch (SQLException e) {
            ExtendetLogger.LogEntry(LogStatus.ERROR, "Failed to Execute Insert Statement - " + sql);
            ExtendetLogger.LogEntry(LogStatus.ERROR, e.getMessage());
        }

    }
    // End Database Functions
    public InserateVerwalten(List<Inserat> piwInserate)
    {
        //=====================================================================
        // Die bei der Instanziierung übergebene Liste wird in der Klasse gesichert
        // =====================================================================
        lInserate = piwInserate;
    }
    public void inserateSichern(SpeicherTypen pstSpeicherTyp, String pstrTabellenName)
    {
        if (pstSpeicherTyp == SpeicherTypen.csv || pstSpeicherTyp == SpeicherTypen.txt)
            fileOutput(pstSpeicherTyp);
        else if (pstSpeicherTyp == SpeicherTypen.sqllite)
            sqliteOutput(pstrTabellenName);

    }
    private void sqliteOutput(String pstrTabellenName)
    {
        Connection conn = this.connectToSQLLiteDatabase();
        try {
            // Damit wird die AutoCommit deaktiviert
            // Dieser führte dazu, dass mit jedem Statement ein Commit durchgeführt wurde - Was zu einer Exception geführt hat
            // Der Commit wird "manuell" im Code nach dem Statement ausgeführt
            conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            ExtendetLogger.LogEntry(LogStatus.INFO, "Failed to Set AutoCommitMode");
        }
        pstrTabellenName = createNewTable(conn, pstrTabellenName);
        ExtendetLogger.LogEntry(LogStatus.INFO, "Inserate werden gesichert... ");
        int intIndex = 1;
        for (Inserat iInserat:lInserate) {
            ExtendetLogger.LogEntry(LogStatus.INFO, "Inserat sichern: " + iInserat.toString());
            insertIntoSQLite(iInserat, conn, pstrTabellenName, intIndex);
            intIndex++;
        }
    }
    private void fileOutput(SpeicherTypen pstSpeicherTyp) {
        OutputStream osExportFile = null;
        File fileExportFile = null;
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
        String strAbsoluterPfad = ExportPfad +"_"+ formattedDateTime +"." + pstSpeicherTyp.toString();
        ExtendetLogger.LogEntry(LogStatus.INFO, "Dateipfad für Sicherung = " + strAbsoluterPfad);

        try {
            fileExportFile = new File(strAbsoluterPfad);
            if (fileExportFile.exists()) {
                ExtendetLogger.LogEntry(LogStatus.FATAL, "Die Datei ist bereits vorhanden und wird vor dem Sichern entfernt. Speichertyp = " + pstSpeicherTyp.toString());
                fileExportFile.delete();
            }
            fileExportFile.createNewFile();
        }
        catch (Exception e) {
            ExtendetLogger.LogEntry(LogStatus.FATAL, "Die Export-Datei konnte nicht erzeugt werden");
            return;
        }

        try {
            osExportFile = new FileOutputStream(strAbsoluterPfad);
        } catch (Exception e) {
            ExtendetLogger.LogEntry(LogStatus.FATAL, "Exception in CSV schreiben  - " + e.getMessage());
        }
        inCSVDateiSchreiben (osExportFile, "#;"+ Inserat.getCSVSpalten());
        //=====================================================================
        // Es wird mittels Schleife über jeden Eintrag der Inserate Liste gegangen
        // =====================================================================
        int index = 1;
        for( Inserat i: lInserate )
        {
            switch (pstSpeicherTyp) {
                case csv:
                    ExtendetLogger.LogEntry(LogStatus.INFO, "Speichertyp wird verwendet -> " + pstSpeicherTyp.toString());
                    String strInseratStringCSV = i.getInseratStringCSV();
                    inCSVDateiSchreiben(osExportFile, Integer.toString(index)+";"+strInseratStringCSV);
                    break;
                case txt:
                    // Weitere Exporte möglich
                    break;
                default:
                    ExtendetLogger.LogEntry(LogStatus.WARNING, "Speichertyp wurde nicht gefunden - " + pstSpeicherTyp.toString());
                    return;
            }
            index ++;
        }
        try {
            osExportFile.flush();
            osExportFile.close();
        } catch (Exception e) {
            ExtendetLogger.LogEntry(LogStatus.FATAL, "Exception in CSV schreiben  - " + e.getMessage());
        }
    }

    public void inCSVDateiSchreiben (OutputStream fileExportFile, String pstrInseratStringCSV)
    {
        PrintWriter pwExport = null;
        String strText = "";

        //=====================================================================
        // Der Übergebene String wird für die Ausgabe vorbereitet
        // Zeilenumbrüche werden für die CSV mit HTML-Tags ersetzt
        // =====================================================================
        strText = pstrInseratStringCSV.replace("\r\n","<br>");
        strText = strText.replace("\n","<br>");
        strText = strText.concat("\r\n");
        //=====================================================================
        // Der Aufgearbeitete String wird in die Datei geschrieben
        // Flush garantiert das der Buffer in die Datei geschrieben wird.
        // =====================================================================
        try {
            pwExport = new PrintWriter(new OutputStreamWriter(fileExportFile, "UTF-8"));
            ExtendetLogger.LogEntry(LogStatus.INFO, "Text wird in die Datei geschrieben...");
            pwExport.append(strText);
            ExtendetLogger.LogEntry(LogStatus.INFO, "Text wurde in die Datei geschrieben.<br>" + strText);
            pwExport.flush();
        } catch (Exception e) {
            ExtendetLogger.LogEntry(LogStatus.FATAL, "Exception in CSV schreiben  - " + e.getMessage());

            System.out.println();
        }
        ExtendetLogger.LogEntry(LogStatus.PASS, "Text erfolgreich in die Datei geschrieben  - " + strText);

    }
}