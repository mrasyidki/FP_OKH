import java.util.Arrays;

public class ExamScheduling
{
    String fileInput;
    int[][] conflictMatrix;
    int[] timeslot;
    int  indexts;

    public ExamScheduling(String file, int[][] conflictMatrix, int totalExams)
    {
        this.conflictMatrix = conflictMatrix;
    }

    public static boolean isTimeslotAvailableSorted(int totalCourse, int indexts, int[][] courseSorted, int[][] conflictMatrix, int[] timeslot)
    {
        for(int i = 0; i < courseSorted.length; i++)
        {
            if(conflictMatrix[courseSorted[totalCourse][0]-1][i] != 0 && timeslot[i] == indexts)
                return false;
        }
        return true;
    }

    public int[] scheduleByDegree(int[][] courseSorted, int totalCourse)
    {
        this.timeslot = new int[totalCourse];
        indexts = 1;

        for(int i= 0; i < courseSorted.length; i++)
        {
            this.timeslot[i] = 0;
        }

        for(totalCourse = 0; totalCourse < courseSorted.length; totalCourse++)
        {
            for (int indexts = 1; indexts <= indexts; indexts++)
            {
                if(isTimeslotAvailableSorted(totalCourse, indexts, courseSorted, conflictMatrix, this.timeslot))
                {
                    this.timeslot[courseSorted[totalCourse][0]-1] = indexts;
                    break;
                }
            }
        }
        System.out.println("Timeslot Scheduling: ");
        for (int i = 0; i < totalCourse; i++)
            System.out.println("Timeslot untuk course "+ (i+1) + " adalah timeslot " + timeslot[i]);
        return this.timeslot;
    }


    public void getTimeslot()
    {
        // sort dengan largest degree (ascending):
        Arrays.sort(timeslot);
        System.out.print("");
        System.out.println("Minimal Timeslots: "+timeslot[timeslot.length-1]);


    }

    public boolean isConflicted(){
        for(int i = 0; i<this.timeslot.length; i++){
            for(int j=i; j<this.timeslot.length; j++){
                if(timeslot[i] == timeslot[j]){
                    int course1 = i;
                    int course2 = j;
                    if(this.conflictMatrix[course1][course2]==1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}