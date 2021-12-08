import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Optimasi {
	int[][] timeslotHillClimbing, initialTimeslot, conflict_matrix, course_sorted,timeslotTabuSearch;
	int[] timeslot;
        double[] tabuSearchPenaltyList1;
	String file;
	int jumlahexam, jumlahmurid, randomCourse, randomTimeslot, iterasi;
	double initialPenalty, bestPenalty, deltaPenalty;
	
	Jadwal schedule;
	
	Optimasi(String file, int[][] conflict_matrix, int[][] course_sorted, int jumlahexam, int jumlahmurid, int iterasi) { 
		this.file = file; 
		this.conflict_matrix = conflict_matrix;
		this.course_sorted = course_sorted;
		this.jumlahexam = jumlahexam;
		this.jumlahmurid = jumlahmurid;
		this.iterasi = iterasi;
	}
	
	// Metode Hill climbing
	public void getTimeslotByHillClimbing() throws IOException {
		schedule = new Jadwal(file, conflict_matrix, jumlahexam);
		timeslot = schedule.schedulingByDegree(course_sorted);
		
		int[][] initialTimeslot = schedule.getJadwal(); 
		timeslotHillClimbing = Evaluator.getTimeslot(initialTimeslot);
		initialPenalty = Evaluator.getPenalty(conflict_matrix, initialTimeslot, jumlahmurid);
		
		int[][] timeslotHillClimbingSementara = Evaluator.getTimeslot(timeslotHillClimbing);
		
		bestPenalty = Evaluator.getPenalty(conflict_matrix, timeslotHillClimbing, jumlahmurid);
		
		for(int i = 0; i < iterasi; i++) {
			try {
				randomCourse = random(jumlahexam); 
				randomTimeslot = random(schedule.getJumlahTimeSlot(initialTimeslot)); 
				
				if (Jadwal.checkRandomTimeslot(randomCourse, randomTimeslot, conflict_matrix, timeslotHillClimbingSementara)) {	
					timeslotHillClimbingSementara[randomCourse][1] = randomTimeslot;
					double penaltiAfterHillClimbing = Evaluator.getPenalty(conflict_matrix, timeslotHillClimbingSementara, jumlahmurid);
					
					
					if(bestPenalty > penaltiAfterHillClimbing) {
						bestPenalty = Evaluator.getPenalty(conflict_matrix, timeslotHillClimbingSementara, jumlahmurid);
						timeslotHillClimbing[randomCourse][1] = timeslotHillClimbingSementara[randomCourse][1];
					} 
						else 
							timeslotHillClimbingSementara[randomCourse][1] = timeslotHillClimbing[randomCourse][1];
				}
				System.out.println("Iterasi ke " + (i+1) + " memiliki penalti : "+ bestPenalty);
			}
				catch (ArrayIndexOutOfBoundsException e) {
				
				}
			
		}
		
		deltaPenalty = ((initialPenalty-bestPenalty)/initialPenalty)*100;
		
		
		System.out.println("=============================================================");
		System.out.println("METODE HILL CLIMBING"); 
		System.out.println("\nInitial Penalty 		 : "+ initialPenalty); 
		System.out.println("Best Penalty 	  		 : "+ bestPenalty); 
		System.out.println("Enhanced Penalty (delta) : " + deltaPenalty + " % from initial penalty" + "\n");
		System.out.println("Timeslot 				 : " + schedule.getJumlahTimeSlot(timeslotHillClimbing) + "\n");
		}
	
    public void getTimeslotByTabuSearch() {
	schedule = new Jadwal(file, conflict_matrix, jumlahexam);
	timeslot = schedule.schedulingByDegree(course_sorted);
	
	// initial solution
	timeslotTabuSearch = schedule.getJadwal();
	initialPenalty = Evaluator.getPenalty(conflict_matrix, timeslotTabuSearch, jumlahmurid);
	
	int[][] bestTimeslot = Evaluator.getTimeslot(timeslotTabuSearch); 
	int[][] bestcandidate  = Evaluator.getTimeslot(timeslotTabuSearch);
	int[][] timeslotTabuSearchSementara = Evaluator.getTimeslot(timeslotTabuSearch);
	

	
	//inisiasi tabulist
    LinkedList<int[][]> tabuList = new LinkedList<int[][]>();
    int maxtabusize = 10;
    tabuList.addLast(Evaluator.getTimeslot(timeslotTabuSearch));
    
  //inisiasi iterasi
    int maxiteration = 1000;
    int iteration=0;
    
  //inisasi menghitung penalty
    double penalty1 = 0;
    double penalty2 = 0;
    double penalty3 = 0;
    
    boolean terminate = false;
    
    while(!terminate){
        iteration++;
        

       ArrayList<int[][]> sneighborhood = new ArrayList<>();

    	LowLevelHeuristics lowLevelHeuristics = new LowLevelHeuristics(conflict_matrix);
    	timeslotTabuSearchSementara = lowLevelHeuristics.move1(timeslotTabuSearchSementara);
		sneighborhood.add(timeslotTabuSearchSementara);
		timeslotTabuSearchSementara = lowLevelHeuristics.swap2(timeslotTabuSearchSementara);
		sneighborhood.add(timeslotTabuSearchSementara);
		timeslotTabuSearchSementara = lowLevelHeuristics.move2(timeslotTabuSearchSementara);
		sneighborhood.add(timeslotTabuSearchSementara);
		timeslotTabuSearchSementara = lowLevelHeuristics.swap3(timeslotTabuSearchSementara);
		sneighborhood.add(timeslotTabuSearchSementara);
		timeslotTabuSearchSementara = lowLevelHeuristics.move3(timeslotTabuSearchSementara);
		sneighborhood.add(timeslotTabuSearchSementara);
			
    		
    		//membandingkan dan memilih best neighbor
       int j = 0;
       while (sneighborhood.size() > j) {
           if( !(tabuList.contains(sneighborhood.get(j))) && 
        		   Evaluator.getPenalty(conflict_matrix, sneighborhood.get(j), jumlahmurid) < Evaluator.getPenalty(conflict_matrix, bestcandidate, jumlahmurid))
             bestcandidate = sneighborhood.get(j);
            	
           j++;
       }
            
       sneighborhood.clear();
            
       //membandingkan best solution dengan best neighbor
       if(Evaluator.getPenalty(conflict_matrix, bestcandidate, jumlahmurid) < Evaluator.getPenalty(conflict_matrix, timeslotTabuSearch, jumlahmurid))
          timeslotTabuSearch = Evaluator.getTimeslot(bestcandidate);
            
       //Input best neighbor ke dalam tabu
       tabuList.addLast(bestcandidate);
       if(tabuList.size() > maxtabusize)
          tabuList.removeFirst();
            
       //Proses Iterasi;
       tabuSearchPenaltyList1 = new double[100];
       if ((iteration+1)%10 == 0)
           System.out.println("Iterasi: " + (iteration+1) + " memiliki penalty " + Evaluator.getPenalty(conflict_matrix, timeslotTabuSearch, jumlahmurid));
       
       if (iteration == maxiteration) 
    	   terminate = true;
    }
    bestPenalty = Evaluator.getPenalty(conflict_matrix, timeslotTabuSearch, jumlahmurid);
    deltaPenalty = ((initialPenalty-bestPenalty)/initialPenalty)*100;
    
    System.out.println("=============================================================");
	System.out.println("		HASIL METODE TABU SEARCH						 			 "); // print best penalty
	System.out.println("\nInitial Penalty : "+ initialPenalty); // print initial penalty
	System.out.println("Best Penalti : " + bestPenalty); // print best penalty
	System.out.println("Enhanced Penalty (delta) : " + deltaPenalty + " % dari inisial solusi");
	System.out.println("Timeslot : " + schedule.getJumlahTimeSlot(timeslotTabuSearch) + "\n");
	
}



public int[][] getTimeslotHillClimbing() { return timeslotHillClimbing; }
    public int[][] getTimeslotTabuSearch() { return timeslotTabuSearch; }


public int getJumlahTimeslotHC() { return schedule.getJumlahTimeSlot(timeslotHillClimbing); }
    public int getJumlahTimeslotTabuSearch() { return schedule.getJumlahTimeSlot(timeslotTabuSearch); }

	private static int randomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

	private static int random(int number) {
		Random random = new Random();
		return random.nextInt(number);
	}

}
