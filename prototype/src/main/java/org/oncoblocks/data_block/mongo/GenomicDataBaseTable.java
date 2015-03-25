package org.oncoblocks.data_block.mongo;

import com.mongodb.DBCursor;

public interface GenomicDataBaseTable {

    public void addData(Object o);

    public long getCount();

    public DBCursor executeQuery(Object o);

    public DBCursor getAll();

    public void deleteAllRecords();
}
