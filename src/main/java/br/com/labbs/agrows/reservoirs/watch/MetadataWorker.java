package br.com.labbs.agrows.reservoirs.watch;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.Task.Status;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

import br.com.labbs.agrows.reservoirs.watch.dto.Reservoir;

@RequestScoped
public class MetadataWorker implements Worker {

	@Inject
	private Api api;
	
	@Inject
	@RestClient
	DataPublisher publisher;
	
	private String reservoirTask = "";

	public MetadataWorker() {
	}

	public MetadataWorker(String reservoirWTask) {
		this.reservoirTask = reservoirWTask;
	}
	
	public void setTaskDefName(String reservoirWTask) {
		this.reservoirTask = reservoirWTask;
	}

	@Override
	public String getTaskDefName() {
		return reservoirTask;
	}

	@Override
	public TaskResult execute(Task task) {
		
		List<Reservoir> reserv = new ArrayList<>();
		reserv.addAll(api.getReservoirsNeData().getReservatorios());
		reserv.addAll(api.getReservoirsSinData().getReservatorios());
		
		for (Reservoir reservoir : reserv) {
			try {
				publisher.putReservoirMetadata("ANA", String.valueOf(reservoir.getCodigo_reservatorio()), reservoir);
	 		} catch (WebApplicationException e) {
				System.err.println("We don't have data to this thing: " + reservoir.getCodigo_reservatorio());
			}
		}
		
		task.setStatus(Status.COMPLETED);

		return new TaskResult(task);
	}

}
