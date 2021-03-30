import java.util.Scanner;
import java.io.*;



public class Test {
    private static Lenkeliste<Pasient> pasientListe;
    private static SortertLenkeliste<Lege> legeListe;
    private static Lenkeliste<Legemiddel> legemidlerListe;
    private static Lenkeliste<Resept> reseptListe;
    private static String outLoop = "", outA = "a", outB = "b", outLoopSub = "";
    private static String hovedMenyValg = "\nTast inn et tall eller 'a' for aa avslutte: ";
    private static String subMenyValg = "\nTast inn et tall eller 'b' for aa gaa tilbake: ";
    private static Lenkeliste<String> hovedM, infoMenu, leggTilMenu, statistikkMenu;
    private static Scanner keyboard = new Scanner(System.in);
    private static Scanner data;
    private static int pos = 0;
    private static Legemiddel legemiddelDATA;
    private static Lege legeDATA;
    private static Pasient pasientDATA;
    private static FileWriter skriv;



    public static void main(String[] args) {

        pasientListe = new Lenkeliste<Pasient>();
        legemidlerListe = new Lenkeliste<Legemiddel>();
        legeListe = new SortertLenkeliste<Lege>();
        reseptListe = new Lenkeliste<Resept>();

        try{
            System.out.println("\nGi et filnavn:");
            data = new Scanner(new File(keyboard.next()));
        } catch (FileNotFoundException e) {
            System.out.println("Filen finnes ikke!\n");
        }

        if (data != null) {
            while(data.hasNextLine()) {
                //System.out.println(data.nextLine());
                String linje = data.nextLine();
                if (linje.startsWith("#")) {
                    pos += 1;
                    //System.out.println(pos);
                } else {
                    String[] parameter = linje.split(",");
                    if (pos == 1) {
                        String navnP = parameter[0];
                        String fnrP = parameter[1];
                        Pasient pasient = new Pasient(navnP, fnrP);
                        pasientListe.leggTil(pasient);
                    } else if (pos == 2) {
                        //type C only 4 parameters
                        if(parameter.length == 4) {
                            String navnLm = parameter[0];
                            double prisLm = Double.parseDouble(parameter[2]);
                            double virkestoffLm = Double.parseDouble(parameter[3]);
                            Legemiddel lmC = new LegemiddelC(navnLm, prisLm, virkestoffLm);
                            legemidlerListe.leggTil(lmC);
                            //System.out.println("Legemiddel C " + lmC + " ble lagt inn i liste");
                        } else {
                            String navnLm = parameter[0];
                            double prisLm = Double.parseDouble(parameter[2]);
                            double virkestoffLm = Double.parseDouble(parameter[3]);
                            String styrkeLm = parameter[4];
                            //cut space
                            String s = styrkeLm.replaceAll(" ","");
                            //make int
                            int styrke = Integer.parseInt(s);
                            String typeLm = parameter[1];
                            if (typeLm.equals(" a")) {
                                Legemiddel lmA = new LegemiddelA(navnLm, prisLm, virkestoffLm, styrke);
                                legemidlerListe.leggTil(lmA);
                                //System.out.println("Legemiddel A " + lmA + " ble lagt inn i liste");
                            } else if (typeLm.equals(" b")) {
                                Legemiddel lmB = new LegemiddelB(navnLm, prisLm, virkestoffLm, styrke);
                                legemidlerListe.leggTil(lmB);
                                //System.out.println("Legemiddel B " + lmB + " ble lagt inn i liste");
                            }
                        }
                    } else if (pos == 3) {
                        String navnL = parameter[0];
                        String avtN = parameter[1];
                        String a = avtN.replaceAll(" ","");
                        int avt = Integer.parseInt(a);
                        if (avt == 0) {
                            Lege lege = new Lege(navnL);
                            legeListe.leggTil(lege);
                        } else {
                            Lege fastLege = new FastLege(navnL, avt);
                            legeListe.leggTil(fastLege);
                        }
                    } else if (pos == 4) {
                        //get reseptType, legemiddelID, legeNavn, pasientID
                        String reseptT = parameter[0];
                        int lmID = Integer.parseInt(parameter[1].replaceAll(" ",""));
                        String legeNavn = parameter[2].replaceFirst(" ","");
                        int personID = Integer.parseInt(parameter[3].replaceAll(" ",""));
                        //prevensjon
                        if (parameter.length == 4) {
                            // private static Legemiddel legemiddelDATA;
                            // private static Lege legeDATA;
                            // private static Pasient pasientDATA;
                            for(Legemiddel lm : legemidlerListe) {
                                if (lm.hentId() == lmID) {
                                    legemiddelDATA = lm;
                                }
                            }
                            for(Lege l : legeListe) {
                                String lNavn = l.toString();
                                if (lNavn.equals(legeNavn)) {
                                    legeDATA = l;
                                }
                            }
                            for(Pasient p : pasientListe) {
                                if (p.hentId() == personID) {
                                    pasientDATA = p;
                                }
                            }
                            PResept pres = new PResept(legemiddelDATA, legeDATA, pasientDATA);
                            reseptListe.leggTil(pres);
                            //System.out.println("resept " + pres + " ble lagt til liste");
                        } else {
                            int reitDATA = Integer.parseInt(parameter[4].replaceAll(" ",""));
                            for(Legemiddel lm : legemidlerListe) {
                                if (lm.hentId() == lmID) {
                                    legemiddelDATA = lm;
                                }
                            }
                            for(Lege l : legeListe) {
                                String lNavn = l.toString();
                                if (lNavn.equals(legeNavn)) {
                                    legeDATA = l;
                                }
                            }
                            for(Pasient p : pasientListe) {
                                if (p.hentId() == personID) {
                                    pasientDATA = p;
                                }
                            }
                            if (reseptT.equals("blaa")) {
                                BlaaResept blaa = new BlaaResept(legemiddelDATA, legeDATA, pasientDATA, reitDATA);
                                reseptListe.leggTil(blaa);
                            }
                            else if (reseptT.equals("hvit")) {
                                HvitResept hvit = new HvitResept(legemiddelDATA, legeDATA, pasientDATA, reitDATA);
                                reseptListe.leggTil(hvit);
                            }
                            else if (reseptT.equals("militaer")) {
                                MillitaerResept mill = new MillitaerResept(legemiddelDATA, legeDATA, pasientDATA, reitDATA);
                                reseptListe.leggTil(mill);
                            }
                        }
                    }
                }
            }
        }




        // Pasient karl = new Pasient("Karl", "12345678901");
        // Pasient john = new Pasient("John", "01234324231");
        // Pasient mike = new Pasient("Mike", "07645433424");
        // pasientListe = new Lenkeliste<Pasient>();
        // pasientListe.leggTil(karl);
        // pasientListe.leggTil(john);
        // pasientListe.leggTil(mike);



        // Legemiddel medA = new LegemiddelA("Paraset", 200.0, 10.0, 5);
        // Legemiddel medB = new LegemiddelB("Not Paraset", 14.0, 25.0, 20);
        // Legemiddel medC = new LegemiddelC("Still Not Paraset", 200.0, 4.0);
        // legemidlerListe = new Lenkeliste<Legemiddel>();
        // legemidlerListe.leggTil(medA);
        // legemidlerListe.leggTil(medB);
        // legemidlerListe.leggTil(medC);


        // Lege lege = new FastLege("Dr. Paus",1234567);
        // Lege lege1 = new Lege("Dr. Ueland");
        // Lege lege2 = new Lege("Dr. Not Paus");
        // legeListe = new SortertLenkeliste<Lege>();
        // legeListe.leggTil(lege);
        // legeListe.leggTil(lege1);
        // legeListe.leggTil(lege2);


        // BlaaResept bla = new BlaaResept(medA, lege, mike, 2);
        // MillitaerResept mill = new MillitaerResept(medB, lege1, john, 2);
        // MillitaerResept mill1 = new MillitaerResept(medB, lege1, john, 2);
        // PResept pres = new PResept(medB, lege1, karl);
        // HvitResept hvit = new HvitResept(medA, lege, mike, 1);
        // reseptListe = new Lenkeliste<Resept>();
        // reseptListe.leggTil(bla);
        // reseptListe.leggTil(mill);
        // reseptListe.leggTil(mill1);
        // reseptListe.leggTil(pres);
        // reseptListe.leggTil(hvit);

        //create Menu
        hovedM = new Lenkeliste<String>();
        hovedM.leggTil("Informasjon");
        hovedM.leggTil("Legg til");
        hovedM.leggTil("Bruk");
        hovedM.leggTil("Statistikk");
        hovedM.leggTil("Skriv til fil");

        //create infoMenu
        infoMenu = new Lenkeliste<String>();
        infoMenu.leggTil("Pasienter");
        infoMenu.leggTil("Leger");
        infoMenu.leggTil("Resepter");
        infoMenu.leggTil("Legemidler");

        //create leggTilMenu
        leggTilMenu = new Lenkeliste<String>();
        leggTilMenu.leggTil("Pasient");
        leggTilMenu.leggTil("Lege");
        leggTilMenu.leggTil("Resept");
        leggTilMenu.leggTil("Legemidler");

        //create StatistikkMenu
        statistikkMenu = new Lenkeliste<String>();
        statistikkMenu.leggTil("Totalt antall utskrevne resepter paa vanedannende legemidler");
        statistikkMenu.leggTil("Totalt antall vanedannende resepter utskrevne til militaer");
        statistikkMenu.leggTil("Statistikk om mulig misbruk av narkotika");


        System.out.println("\n\n\n");
        menu();


    }



