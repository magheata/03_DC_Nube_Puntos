/* Created by andreea on 29/03/2020 */
package Presentation;

import Application.DCController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class PanelPuntos extends JPanel {

    public JRadioButton generarRandomDistribucionNormalButton;
    public JRadioButton generarRandomButton;
    private ButtonGroup tipoDistribucionButtons;
    private JButton startButton;
    private boolean isDistribucionGaussiana;

    private DCController controller;

    public PanelPuntos(DCController controller){
        initComponents();
        this.controller = controller;
        isDistribucionGaussiana = false;
    }

    private void initComponents(){
        startButton = new JButton();
        startButton.setText("Empezar ejecución");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setearParametrosElegidos();
                try {
                    controller.crearNubePunto();
                } catch (ExecutionException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                controller.start();
            }
        });
        generarRandomButton = new JRadioButton();
        generarRandomButton.setText("Distribución Aleatoria");
        generarRandomButton.setSelected(true);
        generarRandomButton.addActionListener(e -> controller.disableGaussianElements());

        generarRandomDistribucionNormalButton = new JRadioButton();
        generarRandomDistribucionNormalButton.setText("Distribución Gaussiana");
        generarRandomDistribucionNormalButton.addActionListener(e -> controller.enableGaussianElements());

        tipoDistribucionButtons = new ButtonGroup();
        this.add(generarRandomButton);
        tipoDistribucionButtons.add(generarRandomButton);
        this.add(generarRandomDistribucionNormalButton);
        tipoDistribucionButtons.add(generarRandomDistribucionNormalButton);
        this.add(startButton);
        this.setVisible(true);
    }
}
