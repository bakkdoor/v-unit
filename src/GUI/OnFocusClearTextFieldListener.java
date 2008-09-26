package GUI;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * 
 * @author Waldemar Smirnow
 * @author Volha Baranouskaya
 */
public class OnFocusClearTextFieldListener implements FocusListener {
	private JTextField textField = null;

	/**
	 * Konstruktor für OnFocusClearTextFieldListener
	 * @param textField wo der Text gesetzt und bei Fokus gelöschts werden soll
	 * @param defaultText zu setzender Text
	 */
	public OnFocusClearTextFieldListener(JTextField textField,
			String defaultText) {
		this.textField = textField;
		this.textField.setText(defaultText);
	}

	/**
	 * Fokus bekommen
	 */
	
	public void focusGained(FocusEvent e) {
		textField.setText("");
	}
	
	/**
	 * Fokus verlohren
	 */
	
	public void focusLost(FocusEvent e) {
	}
}
