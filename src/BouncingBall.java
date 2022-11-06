import java.awt.Color;
import java.awt.Graphics;

/**
 * represents a bouncing ball in a 2D plane
 * @author Cindy Wu
 * @version 1.0 - 11/06/2020
 */
class BouncingBall {
	
	/**
	 * X coordinate of the ball's upper left corner 
	 */
	private double x;
	
	/**
	 * Y coordinate of the ball's upper left corner 
	 */
	private double y;
	
	/**
	 * horizontal velocity
	 */
	private double velX;
	
	/**
	 * vertical velocity
	 */
	private double velY;
	
	/**
	 * colour of the ball
	 */
	private Color colour;
	
	private String s;
	
	/**
	 * constructor
	 * @param x - X coordinate of upper left corner
	 * @param y - Y coordinate of upper left corner
	 * @param velX - horizontal velocity
	 * @param velY - vertical velocity
	 * @param c - color of the ball
	 */
	public BouncingBall(String s, double x, double y, double velX, double velY, Color c) {
		this.s = s;
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.colour = c;
	}
	
	/**
	 * draws the ball on the screen
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(this.colour);
		g.fillOval((int)x, (int)y, 10, 10);
		g.drawString(this.s, (int)x, (int)y);
	}

	/**
	 * returns the X coordinate of the upper left corner
	 * @return x
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * returns the Y coordinate of the upper left corner
	 * @return y
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * returns the horizontal velocity
	 * @return velX - horizontal velocity
	 */
	public double getVelX() {
		return this.velX;
	}
	
	/**
	 * returns the vertical velocity
	 * @return velY - vertical velocity
	 */
	public double getVelY() {
		return this.velY;
	}
	
	/**
	 * changes the horizontal/vertical velocity of the ball to the respective given values
	 * @param velX - new horizontal velocity
	 * @param velY - new vertical velocity
	 */
	public void changeVel(double velX, double velY) {
		this.velX = velX;
		this.velY = velY;
	}

	/**
	 * updates the position and velocity of the ball(if it hits the screen boundaries)
	 */
	public void update() {
		
		//screen boundaries
		if (x+20> 1024) {
			velX *= -1;
			x = 1024-20;
		}
		
		if (x < 0) {
			velX *= -1;
			x = 0;
		}
		
		if (y+20 > 1024) {
			velY *= -1;
			y = 1024-20;
		}
		
		if (y < 0) {
			velY*=-1;
			y = 0;
		}
		
		this.x += velX;
		this.y += velY;
	}

}
