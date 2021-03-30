//Klasse som representerer resepter

public abstract class Resept {
    protected static int id = 0;
    protected Legemiddel legemiddel;
    protected Lege utskrivendeLege;
    protected Pasient p;
    protected int reit;
    protected int reseptId;

    public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient p, int reit) {
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendeLege;
        this.p = p;
        this.reit = reit;
        reseptId = id;
        id++;
    }


    public int hentId() {
        return reseptId;
    }


    public Legemiddel hentLegemiddel() {
        return legemiddel;
    }


    public Lege hentLege() {
        return utskrivendeLege;
    }


    public FastLege hentFastLege() {
        return (FastLege) utskrivendeLege;
    }


    public Pasient hentPasient() {
        return p;
    }


    public int hentReit() {
        return reit;
    }

    public boolean bruk() {
        if ((reit-1) < 0) {
            System.out.println("\nKunne ikke bruke resept paa " + legemiddel + "(ingen gjenvarende reit)");
            return false;
        } else {
            reit--;
            System.out.println("\nBrukte resept paa " + legemiddel + ". Antall gjenvarende reit: " + reit);
            return true;
        }
    }

    public void testInt(int verdi, int forventetVerdi) {
        if (verdi == forventetVerdi) {
            System.out.println("Riktig!");
        } else {
            System.out.println("Feil!");
        }
    }


    public abstract String farge();
    public abstract double prisAaBetale();


    public String hentInfo() {
        return ("\nResept id: " + reseptId +
                "\nLegemidel: " + legemiddel +
                "\nLege: " + utskrivendeLege + "\nPasient: " +
                p + "\nReit: " + reit);
    }


}
