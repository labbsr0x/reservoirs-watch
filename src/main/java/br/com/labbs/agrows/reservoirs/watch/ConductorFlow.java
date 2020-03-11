package br.com.labbs.agrows.reservoirs.watch;


import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.task.WorkflowTaskCoordinator;
import com.netflix.conductor.client.worker.Worker;


public class ConductorFlow {
	
	
	public void run() {
		TaskClient taskClient = new TaskClient();
		taskClient.setRootURI("http://localhost:8080/api/");
		
		int threadCount = 2;
		
		Worker worker1 = new MetadataWorker("getReservoirs");
		Worker worker2 = (Worker)new VolumeAquisitonWorker("getVolums");
		
		//Create WorkflowTaskCoordinator
		WorkflowTaskCoordinator.Builder builder = new WorkflowTaskCoordinator.Builder();
		WorkflowTaskCoordinator coordinator = builder.withWorkers(worker1, worker2).withThreadCount(threadCount).withTaskClient(taskClient).build();
		
		//Start for polling and execution of the tasks
		coordinator.init();
	}

}
