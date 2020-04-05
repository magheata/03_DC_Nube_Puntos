/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Presentation;

import Application.DCController;
import Domain.Variables;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que contiene los puntos que se quieren examinar. Además, también contiene los elementos de control para seleccionar
 * el tipo de distribución de los puntos, así como también el notón encargado de arrancar el proceso de cálculo
 */
public class PanelPuntos extends JPanel{

    public JRadioButton generarRandomDistribucionNormalButton;
    public JRadioButton generarRandomButton;
    private ButtonGroup tipoDistribucionButtons;
    private JButton startButton;
    private PanelCoordenadas panelCoordenadas;
    private DCController controller;

    public PanelPuntos(DCController controller){
        this.controller = controller;
        initComponents();
    }

    private void initComponents(){
        this.setLayout(new BorderLayout());
        startButton = new JButton();
        startButton.setText(Variables.BTN_START_EMPEZAR);
        startButton.addActionListener(e -> {
            // Se recogen el algoritmo de ordenación y el coste asintótico deseado para poder realzizar la ejecución
            controller.setearParametrosElegidos();
            // En el caso de que el ususario haya especificado estos parámetros se prosigue con la ejecución
            if (controller.isParametrosSeteados()){
                // Inicializamos la nube de puntos
                controller.inicializarPuntos();
                // Pintamos los puntos en el PanelPuntos
                controller.pintarPuntos();
                // Comenzamos el proceso de cálculo de las distancias
                controller.start();
            }
            startButton.setText(Variables.BTN_START_DETENER);
        });
        generarRandomButton = new JRadioButton();
        generarRandomButton.setText(Variables.BTN_RANDOM_TEXT);
        generarRandomButton.setSelected(true);
        generarRandomButton.addActionListener(e -> {
            // Se deshabilitan los botones de media y varianza de los puntos
            controller.disableGaussianElements();
            panelCoordenadas.setGaussianDistribution(false);
            panelCoordenadas.repaint(); // Repintamos para que los ejes de coordenadas de modifiquen
        });

        generarRandomDistribucionNormalButton = new JRadioButton();
        generarRandomDistribucionNormalButton.setText(Variables.BTN_GAUSSIAN_TEXT);
        generarRandomDistribucionNormalButton.addActionListener(e -> {
            // Se habilitan los botones de media y varianza de los puntos
            controller.enableGaussianElements();
            panelCoordenadas.setGaussianDistribution(true);
            panelCoordenadas.repaint(); // Repintamos para que los ejes de coordenadas de modifiquen
        });
        JPanel generarButtonsPanel = new JPanel();
        generarButtonsPanel.setSize(new Dimension(Variables.WIDTH_PANELPUNTOS, 50));
        tipoDistribucionButtons = new ButtonGroup();
        generarButtonsPanel.add(generarRandomButton);
        tipoDistribucionButtons.add(generarRandomButton);
        generarButtonsPanel.add(generarRandomDistribucionNormalButton);
        tipoDistribucionButtons.add(generarRandomDistribucionNormalButton);
        generarButtonsPanel.add(startButton);

        panelCoordenadas = new PanelCoordenadas(controller);
        controller.setPanelCoordenadas(panelCoordenadas);
        panelCoordenadas.setSize(new Dimension(Variables.WIDTH_PANELCOORDENADAS, Variables.HEIGHT_PANELCOORDENADAS));

        panelCoordenadas.setVisible(true);
        this.add(generarButtonsPanel, BorderLayout.NORTH);
        this.add(panelCoordenadas, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void setTextStartButton(String text){
        startButton.setText(text);
    }
}
