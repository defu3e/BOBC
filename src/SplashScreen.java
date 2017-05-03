import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class SplashScreen extends JFrame {
	/**
	 * Класс для показа заставки программы
	 */
	private static final long serialVersionUID = 1L;
	private JLabel imglabel;
    private ImageIcon img;
    private static JProgressBar pbar;

public SplashScreen() 
  {
	 super ("BOBC");
	 setSize(375, 427);
     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     setLocationRelativeTo(null);
     setUndecorated(true);
     Color sevsu_color = new Color(9, 82, 130);
     img = new ImageIcon(getClass().getResource("/imgs/splash_screen.png"));
     
     imglabel = new JLabel(img);
     add(imglabel);
     setLayout(null);
     pbar = new JProgressBar();
     pbar.setMinimum(0);
     pbar.setMaximum(100);
     pbar.setStringPainted(false);
     pbar.setForeground(sevsu_color);
     imglabel.setBounds(0, 0, 375, 393);
     add(pbar);
     pbar.setPreferredSize(new Dimension(500, 40));
     pbar.setBounds(0, 395, 375, 30);
     
     setBackground(Color.WHITE);
     setVisible(true);
     
     int i=0;
     while (i <= 100) {
         pbar.setValue(i);
         try {
		     Thread.sleep(15);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    }
         i++;
     }
     
     dispose();
  }
}