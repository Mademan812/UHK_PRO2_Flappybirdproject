package cz.uhk.pro2.flappy_bird.game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import cz.uhk.pro2.flappy_bird.game.World;
import cz.uhk.pro2.flappy_bird.service.CsvWorldLoader;
import cz.uhk.pro2.flappy_bird.service.WorldLoader;

public class MainWindow extends JFrame {
	BoardPanel panel = new BoardPanel();
	World world;
	long x = 0;
	
	class BoardPanel extends JPanel{
		@Override
		public void paint(Graphics g){
			super.paint(g); //draws plain panel
			world.draw(g); //draws the world
		}
	}
	
	public MainWindow(){
		try(InputStream is = new FileInputStream("flappy-level.csv")){
			WorldLoader loader = new CsvWorldLoader(is);
			world = loader.loadLevel();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//world = new World();
		add(panel, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(200, world.getHeightPix()));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				//method kickTheBird
				world.changeBirdiesVelocity();
			}
		});
		
		Timer t = new Timer(20, e -> {
			world.tick(x++);
			panel.repaint();
		});
		
		t.start();
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(()->{
			MainWindow w = new MainWindow();
			w.setVisible(true);
		});
	}
}
