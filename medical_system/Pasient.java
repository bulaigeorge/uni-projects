public class Pasient {
    private String navn;
    private String fodelsnummer;
    private static int id = 0;
    private int pasientId;
    private Stabel<Resept> reseptListe;

    public Pasient(String navn, String fodelsnummer) {
        this.navn = navn;
        this.fodelsnummer = fodelsnummer;
        this.pasientId = id;
        id++;
        this.reseptListe = new Stabel<Resept>();

    }


    public void leggTilResept(Resept resept) {
        reseptListe.leggPaa(resept);
    }


    public Stabel<Resept> hentReseptList() {
        return reseptListe;
    }


    @Override
    public String toString() {
        return navn;
    }


    public int hentId() {
        return pasientId;
    }


    public String hentFnr() {
        return fodelsnummer;
    }

}
