package org.oncoblocks.data_block.mongo;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


// TODO This class is still work under progress. Need to implement Cassandra functionalities
public class CassandraTable implements GenomicDataBaseTable {
  private DBCollection collection;

  public CassandraTable(DBCollection collection) {
    this.collection = collection;
  }

  public void addData(Object o) {
    collection.save((DBObject)o);
  }

  public long getCount() {
    return collection.getCount();
  }

  public DBCursor executeQuery(Object o) {
    return collection.find((DBObject)o);
  }

  public DBCursor getAll() {
    return collection.find();
  }

  public void deleteAllRecords() {
    collection.drop();
  }
}
