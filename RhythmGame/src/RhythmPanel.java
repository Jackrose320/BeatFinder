import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class RhythmPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final int INITIAL_RADIUS = 50;
	private static long SHRINK_TIME = 1500;
	
	private JLabel score;
	private double clickedCircles=0;
	private double totalCircles=0;
	
	private int circleX, circleY;
	private long circleSpawnTime;
	private Timer spawnTimer;
	
	private Timer shrinkTimer;
	private boolean circleVisible=false;
	
	private Timer flashTimer;
	private boolean isFlashing;
	private int flashCount;
	private static final int MAX_FLASHES=6;
	private static final int FLASH_INTERVAL = 100;
	
	private boolean paused=false;
	
	private Random rand = new Random();
	
	public RhythmPanel(JLabel score) {
		this.score=score;
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(600,400));
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (circleVisible) {
					double distance = Math.sqrt(
							Math.pow(e.getX() - circleX,2) +
							Math.pow(e.getY()-circleY, 2));
					
					long elapsedTime = System.currentTimeMillis() - circleSpawnTime;
					long currentRadius = currentRadius(elapsedTime);
					
					if (distance <= currentRadius) {
						startFlashing();
					}
				}
			}
		});
		
		// Circles spawn every (2) seconds
		this.spawnTimer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateScore();
				spawnNewCircle();
			}
		});
		spawnTimer.start();
	}
	
	protected void spawnNewCircle() {
		if (paused) return;
		
		totalCircles++;
		if (shrinkTimer != null && shrinkTimer.isRunning()) {
			shrinkTimer.stop();
		}
		if (flashTimer != null && flashTimer.isRunning()) {
			flashTimer.stop();
			isFlashing = false;
		}
		
		int panelWidth=getWidth();
		int panelHeight=getHeight();
		
		if (panelWidth <= INITIAL_RADIUS*2 || panelHeight <= INITIAL_RADIUS*2) {
			// if panel is too small:
			circleX = panelWidth/2;
			circleY = panelHeight/2;
		} else {
			circleX = rand.nextInt(panelWidth-2*INITIAL_RADIUS) + INITIAL_RADIUS;
			circleY = rand.nextInt(panelHeight-2*INITIAL_RADIUS) + INITIAL_RADIUS;
			}
		
		circleSpawnTime = System.currentTimeMillis();
		circleVisible=true;
		
		shrinkTimer = new Timer(10, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long elapsedTime = System.currentTimeMillis()-circleSpawnTime;
				int currentRadius = currentRadius(elapsedTime);
				
				if (currentRadius <= 0) {
					shrinkTimer.stop();
					circleVisible=false;
					repaint();
				} else {
					repaint();
				}
			}
		});
		shrinkTimer.start();
	}
	
	private int currentRadius(long elapsedTime) {
		double progress = ((double)elapsedTime / (double) SHRINK_TIME); // percentage of time so far
		int currentRadius = (int) (INITIAL_RADIUS * (1.0-progress)); // radius change over that time
		return Math.max(0, currentRadius); // cannot go negative
	}
	
	private void startFlashing() {
		if (isFlashing) return;
		clickedCircles++;
		
		isFlashing=true;
		flashCount=0;
		
		flashTimer = new Timer(FLASH_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				flashCount++;
				if (flashCount > MAX_FLASHES) {
					flashTimer.stop();
					isFlashing=false;
					repaint();
				} else {
					repaint();
				}
			}
		});
		flashTimer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (circleVisible) {
			long elapsedTime = System.currentTimeMillis()-circleSpawnTime;
			int currentRadius = currentRadius(elapsedTime);
			
			if (currentRadius > 0) {
				Graphics2D g2d = (Graphics2D)g;
				
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				if (isFlashing && (flashCount % 2 == 0)) {
					g2d.setColor(Color.RED);
				} else {
					g2d.setColor(Color.BLUE);
				}
				
				int diameter = currentRadius*2;
				g2d.fillOval(circleX-currentRadius, circleY-currentRadius, diameter, diameter);
			}
		}
		
	}
	
	private static String percentToString(double percent) {
		return String.format("%.3f%%", 100.0*percent);
	}
	
	private void updateScore() {
		
		double percent;
		if (clickedCircles+totalCircles != 0) {
			percent = (double)clickedCircles/(double)totalCircles;
		} else {
			percent = 0;
		}
		
		if (percent <= .5) {
			score.setForeground(Color.RED);
		} else if (percent <= .65) {
			score.setForeground(Color.ORANGE);
		} else if (percent <= .8) {
			score.setForeground(Color.YELLOW);
		} else {
			score.setForeground(Color.GREEN);
		}
		
		score.setText(percentToString(percent));
		score.repaint();
		score.revalidate();
	}
	
	protected void updateDifficulty(int difficulty) {
		
		SHRINK_TIME=3000-difficulty;
		this.revalidate();
	}
	
    private void initializeTimers() {
        if (flashTimer == null) {
            flashTimer = new Timer(FLASH_INTERVAL, new ActionListener() {

    			@Override
    			public void actionPerformed(ActionEvent e) {
    				flashCount++;
    				if (flashCount > MAX_FLASHES) {
    					flashTimer.stop();
    					isFlashing=false;
    					repaint();
    				} else {
    					repaint();
    				}
    			}
    		});
            flashTimer.setRepeats(true);
        }

        if (shrinkTimer == null) {
    		shrinkTimer = new Timer(10, new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				long elapsedTime = System.currentTimeMillis()-circleSpawnTime;
    				int currentRadius = currentRadius(elapsedTime);
    				
    				if (currentRadius <= 0) {
    					shrinkTimer.stop();
    					circleVisible=false;
    					repaint();
    				} else {
    					repaint();
    				}
    			}
    		});
            shrinkTimer.setRepeats(true);
        }
        if (spawnTimer == null) {
        	spawnTimer = new Timer(2000, new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				updateScore();
    				spawnNewCircle();
    			}
    		});
        }
    }
	
	protected void setRestart(boolean value, GamePanel gamePanel) {
		initializeTimers();
		
		
        if (this.shrinkTimer != null) {
            this.shrinkTimer.stop();
        }
        if (this.flashTimer != null) {
            this.flashTimer.stop();
        }
        if (this.spawnTimer != null) {
        	this.spawnTimer.stop();
        }
        
		this.paused=value;
		this.clickedCircles=0;
		this.totalCircles=0;
		
		JOptionPane.showMessageDialog(this, "Your final score: " + score.getText());
		
		JPanel game = gamePanel.game;
		game.removeAll();
		
		game.setBackground(new Color(64, 0, 128));
		
		JLabel CountDown = new JLabel();
		CountDown.setForeground(new Color(255, 255, 255));
		CountDown.setHorizontalAlignment(SwingConstants.CENTER);
		CountDown.setFont(new Font("Sylfaen", Font.BOLD, 99));
		CountDown.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		game.add(CountDown);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		game.add(verticalStrut, BorderLayout.NORTH);
		
		gamePanel.countDown(CountDown, 3);
		
	}
}
