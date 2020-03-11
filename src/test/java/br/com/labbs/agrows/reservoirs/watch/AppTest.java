package br.com.labbs.agrows.reservoirs.watch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.geojson.Feature;
import org.geojson.FeatureCollection;

import br.com.labbs.agrows.reservoirs.watch.dto.Reservoir;
import br.com.labbs.agrows.reservoirs.watch.dto.ReservoirsNe;
import br.com.labbs.agrows.reservoirs.watch.dto.ReservoirsSin;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumeData;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumeNe;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumeSin;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumesGeneric;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumesNe;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumesSin;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class AppTest {

	@Inject
	Api api;

	@Inject
	@RestClient
	DataPublisher publisher;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	//@Test
	public void TestGetReservoirVolumInject() {

		VolumesGeneric res = api.getReservoirVolum(12090, "26/06/2019", "30/06/2019");

		assertNotNull(res);
		assertNotNull(res.getReservatorios());

		assertEquals(12090, res.getReservatorios().get(0).getCod_reservatorio());
	}

	//@Test
	public void TestGetReservoirSinVolumInject() {
		VolumesSin res = api.getReservoirSinVolum(19090, "26/06/2019", "30/06/2019");

		assertNotNull(res);
		assertNotNull(res.getReservatorios());
		assertTrue(res.getReservatorios().size() >= 0);

		assertEquals(19090, res.getReservatorios().get(0).getCod_reservatorio());
	}

	//@Test
	public void TestGetReservoirNeVolumInject() {
		VolumesNe res = api.getReservoirNEVolum(12190, "28/06/2019", "30/06/2019");

		assertNotNull(res);
		assertNotNull(res.getReservatorios());
		assertTrue(res.getReservatorios().size() >= 0);
		assertEquals(12190, res.getReservatorios().get(0).getCod_reservatorio());

		VolumeData volum = new VolumeData(res.getReservatorios().get(0));
		publisher.pubVolumData("ANA", volum.getCode(), volum);

	}

	//@Test
	public void TestReservoirsNeInject() {
		ReservoirsNe reservatorios = api.getReservoirsNeData();

		assertEquals(541, reservatorios.getReservatorios().size());

		for (Reservoir reservatorio : reservatorios.getReservatorios()) {
			System.out.println(reservatorio.getCodigo_reservatorio() + ", ");
		}
	}

	//@Test
	public void TestReservoirsSinInject() {
		ReservoirsSin reservatorios = api.getReservoirsSinData();

		assertEquals(160, reservatorios.getReservatorios().size());

		for (Reservoir reservatorio : reservatorios.getReservatorios()) {
			System.out.println(reservatorio.getCodigo_reservatorio() + ", ");
		}
	}

	//@Test
	public void testMetadata() {
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
	}

	//@Test
	public void TestGetGeojsonCollection() {
		List<Reservoir> volumes = new ArrayList<>();
		volumes.addAll(api.getReservoirsNeData().getReservatorios());
		volumes.addAll(api.getReservoirsSinData().getReservatorios());

		FeatureCollection posicoes = api.getGeojson();

		System.out.println("Total de geojsons: " + posicoes.getFeatures().size());
		System.out.println("Total de volumes: " + volumes.size());

		//Clean features list removing ones without meaningful names 
		posicoes.setFeatures(posicoes.getFeatures().stream().filter(p -> (!getName(p).equals(""))).collect(Collectors.toList()));
		
		System.out.println("Geojsons after filtering: " + posicoes.getFeatures().size());

		Map<Integer, Feature> found = new HashMap<>();

		for (Feature pos : posicoes.getFeatures()) {
			for (Reservoir vol : volumes) {
				if (getName(pos).length() > getName(vol).length() && getName(pos).contains(getName(vol))) {
					pos.setProperty("owner", "ANA");
					pos.setProperty("thing", vol.getCodigo_reservatorio());
				}
			}
		}

		System.out.println("Achados: " + found.keySet().size());
	}

	private String getName(Feature feature) {
		return feature.getProperties().get("nooriginal").toString().trim().toUpperCase();
	}

	private String getName(Reservoir reserv) {
		return reserv.getNome_reservatorio().trim().toUpperCase();
	}

	//@Test
	public void TestRetrieveDataFromGeneralAPI() {
		List<Reservoir> reservsNe = api.getReservoirsNeData().getReservatorios();
		List<Reservoir> reservsSin = api.getReservoirsSinData().getReservatorios();
		List<Reservoir> generalFromSin = new ArrayList<>();
		List<Reservoir> generalFromNe = new ArrayList<>();
		List<Reservoir> sinWithData = new ArrayList<>();
		List<Reservoir> neWithData = new ArrayList<>();
		List<Reservoir> generalAndSin = new ArrayList<>();
		List<Reservoir> generalAndNe = new ArrayList<>();

		for (Reservoir reservatorio : reservsNe) {
			VolumesGeneric gen = api.getReservoirVolum(reservatorio.getCodigo_reservatorio(), "11/05/2019", "13/06/2019");
			if (gen.getReservatorios() != null && gen.getReservatorios().size() > 0) {
				generalFromNe.add(reservatorio);
			}

			VolumesNe ne = api.getReservoirNEVolum(reservatorio.getCodigo_reservatorio(), "11/05/2018", "13/06/2018");
			if (ne.getReservatorios() != null && ne.getReservatorios().size() > 0) {
				neWithData.add(reservatorio);
			}
		}

		for (Reservoir reservatorio : reservsSin) {
			VolumesGeneric gen = api.getReservoirVolum(reservatorio.getCodigo_reservatorio(), "11/05/2019", "13/06/2019");
			if (gen.getReservatorios() != null && gen.getReservatorios().size() > 0) {
				generalFromSin.add(reservatorio);
			}

			VolumesSin sin = api.getReservoirSinVolum(reservatorio.getCodigo_reservatorio(), "11/05/2018", "13/06/2018");
			if (sin.getReservatorios() != null && sin.getReservatorios().size() > 0) {
				sinWithData.add(reservatorio);
			}
		}

		System.out.println("Reserv Ne: " + reservsNe.size());
		System.out.println("Reserv Ne with data: " + neWithData.size());

		System.out.println("Reserv Sin: " + reservsSin.size());
		System.out.println("Reserv Sin with data: " + sinWithData.size());

		System.out.println("General from NE: " + generalFromNe.size());
		System.out.println("General from Sin: " + generalFromSin.size());

		for (Reservoir sin : generalFromSin) {
			if (sinWithData.contains(sin)) {
				generalAndSin.add(sin);
			}
		}

		for (Reservoir ne : generalFromNe) {
			if (neWithData.contains(ne)) {
				generalAndNe.add(ne);
			}
		}

		System.out.println("Sin And General: " + generalAndSin.size());
		System.out.println("NE And General: " + generalAndNe.size());

	}

	//@Test
	public void TestRetrieveAndPublishData() {

		List<Reservoir> reservsNe = api.getReservoirsNeData().getReservatorios();
		List<Reservoir> reservsSin = api.getReservoirsSinData().getReservatorios();

		LocalDate local = LocalDate.parse("01/01/2019", formatter);

		for (int i = 0; i < 30; i++) {

			String today = local.format(formatter);
			String tomorrow = local.plusDays(1).format(formatter);

			for (Reservoir reserv : reservsSin) {
				VolumesSin ret = api.getReservoirSinVolum(reserv.getCodigo_reservatorio(), today, tomorrow);
				if (ret != null && ret.getReservatorios() != null && ret.getReservatorios().size() > 0) {
					VolumeData vol = new VolumeData(ret.getReservatorios().get(0));
					publisher.pubVolumData("anat", vol.getCode(), vol);
				}
			}

			for (Reservoir reserv : reservsNe) {
				VolumesNe ret = api.getReservoirNEVolum(reserv.getCodigo_reservatorio(), today, tomorrow);
				if (ret != null && ret.getReservatorios() != null && ret.getReservatorios().size() > 0) {
					VolumeData vol = new VolumeData(ret.getReservatorios().get(0));
					publisher.pubVolumData("anat", vol.getCode(), vol);
				}
			}

			local = local.plusDays(1);

		}
	}

	//@Test
	public void TestRetrieveAndPublishData2() {

		List<Reservoir> reservsNe = api.getReservoirsNeData().getReservatorios();
		List<Reservoir> reservsSin = api.getReservoirsSinData().getReservatorios();

		String start = "01/01/2019";
		String end = "14/08/2019";

		for (Reservoir reserv : reservsSin) {
			VolumesSin ret = api.getReservoirSinVolum(reserv.getCodigo_reservatorio(), start, end);
			if (ret != null && ret.getReservatorios() != null && ret.getReservatorios().size() > 0) {
				for (VolumeSin res : ret.getReservatorios()) {
					VolumeData vol = new VolumeData(res);
					publisher.pubVolumData("anat", vol.getCode(), vol);
				}
			}
		}

		for (Reservoir reserv : reservsNe) {
			VolumesNe ret = api.getReservoirNEVolum(reserv.getCodigo_reservatorio(), start, end);
			if (ret != null && ret.getReservatorios() != null && ret.getReservatorios().size() > 0) {
				for (VolumeNe res : ret.getReservatorios()) {
					VolumeData vol = new VolumeData(res);
					publisher.pubVolumData("anat", vol.getCode(), vol);
				}
			}
		}

	}

	//@Test
	public void testFormatter() {
		LocalDateTime today = LocalDateTime.now();

		String todayString = formatter.format(today);
		System.out.println("From:  " + todayString);

		LocalDateTime dateAgain = LocalDateTime.parse(todayString, formatter);

		System.out.println("TO: " + dateAgain);
	}

	public LocalDateTime getFirstDate(String since) {

		LocalDateTime refDate = getToday();

		if (since != null) {
			refDate = LocalDateTime.parse(since, formatter);
		}

		return refDate;
	}

	public LocalDateTime getToday() {
		return new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public String getNextDate(LocalDateTime refDate) {
		LocalDateTime next = refDate.plusDays(1);
		LocalDateTime today = getToday();

		if (next.isAfter(today)) {
			return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(today);
		}

		return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(next);
	}

}