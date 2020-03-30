/* Created by andreea on 29/03/2020 */
package Presentation;

import javax.swing.*;

public class PanelPuntos extends JPanel {

    public JRadioButton generarRandomDistribucionNormalButton;
    public JRadioButton generarRandomButton;
    private ButtonGroup tipoDistribucionButtons;


    public PanelPuntos(){
        initComponents();
    }

    private void initComponents(){
        generarRandomButton = new JRadioButton();
        generarRandomButton.setText("Distribución Aleatoria");
        generarRandomButton.setSelected(true);
        generarRandomDistribucionNormalButton = new JRadioButton();
        generarRandomDistribucionNormalButton.setText("Distribución Gaussiana");

        tipoDistribucionButtons = new ButtonGroup();
        this.add(generarRandomButton);
        tipoDistribucionButtons.add(generarRandomButton);
        this.add(generarRandomDistribucionNormalButton);
        tipoDistribucionButtons.add(generarRandomDistribucionNormalButton);

        //this.add(tipoDistribucionButtons);
        this.setVisible(true);
    }
}
