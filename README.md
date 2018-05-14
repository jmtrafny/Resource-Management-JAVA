# Resource Sharing Problem  
Modeling a System with Shared  
Resources  
SE 410 Final Project by James Trafny  

## PROBLEM STATEMENT
Consider a system with four instances of process P, two instances of resource R1, and two
instances of resource R2. Each process shall have three states; Idle, Acquiring Resources, and
Servicing Request. In this system, requests are randomly generated and assigned to a process
in an idle state. The process must then acquire an instance or resource R1 and an instance of
resource R2 one at a time, then the process must service the request and simultaneously
release each resource.
The following criteria must always be observed:
1) No process shall service a request without acquiring one instance each of resource R1
and R2.
2) The number of resources of each type held by the processes cannot exceed the number
of existing resources.
3) Deadlock shall not occur.
The Coffman conditions describe the four necessary conditions that must hold for deadlock to
occur (Shibu, 2009.) The system as described meets all four of these conditions including
mutual exclusion, hold and wait, no preemption, and circular wait. To successfully meet all
three criteria, a method will have to be designed to defeat one of these conditions.
## BACKGROUND
The “Dining Philosophers Problem” was formulated in 1965 by Edsger Dijkstra as a way to
describe and illustrate techniques for solving synchronization issues in a multi-threaded
environment (Dijkstra, N.D.). In Dijkstra’s problem, there are more resources required than
available and some resource sharing algorithm must be put into place. Dijkstra’s problem can
be directly applied to our problem statement above, where the philosophers are the processes,
and the chopsticks (sometimes forks) are the resources. The goal is for the processes to be
able to continually service requests without starvation or deadlock. Deadlock is a situation in
which the progress of a system is stopped because all processes are waiting to acquire a
resource that is already held by another process.
If the system were designed to acquire resources as soon as they are available without any
consideration towards synchronization, then we will end up with circular writing, one of the
Coffman conditions leading to deadlock.

## SYSTEM
Our system consists of three Java class files; SharedResourceProblem.java, Resource.java,
and Process.java. The first class contains the main method and executes in the command line.
The class has to static variables that control the parameters of the system. By editing the
variables NO_OF_PROCESSES and TIME_MILLIS you can adjust how long the system
simulates for and how many processes to use during the simulation. The default values have
the system run for five seconds with four processes.
The main thread begins by instantiating four instances each of our process class and resource
class, then assigns two resources to each process, then starts the newly created tasks for the
processes. The main thread then puts itself to sleep for four seconds, then stops each process
task. After each task is stopped, the main task prints to the console the number of successfully
completed service requests.
The resource class contains a constructor for creating each resource. In our system, each
resource has a Reentrant Lock that guard the two main functions in the class; one for acquiring
resources, and one for releasing resources. For a process to acquire a resource it must call a
function with a reentrant lock, if the lock is open the resource is free, otherwise the resource is
being used by another process. The resource class also contains a toString method for console
output and tracking.
The process class contains a constructor and several methods for processing requests. The
most notable function is the overridden run method which contains part of our algorithm for
preventing deadlock, namely defeating the hold-and- wait condition. This is discussed further
below. The next two important methods are idle and serviceRequest. The idle method
simulates the random waiting between service requests, and prints to the console the amount of
time till the next request comes in. The serviceRequest method simulates the completion of a
service request and increments our results counter.
Our solution for preventing deadlock is built into the resource and process class in the form of
mutual exclusion, and actively preventing hold-and- wait. By defeating two of the four Coffman
conditions, our system is safe from deadlock.

## SOLUTION
Our system is built to avoid hold-and- wait and mutual exclusion violations to prevent deadlock
from occurring. The problem of hold-and- wait was handled at the process level, and mutual
exclusion was handled at the resource class.
Preventing hold-and- wait was accomplished in the overridden run task, inside the process class.
When the process receives a request it first tries to acquire its first resource. If the process
does that, then it attempts to acquire a second resource. If this is successful, then the process
services the request and releases the resource. If the process is unable to obtain a second
resource after ten milliseconds, then the process releases the first resource back to the pool
and starts over. This release of the first resource prevents hold-and- wait scenarios.
Mutual exclusion is built into the resources with the use of reentrant locks. Before a resource is
considered acquired, the process must first try to acquire the lock belonging to that resource. If
the lock is available, then the process makes the lock unavailable and moves on to acquiring
the next resource. If the process acquires a second resource lock, then it can service the
request and make the lock available to other resources. Each process attempts to acquire the
lock for ten milliseconds before returning its second resource back to the pool.

## REFERENCES
Dijkstra, Edsger W. EWD-1000 (PDF). E.W. Dijkstra Archive. Center for American History,
University of Texas at Austin. (transcription)

Shibu, K. (2009). Intro To Embedded Systems (1st ed.). Tata McGraw-Hill Education. p. 446.
ISBN 9780070145894. Retrieved 28 January 2012.
