//Klasse som representerer en sub type av hvit en resept(P).


public class PResept extends HvitResept {
    private static double rabatt = 116.0;

    //uten reit, det er alltid 3
    public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient p) {
        super(legemiddel, utskrivendeLege, p, 3);
    }

    @Override
    public double prisAaBetale() {
        double vanligPris = legemiddel.hentPris();
        if ((vanligPris-rabatt) <= 0.0) {
            return 0.0;
        } else {
            return vanligPris-rabatt;
        }
    }
}
