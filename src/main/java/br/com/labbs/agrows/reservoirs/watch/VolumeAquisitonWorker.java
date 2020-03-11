package br.com.labbs.agrows.reservoirs.watch;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.Task.Status;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

import br.com.labbs.agrows.reservoirs.watch.dto.Reservoir;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumesNe;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumesSin;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumeData;
import br.com.labbs.agrows.reservoirs.watch.dto.interfaces.IVolumeData;

@RequestScoped
public class VolumeAquisitonWorker implements Worker {

	@Inject
	Api api;

	@Inject
	@RestClient
	DataPublisher publisher;
	
	private static final String DAYS_AHEAD_KEY = "daysAhead";
	private static final String SINCE = "since";

	private static final DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy[ HH:mm:ss]")
			.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
			.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
			.toFormatter();

	private String reservoirTask = "";

	public VolumeAquisitonWorker() {
	}

	public VolumeAquisitonWorker(String reservoirWTask) {
		this.reservoirTask = reservoirWTask;
	}

	public void setTaskDefName(String reservoirWTask) {
		this.reservoirTask = reservoirWTask;
	}

	public String getTaskDefName() {
		return reservoirTask;
	}
	
	@Override
	public TaskResult execute(Task task) {
		Map<String, Object> outputMap = new HashMap<>();
		Map<String, Object> inputData = task.getInputData();
		String since = (String)inputData.get(SINCE);
		Integer daysAhead = Integer.valueOf((String)inputData.get(DAYS_AHEAD_KEY));

		getAndPostVolums(since, daysAhead);

		outputMap.put(SINCE, getNextDate(getFirstDate(since), daysAhead));
		task.setStatus(Status.COMPLETED);

		TaskResult result = new TaskResult(task);
		result.setOutputData(outputMap);

		return result;
	}

	void getAndPostVolums(String sinceDate, Integer daysAhead) {
		
		if (!isValidTimespan(sinceDate)) {
			return;
		}

		List<Reservoir> reservsNe = api.getReservoirsNeData().getReservatorios();
		List<Reservoir> reservsSin = api.getReservoirsSinData().getReservatorios();

		String since = getFirstDate(sinceDate);
		String to = getNextDate(since, daysAhead);

		for (Reservoir reserv : reservsSin) {
			VolumesSin ret = api.getReservoirSinVolum(reserv.getCodigo_reservatorio(), since, to);
			if (ret != null && ret.getReservatorios() != null && ret.getReservatorios().size() > 0) {
				List<IVolumeData> data = ret.getReservatorios().stream().map(value -> new VolumeData(value)).collect(Collectors.toList());
				publisher.pubVolumDataList("ANA", reserv.getCodigo_reservatorio().toString(), data);
			}
		}

		for (Reservoir reserv : reservsNe) {
			VolumesNe ret = api.getReservoirNEVolum(reserv.getCodigo_reservatorio(), since, to);
			if (ret != null && ret.getReservatorios() != null && ret.getReservatorios().size() > 0) {
				List<IVolumeData> data = ret.getReservatorios().stream().map(value -> new VolumeData(value)).collect(Collectors.toList());
				publisher.pubVolumDataList("ANA", reserv.getCodigo_reservatorio().toString(), data);
			}
		}

	}
	
	public boolean isValidTimespan(String sinceDate) {
		LocalDateTime refDate = LocalDateTime.parse(sinceDate, dateFormat);
		return getToday().isAfter(refDate);
	}

	private String getFirstDate(String sinceDate) {

		LocalDateTime today = getToday();

		if (sinceDate != null) {
			LocalDateTime refDate = LocalDateTime.parse(sinceDate, dateFormat);
			if (refDate.isBefore(today)) {
				return sinceDate;
			}
		}

		return simpleDateFormat.format(today);
	}

	private LocalDateTime getToday() {
		return new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	private String getNextDate(String sinceDate, Integer daysAhead) {
		LocalDateTime next = LocalDateTime.parse(sinceDate, dateFormat).plusDays(daysAhead);
		LocalDateTime today = getToday();

		if (next.isAfter(today)) {
			return simpleDateFormat.format(today);
		}

		return simpleDateFormat.format(next);
	}

}