    static void writeFile() {
        System.out.println("\nHovedmeny:\n[...]\n\n\nSkriv ny filnavn:");
        String filnavn = keyboard.next();
        try {
            skriv = new FileWriter(filnavn);
            BufferedWriter writer = new BufferedWriter(skriv);
            writer.write("# Pasienter (navn, fnr)");
            writer.newLine();
            for (Pasient p : pasientListe) {
                String navn = p.toString();
                String fnr = p.hentFnr();
                writer.write(navn + "," + fnr);
                writer.newLine();
            }
            writer.write("# Legemidler (navn, type, pris, virkestoff [, styrke])");
            writer.newLine();
            for (Legemiddel lm : legemidlerListe) {
                String navn = lm.hentNavn();
                //cast double into int, then make it string
                String pris = (int)lm.hentPris() + "";
                String virkestoff = (int)lm.hentVirkestoff() + "";
                if (lm instanceof LegemiddelC) {
                    String type = "c";
                    writer.write(navn + ", " + type + ", " + pris + ", " + virkestoff);
                    writer.newLine();
                }
                else if (lm instanceof LegemiddelA) {
                    LegemiddelA lmA = (LegemiddelA) lm;
                    String type = "a";
                    String styrke = lmA.hentNarkotiskStyrke() + "";
                    writer.write(navn + ", " + type + ", " + pris + ", " + virkestoff + ", " + styrke);
                    writer.newLine();
                }
                else if (lm instanceof LegemiddelB) {
                    LegemiddelB lmB = (LegemiddelB) lm;
                    String type = "b";
                    String styrke = lmB.hentVanedannendeStyrke() + "";
                    writer.write(navn + ", " + type + ", " + pris + ", " + virkestoff + ", " + styrke);
                    writer.newLine();
                }
            }
            writer.write("# Leger (navn, avtalenr / 0 hvis ingen avtale)");
            writer.newLine();
            for (Lege l : legeListe) {
                String navn = l.toString();
                if (l instanceof FastLege) {
                    FastLege fl = (FastLege) l;
                    String avt = fl.hentAvtalenummer() + "";
                    writer.write(navn + ", " + avt);
                    writer.newLine();
                } else {
                    writer.write(navn + ", 0");
                    writer.newLine();
                }
            }
            writer.write("# Resepter (type, legemiddelNummer, legeNavn, persID, [reit])");
            writer.newLine();
            for (Resept r : reseptListe) {
                String lmNummer = r.hentLegemiddel().hentId() + "";
                String lege = r.hentLege().toString();
                String persID = r.hentPasient().hentId() + "";
                if (r instanceof BlaaResept) {
                    BlaaResept blaa = (BlaaResept) r;
                    String type = blaa.farge();
                    String reit = blaa.hentReit() + "";
                    writer.write(type + ", " + lmNummer + ", " + lege + ", " + persID + ", " + reit);
                    writer.newLine();
                }
                else if (r instanceof HvitResept) {
                    HvitResept hvit = (HvitResept) r;
                    if (hvit instanceof PResept) {
                        String type = "prevensjon";
                        writer.write(type + ", " + lmNummer + ", " + lege + ", " + persID);
                        writer.newLine();
                    }
                    else if (hvit instanceof MillitaerResept) {
                        String type = "militaer";
                        String reit = hvit.hentReit() + "";
                        writer.write(type + ", " + lmNummer + ", " + lege + ", " + persID + ", " + reit);
                        writer.newLine();
                    }
                    else {
                        String type = hvit.farge();
                        String reit = hvit.hentReit() + "";
                        writer.write(type + ", " + lmNummer + ", " + lege + ", " + persID + ", " + reit);
                        writer.newLine();
                    }
                }
            }


            writer.close();
            System.out.println("En ny fil ble opprettet\n\n");
        } catch (IOException e) {
            System.out.println("noe galt skjer");
        }
    }




