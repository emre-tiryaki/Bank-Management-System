package HelpPackage;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class TextFieldFocusListener implements FocusListener {

	String text;
	
	public TextFieldFocusListener(String text) {
		this.text = text;
	}
	
	@Override
    public void focusGained(FocusEvent e) {
        JTextField textField = (JTextField) e.getSource();
        //System.out.println(textField.getText() + " " +  text);
        if(textField.getText().equals(text)) {
        	textField.setText("");
        }
    }

	@Override
	public void focusLost(FocusEvent e) {
		
	}

}
