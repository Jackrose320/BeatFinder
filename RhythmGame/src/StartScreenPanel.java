import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;

public class StartScreenPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage bgImage;
	private BufferedImage helpImage;
	
	private final JFrame mainframe;

	/**
	 * Create the panel.
	 */
	public StartScreenPanel(GameApp mainapp) {
		this.mainframe=mainapp.frame;
		try {
			InputStream bgStream = getClass().getResourceAsStream("/resources/Background1.jpg");
			bgImage = ImageIO.read(bgStream);
			InputStream helpStream = getClass().getResourceAsStream("/resources/Help.png");
			helpImage = ImageIO.read(helpStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	setLayout(new BorderLayout(0, 0));
	
	
	JPanel TitlePanel = new TitlePanel();
	TitlePanel.setPreferredSize(new Dimension(100, 100));
	TitlePanel.setLayout(null);
	add(TitlePanel, BorderLayout.NORTH);
	
	JPanel AboutPanel = new JPanel();
	AboutPanel.setOpaque(false);
	AboutPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
	AboutPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
	add(AboutPanel, BorderLayout.SOUTH);
	AboutPanel.setLayout(new BoxLayout(AboutPanel, BoxLayout.X_AXIS));
	
	JPanel AboutImageContainer = new JPanel() {
		private static final long serialVersionUID = 1L;
		@Override
		public void paintComponent(Graphics g) {
			if (helpImage != null) {
				g.drawImage(helpImage, 0,0, getWidth(), getHeight(), this);
			} else {
	            g.setColor(new Color(50, 50, 100));
	            g.fillRect(0, 0, getWidth(), getHeight());
			}
		}
	};
	AboutImageContainer.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			AboutDialog.show(AboutImageContainer);
		}
	});
	AboutImageContainer.setPreferredSize(new Dimension(40, 40));
	AboutImageContainer.setMaximumSize(new Dimension(40,40));
	AboutImageContainer.setMinimumSize(new Dimension(40,40));
	AboutPanel.add(AboutImageContainer);
	
	
	JSplitPane StartScreenButtons = new JSplitPane();
	StartScreenButtons.setEnabled(false);
	StartScreenButtons.setOpaque(false);
	StartScreenButtons.setContinuousLayout(false);
	StartScreenButtons.addComponentListener(new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			int width = StartScreenButtons.getWidth();
			StartScreenButtons.setDividerLocation(width/2);
		}
	});
	add(StartScreenButtons, BorderLayout.CENTER);
	
	JButton StartButton = new JButton("START");
	StartButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
	StartButton.addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			StartButton.setForeground(new Color(58, 105, 40));
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			mainapp.startGame();
		}
	});
	StartButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.BLACK));
	StartButton.setForeground(new Color(0, 0, 0));
	StartButton.setBackground(new Color(76, 175, 80));
	StartScreenButtons.setLeftComponent(StartButton);
	
	JButton ExitButton = new JButton("EXIT");
	ExitButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
	ExitButton.addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			ExitButton.setForeground(Color.RED);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			int choice = JOptionPane.showOptionDialog(mainframe, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, e);
			if (choice == JOptionPane.YES_OPTION) {
				mainframe.dispatchEvent(new WindowEvent(mainframe, WindowEvent.WINDOW_CLOSING));
			} else {
				ExitButton.setForeground(Color.WHITE);
			}
		}
	});
	ExitButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.BLACK));
	ExitButton.setForeground(new Color(255, 255, 255));
	ExitButton.setBackground(new Color(255, 0, 0));
	
	StartScreenButtons.setRightComponent(ExitButton);
	StartScreenButtons.setDividerLocation(90);
	
	Component LeftStartStrut = Box.createHorizontalStrut(91);
	add(LeftStartStrut, BorderLayout.WEST);
	
	Component RightStartStrut = Box.createHorizontalStrut(91);
	add(RightStartStrut, BorderLayout.EAST);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (bgImage != null) {
			g.drawImage(bgImage, 0,0, getWidth(), getHeight(), this);
		} else {
            g.setColor(new Color(50, 50, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
