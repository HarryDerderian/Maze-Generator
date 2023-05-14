import javax.swing.SwingUtilities;

import GUI.Frame;
public class Main
{
    public static void main(String[] args)
    {
        // Run the G.U.I. main loop:
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new Frame();
            }
        });
    }
}