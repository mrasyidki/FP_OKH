import java.util.Arrays;
import java.util.Random;

public class LowLevelHeuristics {

	static int[][] conflict_matrix;
	LowLevelHeuristics(int[][] conflictmatrix) {
		conflict_matrix = conflictmatrix;
	}
	
	public static int[][] move(int[][] timeslot, int movetotal) {
		int[][] temporaryTimeslot = timeslot;
		int[] slot = new int[temporaryTimeslot.length];
		
		for (int i=0 ; i < temporaryTimeslot.length; i++) {
			slot[i] = temporaryTimeslot[i][1];
		}
		
		for (int i = 0; i < movetotal; i++) {
			int randomCourse = random(timeslot.length);
			int randomTimeSlot = random(Arrays.stream(slot).max().getAsInt());
			System.out.println("number to random: " + Arrays.stream(slot).max().getAsInt());
			
			temporaryTimeslot[randomCourse][1] = randomTimeSlot;
		}
		
		return temporaryTimeslot;
	}
	public int[][] move1(int[][] timeslot) {
		int[][] temporaryTimeslot = timeslot;
		int[] slot = new int[temporaryTimeslot.length];
		
		for (int i=0 ; i < temporaryTimeslot.length; i++) {
			slot[i] = temporaryTimeslot[i][1];
		}
		
		
		int randomCourse = random(timeslot.length);
		int randomTimeSlot = random(Arrays.stream(slot).max().getAsInt());

		if (Jadwal.checkRandomTimeslot(randomCourse, randomTimeSlot, conflict_matrix, timeslot))
			temporaryTimeslot[randomCourse][1] = randomTimeSlot;
		
		return temporaryTimeslot;
	}
	public int[][] move2(int[][] timeslot) {
		int[][] temporaryTimeslot = timeslot;
		int[] slot = new int[temporaryTimeslot.length];
		
		for (int i=0 ; i < temporaryTimeslot.length; i++) {
			slot[i] = temporaryTimeslot[i][1];
		}
		
		int randomCourse1 = random(timeslot.length);
		int randomCourse2 = random(timeslot.length);
		int randomTimeSlot1 = random(Arrays.stream(slot).max().getAsInt());
		int randomTimeSlot2 = random(Arrays.stream(slot).max().getAsInt());

		if (Jadwal.checkRandomTimeslot(randomCourse1, randomTimeSlot1, conflict_matrix, timeslot))
			temporaryTimeslot[randomCourse1][1] = randomTimeSlot1;
	
		if (Jadwal.checkRandomTimeslot(randomCourse2, randomTimeSlot2, conflict_matrix, timeslot))
			temporaryTimeslot[randomCourse2][1] = randomTimeSlot2;
		
		return temporaryTimeslot;
	}
	public int[][] move3(int[][] timeslot) {
		int[][] temporaryTimeslot = timeslot;
		int[] slot = new int[temporaryTimeslot.length];
		
		for (int i=0 ; i < temporaryTimeslot.length; i++) {
			slot[i] = temporaryTimeslot[i][1];
		}
		
		int randomCourse1 = random(timeslot.length);
		int randomCourse2 = random(timeslot.length);
		int randomCourse3 = random(timeslot.length);
		int randomTimeSlot1 = random(Arrays.stream(slot).max().getAsInt());
		int randomTimeSlot2 = random(Arrays.stream(slot).max().getAsInt());
		int randomTimeSlot3 = random(Arrays.stream(slot).max().getAsInt());

		if (Jadwal.checkRandomTimeslot(randomCourse1, randomTimeSlot1, conflict_matrix, timeslot))
			temporaryTimeslot[randomCourse1][1] = randomTimeSlot1;
		
		if (Jadwal.checkRandomTimeslot(randomCourse2, randomTimeSlot2, conflict_matrix, timeslot))
			temporaryTimeslot[randomCourse2][1] = randomTimeSlot2;
		
		if (Jadwal.checkRandomTimeslot(randomCourse3, randomTimeSlot3, conflict_matrix, timeslot))
			temporaryTimeslot[randomCourse3][1] = randomTimeSlot3;
		
		return temporaryTimeslot;
	}
	
	public static int[][] swap(int[][] timeslot, int swaptotal) {
		int[][] temporaryTimeslot = timeslot;
		
		for(int i=0; i < swaptotal; i++) {
			int exam1 = randomNumber(0, timeslot.length);
			int exam2 = randomNumber(0, timeslot.length);
			
			int slot1 = timeslot[exam1][1];
			int slot2 = timeslot[exam2][1];
			
			temporaryTimeslot[exam1][1] = slot2;
			temporaryTimeslot[exam2][1] = slot1;
		}
		
		return temporaryTimeslot;
	}
	public int[][] swap2(int[][] timeslot) {
		int[][] temporaryTimeslot = timeslot;
		
		
		int randomcourse1 = random(timeslot.length);
		int randomcourse2 = random(timeslot.length);
		while (randomcourse2 == randomcourse1) {
			randomcourse2 = random(timeslot.length);
		}
		
		int slot1 = timeslot[randomcourse1][1];
		int slot2 = timeslot[randomcourse2][1];
		
		if (Jadwal.checkRandomTimeslot(randomcourse1, slot2, conflict_matrix, timeslot)) {
			temporaryTimeslot[randomcourse1][1] = slot2;
		}
		if (Jadwal.checkRandomTimeslot(randomcourse2, slot1, conflict_matrix, timeslot)) {
			temporaryTimeslot[randomcourse2][1] = slot1;
		}
		return temporaryTimeslot;
	}
	public int[][] swap3(int[][] timeslot) {
		int[][] temporaryTimeslot = timeslot;
		
		int randomcourse1, randomcourse2, randomcourse3;
		do {
			randomcourse1 = random(timeslot.length);
			randomcourse2 = random(timeslot.length);
			randomcourse3 = random(timeslot.length);
		} while (randomcourse2 == randomcourse1 || randomcourse2 == randomcourse3 || randomcourse1 == randomcourse3);
		
		int slot1 = timeslot[randomcourse1][1];
		int slot2 = timeslot[randomcourse2][1];
		int slot3 = timeslot[randomcourse3][1];

		
		if (Jadwal.checkRandomTimeslot(randomcourse1, slot2, conflict_matrix, timeslot)) {
			temporaryTimeslot[randomcourse1][1] = slot2;
		}
		if (Jadwal.checkRandomTimeslot(randomcourse2, slot3, conflict_matrix, timeslot)) {
			temporaryTimeslot[randomcourse2][1] = slot3;
		}
		if (Jadwal.checkRandomTimeslot(randomcourse3, slot1, conflict_matrix, timeslot)) {
			temporaryTimeslot[randomcourse3][1] = slot1;
		}
		return temporaryTimeslot;
	}
	private static int randomNumber(int min, int max) {
		Random random = new Random();
		try {
			return random.nextInt(max - min) + min;	
		}
			catch(Exception e) {
				if (Math.abs(max - min) == 0) {
					return random.nextInt(Math.abs(max - min)+1) + min;
				}
					else
						return random.nextInt(Math.abs(max - min)) + min;
			}
	}
	
	private static int random(int number) {
		Random random = new Random();
		return random.nextInt(number);
	}
}

