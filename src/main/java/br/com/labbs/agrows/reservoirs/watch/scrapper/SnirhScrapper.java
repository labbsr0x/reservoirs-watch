package br.com.labbs.agrows.reservoirs.watch.scrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.com.labbs.agrows.reservoirs.watch.logger.RequestLogger;

@Path("")
@RegisterRestClient
@RegisterProvider(RequestLogger.class)
public interface SnirhScrapper {

	@GET
	@Path("/Massa_d_Agua_Regiao_Amazonica/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	//?where=objectid_1 < 10&returnGeometry=false&f=geojson&outFields=objectid_1, nooriginal
	public String getReservoirsGeojsonAM(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Araguaia_Tocantins/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonART(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Atlantico_Nordeste_Ocidental/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonANO(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Atlantico_Nordeste_Oridental/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonANOr(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Atlantico_Sudeste/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonASud(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Atlantico_Sul/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonASul(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Paraguai/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonPG(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Parana/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonPR(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Paranaiba/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonPRN(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Sao_Francisco/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonSF(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_Regiao_Uruguai/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonUR(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

	@GET
	@Path("/Massa_d_Agua_RHI_Atlantico_Leste/FeatureServer/0/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReservoirsGeojsonAL(@QueryParam("where") String where, @QueryParam("returnGeometry") Boolean bol, @QueryParam("f") String formats,
			@QueryParam("returnDistinctValues") Boolean distinct, @QueryParam("outFields") String outFileds);

}
