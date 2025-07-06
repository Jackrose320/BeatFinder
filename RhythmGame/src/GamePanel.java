import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	RhythmPanel circles;
	protected JPanel game;
	private JSlider difficulty;
	protected JLabel score;
	protected JButton restartButton;

	/**
	 * Create the panel.
	 */
	public GamePanel(GameApp app) {
		setLayout(new BorderLayout(0, 0));
		app.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JSplitPane splitPane = new JSplitPane();
		splitPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				splitPane.setDividerLocation(getWidth()/3);
			}
		});
		splitPane.setResizeWeight(1.0);
		splitPane.setEnabled(false);
		add(splitPane);
		
		JPanel gameinfo = new JPanel();
		gameinfo.setBackground(new Color(192, 192, 192));
		splitPane.setLeftComponent(gameinfo);
		gameinfo.setLayout(new BorderLayout(0, 0));
		
		JPanel TitlePanel = new TitlePanel();
		TitlePanel.setPreferredSize(new Dimension(100, 100));
		gameinfo.add(TitlePanel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(32767, 300));
		panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel.setOpaque(false);
		panel.setBorder(null);
		gameinfo.add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		
		JLabel accuracyLabel = new JLabel("Accuracy:");
		panel.add(accuracyLabel);
		accuracyLabel.setVerticalAlignment(SwingConstants.TOP);
		accuracyLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 300));
		panel_1.setMaximumSize(new Dimension(32767, 300));
		panel_1.setOpaque(false);
		gameinfo.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		this.score = new JLabel("100.000%");
		score.setFont(new Font("Sylfaen", Font.PLAIN, 72));
		panel_1.add(score);
		
		JPanel panel_2 = new JPanel();
		panel_2.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_2.setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(255, 0, 255)));
		panel_2.setPreferredSize(new Dimension(150, 300));
		panel_2.setMinimumSize(new Dimension(100, 400));
		gameinfo.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JButton exitButton = new JButton("LEAVE");
		exitButton.setAlignmentY(Component.TOP_ALIGNMENT);
		exitButton.setMaximumSize(new Dimension(32767, 50));
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(exitButton);
		exitButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				app.switchToStart();
			}
		});
		exitButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		exitButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		exitButton.setForeground(new Color(255, 255, 255));
		exitButton.setBackground(new Color(255, 0, 0));
		exitButton.setMinimumSize(new Dimension(500, 100));
		exitButton.setPreferredSize(new Dimension(500, 100));
		
		this.restartButton = new JButton("RESTART");
		this.restartButton.setEnabled(false);
		
		this.restartButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if (circles != null) {
					circles.setRestart(true, GamePanel.this);
				} else {
					System.err.println("Cannot restart RhythmPanel: Game elements are not yet initialized.");
				}
			}
		});
		restartButton.setPreferredSize(new Dimension(500, 100));
		restartButton.setMinimumSize(new Dimension(500, 100));
		restartButton.setMaximumSize(new Dimension(32767, 50));
		restartButton.setForeground(Color.WHITE);
		restartButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		restartButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		restartButton.setBackground(Color.RED);
		restartButton.setAlignmentY(0.0f);
		restartButton.setAlignmentX(0.5f);
		panel_2.add(restartButton);
		
		Component verticalGlue = Box.createVerticalGlue();
		verticalGlue.setMinimumSize(new Dimension(0, 30));
		panel_2.add(verticalGlue);
		
		JLabel SliderLabel = new JLabel("Difficulty: 50");
		SliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		SliderLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_2.add(SliderLabel);
		
		difficulty = new JSlider(1000,2000);
		difficulty.setEnabled(false);
		difficulty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				
				circles.updateDifficulty(value);
				
				SliderLabel.setText("DIFFICULTY: " + (value/10 - 100));
				SliderLabel.setForeground(new Color((int)(value/7.9),0,0));
				
				SliderLabel.revalidate();
				SliderLabel.repaint();
				
			}
		});
		difficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
		difficulty.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_2.add(difficulty);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		verticalGlue_1.setMinimumSize(new Dimension(0, 30));
		panel_2.add(verticalGlue_1);
		
		this.game = new JPanel();
		game.setBackground(new Color(64, 0, 128));
		splitPane.setRightComponent(game);
		game.setLayout(new BorderLayout(0, 0));
		
		JLabel CountDown = new JLabel();
		CountDown.setForeground(new Color(255, 255, 255));
		CountDown.setHorizontalAlignment(SwingConstants.CENTER);
		CountDown.setFont(new Font("Sylfaen", Font.BOLD, 99));
		CountDown.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		game.add(CountDown);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		game.add(verticalStrut, BorderLayout.NORTH);
		splitPane.setDividerLocation(150);
		
		countDown(CountDown, 3);
	}
	
	private Timer gameTimer;
	protected void countDown(JLabel countdown, int seconds) {
		this.restartButton.setEnabled(false);
		this.gameTimer = new Timer(1000, new ActionListener() {
			AtomicInteger atomint = new AtomicInteger(seconds);
			@Override
			public void actionPerformed(ActionEvent e) {
				int curr = atomint.getAndDecrement();
				if (curr > 0) {
					countdown.setText(curr+"");
				} else if (curr==0) {
					countdown.setText("GO!");
				} else {
					gameTimer.stop();
					
					game.remove(countdown);
					game.removeAll();
					GamePanel.this.circles = new RhythmPanel(score);
					restartButton.setEnabled(true);
					
					game.add(circles, BorderLayout.CENTER);
					
					game.revalidate();
					game.repaint();
					
					SwingUtilities.invokeLater(() -> {
						difficulty.setEnabled(true);
						circles.spawnNewCircle();
					});
				}
			}
			
		});
		gameTimer.start();
	}
}
