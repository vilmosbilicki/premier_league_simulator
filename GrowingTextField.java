package Nagyhazi;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


/**
 * Olyan szövegdoboz, amelynek mérete dinamikusan nő a beleírt szöveggel együtt.
 * A JTextField osztály leszármazottja
 * @author Bilicki Vilmos
 */
public class GrowingTextField extends JTextField {
    private final int minWidth;
    private final int maxHeight = 25;


    /**
     * Konstruktor, létrehoz egy GrowingTextfield objektumot a paraméterként megadott alapszélességgel
     * @param columns az alapszélesség
     */
    public GrowingTextField(int columns) {
        super(columns);
        minWidth = super.getPreferredSize().width;
        setPreferredSize(new Dimension(getPreferredSize().width, maxHeight));
        setMinimumSize(new Dimension(minWidth, maxHeight));
        setMaximumSize(new Dimension(Short.MAX_VALUE, maxHeight));


        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { adjustWidth(); }
            @Override
            public void removeUpdate(DocumentEvent e) { adjustWidth(); }
            @Override
            public void changedUpdate(DocumentEvent e) { adjustWidth(); }
        });
    }

    /**
     * ez segít igazítani a GrowingTextField objektumot
     */
    private void adjustWidth() {
        FontMetrics fm = getFontMetrics(getFont());
        int textWidth = fm.stringWidth(getText());
        int padding = 10;
        int width = Math.max(minWidth, textWidth + padding);
        setPreferredSize(new Dimension(width, maxHeight));
        revalidate();
    }
}
