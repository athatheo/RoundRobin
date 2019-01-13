public class RoundRobin
{
	// Method that calculates the waiting time for all processes
	static int[] calcWaitingTime(int burstTime[], int quantum)
	{
		int[] waitingTime = new int[burstTime.length];
		int[] leftTime = new int[burstTime.length];
		boolean[] arrived = new boolean[burstTime.length];
		for (int i = 0; i < burstTime.length; i++){
			leftTime[i] = burstTime[i];
			waitingTime[i] = 0;
			arrived[i] = false;
		}
		int time;
		boolean doneCalculating = false;
		while (!doneCalculating){
			doneCalculating = true;
			for (int i = 0; i < burstTime.length; i++){
				if (leftTime[i] > 0 ){
					arrived[i] = true;
					doneCalculating = false;
					if (leftTime[i] >= quantum){
						leftTime[i] -= quantum;
						time = quantum;
					} else {
						time = leftTime[i];
						leftTime[i] = 0;
					}
					for (int j = 0; j < burstTime.length; j++){
						if (j!=i && leftTime[j] > 0 && arrived[i]){
							waitingTime[j] += time;
						}
					}
				}
			}
		}
		return  waitingTime;
	}

	// Method that calculates turn around time for all processes
	static int[] calcTurnAroundTime(int burstTime[], int waitingTime[])
	{
		int[] turnAroundTime = new int[burstTime.length];
		for (int i = 0; i < burstTime.length; i++){
			turnAroundTime[i] = burstTime[i] + waitingTime[i];
		}
		return turnAroundTime;
	}

	// Method that prints the results and calculates the average waiting and
	// turnaround times
	static void printAvgTimes(int burstTime[], int quantum)
	{
		int n = burstTime.length;
		int totalWaitingTime = 0;
		int totalTurnAroundTime = 0;

		// Find waiting time of all processes
		int[] waitingTime = calcWaitingTime(burstTime, quantum);

		// Find turn around time for all processes
		int[] turnAroundTime = calcTurnAroundTime(burstTime, waitingTime);

		// Display processes along with all details
		System.out.println("Process " + " Burst Time " +
				" Waiting Time " + " Turnaround Time");
		System.out.println("=======  ==========  ============  ===============");
		// Calculate total waiting time and total turn
		// around time
		for (int i = 0; i < n; i++) {
			totalWaitingTime += waitingTime[i];
			totalTurnAroundTime += turnAroundTime[i];
			System.out.println(i + "\t\t" + burstTime[i] +"\t " +
					waitingTime[i] +"\t\t " + turnAroundTime[i]);
		}

		System.out.println("\nAverage waiting time = " +
				(float)totalWaitingTime / (float)n);
		System.out.println("Average turnaround time = " +
				(float)totalTurnAroundTime / (float)n);
	}

	// Driver Method to test your algorithm with a simple example
	public static void main(String[] args)
	{
		// Burst time of processes. The array index is the process ID.
		int burstTime[] = {5, 15, 4, 3};

		// Time quantum
		int quantum = 3;

		printAvgTimes(burstTime, quantum);
	}
}
