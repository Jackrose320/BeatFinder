import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class TitlePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TitlePanel() {
		
	}
	
		@Override
		public void paintComponent(Graphics g) {
			setOpaque(false);
			super.paintComponent(g);
			// Transform to 2D:
			assert g instanceof Graphics2D;
			Graphics2D g2d = (Graphics2D) g;
			
			// Reduce edge noise!
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// Gradient
			GradientPaint gradient = new GradientPaint(0,0, new Color(128, 128, 255),0,getHeight(),Color.PINK);
			// Font
            Font font = new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 82);
            g2d.setFont(font);
			// Font placement
			FontMetrics fm = g2d.getFontMetrics();
			int textWidth = fm.stringWidth("BEAT FINDER");
			int x = (getWidth()-textWidth)/2;
			int y = (getHeight()+fm.getAscent() - fm.getDescent())/2;
			
			// Border
            FontRenderContext frc = g2d.getFontRenderContext();
            TextLayout tl = new TextLayout("BEAT FINDER", font, frc);
            Shape textShape = tl.getOutline(null);
            AffineTransform transform = AffineTransform.getTranslateInstance(x, y);
            Shape transformedTextShape = transform.createTransformedShape(textShape);
            float borderWidth = 5;
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.setColor(Color.BLACK);
            g2d.draw(transformedTextShape);
			
			g2d.setPaint(gradient);
			g2d.drawString("BEAT FINDER", x, y);
		}

}
