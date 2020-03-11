package br.com.labbs.agrows.reservoirs.watch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class WorkerTest {
	
	@Inject
	VolumeAquisitonWorker worker;
	
	@Inject
	MetadataWorker mdWorker;
	
	private static final String DAYS_AHEAD_VALUE = "365";
	private static final String DAYS_AHEAD_KEY = "daysAhead";
	private static final String SINCE = "since";
	private static final String INITIAL_DATE = "01/01/1998";
	
	public static Task createDefaultTask() {
		Task task = new Task();
		
		Map<String, Object> inputData = new HashMap<>();
		inputData.put(SINCE, INITIAL_DATE);
		inputData.put(DAYS_AHEAD_KEY, DAYS_AHEAD_VALUE);
		task.setInputData(inputData);
		
		return task;
	}

	//@Test
	public void testWorkerSingleExecution() {
		
		Task task = createDefaultTask();
		worker.setTaskDefName("testTask");
		
		TaskResult res = worker.execute(task);
		
		System.out.println(res.getOutputData().get(SINCE));
	}
	
	//@Test
	public void testWorkerMultipleExecution() {
		
		Task task = createDefaultTask();
		worker.setTaskDefName("testTask");
		
		Integer iterations = 2;
		List<TaskResult> results = new ArrayList<>();
		
		for (int i = 0; i < iterations; i++) {
			TaskResult res = worker.execute(task);
			results.add(res);
			task = updateTaskInitialDay(task, res);
		}
		
		System.out.println("Executions with results: " + results.size());
	}
	
	//@Test
	public void testMetadataWorker() {
		
		Task task = createDefaultTask();
		mdWorker.setTaskDefName("metadataTask");
		
		mdWorker.execute(task);
		
		System.out.println("Looks like everything worked fine");
	}
	
	private Task updateTaskInitialDay(final Task actual, final TaskResult newInput) {
		Task next = new Task();
		Map<String, Object> inputData = actual.getInputData();
		inputData.put(SINCE, newInput.getOutputData().get(SINCE));
		
		next.setInputData(inputData);
		
		return next;
	}
}
