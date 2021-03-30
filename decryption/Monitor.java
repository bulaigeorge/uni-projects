import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Monitor {
    private ArrayList<Melding> meldingerListe;
    private Lock lock = new ReentrantLock();
    private int antall;
    private int total, countedTelegrafister, counted, countedKryptograf;
    private Melding melding, nymeld;

    private Condition kanTa = lock.newCondition();
    //private Condition leggMelding = lock.newCondition();
    private Condition startOP = lock.newCondition();

    public Monitor() {
        this.meldingerListe = new ArrayList<Melding>();
    }


    int antallProdusenter;
    public Monitor(int antallProdusenter){
        this.antallProdusenter = antallProdusenter;
    }



    public void leggTil(Melding melding) {
        lock.lock();
        try {

            if(melding == null){
                antallProdusenter--;
            }{
                // while (antall > 0)
                //     leggMelding.await();
                meldingerListe.add(melding);
                counted++;
                antall++;
                if (antall > 0)
                    kanTa.signalAll();
            }



        }/* catch (InterruptedException e) {
        }*/ finally {
            lock.unlock();
        }
    }


    public void ferdigTelegrafist() {
        lock.lock();
        try {
            countedTelegrafister++;
        } finally {
            lock.unlock();
        }
    }


    public int getCountedKryptograf() {
        return countedKryptograf;
    }


    public void ferdigKryptograf() {
        lock.lock();
        try {
            countedKryptograf++;
        } finally {
            lock.unlock();
        }
    }


    public Melding hentDekryptertMelding() {
        lock.lock();
        if (countedKryptograf == Test.ANTALL_Kryptograf && (total == counted)) {nymeld = null;}
        //System.out.println(total + " " + counted);
        try {
            //as long as there is no message to be taken and krytografs are not done yet!
            while (antall == 0 && countedKryptograf != Test.ANTALL_Kryptograf) {
                kanTa.await();
            }
            nymeld = meldingerListe.get(total++);
            antall--;
            // if (countedKryptograf == Test.ANTALL_Kryptograf && (total == counted)) {
            //     nymeld = null;
            //     //System.out.println("aici return null");
            // }
        } catch (InterruptedException e) {
            System.out.println("Got interrupted while trying to get a messsage");
        } finally {
            lock.unlock();
            return nymeld;
       }
    }


    public Melding hentMelding() {
        lock.lock();
        if (countedTelegrafister == Tekster.ANTALL_TEKSTER && (total == counted)) {
            melding = null;
            // System.out.println(total);
        }
        try {
            //as long as there is no message to be taken and telegrafists are not done yet!
            while (antall == 0 && countedTelegrafister != Tekster.ANTALL_TEKSTER) {
                kanTa.await();
            }

            if(meldingerListe.size() == 0 && antallProdusenter == 0){
                return null;
            }


            melding = meldingerListe.get(total++);
            antall--;
            // System.out.println(counted + "Counted " + total + " Total");
            // System.out.println("ANTALL KRYPTO FRA TEST: " + Test.ANTALL_Kryptograf);
            // if (countedTelegrafister == Tekster.ANTALL_TEKSTER && (total == counted)) {
            //     melding = null;
            //     // System.out.println(total);
            // }
            // if (antall == 0)
            //     leggMelding.signal();
        } catch (InterruptedException e) {
            System.out.println("Got interrupted while trying to get a messsage");
        } finally {
            lock.unlock();
            return melding;
        }
    }



    public int antallMeldinger() {
        return meldingerListe.size();
    }




    public boolean sjekk() {
        return meldingerListe.isEmpty();
    }

    public void printListe() {
        for (Melding m : meldingerListe) {
            System.out.println(m);
        }
    }

}
