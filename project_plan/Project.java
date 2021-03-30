import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.io.File;



public class Project {
    private static int nrTasks;
    private static Task[] taskList;
    private static Scanner data;

    public Project(int nrTasks, Task[] taskList) {
        this.nrTasks = nrTasks;
        this.taskList = taskList;
    }

    public static Project readFromFile(File fil) throws FileNotFoundException{
        data = new Scanner(fil);

        if (data != null) {
            String line = data.nextLine();
            nrTasks = Integer.parseInt(line);
            taskList = new Task[nrTasks];
            for(int i = 0; i < nrTasks; i++) {
                taskList[i] = new Task(i+1);
            }


            //fill the empty tasks that were created with information
            while (data.hasNextLine()) {
                String lineInfo = data.nextLine();
                if (lineInfo.equals("")) {
                    //do nothing
                }
                else {
                    String[] splitLine = lineInfo.split("\\s+");
                    int id = Integer.parseInt(splitLine[0]);
                    int time = Integer.parseInt(splitLine[2]);
                    int staff = Integer.parseInt(splitLine[3]);

                    taskList[id-1].fillTask(splitLine[1], time, staff);


                    //get the dependecies
                    int counter = 4;
                    while (!splitLine[counter].equals("0")) {
                        Task taskToAdd = taskList[id-1];
                        taskList[id-1].increasePredecessors();
                        //System.out.println(taskList[id-1].getId() + " has " + taskToAdd.returnPredecessors());
                        taskList[Integer.parseInt(splitLine[counter])-1].fillOutEdges(taskToAdd);
                        //System.out.println(taskList[Integer.parseInt(splitLine[counter])-1].getId() + " has the dependency of " + taskToAdd.getId());
                        counter++;
                    }
                }
            }
        }
        return new Project(nrTasks, taskList);
    }



    public void printTasks() {
        for (int i = 0; i < nrTasks; i++){
            System.out.println(taskList[i] + " id: " +
            taskList[i].getId());
        }
    }


    public void checkCycle() {
        for (Task task : taskList) {
            if (task.returnPredecessors() == 0) {
                //if it has no predecessors it cannot be part of the loop
                String cycle = "";
                task.checkCycle(cycle);
            }
        }
        System.out.println("Project realizable!");
    }


    public void setEarliestTime() {
        for (Task task : taskList) {
            if (task.returnPredecessors() == 0) {
                task.setEarliestTime(0);
                //System.out.println(task.getId() +" has the earliestStart at 0");
                for (Task t : task.getOutEdges()) {
                    t.setEarliestTime(task.getTime());
                    //System.out.println(t.getId() + " has the earliest Start at " + (task.getEarliest() + task.getTime()));
                }
            }
        }
        for (Task task : taskList) {
            for (Task t : task.getOutEdges()) {
                if (t.getEarliest() < task.getEarliest() + task.getTime()) {
                    t.setEarliestTime(task.getEarliest() + task.getTime());
                    //System.out.println(t.getId() + " has the earliest Start at " + (task.getEarliest() + task.getTime()));
                }
            }
        }
    }

    public void setLatestTime() {
        for (Task task : taskList) {
            if (task.getOutEdges().size() == 0) {
                task.setLatest(task.getEarliest());
            }
            for (Task t : task.getOutEdges()) {
                //
            }
        }
    }

    public void setEarlyFinishTime() {
        for (Task task : taskList) {
            if (task.returnPredecessors() == 0) {
                task.setEarlyFinish(task.getTime());
                //System.out.println(task.getEarlyFinish() +"early finish"+task.getId());
            }
            else {
                task.setEarlyFinish(task.getEarliest() + task.getTime());
                //System.out.println(task.getEarlyFinish() +"early finish" +task.getId());
            }
        }
    }

    public void setLateFinishTime() {
        for (Task task : taskList) {
            task.setLateFinish(task.getTime() + task.getLatest());
        }
    }


    public void setSlack() {
        for (Task task : taskList) {
            task.setSlack(task.getLatest() - task.getEarliest());
        }
    }


    //function that adjusts the slack and lateFinishTime of the tasks that
    //have no out edges. A variable that stores the latest finish time from all
    //tasks will be used. This method should be called after setSlack and setLateFinish.
    public void adjustTasks() {
        int endOfProject = -1;
        for (Task task : taskList) {
            if (task.getLateFinish() > endOfProject) {
                endOfProject = task.getLateFinish();
            }
        }

        for (Task task : taskList) {
            if (task.getOutEdges().size() == 0) {
                task.setLateFinish(endOfProject);
                task.setSlack(endOfProject - (task.getLatest() + task.getTime()));
                task.setLatest(task.getLatest() + task.getSlack());
            }
        }
    }

