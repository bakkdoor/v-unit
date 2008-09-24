package GUI;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class OnFocusClearTextFieldListener implements FocusListener {
	private JTextField textField = null;

	public OnFocusClearTextFieldListener(JTextField textField,
			String defaultText) {
		this.textField = textField;
		this.textField.setText(defaultText);
	}

	@Override
	public void focusGained(FocusEvent e) {
		textField.setText("");
	}

	@Override
	public void focusLost(FocusEvent e) {
	}
}
