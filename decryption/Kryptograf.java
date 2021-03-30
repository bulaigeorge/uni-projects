public class Kryptograf implements Runnable {
    private Monitor kryptertMonitor, dekryptertMonitor;

    public Kryptograf(Monitor kryptertMonitor, Monitor dekryptertMonitor) {
        this.kryptertMonitor = kryptertMonitor;
        this.dekryptertMonitor = dekryptertMonitor;
    }

    //maybe method for Melding class to sett the decrypted value instead of creating a new object
    public void run() {
        for (Melding m = kryptertMonitor.hentMelding(); m != null; m = kryptertMonitor.hentMelding()) {
            int kanalId = m.hentKanalId();
            int sekvensNr = m.hentSekvensNr();
            //System.out.println("SekvensNr: " + sekvensNr + " KanalID: "+ kanalId);
            String dekryptert = Kryptografi.dekrypter(m.toString());
            Melding dekryptertMld = new Melding(dekryptert, kanalId, sekvensNr);
            dekryptertMonitor.leggTil(dekryptertMld);
            //System.out.println("DekryptertMelding lagt");

        }
        dekryptertMonitor.ferdigKryptograf();
        System.out.println("KRYPTO DONE" + dekryptertMonitor.getCountedKryptograf());
        //System.out.println(dekryptertMonitor.antallMeldinger() + " THIS IS HOW MUCH KRYPTO SENDS TO THE DEKRYPTEDMONITOR");
    }


}
