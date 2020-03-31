/* Created by andreea on 29/03/2020 */
package Presentation;

import javax.swing.*;

public class PanelPuntos extends JPanel {

    public JRadioButton generarRandomDistribucionNormalButton;
    public JRadioButton generarRandomButton;
    private ButtonGroup tipoDistribucionButtons;
    private JButton startButton;
    private boolean isDistribucionGaussiana;

    public PanelPuntos(){
        initComponents();
        isDistribucionGaussiana = false;
    }

    private void initComponents(){
        startButton = new JButton();
        startButton.setText("Empezar ejecución");
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
        this.add(startButton);
        this.setVisible(true);
    }
}
