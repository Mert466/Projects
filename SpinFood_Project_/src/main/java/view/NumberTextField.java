package view;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Represents a text field that only allows input of numeric values up to a maximum value.
 * Non-numeric input is ignored.
 */
public class NumberTextField extends JTextField {
    private int max_value;

    /**
     * Constructs a new NumberTextField with the specified maximum value.
     *
     * @param max_value the maximum value allowed in the text field
     */
    public NumberTextField(int max_value) {
        this.max_value = max_value;
        setDocument(new NumberDocument());
    }

    private class NumberDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }

            String currentText = getText(0, getLength());
            String newText = currentText.substring(0, offs) + str + currentText.substring(offs);

            try {
                int number = Integer.parseInt(newText);
                if (number <= max_value) {
                    super.insertString(offs, str, a);
                }
            } catch (NumberFormatException e) {
                // Ignoring non-numeric input
            }
        }
    }
}