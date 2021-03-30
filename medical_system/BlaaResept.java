//Klasse som representerer en type av resept(blå).

public class BlaaResept extends Resept {
    public BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient p, int reit) {
        super(legemiddel, utskrivendeLege, p, reit);
        p.leggTilResept(this);
        utskrivendeLege.leggTilResept(this);
    }


    @Override
    public String farge() {
        return "blaa";
    }


    @Override
    public double prisAaBetale() {
        double vanligPris = legemiddel.hentPris();
        return vanligPris/4;
    }

    public String hentInfo() {
        return (super.hentInfo() + "\nType av resept: " + this.farge() +
                          "\nPris: " + this.prisAaBetale());
    }
}
