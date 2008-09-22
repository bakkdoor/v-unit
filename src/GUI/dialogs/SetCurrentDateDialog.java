package GUI.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import GUI.Layout;

import logging.Logger;
import model.CurrentDate;
import model.Date;
import model.Warning;
import model.events.EventManager;
import model.events.SetCurrentDateEvent;
import model.events.VideothekEvent;
import model.events.VideothekEventListener;
import model.exceptions.CurrentDateException;

/**
 * WarningCreatedInfoDialog.java
 * 
 * @author Christopher Bertels (chbertel@uos.de) 22.09.2008
 */
public class SetCurrentDateDialog extends JDialog implements VideothekEventListener
{
	private static final long serialVersionUID = 2048499314340116962L;
	
	private JTextField textFieldDay = new JTextField();
	private JTextField textFieldMonth = new JTextField();
	private JTextField textFieldYear = new JTextField();
	
	public SetCurrentDateDialog()
	{
		super();
		
		EventManager.registerEventListener(SetCurrentDateEvent.class, this);
		
		this.setModal(true);
		this.setTitle("Tagesdatum angeben");
		this.setSize(new Dimension(250, 70));
		
		createContent();
	}

	private void createContent()
	{
		
		textFieldDay.setSize(50, 50);
		textFieldMonth.setSize(50, 50);
		textFieldYear.setSize(50, 50);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				EventManager.fireEvent(new SetCurrentDateEvent());
			}
		});
		
		okButton.setSize(50, 50);

		this.getContentPane().add(textFieldDay);
		this.getContentPane().add(textFieldMonth);
		this.getContentPane().add(textFieldYear);
		this.getContentPane().add(okButton);

		this.getContentPane().setLayout(new GridLayout(1, 4));
	}

	@Override
	public void handleEvent(VideothekEvent event)
	{
		if(event instanceof SetCurrentDateEvent)
		{
			int day = Integer.parseInt(textFieldDay.getText());
			int month = Integer.parseInt(textFieldMonth.getText());
			int year = Integer.parseInt(textFieldYear.getText());

			try
			{
				Date newCurrentDate = new Date(day, month, year);
				CurrentDate.set(newCurrentDate);
				this.dispose();
			}
			catch (CurrentDateException e1)
			{
				e1.printStackTrace();
			}
		}
	}

}
