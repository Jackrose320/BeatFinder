import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

public class AboutDialog {
	
	public static void show(Component parent) {
		
        Color oldBg = UIManager.getColor("OptionPane.background");
        Color oldPanelBg = UIManager.getColor("Panel.background");
        Color oldMsgFg = UIManager.getColor("OptionPane.messageForeground");
        Color oldBtnBg = UIManager.getColor("Button.background");
        Color oldBtnFg = UIManager.getColor("Button.foreground");
        changeUI();
		
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("🎵 BeatFinder: Rhythm Challenge 🎵");
        title.setForeground(Color.CYAN);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea info = new JTextArea(
                "BeatFinder is a rhythm-based reaction game where timing is everything!\n\n"
              + "🔹 Circles appear and shrink — your goal is to click them before they vanish.\n"
              + "🔹 The faster you react, the better your accuracy.\n"
              + "🔹 Your score (shown in color) reflects your precision and rhythm.\n"
              + "🔹 Green = Excellent, Yellow/Orange = Needs work, Red = Try harder!\n\n"
              + "🎮 Tip: Focus and time your clicks as close to the circle's center as possible!\n\n"
              + "Made with ❤️ by ra4"
            );
        info.setEditable(false);
        info.setOpaque(false);
        info.setFont(new Font("SansSerif", Font.PLAIN, 14));
        info.setForeground(Color.WHITE);
        info.setWrapStyleWord(true);
        info.setLineWrap(true);
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JScrollPane scrollPane = new JScrollPane(info);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(scrollPane);
        
        JOptionPane.showMessageDialog(parent, panel, "About BeatFinder", JOptionPane.INFORMATION_MESSAGE);
        
        UIManager.put("OptionPane.background", oldBg);
        UIManager.put("Panel.background", oldPanelBg);
        UIManager.put("OptionPane.messageForeground", oldMsgFg);
        UIManager.put("Button.background", oldBtnBg);
        UIManager.put("Button.foreground", oldBtnFg);
	}
	
	private static void changeUI() {

        // Set dark theme for the dialog
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", Color.DARK_GRAY);
        UIManager.put("Button.foreground", Color.WHITE);
	}
}
