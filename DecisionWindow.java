package Nagyhazi;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Felugró ablak yes vagy no opcióval, és egy kiírandó szöveggel
 * @author Bilicki Vilmos
 */
public class DecisionWindow extends JDialog {
    private boolean decision;


    /**
     * Konstruktor, létrehozza az adott JFrame szűlővel és String üzenettel rendelkező Decisionwindow-t
     * @param frame ,ez a szülő JFrame objektum
     * @param message ,ez a kiírandó üzenet
     */
    public DecisionWindow(JFrame frame, String message) {
        super(frame, "Result", true);
        JPanel messagePanel = new JPanel();

        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setColumns(20);
        messagePanel.add(textArea);
        JPanel buttonPanel = new JPanel();
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        yesButton.addActionListener(_ -> {
            decision = true;
            dispose();
        });
        noButton.addActionListener(_ -> {
            decision = false;
            dispose();
        });
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        setLayout(new BorderLayout());
        add(messagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setSize(300, 150);
        setLocationRelativeTo(frame);
        Point parentLocation = frame.getLocation();
        int parentX = parentLocation.x;
        int parentY = parentLocation.y;
        int parentWidth = frame.getWidth();
        setLocation(parentX + parentWidth - 20, parentY);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Getter, visszaadja a desicion értékét
     * @return boolean
     */
    public boolean getDecision() {
        return decision;
    }


    /**
     * Megjeleníti az ablakot, rajta egy yes és egy no opcióval
     * @param frame szülő JFrame objektum
     * @param panel szülő JPanel objektum
     */
    public void decisionWindowResult(JFrame frame, JPanel panel) {
        panel.removeAll();
        panel.repaint();
        panel.revalidate();
        setVisible(true);
        setVisible(false);
        if(getDecision()) {
            setVisible(false);
            frame.setSize(400, 300);

        }
        else {
            frame.dispose();

        }
    }
}
