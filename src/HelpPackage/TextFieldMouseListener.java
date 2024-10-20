package HelpPackage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class TextFieldMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		JTextField textField = (JTextField) e.getSource();
		if(!textField.isFocusable()) {
			textField.setFocusable(true);
        	textField.requestFocus();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
