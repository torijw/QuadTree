
//Graphics &GUI imports
import java.awt.Color;
import java.awt.Graphics;
//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

class QuadTreeDisplay extends JFrame { 

	static GameAreaPanel gamePanel;  
	static QuadTree tree;
	static SingleLinkedList<BouncingBall> masterList;
	
	public static void main(String[] args) { 
		new QuadTreeDisplay ();
	}

	QuadTreeDisplay () { 

		super("QuadTree Fun!");      
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1024,1024);
		gamePanel = new GameAreaPanel();
		this.add(new GameAreaPanel());    
		MyKeyListener keyListener = new MyKeyListener();
		this.addKeyListener(keyListener);
		this.requestFocusInWindow();    
		this.setVisible(true);

		//***** Initialize the Tree here *****
		tree = new QuadTree(this.getBounds(), 1);
		masterList = new SingleLinkedList<BouncingBall>();

		//Start the game loop in a separate thread (yikes)
		Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
		t.start();

	} //End of Constructor

	//the main gameloop
	public void animate() { 

		while(true){
			try{ Thread.sleep(50);} catch (Exception exc){exc.printStackTrace();}  //delay
			this.repaint();
		}    
	}

	/** --------- INNER CLASSES ------------- **/

	// Inner class for the the game area - This is where all the drawing of the screen occurs
	private class GameAreaPanel extends JPanel {
		
		public void paintComponent(Graphics g) {   
			super.paintComponent(g); //required
			setDoubleBuffered(true); 

			//clear tree
			tree.clear();
			
			//update and re-insert all balls into tree each frame
			for (int i=0;i<masterList.size();i++) {
				masterList.get(i).update();
				tree.insert(masterList.get(i));
				masterList.get(i).draw(g);
			}
			
			//----Check collisions----//
			
			//list of all balls to compare
			SingleLinkedList<BouncingBall> possible = new SingleLinkedList<BouncingBall>();
			
			//list of balls already checked for ball 'i' in masterList 
			SingleLinkedList<BouncingBall> checked = new SingleLinkedList<BouncingBall>();
			
			for (int i=0;i<masterList.size();i++) {
				
				//only check next if it hasn't been already
				if (checked.indexOf(masterList.get(i))==-1) {

					checked.add(masterList.get(i));
					//get corresponding list from the leaf it belongs to
					possible = tree.retrieve(masterList.get(i));
					
					//iterate through all possible balls it can collide with	
					for (int j=0;j<possible.size();j++) {
						
						//distance between centers
						double distance = Math.sqrt(Math.pow(masterList.get(i).getX() - possible.get(j).getX(), 2)
											+ Math.pow(masterList.get(i).getY() - possible.get(j).getY(), 2));
						
						//if the 2 balls are touching, change velocities of both
						if (distance<=20 && distance!=0) {						
							
							//swap velocities
							double tempVelX = masterList.get(i).getVelX();
							double tempVelY = masterList.get(i).getVelY();
								
							masterList.get(i).changeVel(possible.get(j).getVelX(), possible.get(j).getVelY());
							possible.get(j).changeVel(tempVelX, tempVelY);
							
							//add to checked to prevent repeated swapping
							checked.add(possible.get(j));
						}
					}
				}
			}
			
			//draw tree boundaries
			tree.draw(g);
		}
	}

	// -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
	private class MyKeyListener implements KeyListener {

		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {

			//if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  
				//System.out.println("Adding Ball!");
				
			String s = e.getKeyChar()+"";
			
				//randomly generated x, y within bounds
				double x = Math.random()*1024;
				double y = Math.random()*1024;
				
				//randomly generated velocities from -10 to 10
				double velX = (Math.random()*10)*(Math.random()>0.5?1:-1);
				double velY = (Math.random()*10)*(Math.random()>0.5?1:-1);
				
				Random rand = new Random();
				Color c = new Color(rand.nextInt(250), rand.nextInt(250),rand.nextInt(250)); 

				BouncingBall b = new BouncingBall(s, x, y, velX, velY, c);
				
				//add into tree and masterlist
				masterList.add(b);
				tree.insert(b);
				
			//} else 
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { 
				System.out.println("YIKES ESCAPE KEY!"); 
				System.exit(0);
			} 
		}              
	}  
}