    static void menu() {
        String velg = "";
        //Scanner keyboard = new Scanner(System.in);
        while(!outLoop.equals(outA)){
            System.out.println("Hovedmeny:");
            printMenu(hovedM);
            System.out.println(hovedMenyValg);
            velg = keyboard.next();
            if(velg.equals(outA)) {
                outLoop = outA;
            } else {
                //make it into int
                int velgInt = Integer.parseInt(velg);
                if(velgInt == 0) {loopOverInfo();}
                else if(velgInt == 1) {loopOverLeggtil();}
                else if(velgInt == 2) {loopOverBruk();}
                else if(velgInt == 3) {loopOverStatistikk();}
                else if(velgInt == 4) {writeFile();}
            }
        }
    }



    //menu Info
    static void loopOverInfo() {
        outLoopSub = "";
        while(! outLoopSub.equals(outB)) {
            System.out.println("\nHovedmeny:\n[...]");
            printMenu(infoMenu);
            System.out.println(subMenyValg);
            String velg = keyboard.next();
            //go back if "b" is tasted
            if(velg.equals(outB)) {
                outLoopSub = outB;
            } else {
                //make it into int
                int velgInt = Integer.parseInt(velg);
                if (velgInt == 0) {printPasienter();}
                else if (velgInt == 1) {printLeger();}
                else if (velgInt == 2) {printResepter();}
                else if (velgInt == 3) {printLegemidler();}
            }
        }
    }


