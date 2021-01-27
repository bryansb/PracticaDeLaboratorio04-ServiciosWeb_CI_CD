package ec.edu.ups.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.ProductWarehouseFacade;
import ec.edu.ups.entities.ProductWarehouse;

@Path("/order/")
public class OrderResource {
	
	@EJB
	private ProductWarehouseFacade productWarehouseFacade;
	
	@GET
    @Path("/findProducts/{warehouseId}/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getProductListByCategoryIdAndWarehouseId(
			@PathParam("warehouseId") Integer warehouseId, 
			@PathParam("categoryId") Integer categoryId) {
		Jsonb jsonb = JsonbBuilder.create();
		
		List<ProductWarehouse> productWarehouses = productWarehouseFacade.findByWarehouseAndCategoryId(warehouseId, categoryId, "");		
		System.out.println(productWarehouses.size());
		return Response.ok(jsonb.toJson(productWarehouses))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
	}

}
