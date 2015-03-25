package io.swagger.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponses;
import org.oncoblocks.data_block.model.Gene;
import org.oncoblocks.data_block.model.Mutation;
import org.oncoblocks.data_block.mongo.GeneMongo;
import org.oncoblocks.data_block.mongo.MutationMongo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

@Path("/mutations")
@Api(value = "/mutations", description = "the mutations API")
public class MutationsApi {

    @GET
    // MutationData
    @ApiOperation(value = "Get mutation data.", notes = "Retrieves mutation data by mutation properties, or gene.", response = Mutation.class, responseContainer = "List")
    @ApiResponses(value = {})

    public Response mutationsGet(
        @ApiParam(value = "Gene Symbol.") @QueryParam("gene_symbol") String geneSymbol,
        @ApiParam(value = "Case Id.") @QueryParam("case_id") String caseId,
        @ApiParam(value = "Entrez Gene ID(s).") @QueryParam("entrez_id") Long entrezId)
        throws NotFoundException, IOException {
        ArrayList<Mutation> mutationList = new ArrayList<Mutation>();
        MutationMongo mutationMongo = new MutationMongo();

        if (entrezId != null) {
            mutationList = mutationMongo.getMutationsByEntrezId(entrezId);
        } else if (geneSymbol != null) {
            GeneMongo geneMongo = new GeneMongo();
            ArrayList<Gene> geneList = geneMongo.getGeneBySymbol(geneSymbol);
            System.out.println("Got gene list:  " + geneList.size() + geneSymbol);
            if (geneList != null && geneList.size() > 0) {
                Gene gene = geneList.get(0);
                mutationList = mutationMongo.getMutationsByEntrezId(gene.getEntrezGeneId());
            }
        } else if (caseId != null) {
            mutationList = mutationMongo.getMutationsByCaseIdId(caseId);
        }
        return Response.ok().entity(toJson(mutationList)).build();
    }

    private String toJson(Object obj) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        return json;
    }
}
