package com.app;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {
	private static final long serialVersionUID = 1L;

	private static final int GAME_WIDTH = 800;
	private static final int GAME_HEIGHT = 600;

	private Tank tank = new Tank(50, 50, true, this);
	
	private Explode explode = null;
	private List<Missile> missiles = new ArrayList<Missile>();
	private List<Explode> explodes = new ArrayList<Explode>();
	private List<Tank> tanks = new ArrayList<Tank>();
	
	public TankClient() {
		for (int i = 0; i < 10; i++) {
			tanks.add(new Tank(150 + 50 * i , 50, false, this));
		}
	}

	@Override
	public void paint(Graphics g) {
		for (Missile m : missiles) {
			m.hitTank(tanks);
			m.draw(g);
		}
		
		for (Explode e : explodes) {
			e.draw(g);
		}
		
		tank.draw(g);
		
		if(explode != null){
			explode.draw(g);
		}
		
		for (Tank t : tanks) {
			t.draw(g);
		}
		
		g.drawString("Missiles count: " + missiles.size(), 50, 50);
		g.drawString("Explods count: " + explodes.size(), 50, 80);
		g.drawString("Tanks count: " + tanks.size(), 50, 110);
	}

	private Image backScreenImage;

	@Override
	public void update(Graphics g) {
		if (this.backScreenImage == null) {
			backScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}

		// p Image graphics
		Graphics gImage = backScreenImage.getGraphics();
		Color c = gImage.getColor();
		gImage.setColor(Color.GREEN);
		gImage.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gImage.setColor(c);

		// g screen graphics
		paint(gImage);

		g.drawImage(backScreenImage, 0, 0, null);
	}

	public void lauchFrame() {
		this.setLocation(100, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setResizable(false);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.addKeyListener(new KeyMonitor());
		Thread paintThead = new Thread(new PaintThread());
		paintThead.start();
	}

	public static void main(String[] args) {
		TankClient client = new TankClient();
		client.lauchFrame();
	}

	private class PaintThread implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			tank.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			tank.keyReleased(e);
		}
	}

	public void add(Missile missile) {
		this.missiles.add(missile);
	}
	
	public void add(Explode explode ){
		this.explodes.add(explode);
	}

	public List<Tank> getTanks() {
		return tanks;
	}
}
