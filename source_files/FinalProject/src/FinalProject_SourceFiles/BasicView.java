package FinalProject_SourceFiles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class BasicView extends JComponent {

	/* Author: Michael Fairbank
	 * Creation Date: 2016-01-28
	 */

	// background colour
	public static final Color BG_COLOR = Color.WHITE;

	private Gameplay game;

	public BasicView(Gameplay game) {
		this.game = game;
	}
	
	@Override
	public void paintComponent(Graphics g0) {

		Gameplay game;

		synchronized(this) {
			game=this.game;
		}

		Graphics2D g = (Graphics2D) g0;
		// paint the background
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (BasicParticle p : game.particles)
			p.draw(g);
		for (BasicRectangle r: game.rectangles)
			r.draw(g);
		for (AnchoredBarrier b : game.barriers)
			b.draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return Gameplay.FRAME_SIZE;
	}
	
	public synchronized void updateGame(Gameplay game) {
		this.game=game;
	}
}