package io.swagger.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponses;
import org.oncoblocks.data_block.model.Gene;
import org.oncoblocks.data_block.mongo.GeneMongo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

@Path("/gene")
@Api(value = "/gene", description = "the genes API")
public class GeneApi {

    @GET
    // GeneData
    @ApiOperation(value = "Get gene data.", notes = "Retrieves gene data by gene symbol.", responseContainer = "List")
    @ApiResponses(value = {})

    public Response geneGet(
        @ApiParam(value = "Gene Symbol.") @QueryParam("gene_symbol") String geneSymbol)
        throws NotFoundException, IOException {
        ArrayList<Gene> geneList = new ArrayList<Gene>();
        if (geneSymbol != null) {
            GeneMongo geneMongo = new GeneMongo();
            geneList = geneMongo.getGeneBySymbol(geneSymbol);
        }
        return Response.ok().entity(toJson(geneList)).build();
    }

    private String toJson(Object obj) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        return json;
    }
}
