//Klasse som representerer en sub type av hvit en resept(millitær).


public class MillitaerResept extends HvitResept {

    public MillitaerResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient p, int reit) {
        super(legemiddel, utskrivendeLege, p, reit);
    }

    @Override
    public double prisAaBetale() {
        return 0.0;
    }
}