    //menu LeggTil
    static void loopOverLeggtil() {
        outLoopSub = "";
        while(! outLoopSub.equals(outB)) {
            System.out.println("\nHovedmeny:\n[...]");
            printMenu(leggTilMenu);
            System.out.println(subMenyValg);
            String velg = keyboard.next();
            //go b if b is tasted
            if(velg.equals(outB)) {
                outLoopSub = outB;
            } else {
                int velgInt = Integer.parseInt(velg);
                if (velgInt == 0) {
                    Pasient pasient = createPasient();
                    pasientListe.leggTil(pasient);
                } else if (velgInt == 1) {
                    Lege lege = createLege();
                    legeListe.leggTil(lege);
                } else if (velgInt == 2) {
                    Resept resept = getResept();
                    reseptListe.leggTil(resept);
                } else if (velgInt == 3) {
                    Legemiddel legemiddel = getLegemiddel();
                    legemidlerListe.leggTil(legemiddel);
                }
            }
        }
    }



    //menu bruk
    static void loopOverBruk() {
        outLoopSub = "";
        while(! outLoopSub.equals(outB)) {
            System.out.println("\nHovedmeny:\n[...]");
            System.out.println("\nHvilken pasient vil du se resepter for?");
            System.out.println(subMenyValg);
            printPasienter();
            String velgStr = keyboard.next();
            if(velgStr.equals(outB)) {outLoopSub = outB;}
            else {
                int velg = Integer.parseInt(velgStr);
                System.out.println("\nValgt pasient: " + pasientListe.hent(velg));
                System.out.println("Hvilken resept vil du bruke?");
                Stabel<Resept> resepter = pasientListe.hent(velg).hentReseptList();
                if(resepter.stoerrelse() == 0){
                    System.out.println("Pasienten har ingen resepter!");
                    return;
                }
                int i = 0;
                for(Resept r : resepter) {
                    System.out.println(i++ + ": " + r.hentLegemiddel().hentNavn() +"(" + r.hentReit() + " reit)");
                } velg = keyboard.nextInt();
                resepter.hent(velg).bruk();
            }
        }
    }


