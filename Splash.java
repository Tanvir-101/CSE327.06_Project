
import javax.swing.*;
import java.awt.*;

public class Splash {
 
    public void showSplash(int duration) {
        JWindow splash = new JWindow();
        JPanel content = (JPanel) splash.getContentPane();

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
       
        splash.setBounds(0,0,screen.width,screen.height-30);

        // build the splash screen
        JLabel label = new JLabel(new ImageIcon(ClassLoader.getSystemResource("images/splash.JPG")));
        JLabel copyrt = new JLabel("", JLabel.CENTER);
        copyrt.setFont(new Font("Tahoma", Font.BOLD, 10));
        //copyrt.setBorder(BorderFactory.createEtchedBorder());
        content.setBackground(Color.BLACK);
        //content.setBackground(new Color(232, 232, 228));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // display it
        splash.setVisible(true);

        // Wait a little while, maybe while loading resources
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
        }

        splash.setVisible(false);
    }
}
