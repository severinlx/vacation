package eu.fincon;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Datensatz
{
    public String Ueberschrift;
    public String Firmenname;
    public String DatumVon;
    public String DatumBis;
    public String Ort;
    public String Bezahlung;
    public String Beschreibung;
    public Datensatz(String ueberschrift, String firmenname, String datumvon, String datumbis, String ort, String bezahlung, String beschreibung)
    {
        Ueberschrift = ueberschrift;
        Firmenname = firmenname;
        DatumVon = datumvon;
        DatumBis = datumbis;
        Ort = ort;
        Bezahlung = bezahlung;
        Beschreibung = beschreibung;
    }

    public void Write_file(int index, String pfad)
    {
        String strIndex = index + "";
        String text = strIndex  + ";" + Ueberschrift + ";" + Firmenname + ";" + DatumVon + ";" + DatumBis + ";" + Ort + ";" + Bezahlung + ";" + Beschreibung;
        text = text.replace("\n","<br>");
        // Datensatz wird in die Datei geschrieben
        Write_to_File(pfad, text);
    }
    private void Write_to_File(String pfad, String text)
    {
        //Getting the Path object
        Path path = Paths.get(pfad);
        File file = new File(pfad);
        boolean fileExists = file.exists();
        //Creating a BufferedWriter object
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pfad, true),"UTF-8"));
            //Appending the UTF-8 String to the file
            if (!fileExists) {
                writer.append("Index;Überschrift;Firmenname;Datum Von;Datum Bis;Ort;Bezahlung;Beschreibung").append("\r\n");
            }
            writer.append(text).append("\r\n");
            //Flushing data to the file
            writer.flush();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
