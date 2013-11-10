package ballInHole.model.gameElements;

public interface IGameElement {
	float getX();
	void setX(float x);
	
	float getY();
	void setY(float y);
	
	void updatePosition(float x, float y, float z,long timestamp);
	boolean reactToCollisionWithBorders(int horizontalBorder, int verticalBorder);
	boolean reactToCollisionWithGameElement(ICircle ci);
}
