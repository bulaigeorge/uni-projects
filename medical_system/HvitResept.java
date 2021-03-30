//Klasse som representerer en type av resept(hvit).

public class HvitResept extends Resept {

    public HvitResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient p, int reit) {
        super(legemiddel, utskrivendeLege, p, reit);
        p.leggTilResept(this);
        utskrivendeLege.leggTilResept(this);
    }


    @Override
    public double prisAaBetale() {
        return legemiddel.hentPris();
    }


    @Override
    public String farge() {
        return "hvit";
    }

    @Override
    public String hentInfo() {
        return (super.hentInfo() + "\nType av resept: " + this.farge() +
                                   "\nPris: " + this.prisAaBetale());
    }
}
