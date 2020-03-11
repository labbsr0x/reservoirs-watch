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
public interface AnaSarScrapper {
	
	@GET
	@Path("/ReservatoriosSIN")
	@Produces(MediaType.APPLICATION_XML)
	public String getReservoirsSinData();
	
	@GET
	@Path("/ReservatoriosNordeste")
	@Produces(MediaType.APPLICATION_XML)
	public String getReservoirsNe();
	
	@GET
	@Path("/DadosHistoricosSIN")
	@Produces(MediaType.APPLICATION_XML)
	public String getReservoirSinValues(@QueryParam("CodigoReservatorio") Integer code, @QueryParam("DataInicial") String since, @QueryParam("DataFinal")String to);
	
	@GET
	@Path("/DadosHistoricosReservatorios")
	@Produces(MediaType.APPLICATION_XML)
	public String getReservoirValues(@QueryParam("CodigoReservatorio") Integer code, @QueryParam("DataInicial") String since, @QueryParam("DataFinal")String to);
	
	@GET
	@Path("/DadosHistoricosNordeste")
	@Produces(MediaType.APPLICATION_XML)
	public String getReservoirNeValues(@QueryParam("CodigoReservatorio") Integer code, @QueryParam("DataInicial") String since, @QueryParam("DataFinal")String to);
	
	
}