    public void setCritical() {
        for (Task task : taskList) {
            if (task.getSlack() == 0) {
                task.setCrit();
            }
        }
    }



    public void startProject() {
        int timeCounter = 0;
        int tasksFinished = 0;
        Task[] starting = new Task[nrTasks];
        Task[] finished = new Task[nrTasks];
        int currentStaff = 0;
        while(tasksFinished != nrTasks) {
            int startingCount = 0;
            int finishedCount = 0;
            for (Task task : taskList) {
                if (timeCounter == task.getEarliest()) {
                    starting[startingCount] = task;
                    startingCount++;
                }
                else if (timeCounter == task.getEarlyFinish()) {
                    finished[finishedCount] = task;
                    finishedCount++;
                    tasksFinished++;
                }
            }
            //lists of starting and finishing.
            if (starting[0] != null || finished[0] != null) {
                System.out.println("\n" + "Time :" + timeCounter);
                for (Task task : starting) {
                    if (task != null) {
                        System.out.println("Task " + task.getId() + " started.");
                        currentStaff += task.getStaff();
                    }
                }
                for (Task task : finished) {
                    if (task != null) {
                        System.out.println("Task " + task.getId() + " finished.");
                        currentStaff -= task.getStaff();
                    }
                }
                //empty arrays.
                starting = new Task[nrTasks];
                finished = new Task[nrTasks];
                System.out.println("Current Staff: " + currentStaff);
            }


            timeCounter++;
        }
        System.out.println("\n**** Shortest possible project execution is " + (timeCounter-1) +  " ****\n");

        for (Task task : taskList) {
            String dependencies = "";
            for (Task t : task.getOutEdges()) {
                dependencies += t.getId() + ",";
            }
            System.out.println("\n Id: " + task.getId() + "\n Name: " + task.getName() +
                                "\n Time: " + task.getTime() + "\n Staff: " + task.getStaff() +
                                "\n Earliest start: " + task.getEarliest() + "\n Latest start: " +
                                task.getLatest() + "\n Slack: " + task.getSlack() + "\n Dependencies : [" +
                                dependencies + "]");
        }
    }


    //I assume the the file
    //"output.txt" has the information about 1 project at the time.
    public void printToFile()  {

        File file = new File("output.txt");
        PrintWriter printWriter = null;
        try
        {
            printWriter = new PrintWriter(file);


            int timeCounter = 0;
            int tasksFinished = 0;
            Task[] starting = new Task[nrTasks];
            Task[] finished = new Task[nrTasks];
            int currentStaff = 0;
            while(tasksFinished != nrTasks) {
                int startingCount = 0;
                int finishedCount = 0;
                for (Task task : taskList) {
                    if (timeCounter == task.getEarliest()) {
                        starting[startingCount] = task;
                        startingCount++;
                    }
                    else if (timeCounter == task.getEarlyFinish()) {
                        finished[finishedCount] = task;
                        finishedCount++;
                        tasksFinished++;
                    }
                }
                //lists of starting and finishing.
                if (starting[0] != null || finished[0] != null) {
                    printWriter.println("\n" + "Time :" + timeCounter);
                    for (Task task : starting) {
                        if (task != null) {
                            printWriter.println("Task " + task.getId() + " started.");
                            currentStaff += task.getStaff();
                        }
                    }
                    for (Task task : finished) {
                        if (task != null) {
                            printWriter.println("Task " + task.getId() + " finished.");
                            currentStaff -= task.getStaff();
                        }
                    }
                    //empty arrays.
                    starting = new Task[nrTasks];
                    finished = new Task[nrTasks];
                    printWriter.println("Current Staff: " + currentStaff);
                }


                timeCounter++;
            }
            printWriter.println("\n**** Shortest possible project execution is " + (timeCounter-1) +  " ****\n");



            for (Task task : taskList) {
                String dependencies = "";
                for (Task t : task.getOutEdges()) {
                    dependencies += t.getId() + ",";
                }
                printWriter.println(" Id: " + task.getId() + "\n Name: " + task.getName() +
                                    " Time: " + task.getTime() + " Staff: " + task.getStaff() +
                                    " Earliest start: " + task.getEarliest() + " Latest start: " +
                                    task.getLatest() + " Slack: " + task.getSlack() + " Dependencies : [" +
                                    dependencies + "]");
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        printWriter.close();
    }





}