    //menu Statistikk
    static void loopOverStatistikk() {
        outLoopSub = "";
        while(! outLoopSub.equals(outB)) {
            System.out.println("\nHovedmeny:\n[...]");
            printMenu(statistikkMenu);
            System.out.println(subMenyValg);
            String velgStr = keyboard.next();
            if(velgStr.equals(outB)) {outLoopSub = outB;}
            else {
                int velg = Integer.parseInt(velgStr);

                if (velg == 0) {
                    int antall = 0;
                    for(Legemiddel l : legemidlerListe) {
                        if(l instanceof LegemiddelB) {
                            antall++;
                        }
                    } System.out.println("Antall utskrevne resepter paa vanedannende legemidler: " + antall);

                } else if (velg == 1) {
                    int antall = 0;
                    for(Resept r : reseptListe) {
                        //get MillitaerResept
                        if(r instanceof MillitaerResept) {
                            //sjekk hvis det er type B
                            if(r.hentLegemiddel() instanceof LegemiddelB) {
                                antall++;
                            }
                        }
                    } System.out.println("Antall vanedannende resepter utskrevne til militaer: " + antall);

                } else if (velg == 2) {
                    System.out.println("\nLeger som har skrevet ut minst en resept paa narkotiske legemidler:");
                    for(Lege lege : legeListe) {
                        int antall = 0;
                        //get list av resepter
                        Lenkeliste<Resept> resepter = lege.hentReseptList();
                        for(Resept r : resepter) {
                            if(r.hentLegemiddel() instanceof LegemiddelA) {
                                antall++;
                            }
                        } if(antall > 0) {System.out.println("Lege: " + lege + ", antall resepter: " + antall);}
                    }
                    System.out.println("\nPasienter som har minst en gyldig resept paa narkotiske legemidler:");
                    for(Pasient p : pasientListe) {
                        int antall = 0;
                        //get list av resepter for pasient
                        Stabel<Resept> resepter = p.hentReseptList();
                        for(Resept r : resepter) {
                            if(r.hentLegemiddel() instanceof LegemiddelA && r.hentReit() > 0) {
                                antall++;
                            }
                        }if (antall > 0) {System.out.println("Pasient: " + p + ", antall resepter: " + antall);}
                    }
                }
            }
        }
    }



    static Pasient createPasient() {
        System.out.println("\nNavn pasient:");
        String navn = keyboard.next();
        navn += keyboard.nextLine();
        System.out.println("\nFodelsnummer pasient:");
        String fod = keyboard.next();
        Pasient pasient = new Pasient(navn, fod);
        return pasient;
    }


    static Lege createLege() {
        System.out.println("\nVelg lege type:\n\n0. Lege\n1. Fastlege");
        String velgStr = keyboard.next();
        int velg = Integer.parseInt(velgStr);
        if (velg == 0) {
            System.out.println("\nNavn lege:");
            String navn = "Dr. " + keyboard.next();
            navn += keyboard.nextLine();
            Lege lege = new Lege(navn);
            return lege;
        } else if(velg == 1) {
            System.out.println("\nNavn lege:");
            String navn = "Dr. " + keyboard.next();
            navn += keyboard.nextLine();
            System.out.println("\nAvtalenummer lege:");
            int avtalenummer = Integer.parseInt(keyboard.next());
            Lege lege = new FastLege(navn, avtalenummer);
            return lege;
        } return new Lege("FEIL");
    }



    static Resept getResept() {
        System.out.println("\nVelg resept type:\n\n0. Blaa\n1. Hvit\n2. Prevensjon\n3. Militaer");
        String velgStr = keyboard.next();
        int velg = Integer.parseInt(velgStr);

        // create BLAA
        if (velg == 0) {return createResept("blaa");}
        else if (velg == 1) {return createResept("hvit");}
        else if (velg == 2) {return createResept("pres");}
        else if (velg == 3) {return createResept("militaer");}

        return new BlaaResept(new LegemiddelA("n",1,3,10), new Lege("n"), new Pasient("a","12"), 0);
    }




