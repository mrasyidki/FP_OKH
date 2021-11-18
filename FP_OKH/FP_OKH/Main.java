import java.util.Scanner;
import java.io.IOException;


public class Main
{
    static String fileName[][] = {{"car-s-91", "CAR91"}, {"car-f-92", "CAR92"}, {"ear-f-83", "EAR83"}, {"hec-s-92", "HEC92"}, {"kfu-s-93", "KFU93"}, {"lse-f-91", "LSE91"}, {"pur-s-93", "Pur93"}, {"rye-s-93", "RYE93"}, {"sta-f-83", "STA83"}, {"tre-s-92", "TRE92"}, {"uta-s-92", "UTA92"}, {"ute-s-92", "UTE92"}, {"yor-f-83", "YOR83"}};;
    static int[][] conflictMatrix, sortedCourse, weightedClashMatrix, sortedWeightedCourse;
    static int timeslot[];

    public static void main(String[] args) throws IOException
    {	//Menampilkan dataset
        System.out.println("");
        System.out.println("Dataset: ");

        for(int i=0; i< fileName.length; i++)
        {
            System.out.println(i+1 +". "+  fileName[i][1]);
        }
        //Memilih dataset yang ingin digunakan
        System.out.print("\nChoose dataset: ");
        Scanner input = new Scanner(System.in);

        long startTime = System.nanoTime();
        int dataset = input.nextInt();
        
        String selectedDataset = fileName[dataset-1][0];
        System.out.println("\n------------------------------------------------\n");

        CourseData course = new CourseData(selectedDataset);

        int courseTotal = course.getNumberOfCourses();
        conflictMatrix = new int[courseTotal][courseTotal];

        //memperoleh conflict matrix:
        conflictMatrix = course.getConflictMatrix();
        System.out.println(" ");


        //memperoleh hasil sorting largest degree:
        sortedCourse = course.sortByDegree(conflictMatrix, courseTotal);
        System.out.println("\n------------------------------------------------\n");

        //menjalankan scheduling (Largest Degree)
        ExamScheduling sch = new ExamScheduling(selectedDataset, conflictMatrix, courseTotal);
        timeslot = sch.scheduleByDegree(sortedCourse,courseTotal);

        //memeriksa apakah terdapat konflik pada schedule
        System.out.println(" ");
        System.out.println("Is there any conflict? : "+ sch.isConflicted());
        System.out.println("Penalty : "+Penalty.countPenalty(course.getStudentData(), timeslot));
        sch.getTimeslot();
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds : " +
                timeElapsed / 1000000);
    }
}
