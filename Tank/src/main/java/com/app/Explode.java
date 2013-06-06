package com.app;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	
	private int x;
	private int y;
	private TankClient tc;
	
	private boolean live = true;
	
	private int[] diameters = new int[]{6, 10, 15, 25, 40, 30, 20, 10};
	
	private int step = 0;
	
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	public void draw(Graphics g) {
		if(step == diameters.length || !live){
			step = 0;
			live = false;
			return ;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameters[step], diameters[step]);
		g.setColor(c);
		
		step++;
	}
}
