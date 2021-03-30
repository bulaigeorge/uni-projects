//Klasse som representerer en leggemiddel

public abstract class Legemiddel {
    protected String navn;
    protected static int id = 0;
    protected double pris;
    //virkestoff i mg
    protected double virkestoff;
    protected int middelId;

    public Legemiddel(String navn,double pris, double virkestoff) {
        this.navn = navn;
        this.pris = pris;
        this.virkestoff = virkestoff;
        middelId = id;
        id++;

    }


    public int hentId() {
        return middelId;
    }


    public String hentNavn() {
        return navn;
    }


    public double hentPris() {
        return pris;
    }


    public double hentVirkestoff() {
        return virkestoff;
    }


    public void settNyPris(double nyPris) {
        pris = nyPris;
    }


    @Override
    public String toString() {
        return navn;
    }


    public void testDouble(double verdi, double forventetVerdi) {
        if (verdi == forventetVerdi) {
            System.out.println("Riktig!");
        } else {
            System.out.println("Feil!");
        }
    }


    public String hentInfo() {
        return ("\nNavn: " + navn + "\nID: " + middelId +
                "\nPris: " + pris + "\nVirkestoff: " + virkestoff + " mg");
    }


}
