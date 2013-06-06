package com.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missile {
	private int x, y;

	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	public static final int XSPEED = 30;
	public static final int YSPEED = 30;
	
	private boolean live = true;
	
	private Direction direction;

	private TankClient tc;

	public Missile(int x, int y, Direction direction, TankClient tc) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if(!live){
			return ;
		}
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}
	
	
	
//	private class PaintThread implements Runnable{
//		public void run() {
//			switch (direction) {
//			case L:
//				x -= XSPEED;
//				break;
//			case LU:
//				x -= XSPEED;
//				y -= YSPEED;
//				break;
//			case U:
//				y -= YSPEED;
//				break;
//			case RU:
//				x += XSPEED;
//				y -= YSPEED;
//				break;
//			case R:
//				x += XSPEED;
//				break;
//			case RD:
//				x += XSPEED;
//				y += YSPEED;
//				break;
//			case D:
//				y += YSPEED;
//				break;
//			case LD:
//				x -= XSPEED;
//				y += YSPEED;
//				break;
//			default:
//				break;
//			}
//			
//			try {
//				Thread.sleep(20);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	private void move(){
		switch (direction) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		default:
			break;
		}
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean hitTank(Tank t){
		if(this.getRectangle().intersects(t.getRectangle()) && t.isLive()){
			tc.add(new Explode(this.getX(), this.getY(), tc));
			t.setLive(false);
			setLive(false);
			return true;
		}
		return false;	
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void hitTank(List<Tank> tanks) {
		for (Tank tank : tanks) {
			hitTank(tank);
		}
	}
}
