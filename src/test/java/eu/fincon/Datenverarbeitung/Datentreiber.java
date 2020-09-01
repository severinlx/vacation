package eu.fincon.Datenverarbeitung;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Datentreiber {
    //=====================================================================
    // Pfad zum Datentreiber
    // =====================================================================
    static String DatentreiberPfad = "Datentreiber.xlsx";
    public static void datentreiberSetzen(String pstrDateipfad)
    {
        DatentreiberPfad = pstrDateipfad;
    }
    public static List<Testdatum> getTestdatenEXCEL()
    {
        //=====================================================================
        // Die Liste an Testdaten wird erzeugt
        // =====================================================================
        List<Testdatum> lTestdatumListe = new ArrayList<Testdatum>();
        //=====================================================================
        // Datei wird zum lesen geöffnet
        // =====================================================================
        File myFile = new File(DatentreiberPfad);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return lTestdatumListe;
        }
        //=====================================================================
        // Workbook Instanz wird gesetzt
        // =====================================================================
        XSSFWorkbook myWorkBook = null;
        try {
            myWorkBook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return lTestdatumListe;
        }
        //=====================================================================
        // Die erste Mappe des Workbooks wird selektiert
        // =====================================================================
        // Return first sheet from the XLSX workbook
        // XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        XSSFSheet mySheet = myWorkBook.getSheet("Testdaten");
        //=====================================================================
        // Iterator für Zeilen wird gesetzt
        // =====================================================================
        Iterator<Row> rowIterator = mySheet.iterator();
        //=====================================================================
        // Schleife über die Zeilen der Mappe (Skip Header);
        // =====================================================================
        for (int i = 1; i<mySheet.getPhysicalNumberOfRows();i++)
        {
            String strSeite="";
            String strSuchbegriff="";
            String strBrowser="";
            boolean blnSkipTestdatum=false;
            Row row = mySheet.getRow(i);
            if (row != null) {
                Cell cellSuchbegriff = row.getCell(0);
                if (strSuchbegriff != null)
                {
                    try {
                        strSuchbegriff = cellSuchbegriff.getStringCellValue();
                    }
                    catch (Exception e)
                    {
                        blnSkipTestdatum = true;
                        System.out.println("Skipped Testdatum (Suchbegriff)"+e.getMessage());
                    }
                }
            }
            if (!blnSkipTestdatum) {
                lTestdatumListe.add(new Testdatum(strSuchbegriff));
            }
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lTestdatumListe;
    }
}
