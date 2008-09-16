package GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Layout {
	
	// Ausrichtungswerte
	private final int  HORIZONTAL = GridBagConstraints.HORIZONTAL;
	private final int  VERTICAL = GridBagConstraints.VERTICAL;
	private final int  BOTH = GridBagConstraints.BOTH;
	private final int  BELOW_BASELINE = GridBagConstraints.BELOW_BASELINE;
	private final int  NORTHWEST = GridBagConstraints.NORTHWEST;

	public static void addComponent(Container container, Component component, 	
			int x, int y) {
		Layout.addComponent(container, component, x, y, 1, 1, 0.0, 0.0);	
	}
	
	public static void addComponent(Container container, Component component, 	
									int x, int y, 
									int gridwidth, int gridheight,
									double widthx, double widthy) {
	
		addComponent(container, component, x, y, gridwidth, gridheight, widthx, widthy, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 3, 3));
	}
	
	public static void addComponent(Container container, Component component,
									int x, int y, 
									int gridwidth, int gridheight, 
									double widthx, double widthy, 
									int ipadx, int ipady, 
									int fill, int anchor,
									Insets insets) {

		GridBagConstraints gridBagConstMainContainer = new GridBagConstraints();

		gridBagConstMainContainer.gridx = x;
		gridBagConstMainContainer.gridy = y;
		gridBagConstMainContainer.gridwidth = gridwidth;
		gridBagConstMainContainer.gridheight = gridheight;
		gridBagConstMainContainer.weightx = widthx;
		gridBagConstMainContainer.weighty = widthy;
		gridBagConstMainContainer.ipadx = ipadx;
		gridBagConstMainContainer.ipady = ipady;
		gridBagConstMainContainer.fill = fill;
		gridBagConstMainContainer.anchor = anchor;
		gridBagConstMainContainer.insets = insets;
		container.add(component, gridBagConstMainContainer);
	}
}
