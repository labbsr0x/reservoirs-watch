package br.com.labbs.agrows.reservoirs.watch;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.com.labbs.agrows.reservoirs.watch.dto.Reservoir;
import br.com.labbs.agrows.reservoirs.watch.dto.interfaces.IVolumeData;
import br.com.labbs.agrows.reservoirs.watch.logger.RequestLogger;

@Path("")
@RegisterRestClient
@RegisterProvider(RequestLogger.class)
public interface DataPublisher {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/owner/{owner}/thing/{code}/node/volume")
	public String pubVolumData(@PathParam("owner") String owner, @PathParam("code") String code, IVolumeData volum);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/owner/{owner}/thing/{code}/node/volume")
	public String pubVolumDataList(@PathParam("owner") String owner, @PathParam("code") String code, List<IVolumeData> volums);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/owner/{owner}/thing/{code}/node/volume")
	public IVolumeData getVolumData(@PathParam("owner") String owner, @PathParam("code") String code);
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/owner/{owner}/thing/{code}")
	public void putReservoirMetadata(@PathParam("owner") String owner, @PathParam("code") String code, Reservoir reservoir);

}
