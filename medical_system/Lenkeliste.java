/*
    Lenkeliste som implementers Liste interface og er dobbeltlenket.
*/
import java.util.Iterator;

public class Lenkeliste<T> implements Liste<T> {
    protected Node head;
    protected Node tail;
    protected int size;


    protected class Node {
        T value;
        Node next;
        Node prev;

        Node(T value) {
            this.value = value;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LenkelisteIterator();
    }


    private class LenkelisteIterator implements Iterator<T> {
        private Node currNode = head.next;

        public boolean hasNext() {
            return currNode != tail;
        }


        public T next() {
            T element = currNode.value;
            currNode = currNode.next;
            return element;
        }
    }


    public Lenkeliste() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;
    }


    @Override
    public int stoerrelse() {
        return size;
    }


    @Override
    public void leggTil(int pos, T x) {
        if ( pos > size || pos < 0) {
            throw new UgyldigListeIndeks(pos);
        }
        Node newNode = new Node(x);
        Node tmp = head.next;
        //hvis listen er tom
        if (size == 0) {
            newNode.next = tail;
            newNode.prev = tail.prev;
            tail.prev.next = newNode;
            tail.prev = newNode;
            size++;
        } else {
            for (int i = 0; i < size+1; i++) {
                if (pos > size) {
                    throw new UgyldigListeIndeks(pos);
                }
                if (i == pos) {
                    newNode.next = tmp;
                    newNode.prev = tmp.prev;

                    tmp.prev.next = newNode;
                    tmp.prev = newNode;
                    size++;
                } else {
                    tmp = tmp.next;
                }
            }
        }
    }

    @Override
    public void leggTil(T x) {
        Node newNode = new Node(x);

        newNode.next = tail;
        newNode.prev = tail.prev;

        tail.prev.next = newNode;
        tail.prev = newNode;
        size++;
    }


    @Override
    public void sett(int pos, T x) {
        if (pos >= size || pos < 0) {
            throw new UgyldigListeIndeks(0);
        }
        Node newNode = new Node(x);
        Node tmp = head.next;
        for (int i = 0; i < size; i++) {
            if (i == pos) {
                newNode.prev = tmp.prev;
                newNode.next = tmp.next;

                tmp.prev.next = newNode;
                tmp.next.prev = newNode;
            } else {
                tmp = tmp.next;
            }
        }
    }


    @Override
    public T hent(int pos) {
        if ((size == 0 && pos == 0) || pos >= size || pos < 0) {
            throw new UgyldigListeIndeks(0);
        }
        Node getNode = head.next;
        for (int i = 0; i < pos; i++) {
            getNode = getNode.next;
        }
        return getNode.value;
    }


    @Override
    public T fjern(int pos) {
        if (pos >= size || size == 0 || pos < 0) {
            throw new UgyldigListeIndeks(0);
        }
        Node delNode = head.next;
        for (int i = 0; i < size; i++) {
            if (i == pos) {
                delNode.prev.next = delNode.next;
                delNode.next.prev = delNode.prev;
                size--;
                return delNode.value;
            } else {
                delNode = delNode.next;
            }
        } return delNode.value;
    }


    @Override
    public T fjern() {
        if (size == 0) {
            throw new UgyldigListeIndeks(0);
        }
        Node delNode = head.next;
        delNode.prev.next = delNode.next;
        delNode.next.prev = delNode.prev;
        size--;
        return delNode.value;
    }


    @Override
    public String toString() {
        if (size == 0){
            return "[]";
        }
        String s = "[";
        for (Node curr = head.next; curr != tail; curr = curr.next) {
            s += curr.value + ", ";
        }
    return s.substring(0, s.length() - 2) + "]";
  }

}
