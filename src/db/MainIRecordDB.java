package db;

import java.util.ArrayList;
import java.util.List;

import db.interfaces.IRecord;
import db.interfaces.IRecordDB;
import db.interfaces.IdentifiableRecord;

public class MainIRecordDB implements IRecordDB{
	
	private List<IdentifiableRecord> records;
	private long idCounter;
	private IRecord lastAddedIRecord;
	
	public MainIRecordDB(){
		records = new ArrayList<IdentifiableRecord>();
		idCounter = 0;
		
	}
	private long nextId(){
		idCounter++;
		return idCounter;
	}
	@Override
	public void addRecord(IRecord r) {
		IdentifiableRecord idRecord = new MainIdentifiableRecord(nextId(), r);
		records.add(idRecord);
		setLastAddedRecord(r);
	}

	@Override
	public void removeRecord(int index) {
		records.remove(index);
		
	}

	@Override
	public void removeRecord(long id) {
		IdentifiableRecord r =this.findRecordWithId(id);
		if(r != null){
			records.remove(r);
		}
	}

	@Override
	public IRecord getRecord(int index) {
		return records.get(index).getRecord();
		
	}

	@Override
	public IRecord getRecord(long id) {
		IdentifiableRecord r =this.findRecordWithId(id);
		IRecord ir = null;
		if(r != null){
			ir = r.getRecord();
		}
		return ir;
	}

	@Override
	public List<IRecord> getAllRecords() {
		List<IRecord> allRecords = new ArrayList<IRecord>();
		for(IdentifiableRecord ir:records){
			allRecords.add(ir.getRecord());
		}
		return allRecords;
	}
	private IdentifiableRecord findRecordWithId(long id){
		IdentifiableRecord r = null;
		int i = 0;
		while(r == null && i<records.size()){
			if(records.get(i).getId() == id){
				r = records.get(i);
			}
			i++;
		}
		return r;
	}
	@Override
	public boolean contains(IRecord r) {
		boolean contains = false;
		for(IdentifiableRecord ir :records){
			if(ir.getRecord().compareTo(r)==0){
				contains = true;
			}
		}
		return contains;
	}
	public IRecord getLastAddedRecord() {
		return lastAddedIRecord;
	}
	public void setLastAddedRecord(IRecord lastAddedIRecord) {
		this.lastAddedIRecord = lastAddedIRecord;
	}

}
