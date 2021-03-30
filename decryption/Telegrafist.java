public class Telegrafist implements Runnable {

    private Kanal kanal;
    private Monitor monitor;
    private int sekvensnummer;

    public Telegrafist(Kanal kanal, Monitor monitor) {
        this.kanal = kanal;
        this.monitor = monitor;
    }

    public void run() {
        int kanalId = kanal.hentId();

        for (String m = kanal.lytt(); m != null; m = kanal.lytt()) {
            Melding melding = new Melding(m, kanalId, sekvensnummer);
            //System.out.println(melding);
            monitor.leggTil(melding);
            //System.out.println("kryptert melding added");
            sekvensnummer++;
        }
        System.out.println("telegrafist DONE");
        monitor.ferdigTelegrafist();
    }


}
