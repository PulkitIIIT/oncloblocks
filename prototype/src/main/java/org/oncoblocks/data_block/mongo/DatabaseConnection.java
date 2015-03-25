package org.oncoblocks.data_block.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class DatabaseConnection {
    // Can use this boolean to use cassandra or any other db in future instead of MongoDb
    private static final boolean useCassandra = false;
    public static final String DB_NAME = "cgds";
    private static DatabaseConnection con = null;
    private DB db = null;
    private MongoClient mongoClient;

    /**
     * Private Constructor, to ensure Singleton pattern.
     *
     * @throws UnknownHostException
     */
    private DatabaseConnection() throws UnknownHostException {
        if (useCassandra) {

        } else {
            this.mongoClient = new MongoClient();
            this.db = mongoClient.getDB(DB_NAME);
        }
    }

    /**
     * Returns Singleton Instance.
     *
     * @return DatabaseConnection.
     * @throws UnknownHostException
     */
    public static synchronized DatabaseConnection getInstanceClass() throws UnknownHostException {
        if (con == null) {
            con = new DatabaseConnection();
        }
        return con;
    }

    public GenomicDataBaseTable getTable(String collectionName) {
        if (useCassandra) {
            return getCassandraTable(collectionName);
        } else {
            return getMongoTable(collectionName);
        }
    }

    private GenomicDataBaseTable getMongoTable(String collectionName) {
        return new MongoTable(db.getCollection(collectionName));
    }


    // This function still has to get a correct implementation
    private GenomicDataBaseTable getCassandraTable(String collectionName) {
        // TODO need to pass in correct params
        return new CassandraTable(null);
    }
}