//Klasse som beskriver Legemiddel av type B

public class LegemiddelB extends Legemiddel {
    private int vanedannendeStyrke;

    public LegemiddelB(String navn,double pris, double virkestoff,int vanedannendeStyrke) {
        super(navn,pris,virkestoff);
        this.vanedannendeStyrke = vanedannendeStyrke;
    }


    public int hentVanedannendeStyrke() {
        return vanedannendeStyrke;
    }


    @Override
    public String hentInfo() {
        return (super.hentInfo() + "\nVanedannende styrke: " + vanedannendeStyrke);
    }
}
