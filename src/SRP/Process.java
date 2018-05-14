package SRP;

import java.util.Random;

/**
* The Process class defines the behavior of each process in the system.  
* Class contains constructor and methods needed to service requests. 
*
* @author  James Trafny
* @author  Peter Devyatkin
*/
public class Process implements Runnable {

	private final int id;
	private final Resource resource_1;
	private final Resource resource_2;
	// Triggered in Main by simulation time
	volatile boolean finished = false;
	// To randomize "service requests"
	private Random randomGenerator = new Random();
	// Number of requests successfully serviced
	private int numOfSuccessfulReq = 0;

	/**
	 * Constructor.  Resources are assigned such that the first resource matches the
	 * process id, and the second resource is process id++, except for the last
	 * process which is assigned resource 0 as it's second resource. 
	 * 
	 * @param id 				- (Required) unique integer ID.
	 * @param firstResource		- (Required) first resource needed to service request.
	 * @param secondResource	- (Required) second resource needed to service request.
	 */
	public Process(int id, Resource firstResource, Resource secondResource) {
		this.id = id;
		this.resource_1 = firstResource;
		this.resource_2 = secondResource;
	}

	/**
	 * Overridden run method, defines the concurrent behavior of the
	 * processes in the system.  The process waits for a service 
	 * request, once received the process attempts to acquire its
	 * first resource.  If successful, it will attempt to acquire its
	 * second resource.  If successful it will service the request
	 * and release its held resources.
	 */
	@Override
	public void run() {

		try {
			while (!finished) {
				// Wait for service request.
				idle();
				// Make the mechanism obvious.
				if (resource_1.acquireResource(this, "first")) {
					if (resource_2.acquireResource(this, "second")) {
						// SUCCESS!.
						serviceRequest();
						// Finished, return resources one at a time
						resource_2.releaseResource(this, "second");
					}
					// Done.
					resource_1.releaseResource(this, "first");
				}
			}
		} catch (

		Exception e) {
			// Catch the exception outside the loop.
			e.printStackTrace();
		}
	}

	/**
	 * The idle method waits for a random amount of time to 
	 * simulate randomly incoming requests from some server.
	 * 
	 * @throws InterruptedException Process Interrupted  
	 */
	private void idle() throws InterruptedException {
		/*
		 * SET idleTime to a constant int (like 300 or something)
		 * to force collisions.  Otherwise a collision is not
		 * likely to happen, we're just too good that way.
		 */
		//int idleTime = randomGenerator.nextInt(1000);
		int idleTime = 100;
		System.out.println(this + " next request in " + idleTime + "ms.");
		Thread.sleep(idleTime);
	}

	/**
	 * The serviceRequest method simulates servicing a request
	 * received by a server.  In this simulation, "servicing
	 * a request" means printing its ID to the console.
	 *   
	 * @throws InterruptedException Process Interrupted 
	 */
	private void serviceRequest() throws InterruptedException {
		// System.out.println(this + " has begun to service request.");
		// Thread.sleep(randomGenerator.nextInt(1000));
		System.out.println(this + " Serviced Request!");
		numOfSuccessfulReq++;
	}

	/** @return Integer of successful service requests. */
	public int getResults() {
		return numOfSuccessfulReq;
	}

	/** @return String of the form "Process-#"; where # is the process ID. */
	@Override
	public String toString() {
		return "Process-" + id;
	}

}
