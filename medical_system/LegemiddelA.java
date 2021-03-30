//Klasse som beskriver Legemiddel av type A

public class LegemiddelA extends Legemiddel {
    private int narkotiskStyrke;

    public LegemiddelA(String navn,double pris, double virkestoff,int narkotiskStyrke) {
        super(navn,pris,virkestoff);
        this.narkotiskStyrke = narkotiskStyrke;
    }


    public int hentNarkotiskStyrke() {
        return narkotiskStyrke;
    }


    @Override
    public String hentInfo() {
        return (super.hentInfo() + "\nNarkotiskStyrke: " + narkotiskStyrke);
    }
}
