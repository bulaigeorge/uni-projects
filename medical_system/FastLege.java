//Klasse som representerer en fastlege.

public class FastLege extends Lege implements Kommuneavtale {
    private int avtaleNummer;

    public FastLege(String navn, int avtaleNummer) {
        super(navn);
        this.avtaleNummer = avtaleNummer;
    }


    @Override
    public int hentAvtalenummer() {
        return avtaleNummer;
    }
}
