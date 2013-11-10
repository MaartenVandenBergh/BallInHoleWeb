package ballInHole.model.gameElements;

public class Circle implements ICircle{
	private static final float bouncyNess = 0.60f;
	
	private float x;
	private float y;
	private float velX;
	private float velY;
	
	private float r;
	
	public Circle(float x,float y,float r){
		setX(x);
		setY(y);
		setR(r);
	}

	@Override
	public void setR(float r) {
		this.r = r;
		
	}
	@Override
	public float getR(){
		return r;
	}
	@Override
	public void setY(float y) {
		this.y = y;
		
	}
	@Override
	public float getY(){
		return y;
	}
	@Override
	public void setX(float x) {
		this.x = x;
		
	}
	@Override
	public float getX(){
		return x;
	}

	@Override
	public void updatePosition(float x, float y,float z, long timestamp) {
		float dt = 5*((System.nanoTime()-timestamp)/1000000000.0f);
		velX += -x*dt;
		velY += y*dt;
		
		this.x+= velX*dt;
		this.y+= velY*dt;
		
		
	}

	@Override
	public boolean reactToCollisionWithBorders(int horizontalBorder, int verticalBorder) {
		if (x > horizontalBorder-r) {
            x = horizontalBorder-r;
            velX = -velX * bouncyNess;
        } else if (x < 0+r) {
            x = 0+r;
            velX = -velX * bouncyNess;
        }
        if (y > verticalBorder-r) {
            y = verticalBorder-r;
            velY = -velY * bouncyNess;
        } else if (y < 0+r) {
            y = 0+r;
            velY = -velY * bouncyNess;
        }
        return true;
	}
	@Override
	public boolean reactToCollisionWithGameElement(ICircle ci){
		boolean in = false;
		if((x+r)<(ci.getX()+ci.getR()) && (x-r)>(ci.getX()-ci.getR())){
			if((y+r)<(ci.getY()+ci.getR()) && (y-r)>(ci.getY()-ci.getR())){
				in = true;
			}
		}
		return in;
	}
}
