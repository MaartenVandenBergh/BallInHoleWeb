package db.interfaces;

public interface IRecord extends Comparable<IRecord>{
	int getHours();
	void setHours(int h);
	int getMinutes();
	void setMinutes(int m);
	int getSeconds();
	void setSeconds(int s);
}
