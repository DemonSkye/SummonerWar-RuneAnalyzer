import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Damien on 2/25/2017.
 */
public class runeAnalyzer {
    private JButton selectSwproxyRunsFileButton;
    private JTextField textField1;

    public runeAnalyzer() {
        selectSwproxyRunsFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(selectSwproxyRunsFileButton);
            }
        });
    }
}
