package db;

import db.interfaces.IRecord;
import db.interfaces.IdentifiableRecord;

public class MainIdentifiableRecord implements IdentifiableRecord{
	long id;
	IRecord record;
	
	public MainIdentifiableRecord(long id,IRecord r){
		this.setId(id);
		this.setRecord(r);
	}

	@Override
	public long getId() {
		return this.id;
	}
	private void setId(long id){
		this.id = id;
	}

	@Override
	public IRecord getRecord(){
		return this.record;
	}

	@Override
	public void setRecord(IRecord r){
		this.record = r;
	}
}
