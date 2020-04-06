/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Presentation.Botones;

import Application.DCController;
import Domain.Variables;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que define todos los elementos de control relacionados con la nube de puntos y la distribución de los mismos
 * en caso de que se trate de una distribución Gaussiana
 */
public class BotonesGraph extends Botones{

    private JButton increaseMeanXButton, decreaseMeanXButton, increaseVarianceXButton, decreaseVarianceXButton;
    private JLabel  meanXLabel, varianceXLabel, meanYLabel, varianceYLabel;
    private JTextArea meanXTextArea, varianceXTextArea, meanYTextArea, varianceYTextArea;
    private boolean coordenadaX = true;
    private DCController controller;

    public BotonesGraph(DCController controller, boolean coordenadaX){
        super();
        this.coordenadaX = coordenadaX;
        this.controller = controller;

        inicializarElementosGaussianos();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        buttonsPanel.add(meanXLabel);
        buttonsPanel.add(decreaseMeanXButton);
        buttonsPanel.add(meanXTextArea);
        buttonsPanel.add(increaseMeanXButton);
        buttonsPanel.add(Box.createHorizontalStrut(20)); // Fixed width invisible separator.
        buttonsPanel.add(varianceXLabel);
        buttonsPanel.add(decreaseVarianceXButton);
        buttonsPanel.add(varianceXTextArea);
        buttonsPanel.add(increaseVarianceXButton);

        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.setVisible(true);

        deshabilitarElementosGaussianos();
    }

    private void inicializarElementosGaussianos(){
        meanXLabel = new JLabel();
        meanXLabel.setText(Variables.LABEL_MEAN);

        varianceXLabel = new JLabel();
        varianceXLabel.setText(Variables.LABEL_VARIANCE);

        meanYLabel = new JLabel();
        meanYLabel.setText(Variables.LABEL_MEAN);

        varianceYLabel = new JLabel();
        varianceYLabel.setText(Variables.LABEL_VARIANCE);

        meanXTextArea = new JTextArea();
        meanXTextArea.setText("0");
        meanXTextArea.setOpaque(false);

        varianceXTextArea = new JTextArea();
        varianceXTextArea.setText("1");
        varianceXTextArea.setOpaque(false);

        meanYTextArea = new JTextArea();
        meanYTextArea.setText("0");
        meanYTextArea.setOpaque(false);

        varianceYTextArea = new JTextArea();
        varianceYTextArea.setText("1");
        varianceYTextArea.setOpaque(false);

        inicializarBotonesGaussianos();
    }

    private void inicializarBotonesGaussianos(){
        increaseMeanXButton = new JButton();
        increaseMeanXButton.setText(Variables.LABEL_INCREMENTAR);
        increaseMeanXButton.addActionListener(e -> {
            int media = Integer.parseInt(meanXTextArea.getText());
            if (!(media >= Variables.MEAN_MAX)) {
                media = media + 1;
                meanXTextArea.setText(Integer.toString(media));
                controller.updateGraph(media, -99, coordenadaX);
                controller.setMediaPuntosX(media, coordenadaX);
            }
        });

        decreaseMeanXButton = new JButton();
        decreaseMeanXButton.setText(Variables.LABEL_DECREMENTAR);
        decreaseMeanXButton.addActionListener(e -> {
            int media = Integer.parseInt(meanXTextArea.getText());
            if (!(media <= -Variables.MEAN_MAX)) {
                media = media - 1;
                meanXTextArea.setText(Integer.toString(media));
                controller.updateGraph(media, -99, coordenadaX);
                controller.setMediaPuntosX(media, coordenadaX);
            }
        });

        increaseVarianceXButton = new JButton();
        increaseVarianceXButton.setText(Variables.LABEL_INCREMENTAR);
        increaseVarianceXButton.addActionListener(e -> {
            double varianza = Double.parseDouble(varianceXTextArea.getText());
            if (!(varianza >= Variables.VARIANCE_MAX)) {
                varianza = varianza + 0.25;
                varianceXTextArea.setText(Double.toString(varianza));
                controller.updateGraph(-99, varianza, coordenadaX);
                controller.setVarianzaPuntosX(varianza, coordenadaX);
            }
        });

        decreaseVarianceXButton = new JButton();
        decreaseVarianceXButton.setText(Variables.LABEL_DECREMENTAR);
        decreaseVarianceXButton.addActionListener(e -> {
            double varianza = Double.parseDouble(varianceXTextArea.getText());
            if (!(varianza <= Variables.VARIANCE_MIN)) {
                varianza = varianza - 0.25;
                varianceXTextArea.setText(Double.toString(varianza));
                controller.updateGraph(-99, varianza, coordenadaX);
                controller.setVarianzaPuntosX(varianza, coordenadaX);
            }
        });
    }

    public void deshabilitarElementosGaussianos(){
        decreaseMeanXButton.setEnabled(false);
        increaseMeanXButton.setEnabled(false);
        decreaseVarianceXButton.setEnabled(false);
        increaseVarianceXButton.setEnabled(false);
    }

    public void habilitarElementosGaussianos(){
        decreaseMeanXButton.setEnabled(true);
        increaseMeanXButton.setEnabled(true);
        decreaseVarianceXButton.setEnabled(true);
        increaseVarianceXButton.setEnabled(true);
    }
}
