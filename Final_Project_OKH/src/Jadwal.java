import java.util.Arrays;

public class Jadwal {
	
	String file;
	int[][] conflictMatrix;
	int[] timeSlot;
	int Totalexam, timeslotIndex;
	
	public Jadwal(String file, int[][] conflictMatrix, int totalexam) {
		this.file = file;
		this.conflictMatrix = conflictMatrix;
		this.Totalexam = totalexam;
	}
	
	public int[][] getJadwal() {
		// Mengisi hasil timeslot array
		int [][] timeslotSchedule = new int[Totalexam][2];
    	for (int course = 0; course < Totalexam; course++) {
    		timeslotSchedule[course][0] = (course+1);
    		timeslotSchedule[course][1] = timeSlot[course];
    	}
		return timeslotSchedule; 
	}
	
	public int[] scheduling(int[] timeslot) {
		this.timeSlot = new int[Totalexam];
		timeslotIndex = 1;
    	for(int i= 0; i < conflictMatrix.length; i++)
    		this.timeSlot[i] = 0;
    	
		for(int i = 0; i < conflictMatrix.length; i++) {
			for (int j = 1; j <= timeslotIndex; j++) {
				if(isTimeslotAvailable(i, j, conflictMatrix, timeSlot)) {
					this.timeSlot[i] = j;
					break;
				}
					else
						timeslotIndex = timeslotIndex+1;
			}
		}
		return this.timeSlot;
	}
	public int[] schedulingByDegree(int [][] sortedCourse) {
    	this.timeSlot = new int[Totalexam];
    	timeslotIndex = 1;
    	for(int i= 0; i < sortedCourse.length; i++)
    		this.timeSlot[i] = 0;
    	
		for(int course = 0; course < sortedCourse.length; course++) {
			for (int time_slotindex = 1; time_slotindex <= timeslotIndex; time_slotindex++) {
				if(isTimeslotAvailableWithSorted(course, time_slotindex, conflictMatrix, sortedCourse, this.timeSlot)) {
					this.timeSlot[sortedCourse[course][0]-1] = time_slotindex;
					break;
				}
					else
						timeslotIndex = timeslotIndex+1; // pindah ke ts+1 if ts jika tabrakan
			}
		}
		return this.timeSlot;
        }

	public int getJumlahTimeSlot(int[][] timeslot) { 
		int jumlah_timeslot = 0;
		for(int i = 0; i < timeslot.length; i++) {
			if(timeslot[i][1] > jumlah_timeslot)
				jumlah_timeslot = timeslot[i][1];}
		return jumlah_timeslot; 
	}

    
        
    public static boolean isTimeslotAvailable(int course, int timeslot, int[][] conflictmatrix, int[] timeslotarray) {
		for(int i = 0; i < conflictmatrix.length; i++)
			if(conflictmatrix[course][i] != 0 && timeslotarray[i] == timeslot)
				return false;
		return true;
	}
    public static boolean isTimeslotAvailableWithSorted(int course, int timeslot, int[][] conflictMatrix, int[][] sortedmatrix, int[] timeslotarray) {
		for(int i = 0; i < sortedmatrix.length; i++) 
			if(conflictMatrix[sortedmatrix[course][0]-1][i] != 0 && timeslotarray[i] == timeslot) {
				return false;
                        }return true;
	}
    public static boolean checkRandomTimeslot(int randomCourse, int randomTimeslot, int[][] conflict_matrix, int[][] jadwal){
        for(int i=0; i<conflict_matrix.length; i++)
            if(conflict_matrix[randomCourse][i] !=0 && jadwal[i][1]==randomTimeslot)
                return false;
        return true;              
    }
    
    public static boolean checkRandomTimeslotForLLH(int randomCourse, int randomTimeslot, int[][] conflict_matrix, int[][] jadwal){
        for(int i=0; i<conflict_matrix.length; i++)
            if(conflict_matrix[randomCourse][i] !=0 && jadwal[i][1]==randomTimeslot)
                return false;
        return true;              
    }
}
