package FinalProject_SourceFiles;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class ThreadedGuiForPhysicsEngine {

	/* Author: Michael Fairbank
	 * Creation Date: 2016-01-28
	 * Significant changes applied: 2020-04-04 by Ishwar Venugopal
	 */
	
	public ThreadedGuiForPhysicsEngine() {
	}

	private static JButton jButton_go;
	private static Thread theThread;
	
	public static void main(String[] args) throws Exception {
		
		Gameplay game = new Gameplay (0,0);
		
		final BasicView view = new BasicView(game);
		JComponent mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(view, BorderLayout.CENTER);
		JPanel sidePanel=new JPanel();
		sidePanel.setLayout(new FlowLayout());
		
		jButton_go=new JButton("Reset");
		sidePanel.add(jButton_go);
		mainPanel.add(sidePanel, BorderLayout.WEST);
		
		JComponent topPanel=new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(new JLabel(""));
		mainPanel.add(topPanel, BorderLayout.NORTH);
		
		JEasyFrame frame = new JEasyFrame(mainPanel, "SPIN");
		view.addKeyListener(new BasicKeyListener());

		ActionListener listener=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==jButton_go) {
					try {
						
						// recreate all particles in their original positions:
						final Gameplay game2 = new Gameplay (0,0);
						
						// Tell the view object to start displaying this new Physics engine instead:
						view.updateGame(game2);

						view.requestFocus();// needed for keyboard listener to work 
						startThread(game2, view); // start a new thread for the new game object:
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		jButton_go.addActionListener(listener);
	}
	
	private static void startThread(final Gameplay game, final BasicView view) throws InterruptedException {
	    Runnable r = new Runnable() {
	         public void run() {
	        	// this while loop will exit any time this method is called for a second time, because 
	    		while (theThread==Thread.currentThread()) {
    				game.update();
    				view.repaint();
	    			try {
						Thread.sleep(Gameplay.DELAY);
					} catch (InterruptedException e) {
					}
	    		}
	         }
	     };

	     theThread=new Thread(r);// this will cause any old threads running to self-terminate
	     theThread.start();
	}
	
}


