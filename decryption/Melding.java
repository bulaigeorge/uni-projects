
public class Melding implements Comparable<Melding> {
    private String melding;
    private int sekvensNr, kanalId;

    public Melding(String melding, int kanalId, int sekvensNr) {
        this.melding = melding;
        this.kanalId = kanalId;
        this.sekvensNr = sekvensNr;

    }

    public int hentKanalId() {
        return kanalId;
    }

    public int hentSekvensNr() {
        return sekvensNr;
    }

    @Override
    public String toString() {
        return melding;
    }

    @Override
    public int compareTo(Melding m) {
        return Integer.compare(this.sekvensNr, m.sekvensNr);
    }



}
