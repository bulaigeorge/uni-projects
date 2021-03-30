import java.util.ArrayList;
import java.util.Collections;
import java.io.*;


public class Operasjonsleder implements Runnable {
    private Monitor dekryptertMonitor;
    private ArrayList<Melding> kanalEn, kanalTo, kanalTre;
    private PrintWriter writer1, writer2, writer3;

    public Operasjonsleder(Monitor monitor) {
        this.dekryptertMonitor = monitor;
        this.kanalEn = new ArrayList<Melding>();
        this.kanalTo = new ArrayList<Melding>();
        this.kanalTre = new ArrayList<Melding>();

    }

    public void run() {
        for (Melding m = dekryptertMonitor.hentDekryptertMelding(); m != null; m = dekryptertMonitor.hentDekryptertMelding()) {
            //if (Test.ANTALL_Kryptograf != dekryptertMonitor.getCountedKryptograf()) {
            if (m.hentKanalId() == 1) {
                kanalEn.add(m);
            } else if (m.hentKanalId() == 2) {
                 kanalTo.add(m);
            } else if (m.hentKanalId() == 3) {
                kanalTre.add(m);
            }
            //System.out.println(m.hentKanalId());
            //nothing else works
            System.out.println("WHY this line (Operasjonsleder.java)");
            // try {
            //     Thread.sleep(1);
            // } catch (InterruptedException e) {}
        }
        if (Test.ANTALL_Kryptograf == dekryptertMonitor.getCountedKryptograf()) {
            //System.out.println("TRY TO SORT");
            Collections.sort(kanalTre);
            Collections.sort(kanalTo);
            Collections.sort(kanalEn);
        }
        //printer 1
        try {
            //System.out.println("TRY CREATE PRINTER");
            writer1 = new PrintWriter("1.txt", "utf-8");
            //System.out.println("PRINTER CREATED");
        } catch (FileNotFoundException | UnsupportedEncodingException e){System.out.println("PRINTER NOT CREATED");}
        for (Melding m : kanalEn) {
            if (m != null) {
                writer1.println(m.toString());
            }
        }
        writer1.close();


        //printer 2
        try {
            //System.out.println("TRY CREATE PRINTER");
            writer2 = new PrintWriter("2.txt", "utf-8");
            //System.out.println("PRINTER CREATED");
        } catch (FileNotFoundException | UnsupportedEncodingException e){System.out.println("PRINTER NOT CREATED");}
        for (Melding m : kanalTo) {
            if (m != null) {
                writer2.println(m.toString());
            }
        }
        writer1.close();


        //printer 3
        try {
            //System.out.println("TRY CREATE PRINTER");
            writer3 = new PrintWriter("3.txt", "utf-8");
            //System.out.println("PRINTER CREATED");
        } catch (FileNotFoundException | UnsupportedEncodingException e){System.out.println("PRINTER NOT CREATED");}
        for (Melding m : kanalTre) {
            if (m != null) {
                writer3.println(m.toString());
            }
        }
        writer1.close();
        writer2.close();
        writer3.close();
        System.out.println("Operasjonsleder er ferdig");
    }




}
