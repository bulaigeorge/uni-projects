//Klasse som representerer en lege

public class Lege implements Comparable<Lege>{
    protected String navn;
    private Lenkeliste<Resept> reseptListe;


    public Lege(String navn) {
        this.navn = navn;
        this.reseptListe = new Lenkeliste<Resept>();
    }


    public void leggTilResept(Resept resept) {
        reseptListe.leggTil(resept);
    }


    public Lenkeliste<Resept> hentReseptList() {
        return reseptListe;
    }


    @Override
    public String toString() {
        return navn;
    }


    public int hentAvtalenummer() {
        return 0;
    }


    @Override
    public int compareTo(Lege lege) {
        return this.navn.compareTo(lege.toString());
    }


}
