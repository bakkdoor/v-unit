package GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LayoutMangment {

	public static void addComponent(Container container, Component component,
									int x, int y, 
									int gridwidth, int gridheight, 
									double widthx, double widthy, 
									int ipadx, int ipady, 
									int fill, int anchor,
									int insets1, int insets2, 
									int insets3, int insets4) {

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
		gridBagConstMainContainer.insets = new Insets(insets1, insets2,
				insets3, insets4);
		container.add(component, gridBagConstMainContainer);
	}
}
