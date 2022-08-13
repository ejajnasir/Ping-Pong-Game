import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Paddle extends Rectangle{
	
	int id;
	int yVelocity=10;
	int speed = 10;
	
	Paddle(int x, int y,int PADDLE_WIDTH, int PADDLE_HEIGHT,int id){
		super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
			this.id = id;
		
	}
	
	public void keyPressed(KeyEvent e) {
		switch(id) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_W) {
				SetYDirection(-speed);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				SetYDirection(speed);
				move();
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				SetYDirection(-speed);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				SetYDirection(speed);
				move();
			}
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(id) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_W) {
				SetYDirection(0);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				SetYDirection(0);
				move();
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				SetYDirection(0);
				move();
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				SetYDirection(0);
				move();
			}
			break;	
		}
	}
	
	public void SetYDirection(int yDirection) {
		yVelocity = yDirection;
	}
	
	public void move() {
		y = y + yVelocity;
	}
	
	public void draw (Graphics g) {
		if(id==1)
			g.setColor(Color.red);
		else
			g.setColor(Color.blue);
		
		g.fillRect(x, y, width, height);
		
	}

}
