package GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * 
 * @author Waldemar Smirnow
 * @author Volha Baranouskaya
 */
public class Layout {

	/**
	 * Fügt die übergebene Komponennte in den übergebenen Kontainer an der Pos. x,y ein
	 * @param container zu füllender Kontainer
	 * @param component die einzusetzende Komponennte
	 * @param x Pos x
	 * @param y Posy
	 */
	public static void addComponent(Container container, Component component, 	
			int x, int y) {
		Layout.addComponent(container, component, x, y, 1, 1, 0.0, 0.0);	
	}
	
	/**
	 * Fügt die übergebene Komponennte in den übergebenen Kontainer an der Pos. x,y  und weiteren übergebenen Werten ein
	 * @param container zu füllender Kontainer
	 * @param component die einzusetzende Komponennte
	 * @param x Pos x
	 * @param y Pos y
	 * @param gridwidth einzunehmende Zeilen#
	 * @param gridheight einzunehmende Spalten#
	 * @param widthx analog zu GridBagConstraints widthx
	 * @param widthy analog zu GridGagConstraints widthy
	 */
	public static void addComponent(Container container, Component component, 	
									int x, int y, 
									int gridwidth, int gridheight,
									double widthx, double widthy) {
	
		addComponent(container, component, x, y, gridwidth, gridheight, widthx, widthy, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 3, 3));
	}
	/**
	 * Fügt die übergebene Komponennte in den übergebenen Kontainer an der Pos. x,y  und weiteren übergebenen Werten ein
	 * @param container zu füllender Kontainer
	 * @param component die einzusetzende Komponennte
	 * @param x Pos x
	 * @param y Pos y
	 * @param gridwidth einzunehmende Zeilen#
	 * @param gridheight einzunehmende Spalten#
	 * @param widthx analog zu GridBagConstraints widthx
	 * @param widthy analog zu GridBagConstraints widthy
	 * @param ipadx analog zu GridBagConstraints ipadx
	 * @param ipady analog zu GridBagConstraints ipady
	 * @param fill analog zu GridBagConstraints fill
	 * @param anchor analog zu GridBagConstraints anchor
	 * @param insets Insets Objekt
	 */
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
