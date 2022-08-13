import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable{
	
	static final int GAME_WIDTH = 750;
	static final int GAME_HEIGHT = 500;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 15;
	static final int PADDLE_HEIGHT = 75;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	Random random;
	
	GamePanel(){
		newPaddles();
		newBall();
		score = new Score(GAME_WIDTH,GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new ActionListener());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	public void newBall() {
		ball = new Ball((GAME_WIDTH/2 - BALL_DIAMETER/2),(GAME_HEIGHT/2-BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER);
	}
	
	public void newPaddles() {
		paddle1  = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2  = new Paddle(GAME_WIDTH-PADDLE_WIDTH,((GAME_HEIGHT/2)-(PADDLE_HEIGHT/2)),PADDLE_WIDTH,PADDLE_HEIGHT,2);
	}
	
	public void paint(Graphics g) {
		
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
		
	}
	
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	}
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	public void checkCollision() {
		if(ball.y <= 0)
			ball.setYDirection(-ball.yVelocity);
		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER)
			ball.setYDirection(-ball.yVelocity);
		
		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if(ball.xVelocity<0)
				ball.xVelocity++;
			else
				ball.xVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if(ball.xVelocity<0)
				ball.xVelocity++;
			else
				ball.xVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		if(paddle1.y <= 0)
			paddle1.y = 0;
		if(paddle1.y >= GAME_HEIGHT - PADDLE_HEIGHT)
			paddle1.y= GAME_HEIGHT - PADDLE_HEIGHT;
		if(paddle2.y <= 0)
			paddle2.y = 0;
		if(paddle2.y >= GAME_HEIGHT - PADDLE_HEIGHT)
			paddle2.y= GAME_HEIGHT - PADDLE_HEIGHT;
		
		if(ball.x <= 0) {
			score.player1++;
			newBall();
		}
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player2++;
			newBall();
		}
	}
	public void run() {
		// game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta =0;
		while(true) {
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			if(delta>=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	public class ActionListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
	

}
