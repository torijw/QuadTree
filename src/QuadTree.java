import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * QuadTree that stores a boundary and a list of balls that fit in it. 
 * Tree splits into 4 children trees, each with a smaller boundary, once threshold is reached
 * @author Cindy Wu
 * @version 1.0 - 11/06/2020
 */
class QuadTree {

	/**
	 * max number of levels/subdivisions the tree can have
	 */
	private static int MAXDEPTH = 6;

	/**
	 * max number each leaf can hold until it has to subdivide 
	 */
	private static int THRESHOLD = 5;

	/**
	 * the tree's children; each child is a QuadTree
	 */
	private QuadTree[] children;

	/**
	 * list of all the balls that fit in the tree's boundary
	 */
	private SingleLinkedList<BouncingBall> balls;

	/**
	 * represents the boundary that the tree is responsible for
	 */
	private Rectangle boundary;

	/**
	 * the level of the current tree respective of the entire tree it is a part of
	 */
	private int level;

	/**
	 * constructor
	 * @param boundary
	 */
	public QuadTree(Rectangle boundary, int level) {
		this.children = new QuadTree[4];
		this.balls = new SingleLinkedList<BouncingBall>();
		this.boundary = boundary;
		this.level = level;
	}

	/**
	 * draws the tree's boundaries including it's children's
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(boundary.x, boundary.y, boundary.width, boundary.height);

		if (!isLeaf()) {
			for (QuadTree child:children) {
				child.draw(g);
			}
		}
	}

	/**
	 * divides the tree's boundaries into 4 equal quadrants and assigns them to the children
	 */
	private void subdivide() {
		int width = boundary.width/2;
		int height = boundary.height/2;
		int x = boundary.x;
		int y = boundary.y;

		children[0] = new QuadTree(new Rectangle(x, y, width, height), level+1);
		children[1] = new QuadTree(new Rectangle(x+width, y, width, height), level+1);
		children[2] = new QuadTree(new Rectangle(x, y+height, width, height), level+1);
		children[3] = new QuadTree(new Rectangle(x+width, y+height, width, height), level+1);
	}

	/**
	 * returns the quadrant the given ball lies in the specified boundary
	 * @param ball
	 * @param bounds
	 * @return the quadrant/child index the ball belongs too
	 */
	private int getQuadrant(BouncingBall ball, Rectangle bounds) {
		//gets the child the item belongs to

		double midX = bounds.getX() + bounds.getWidth()/2;
		double midY = bounds.getY() + bounds.getHeight()/2;

		//above midX line
		if (ball.getY()+10<=midY) {
			//child 0
			if (ball.getX()<=midX) {
				return 0;
			}
			//child 1
			return 1;
		}

		//below midX line
		else {
			//child 2
			if (ball.getX()+10<=midX) {
				return 2;
			} 
			//child 3
			return 3;
		}
	}

	/**
	 * inserts a ball into the tree
	 * @param ball
	 */
	public void insert(BouncingBall ball) {

		//add to current first
		balls.add(ball);

		//add current into children if any
		if (!this.isLeaf()) {
			//add into appropriate child
			int quadrant = getQuadrant(ball, getBounds());

			if (quadrant!=-1) {
				children[quadrant].insert(ball);
			}
			return;
		}

		//if exceeds threshold, tree splits
		if (balls.size()>=THRESHOLD && level<=MAXDEPTH) { 
			subdivide();

			//split current balls into corresponding children
			for (int i=0;i<balls.size();i++) {
				int quadrant = getQuadrant(balls.get(i), getBounds());


				if (quadrant!=-1) {
					children[quadrant].insert(balls.get(i));
				}
			}
		}
	}

	/**
	 * returns the tree's boundary
	 * @return boundary
	 */
	public Rectangle getBounds() {
		return this.boundary;
	}

	/**
	 * collapses/merges the tree's children into the parent
	 */
	private void collapse() {

		if (!isLeaf()) {
			children = new QuadTree[4];
		}	
	}

	/**
	 * removes the specified item from the tree
	 * @param item 
	 */
	@SuppressWarnings("unused")
	private void remove(BouncingBall item) {

		//removes from parent list first
		balls.remove(item);

		//check if number of balls is less than threshold and current tree is not a leaf --> collapse children
		if (balls.size()<THRESHOLD && !isLeaf()) {
			collapse();
			return;
		}

		//iterate through each level/child to remove 
		for (QuadTree child:children) {
			if (child!=null) {
				child.remove(item);
			}
		}
	}

	/**
	 * returns the list of balls that are found in the leaf tree that contains the given ball
	 * @param ball
	 * @return list of balls in proximity to the ball
	 */
	public SingleLinkedList<BouncingBall> retrieve (BouncingBall ball) {
		if (isLeaf()) {
			return balls;
		}
		int quadrant = getQuadrant(ball, getBounds());
		return children[quadrant].retrieve(ball);
	}

	/**
	 * checks if the tree is a leaf
	 * @return true if it is a leaf, false otherwise
	 */
	private boolean isLeaf() {
		return children[0] == null;
	}

	/**
	 * clears the entire tree 
	 */
	public void clear() {
		balls.clear();
		children = new QuadTree[4];
	}

}
