package SRP;

/**  
* SharedResourceProblem.java
* 
* Embry-Riddle Aeronautical University  
* SE 410 - Software Modeling Final Project 
* 
* This program illustrates a method for avoiding deadlock and 
* starvation in a system with shared resource.  It accomplishes
* this with the use of thread safe stack operations with 
* Reentrant locks on the resource stack.  Each process will wait
* a random amount of time before attempting to acquire the needed
* resources to service a request.  When the process has two
* resources, it services the request and releases the resources
* back to the resource pool where it can be used by another
* process.  If a process attempts to acquire a resource when
* there are none, it will return the resource that it is holding
* in order to not block the rest of the processes.  The console 
* output shows the action of each process as the program runs.
* The variable TIME_MILIS controls how long the simulation lasts.
* 
* @author  James Trafny
* @author  Peter Devyatkin
* @version 1.0 
*/ 

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


/**
* Class instantiates processes and resources for simulating
* a shared resource problem.  This class contains the main
* method for execution.  NO_OF_PROCESSES controls the 
* number of processes and resources, TIME_MILLIS controls
* the durration of the simulation.
*
* @author  James Trafny
* @author  Peter Devyatkin
*/
public class SharedResourceProblem {

	// How many to test with.
	private static final int NO_OF_PROCESSES = 4;
	private static final int TIME_MILLIS = 1000 * 5;

	/**
	 * Main method instantiates execution threads, then sleeps for specified
	 * duration time.  After the sleep time, the main method ends all active
	 * processes and gracefully exits after a short waiting period. 
	 * 
	 * @param args 					 - Unused
	 * @throws InterruptedException  Process Interrupted 
	 */
	public static void main(String args[]) throws InterruptedException {
		
		ExecutorService executorService = null;
		Process[] processes = null;
		
		try {
			processes = new Process[NO_OF_PROCESSES];
			Resource[] resources = new Resource[NO_OF_PROCESSES];

			for (int i = 0; i < NO_OF_PROCESSES; i++) {
				resources[i] = new Resource(i);
			}

			executorService = Executors.newFixedThreadPool(NO_OF_PROCESSES);

			for (int i = 0; i < NO_OF_PROCESSES; i++) {
				processes[i] = new Process(i, resources[i], resources[(i + 1) % NO_OF_PROCESSES]);
				executorService.execute(processes[i]);
			}
			
			// Main thread sleeps till time of simulation
			Thread.sleep(TIME_MILLIS);
			
			// Stop all processes.
			for (Process process : processes) {
				process.finished = true;
			}

		} finally {
			// Close everything down.
			executorService.shutdown();

			// Wait for all thread to finish
			while (!executorService.isTerminated()) {
				Thread.sleep(1000);
			}

			// Time for check
			for (Process process : processes) {
				System.out.println(process + " => No of sucessful requests =" + process.getResults());
			}
		}
	}
}

