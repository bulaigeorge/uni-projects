/*
    Lenkeliste som implementerer Comparable
*/

public class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {

    public SortertLenkeliste() {
        super();
    }


    @Override
    public void leggTil(T x) {
        Node newNode = new Node(x);
        Node start = head.next;

        if (super.stoerrelse() == 0) {
            super.leggTil(x);
        } else {
            for (int i = 0; i < super.stoerrelse(); i++) {
                if (x.compareTo(start.value) >= 0) {
                    start = start.next;
                }else{
                    break;
                }
            }
            newNode.next = start;
            newNode.prev = start.prev;

            start.prev.next = newNode;
            start.prev = newNode;
            size++;
        }


    }


    @Override
    public T fjern() {
        return super.fjern(super.stoerrelse()-1);
    }


    @Override
    public void sett(int pos, T x) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void leggTil(int pos, T x) {
        throw new UnsupportedOperationException();
    }


}