    static Resept createResept(String reseptType) {
        //get legemiddel.
        System.out.println("\nVelg en legemiddel:");
        for(int i = 0; i < legemidlerListe.stoerrelse();i++) {
            System.out.println(i + ". " + legemidlerListe.hent(i));
        }
        int velgI = keyboard.nextInt();
        Legemiddel legemiddel = legemidlerListe.hent(velgI);

        //get lege.
        System.out.println("\nVelg en lege:");
        for(int i = 0; i < legeListe.stoerrelse();i++) {
            System.out.println(i + ". " + legeListe.hent(i));
        }
        velgI = keyboard.nextInt();
        Lege lege = legeListe.hent(velgI);

        //get pasient.
        System.out.println("\nVelg pasient:");
        for(int i = 0; i < pasientListe.stoerrelse();i++) {
            System.out.println(i + ". " + pasientListe.hent(i));
        }
        velgI = keyboard.nextInt();
        Pasient pasient = pasientListe.hent(velgI);


        //create resept med bestempt type
        if (reseptType.equals("blaa")) {
            //get reit
            System.out.println("\nAntall reit:");
            int reit = keyboard.nextInt();
            Resept blaa = new BlaaResept(legemiddel,lege,pasient,reit);
            return blaa;
        } else if (reseptType.equals("hvit")) {
            //get reit
            System.out.println("\nAntall reit:");
            int reit = keyboard.nextInt();
            Resept hvit = new HvitResept(legemiddel,lege,pasient,reit);
            return hvit;
        } else if (reseptType.equals("pres")) {
            Resept pres = new PResept(legemiddel,lege,pasient);
            return pres;
        } else if (reseptType.equals("militaer")) {
            //get reit
            System.out.println("\nAntall reit:");
            int reit = keyboard.nextInt();
            Resept mill = new MillitaerResept(legemiddel,lege,pasient,reit);
            return mill;
        }



        return new BlaaResept(new LegemiddelA("n",1,3,10), new Lege("n"), new Pasient("a","12"), 0);
    }


    static Legemiddel getLegemiddel() {
        System.out.println("\nVelg legemiddel type:\n0. A\n1. B\n2. C");
        String velgStr = keyboard.next();
        int velg = Integer.parseInt(velgStr);

        // create legemiddel
        if (velg == 0) {return createLegemiddel("A");}
        else if (velg == 1) {return createLegemiddel("B");}
        else if (velg == 2) {return createLegemiddel("C");}

        return new LegemiddelC("a",20.0,0.0);
    }


    static Legemiddel createLegemiddel(String legemiddelType) {
        //get navn
        System.out.println("Navn legemiddel:");
        String navn = keyboard.next();
        navn += keyboard.nextLine();

        //get pris
        System.out.println("Pris:");
        double pris = Double.parseDouble(keyboard.next());

        //get pasient.
        System.out.println("Virkestoff (mg):");
        double virkestoff = Double.parseDouble(keyboard.next());



        //create legemiddel med bestempt type
        if (legemiddelType.equals("A")) {
            //get legemiddel a
            System.out.println("Narkotisk styrke:");
            int narko = keyboard.nextInt();
            Legemiddel a = new LegemiddelA(navn,pris,virkestoff,narko);
            return a;
        } else if (legemiddelType.equals("B")) {
            //get legemiddel b
            System.out.println("Vanedannende styrke:");
            int vann = keyboard.nextInt();
            Legemiddel b = new LegemiddelB(navn,pris,virkestoff,vann);
            return b;
        } else if (legemiddelType.equals("C")) {
            //get legemiddel c
            Legemiddel c = new LegemiddelC(navn,pris,virkestoff);
            return c;
        }
        return new LegemiddelC("a",20.0,0.0);
    }



    static void printPasienter() {
        System.out.println();
        for(Pasient p : pasientListe) {
            System.out.println(p.hentId() + ". Pasient: " + p +
                                "(fnr " + p.hentFnr() + ")");
        }
    }


    static void printLeger() {
        System.out.println();
        for(Lege l : legeListe) {
            if (l.hentAvtalenummer() < 1) {
                System.out.println("Lege: " + l);
            }
            else if (l.hentAvtalenummer() > 0) {
                System.out.println("Lege: " + l + " (avtalenummer " + l.hentAvtalenummer() + ")");
            }
        }
    }


    static void printLegemidler() {
        System.out.println();
        for(Legemiddel lm : legemidlerListe) {
            System.out.println(lm.hentInfo());
        }
    }


    static void printResepter() {
        System.out.println();
        for(Resept r : reseptListe) {
            System.out.println(r.hentInfo());
        }
    }


    static void printMenu(Lenkeliste liste) {
        int i = 0;
        System.out.println();
        for(Object s : liste) {
            System.out.println(i + ". " + s);
            i++;
        }
    }


}
