/* Created by andreea on 29/03/2020 */
package Presentation;

import Application.DCController;

import javax.swing.*;
import java.awt.*;

public class PanelPuntos extends JPanel{

    public JRadioButton generarRandomDistribucionNormalButton;
    public JRadioButton generarRandomButton;
    private ButtonGroup tipoDistribucionButtons;
    private JButton startButton;
    private boolean isDistribucionGaussiana;
    private boolean nubePuntosCreada;
    private PanelCoordenadas panelCoordenadas;


    private DCController controller;

    public PanelPuntos(DCController controller){
        this.controller = controller;
        initComponents();
        isDistribucionGaussiana = false;
        nubePuntosCreada = false;
    }

    private void initComponents(){
        this.setLayout(new BorderLayout());
        startButton = new JButton();
        startButton.setText("Empezar ejecución");
        startButton.addActionListener(e -> {
            controller.setearParametrosElegidos();
            controller.inicializarPuntos();
            controller.pintarPuntos();
            controller.start();
        });
        generarRandomButton = new JRadioButton();
        generarRandomButton.setText("Distribución Aleatoria");
        generarRandomButton.setSelected(true);
        generarRandomButton.addActionListener(e -> {
            controller.disableGaussianElements();
            panelCoordenadas.setGaussianDistribution(false);
            panelCoordenadas.repaint();
            //cambiarGrafo(false);
        });

        generarRandomDistribucionNormalButton = new JRadioButton();
        generarRandomDistribucionNormalButton.setText("Distribución Gaussiana");
        generarRandomDistribucionNormalButton.addActionListener(e -> {
            controller.enableGaussianElements();
            panelCoordenadas.setGaussianDistribution(true);
            panelCoordenadas.repaint();
            //cambiarGrafo(true);
        });
        JPanel generarButtonsPanel = new JPanel();
        generarButtonsPanel.setSize(new Dimension(560, 50));
        tipoDistribucionButtons = new ButtonGroup();
        generarButtonsPanel.add(generarRandomButton);
        tipoDistribucionButtons.add(generarRandomButton);
        generarButtonsPanel.add(generarRandomDistribucionNormalButton);
        tipoDistribucionButtons.add(generarRandomDistribucionNormalButton);
        generarButtonsPanel.add(startButton);

        panelCoordenadas = new PanelCoordenadas(controller);
        controller.setPanelCoordenadas(panelCoordenadas);
        panelCoordenadas.setSize(new Dimension(560, 560));

        panelCoordenadas.setVisible(true);
        this.add(generarButtonsPanel, BorderLayout.NORTH);
        this.add(panelCoordenadas, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}
