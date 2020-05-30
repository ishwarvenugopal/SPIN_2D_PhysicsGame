package FinalProject_SourceFiles;

import javax.swing.JOptionPane;

public class PopUpScreen {

    /* Author: Ishwar Venugopal
     * Creation Date: 2020-04-04
     */

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}