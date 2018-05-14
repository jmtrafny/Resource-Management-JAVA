package SRP;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* The Resource class defines the behavior of each resource in the system.  
* Class contains constructor and methods needed to handle mutual exclusion
* of the resource stack.   
*
* @author  James Trafny
*/
public class Resource {

	// Make sure only one Process can have me at any time.
	Lock up = new ReentrantLock();
	private final int id;

	/**
	 * Constructor for Resource.
	 * 
	 * @param id - (Required) Integer ID value.
	 */
	public Resource(int id) {
		this.id = id;
	}

	/**
	 * The acquireResource method is called by processes in order to attempt to acquire
	 * one of the resources needed to service a request.  The process must successfully 
	 * pass the Mutex lock "up".  
	 * 
	 * @param process					- (Required) Process attempting to acquire resource
	 * @param resourceNum				- (Required) String of the form "first" or "second"
	 * @return							- Boolean value indicating un/successful acquisition
	 * @throws InterruptedException		- Process Interrupted 
	 */
	public boolean acquireResource(Process process, String resourceNum) throws InterruptedException {
		if (up.tryLock(10, TimeUnit.MILLISECONDS)) {
			System.out.println(process + " acquired " + resourceNum + " resource.  (ID: " + this + ")");
			return true;
		}
		System.out.println(process + " could not acquire " + resourceNum + " resource. -----------------------------------------");
		return false;
	}

	/**
	 * The releaseResource method is called by processes to return a resource to the stack
	 * by unlocking the Mutex "up".
	 * 
	 * @param process		- (Required) Process releasing it's resource.
	 * @param resourceNum	- (Required) String of the form "first" or "second"
	 */
	public void releaseResource(Process process, String resourceNum) {
		up.unlock();
		System.out.println(process + " returned " + resourceNum + " resource.  (ID: " + this + ")");
	}

	/** @return String of the form "Resource-#"; where # is the resource ID. */
	@Override
	public String toString() {
		return "Resource-" + id;
	}

}
