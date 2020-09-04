package eu.fincon.Logging;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
    public String format(LogRecord pLogRecord)
    {
        StringBuffer sb = new StringBuffer(1000);
        String strColor = "red";
        //=====================================================================
        // Erzeut eine Table-Row
        // =====================================================================
        sb.append("<tr>");
        //=====================================================================
        // Setzt eine Farbe für die unterschiedlichen Log-Level
        // =====================================================================
        if (pLogRecord.getLevel().intValue() > Level.WARNING.intValue()) {
            strColor = "red";
        } else if (pLogRecord.getLevel().intValue() == Level.WARNING.intValue()) {
            strColor = "yellow";
        } else {
            strColor = "blue";
        }
        //=====================================================================
        // Splitted die Message
        // =====================================================================
        //ToDo Es muss eine bessere Methode zum übergeben der ClassName und Method geben als einen zu splittenden String
        String[] SplittedMessage = pLogRecord.getMessage().split(";");
        String strMessage = SplittedMessage[0];
        String strClassName = SplittedMessage[1];
        String strMethodName = SplittedMessage[2];
        //=====================================================================
        // Über <td> werden die jeweiligen Spalten der Tabelle erzeugt
        // =====================================================================
        sb.append("<td><font color=\""+ strColor + "\">");
        sb.append(pLogRecord.getLevel()+"</font></td>");
        sb.append("<td>"+pLogRecord.getInstant().truncatedTo(ChronoUnit.MILLIS).toString().replaceAll("[TZ]", " ")+"</td>");
        sb.append("<td>"+strMessage+"</td>");
        sb.append("<td>"+strClassName+"</td>");
        sb.append("<td>"+strMethodName+"</td>");
        sb.append('\n');
        //=====================================================================
        // Schließt die Table-Row ab
        // =====================================================================
        sb.append("</tr>");
        return sb.toString();
    }
    public String getHead(Handler h) {
        //=====================================================================
        // Hier wird das Datum in einem formatierten String für den Header erzeugt
        // =====================================================================
        Format formatter = new SimpleDateFormat("dd.mm.yyyy - HH:mm:ss");
        String strFormattedDate = formatter.format(new Date());


        //=====================================================================
        // Hier wird der Header der Log-Datei erzeugt (wird bei der Instanziierung automatisch aufgerufen)
        // =====================================================================
        return "<HTML><HEAD><link rel=\"stylesheet\" href=\"Logging\\stylesheet.css\"> Log-Datum "+(strFormattedDate)+"</HEAD><BODY><H1>Log-Datei Vakanzengrabber</H1><PRE>\n<table><tr><th>Log-Level</th><th>Time-Stamp</th><th>Message</th><th>Source-Class</th><th>Source-Method</th></tr>";
    }
    public String getTail(Handler h) {
        //=====================================================================
        // Hier wird der Footer der Log-Datei erzeugt (wird automatisch aufgerufen)
        // =====================================================================
        return "</table></PRE></BODY></HTML>\n";
    }
}
