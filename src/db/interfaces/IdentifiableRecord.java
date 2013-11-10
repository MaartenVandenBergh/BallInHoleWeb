package db.interfaces;

public interface IdentifiableRecord {
	public long getId();
	public IRecord getRecord();
	public void setRecord(IRecord r);

}