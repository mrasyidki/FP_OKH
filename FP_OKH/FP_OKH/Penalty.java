import java.util.ArrayList;

public class Penalty
{

    public static double countPenalty(ArrayList<String> student, int[] timeslot){
        double penalty = 0;

        //memeriksa course yang setiap student ambil
        for (String s : student) {
            String[] courseTaken = s.split(" ");
            if (courseTaken.length > 1) {
                for (int i = 0; i < courseTaken.length; i++) {
                    for (int j = 0; j < courseTaken.length; j++) {
                        if (i != j) {
                            int timeslotCourse1 = timeslot[i];
                            int timeslotCourse2 = timeslot[j];
                            //menghitung jarak antar course
                            int jarak = Math.abs(timeslotCourse1 - timeslotCourse2);
                            if(jarak < 5){
                                penalty= penalty+(Math.pow(2,(4-jarak)));
                            }
                        }
                    }
                }
            }
        }
        return (penalty/student.size());
    }
}