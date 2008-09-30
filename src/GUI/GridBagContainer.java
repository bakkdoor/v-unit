package GUI;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

/**
 * 
 * @author Waldemar Smirnow
 * @author Volha Baranouskaya
 */
public class GridBagContainer extends JPanel {

    /**
     * Konstruktor, erstellt ein Container mit GridBagLayout
     */
    public GridBagContainer() {
        
        super();
        super.setLayout(new GridBagLayout());
    }
    
	/**
	 * Fügt die übergebene Komponennte in den übergebenen Kontainer an der Pos. x,y ein
	 * @param container zu füllender Kontainer
	 * @param component die einzusetzende Komponennte
	 * @param x Pos x
	 * @param y Posy
	 */
	public void addComponent(Component component, 	
                                            int x, int y) {
		addComponent(component, x, y, 1, 1, 0.0, 0.0);	
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
	public void addComponent(Component component, 	
                                            int x, int y, 
                                            int gridwidth, int gridheight,
                                            double widthx, double widthy) {
	
		addComponent(component, x, y, gridwidth, gridheight, widthx, widthy, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BELOW_BASELINE, new Insets(3, 3, 3, 3));
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
	public void addComponent(Component component,
                                            int x, int y, 
                                            int gridwidth, int gridheight, 
                                            double widthx, double widthy, 
                                            int ipadx, int ipady, 
                                            int fill, int anchor,
                                            Insets insets) {

		GridBagConstraints gridBagConst = new GridBagConstraints();

		gridBagConst.gridx = x;
		gridBagConst.gridy = y;
		gridBagConst.gridwidth = gridwidth;
		gridBagConst.gridheight = gridheight;
		gridBagConst.weightx = widthx;
		gridBagConst.weighty = widthy;
		gridBagConst.ipadx = ipadx;
		gridBagConst.ipady = ipady;
		gridBagConst.fill = fill;
		gridBagConst.anchor = anchor;
		gridBagConst.insets = insets;
		add(component, gridBagConst);
	}
}
