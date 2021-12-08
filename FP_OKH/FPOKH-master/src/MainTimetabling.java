import java.io.IOException;
import java.util.Scanner;

public class MainTimetabling {

    static String folderDataset = "D:\\Naufal\\SMT 7\\OKH\\Toronto\\";
    static String[][] dataset = {{"car-f-92", "CAR92"}, {"car-s-91", "CAR91"}, {"ear-f-83", "EAR83"}, {"hec-s-92", "HEC92"}, {"kfu-s-93", "KFU93"}, 
                                   {"lse-f-91", "LSE91"}, {"pur-s-93", "PUR93"}, {"rye-s-93", "RYE92"}, {"sta-f-83", "STA83"},{"tre-s-92", "TRE92"}, 
                                   {"uta-s-92", "UTA92"}, {"ute-s-92", "UTE92"}, {"yor-f-83", "YOR83"}
                                  };
    
    static int timeSlot[];
    static int[][] conflict_matrix, course_sorted, hasil_timeslot;
	
	private static Scanner scanner;
	
    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);
        for	(int i=0; i< dataset.length; i++)
        	System.out.println(i+1 + ". Penjadwalan " + dataset[i][1]);
        
        System.out.print("\nSilahkan memilih dataset untuk dijadwalkan : ");
        int pilih = scanner.nextInt();
        
        String Input = dataset[pilih-1][0];
        String Output = dataset[pilih-1][1];
        
        String file = folderDataset + Input;
        
        Kelas course = new Kelas(file);
        int jumlahexam = course.getJumlahCourse();
        
        conflict_matrix = course.getConflictMatrix();
        int jumlahmurid = course.getJumlahMurid();
        
	course_sorted = course.sortingByDegree(conflict_matrix, jumlahexam);
		
        
	long starttimeLargestDegree = System.nanoTime();
         Jadwal schedule = new Jadwal(file, conflict_matrix, jumlahexam);
         timeSlot = schedule.schedulingByDegree(course_sorted);
         int[][] timeslotByLargestDegree = schedule.getJadwal();
    long endtimeLargestDegree = System.nanoTime();

                
    Optimasi optimization = new Optimasi(file, conflict_matrix, course_sorted, jumlahexam, jumlahmurid, 1000000);


//		Metode Hill Climbing
/*
		long starttimeHC = System.nanoTime();
		optimization.getTimeslotByHillClimbing(); // menggunakan metode hillclimbing untuk iterasi sebanyak 1000.000
		long endtimeHC = System.nanoTime();
		System.out.println("Execution Time " + ((double) (endtimeHC - starttimeHC)/1000000000) + " detik.\n");     
		System.out.println("=============================================================");*/
		
//      Metode Tabu Search
        long starttimeTS = System.nanoTime();
        optimization.getTimeslotByTabuSearch();
        long endtimeTS = System.nanoTime();
        
        System.out.println("Execution Time " + ((double) (endtimeTS - starttimeTS)/1000000000) + " detik.");
        
        System.out.println("=============================================================");
        
//		SA
		
        SimulatedAnnealing SA = new SimulatedAnnealing(file, conflict_matrix, course_sorted, jumlahexam, jumlahmurid, 1000);
        long starttimeSA = System.nanoTime();
        SA.getTimeslotBySimulatedAnnealing(100.0);
        long endtimeSA = System.nanoTime();
        System.out.println("Time Execution  : " + ((double) (endtimeSA - starttimeSA)/1000000000) + " detik.\n");
        System.out.println("=============================================================");
		
//		Metode Constructive Heuristic
		System.out.println("METODE CONSTRUCTIVE HEURISTIC " + Output + "\n");
	    System.out.println("Timeslot       : " + schedule.getJumlahTimeSlot(schedule.getJadwal()));
	    System.out.println("Penalty        : " + Evaluator.getPenalty(conflict_matrix, schedule.getJadwal(), jumlahmurid));
	    System.out.println("Execution Time :"+ ((double) (endtimeLargestDegree - starttimeLargestDegree)/1000000000) + " detik.\n");
	    System.out.println("=============================================================");               
		
                
		
//
    }


}