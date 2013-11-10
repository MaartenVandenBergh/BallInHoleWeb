package db.interfaces;

import java.util.List;

public interface IRecordDB {
	void addRecord(IRecord r);
	void removeRecord(int index);
	void removeRecord(long id);
	
	IRecord getRecord(int index);
	IRecord getRecord(long id);
	
	List<IRecord> getAllRecords();
	boolean contains(IRecord r);
	
	IRecord getLastAddedRecord();
}
