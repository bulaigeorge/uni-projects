class BSTtest {

    public static int [] tallrekke( int antall ) {
        java.util.Random tilf = new java.util.Random(20102018);
        int[] tall = new int[antall];
        for ( int i=0; i<antall; i++) {
            tall[i] = tilf.nextInt();
        }
        return tall;
    }


    public static void main(String[] args) {

        int antall = Integer.parseInt(args[0]);

        int[] intarr = tallrekke(antall);
        BSTree testtre = new BSTree();

        if (antall == 0 ) {

	    System.out.println("n     antall     høyde");
            for (int ant=1; ant <= 100000000; ant=ant*10) {
                intarr = tallrekke(ant);
                for (int i : intarr) { testtre.add(i); }
                System.out.print(ant);
                System.out.print(" " + testtre.size());
                //System.out.println(" " + testtre.height());
            }

        }
        else {

            for (int i : intarr) { testtre.add(i); }
            //intarr = testtre.preorderArray();
            for (int i : intarr) { System.out.println(i); }
            intarr = testtre.sortedArray();
            System.out.println("Sorted :" + "\n");
            for (int i : intarr) { System.out.println(i); }
        }

    }
}
