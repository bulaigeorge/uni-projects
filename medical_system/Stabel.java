/*
    Stabel
*/

public class Stabel<T> extends Lenkeliste<T> {

    public Stabel() {
        super();
    }


    public void leggPaa(T x) {
        super.leggTil(x);
    }


    public T taAv() {
        return super.fjern(super.stoerrelse()-1);
    }


}
