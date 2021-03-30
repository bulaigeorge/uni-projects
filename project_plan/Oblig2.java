import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Oblig2 {
    public static void main(String[] args) {
        //create project from file
        String fileName = null;

        if (args.length > 0) {
            fileName = args[0];
        } else {
            System.out.println("FEIL! Riktig bruk av programmet: "
                               +"java Oblig2 <projectName>.txt");
            return;
        }
        File fil = new File(fileName);
        Project project = null;
        try {
            project = Project.readFromFile(fil);
        } catch (FileNotFoundException e) {
            System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", fileName);
            System.exit(1);
        }


        project.checkCycle();
        project.setEarliestTime();
        project.setEarlyFinishTime();


        // project.setSlack();
        // project.setLatestTime();
        // project.setLateFinishTime();
        // project.adjustTasks();



        //project.setCritical();
        project.startProject();

        project.printToFile();

    }
}
