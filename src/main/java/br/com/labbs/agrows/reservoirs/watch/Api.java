package br.com.labbs.agrows.reservoirs.watch;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.geojson.Feature;
import org.geojson.FeatureCollection;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.labbs.agrows.reservoirs.watch.dto.ReservoirsNe;
import br.com.labbs.agrows.reservoirs.watch.dto.ReservoirsSin;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumesGeneric;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumesNe;
import br.com.labbs.agrows.reservoirs.watch.dto.VolumesSin;
import br.com.labbs.agrows.reservoirs.watch.scrapper.AnaSarScrapper;
import br.com.labbs.agrows.reservoirs.watch.scrapper.SnirhScrapper;

@RequestScoped
public class Api {

	@Inject
	@RestClient
	AnaSarScrapper anaSarScrapper;

	@Inject
	@RestClient
	SnirhScrapper snirhScrapper;
	
	ObjectMapper mapper = new ObjectMapper();

	public ReservoirsNe getReservoirsNeData() {
		return unmarshal(ReservoirsNe.class, anaSarScrapper.getReservoirsNe());
	}

	public ReservoirsSin getReservoirsSinData() {
		return unmarshal(ReservoirsSin.class, anaSarScrapper.getReservoirsSinData());
	}

	public VolumesGeneric getReservoirVolum(Integer code, String since, String to) {
		return unmarshal(VolumesGeneric.class, anaSarScrapper.getReservoirValues(code, since, to));
	}

	public VolumesSin getReservoirSinVolum(Integer code, String since, String to) {
		return unmarshal(VolumesSin.class, anaSarScrapper.getReservoirSinValues(code, since, to));
	}

	public VolumesNe getReservoirNEVolum(Integer code, String since, String to) {
		return unmarshal(VolumesNe.class, anaSarScrapper.getReservoirNeValues(code, since, to));
	}

	public FeatureCollection getGeojson() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		FeatureCollection coll = null;
		
		try {
			coll = mapper.readValue(snirhScrapper.getReservoirsGeojsonAM("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class);
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonAM("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonAM("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonART("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonART("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonART("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonANOr("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonANOr("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonANOr("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonASud("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonASud("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonASud("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonANO("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonANO("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonANO("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonASul("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonASul("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonASul("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonPG("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonPG("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonPG("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonASul("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonASul("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonASul("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonPRN("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonPRN("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonPRN("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonSF("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonSF("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonSF("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonUR("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonUR("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonUR("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonAL("objectid_1 < 1000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonAL("objectid_1 > 999", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonAL("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
			coll.addAll(mapper.readValue(snirhScrapper.getReservoirsGeojsonAL("objectid_1 > 2000", false, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return coll;
	}
	
	private List<Feature> callUntil(Function<Integer, List<Feature>> call) {
		
		List<Feature> ret = new ArrayList<>();
		
		
		
		return ret;
	}
	
	private List<Feature> callWrapperAM(Integer value) {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<Feature> ret = new ArrayList<>();
		try {
			ret = mapper.readValue(snirhScrapper.getReservoirsGeojsonAM(getWhereIds(value), true, "geojson", true, "objectid_1, nooriginal"),
					FeatureCollection.class).getFeatures();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	private String getWhereIds(Integer value) {
		
		Integer max = value * 1000;
		Integer min = max - 1000;
		
		return String.format("objectid_1 > %1$d AND objectid_1 < %1$d", min, max);
	}

	private String getWhereClause(String name) {
		return String.format("nooriginal = '%s'", name);
	}

	@SuppressWarnings("unchecked")
	private <T> T unmarshal(Class<?> tipo, String xml) {
		T ret = null;
		StringReader reader = new StringReader(xml);
		try {
			ret = (T) JAXBContext.newInstance(tipo).createUnmarshaller().unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return ret;
	}

	}