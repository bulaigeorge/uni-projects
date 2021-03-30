public class Test {

    private static int antallTelegrafister;
    static int ANTALL_Kryptograf = 20;

    public static void main(String[] args) {

        //USE THIS TO RUN: javac -encoding UTF-8 *.java && java Test

        antallTelegrafister = Tekster.ANTALL_TEKSTER;

        Operasjonssentral ops = new Operasjonssentral(antallTelegrafister);
        Kanal[] kanaler = ops.hentKanalArray();

        Monitor kryptertMonitor = new Monitor();
        Monitor dekryptertMonitor = new Monitor();


        for (int i = 0; i < kanaler.length; i++){
            new Thread(new Telegrafist(kanaler[i], kryptertMonitor)).start();
        }
        for (int i = 0; i <ANTALL_Kryptograf; i++) {
            new Thread(new Kryptograf(kryptertMonitor, dekryptertMonitor)).start();
        }

        new Thread(new Operasjonsleder(dekryptertMonitor)).start();



    }
}
