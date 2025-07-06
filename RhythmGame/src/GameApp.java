import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

public class GameApp {

	protected JFrame frame;
	
	protected Music music;
	private InputStream startMusic = getClass().getResourceAsStream("/resources/StartMusic.wav");
	private InputStream gameMusic = getClass().getResourceAsStream("/resources/GameMusic.wav");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameApp window = new GameApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameApp() {
		this.music = new Music();
		music.setStream(startMusic);
		music.play();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Rhythm Game");
		
		InputStream iconImage = getClass().getResourceAsStream("/resources/Icon.png");
		try { frame.setIconImage(ImageIO.read(iconImage)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new StartScreenPanel(this);
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		frame.setLocationRelativeTo(null);
	}
	
	protected void startGame() {
		this.music.stop();
		gameMusic = getClass().getResourceAsStream("/resources/GameMusic.wav");
		music.setStream(gameMusic);
		this.music.play();
		
		JPanel panel = new GamePanel(this);
		frame.getContentPane().removeAll();
		frame.setContentPane(panel);
		
		frame.revalidate();
		frame.repaint();
	}
	
	protected void switchToStart() {
		this.music.stop();
		startMusic = getClass().getResourceAsStream("/resources/StartMusic.wav");
		music.setStream(startMusic);
		this.music.play();
		
		JPanel panel = new StartScreenPanel(this);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setBounds(100, 100, 450, 300);
		frame.revalidate();
		frame.repaint();
	}
}
