//import java.util.List;
import java.util.ArrayList;

public class Task {
    private int id, time, staff, slack;
    private String name;

    private int earliestStart, latestStart = -1, earlyFinishTime, lateFinishTime;
    private ArrayList<Task> outEdges;
    private int cntPredecessors;
    private boolean visited = false;
    private boolean critical = false;


    public Task(int id) {
        this.id = id;
        outEdges = new ArrayList<Task>();
    }

    public void fillTask(String name, int time, int staff) {
        this.name = name;
        this.time = time;
        this.staff = staff;
    }

    public int getStaff() {
        return staff;
    }

    public void setCrit() {
        critical = true;
    }

    public String getName() {
        return name;
    }

    public void setEarlyFinish(int finish) {
        earlyFinishTime = finish;
    }

    public void setLateFinish(int lateFinish) {
        lateFinishTime = lateFinish;
    }

    public int getEarlyFinish() {
        return earlyFinishTime;
    }

    public int getLateFinish() {
        return lateFinishTime;
    }

    public int getSlack() {
        return slack;
    }

    public void setSlack(int newSlack) {
        slack = newSlack;
    }

    public void setEarliestTime(int earlyTime) {
        earliestStart = earlyTime;
    }

    public int getEarliest() {
        return earliestStart;
    }

    public void setLatest(int latest) {
        latestStart = latest;
    }

    public int getLatest() {
        return latestStart;
    }

    public int getTime() {
        return time;
    }

    public void increasePredecessors() {
        cntPredecessors++;
    }

    public boolean visitedCheck() {
        return visited;
    }

    public int returnPredecessors() {
        return cntPredecessors;
    }

    public void fillOutEdges(Task task) {
        outEdges.add(task);
    }

    public void printName() {
        System.out.println(name + " ");
    }

    public int getId() {
        return id;
    }

    public ArrayList<Task> getOutEdges() {
        return outEdges;
    }



    public void checkCycle(String cycle) {
        visited = true;
        for (Task task : outEdges) {
            if (task.outEdges.size() == 0) {
                //cannot be part of the loop
            }
            else if (task.visitedCheck() == false) {
                cycle += task.getId() + " ";
                task.checkCycle(cycle);
                //if it gets out of the call above it means that the rest is
                //a dead end and cannot be a part of the loop.
                cycle = cycle.replace(task.getId() + " ", "");

            }
            else if (task.visitedCheck() == true){
                cycle += task.getId() + " ";
                System.out.println("The program will shut down!");
                System.out.println("Loop found: " + cycle);
                //end program
                System.exit(1);
            }
        }
        visited = false;
    }


}
