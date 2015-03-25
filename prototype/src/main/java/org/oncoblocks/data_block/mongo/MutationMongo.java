package org.oncoblocks.data_block.mongo;

import com.google.gson.Gson;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import org.oncoblocks.data_block.model.Mutation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Mutation Mongo Interface.
 */
public class MutationMongo {
    private static final String MUTATION_COLLECTION = "mutations";
    private GenomicDataBaseTable table;

    /**
     * Default constructor.
     *
     * @throws java.net.UnknownHostException Unknown Mongo DB Host.
     */
    public MutationMongo() throws UnknownHostException {
        DatabaseConnection dbConnection = DatabaseConnection.getInstanceClass();
        table = dbConnection.getTable(MUTATION_COLLECTION);
    }

    /**
     * Adds a mutation record to the database.
     *
     * @param mutation Mutation Object.
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public void addMutation(Mutation mutation) {
        Gson gson = new Gson();
        String json = gson.toJson(mutation);
        DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(json);
        table.addData(dbObject);
    }

    /**
     * Gets total number of mutations in the database.
     *
     * @return number of mutations in the database.
     */
    public long getNumMutations() {
        return this.table.getCount();
    }

    /**
     * Gets mutations by Entrez Gene ID.
     *
     * @return Gene Object.
     */
    public ArrayList<Mutation> getMutationsByEntrezId(Long entrezGeneId) {
        QueryBuilder query = new QueryBuilder().start("entrezGeneId").is(entrezGeneId);
        return executeQuery(query);
    }

    /**
     * Gets mutations by Case ID.
     *
     * @return Gene Object.
     */
    public ArrayList<Mutation> getMutationsByCaseIdId(String caseId) {
        QueryBuilder query = new QueryBuilder().start("caseId").is(caseId);
        return executeQuery(query);
    }


    private ArrayList<Mutation> executeQuery(QueryBuilder query) {
        ArrayList<Mutation> mutationList = new ArrayList<Mutation>();
        DBCursor dbCursor = this.table.executeQuery(query.get());
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            Gson gson = new Gson();
            Mutation mutation = gson.fromJson(dbObject.toString(), Mutation.class);
            mutationList.add(mutation);
        }
        return mutationList;
    }

    /**
     * Deletes all Records in the Database.
     */
    public void deleteAllRecords() {
        this.table.deleteAllRecords();
    }
}