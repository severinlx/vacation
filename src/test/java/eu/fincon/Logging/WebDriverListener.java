package eu.fincon.Logging;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import static eu.fincon.Logging.ExtendetLogger.*;

public class WebDriverListener implements WebDriverEventListener {

    //=====================================================================
    // Enthält Methoden des WebDriverEventListeners
    // Werden automatisch aufgerufen, sobald ein entsprechendes Event gestartet wird
    // =====================================================================
    public void afterAlertAccept(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void afterAlertDismiss(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {

    }
    public void afterClickOn(WebElement arg0, WebDriver arg1) {
        if (arg0 != null)
            LogEntry(LogStatus.INFO, "Element " + arg0.toString() + " wurde geklickt");
        else
            LogEntry(LogStatus.WARNING, "Das Element konnte nicht geklickt werden " + arg0.toString());
    }
    public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
        if (arg0 != null && arg1 != null)
            LogEntry(LogStatus.INFO, "Das Element " + arg1.toString() + " wurde mittels " + arg0.toString() + " ermittelt");
        else
            LogEntry(LogStatus.WARNING, "Das Element konnte nicht mittels " + arg0.toString() + " ermittelt werden");
    }
    public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
        if (arg1 != null)
            LogEntry(LogStatus.INFO, "Screenshot von " + arg1.toString() + " soll erstellt werden...");
    }
    public void afterGetText(WebElement arg0, WebDriver arg1, String arg2){
        if (arg0 != null && arg2 != null)
            LogEntry(LogStatus.INFO, "Aus dem Element " + arg0.toString() + " wurde der Wert " + arg2 + " ausgelesen");
        else
            LogEntry(LogStatus.FATAL, "Aus dem Element konnte der Wert nicht ausgelesen werden.\n" + arg1.toString());
    }
    public void afterNavigateBack(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void afterNavigateForward(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void afterNavigateRefresh(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void afterNavigateTo(String arg0, WebDriver arg1) {
        if (arg0 != null)
            LogEntry(LogStatus.INFO, "Es wurde die Seite " + arg0 + " geöffnet");
    }
    public void afterScript(String arg0, WebDriver arg1) {
// TODO Auto-generated method stub
    }
    public void afterSwitchToWindow(String arg0, WebDriver arg1) {
// TODO Auto-generated method stub
    }
    public void beforeAlertAccept(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void beforeAlertDismiss(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
// TODO Auto-generated method stub
    }
    public void beforeClickOn(WebElement arg0, WebDriver arg1) {
        if (arg0 != null)
            LogEntry(LogStatus.INFO, "Element " + arg0.toString() + " soll geklickt werden...");
        else
            LogEntry(LogStatus.INFO, "Das zu klickende Element ist null...");
    }
    public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
        if (arg1 != null && arg0 != null)
            LogEntry(LogStatus.INFO, "Das Element " + arg1.toString() + " soll mittels " + arg0.toString() + " ermittelt werden...");
        else if (arg0 != null)
            LogEntry(LogStatus.INFO, "Das Element soll mittels" + arg0.toString() + " ermittelt werden...");
    }
    public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
        if (arg0 != null)
            LogEntry(LogStatus.INFO, "Screenshot von " + arg0.toString() + " soll erstellt werden...");
    }
    public void beforeGetText(WebElement arg0, WebDriver arg1) {
        if (arg0 != null)
            LogEntry(LogStatus.INFO, "Aus dem Element " + arg0.toString() + " soll der Wert ausgelesen werden...");
        else
            LogEntry(LogStatus.INFO, "Das auszulesende Element ist NULL");
    }
    public void beforeNavigateBack(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void beforeNavigateForward(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void beforeNavigateRefresh(WebDriver arg0) {
// TODO Auto-generated method stub
    }
    public void beforeNavigateTo(String arg0, WebDriver arg1) {
        if (arg0 != null)
            LogEntry(LogStatus.INFO, "Die Seite " + arg0 + " soll geöffnet werden...");
    }
    public void beforeScript(String arg0, WebDriver arg1) {
// TODO Auto-generated method stub
    }
    public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
// TODO Auto-generated method stub
    }
    public void onException(Throwable arg0, WebDriver arg1) {
// TODO Auto-generated method stub
        if (arg0 != null)
            LogEntry(LogStatus.WARNING, "Exception: " + arg0.getMessage());
        else
            LogEntry(LogStatus.WARNING, "Unkown Exception");
    }
}
