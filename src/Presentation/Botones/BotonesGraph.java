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

    private JButton increaseMeanButton, decreaseMeanButton, increaseVarianceButton, decreaseVarianceButton, incrementarPuntosButton, decrementarPuntosButton;
    private JLabel puntosTotalesLabel, graphLabel, meanLabel, varianceLabel;
    private JTextArea totalPointsTextArea, meanTextArea, varianceTextArea;

    private DCController controller;

    public BotonesGraph(DCController controller){
        super();
        this.controller = controller;

        graphLabel = new JLabel();
        graphLabel.setVisible(true);
        graphLabel.setText(Variables.LABEL_GRAPH);

        inicializarElementosPuntos();

        JPanel puntosTotalesPanel = new JPanel();
        puntosTotalesPanel.setLayout(new FlowLayout());

        puntosTotalesPanel.add(puntosTotalesLabel);
        puntosTotalesPanel.add(decrementarPuntosButton);
        puntosTotalesPanel.add(totalPointsTextArea);
        puntosTotalesPanel.add(incrementarPuntosButton);

        inicializarElementosGaussianos();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        buttonsPanel.add(meanLabel);
        buttonsPanel.add(decreaseMeanButton);
        buttonsPanel.add(meanTextArea);
        buttonsPanel.add(increaseMeanButton);
        buttonsPanel.add(Box.createHorizontalStrut(20)); // Fixed width invisible separator.
        buttonsPanel.add(varianceLabel);
        buttonsPanel.add(decreaseVarianceButton);
        buttonsPanel.add(varianceTextArea);
        buttonsPanel.add(increaseVarianceButton);

        JPanel buttonsWrapperPanel = new JPanel();
        buttonsWrapperPanel.setLayout(new BorderLayout());
        buttonsWrapperPanel.add(puntosTotalesPanel, BorderLayout.CENTER);
        buttonsWrapperPanel.add(buttonsPanel, BorderLayout.SOUTH);

        this.add(graphLabel, BorderLayout.NORTH);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
        this.add(buttonsWrapperPanel, BorderLayout.SOUTH);
        this.setVisible(true);

        deshabilitarElementosGaussianos();
    }

    private void inicializarElementosGaussianos(){
        meanLabel = new JLabel();
        meanLabel.setText("μ: ");

        varianceLabel = new JLabel();
        varianceLabel.setText("σ: ");

        meanTextArea = new JTextArea();
        meanTextArea.setText("0");
        meanTextArea.setOpaque(false);

        varianceTextArea = new JTextArea();
        varianceTextArea.setText("1");
        varianceTextArea.setOpaque(false);

        inicializarBotonesGaussianos();
    }

    private void inicializarElementosPuntos(){
        totalPointsTextArea = new JTextArea();
        totalPointsTextArea.setText("1000");
        totalPointsTextArea.setOpaque(false);

        totalPointsTextArea = new JTextArea();
        totalPointsTextArea.setText("1000");
        totalPointsTextArea.setOpaque(false);

        puntosTotalesLabel = new JLabel();
        puntosTotalesLabel.setText(Variables.LABEL_PUNTOS_TOTALES);
        inicializarBotonesPuntos();
    }

    private void inicializarBotonesPuntos(){
        decrementarPuntosButton = new JButton();
        decrementarPuntosButton.setText(Variables.LABEL_DECREMENTAR);
        decrementarPuntosButton.addActionListener(e -> {
            int puntosTotales = Integer.parseInt(totalPointsTextArea.getText());
            if (!(puntosTotales <= Variables.PUNTOS_MIN)) {
                puntosTotales = puntosTotales / 10;
                totalPointsTextArea.setText(Integer.toString(puntosTotales));
                controller.setTotalPuntos(puntosTotales);
            }
        });

        incrementarPuntosButton = new JButton();
        incrementarPuntosButton.setText(Variables.LABEL_INCREMENTAR);
        incrementarPuntosButton.addActionListener(e -> {
            int puntosTotales = Integer.parseInt(totalPointsTextArea.getText());
            if (!(puntosTotales >= Variables.PUNTOS_MAX)) {
                puntosTotales = puntosTotales * 10;
                totalPointsTextArea.setText(Integer.toString(puntosTotales));
                controller.setTotalPuntos(puntosTotales);
            }
        });
    }

    private void inicializarBotonesGaussianos(){
        increaseMeanButton = new JButton();
        increaseMeanButton.setText(Variables.LABEL_INCREMENTAR);
        increaseMeanButton.addActionListener(e -> {
            int media = Integer.parseInt(meanTextArea.getText());
            if (!(media >= Variables.MEAN_MAX)) {
                media = media + 1;
                meanTextArea.setText(Integer.toString(media));
                controller.updateGraph(media, -99);
                controller.setMediaPuntos(media);
            }
        });

        decreaseMeanButton = new JButton();
        decreaseMeanButton.setText(Variables.LABEL_DECREMENTAR);
        decreaseMeanButton.addActionListener(e -> {
            int media = Integer.parseInt(meanTextArea.getText());
            if (!(media <= -Variables.MEAN_MAX)) {
                media = media - 1;
                meanTextArea.setText(Integer.toString(media));
                controller.updateGraph(media, -99);
                controller.setMediaPuntos(media);
            }
        });

        increaseVarianceButton = new JButton();
        increaseVarianceButton.setText(Variables.LABEL_INCREMENTAR);
        increaseVarianceButton.addActionListener(e -> {
            double varianza = Double.parseDouble(varianceTextArea.getText());
            if (!(varianza >= Variables.VARIANCE_MAX)) {
                varianza = varianza + 0.25;
                varianceTextArea.setText(Double.toString(varianza));
                controller.updateGraph(-99, varianza);
                controller.setVarianzaPuntos(varianza);
            }
        });

        decreaseVarianceButton = new JButton();
        decreaseVarianceButton.setText(Variables.LABEL_DECREMENTAR);
        decreaseVarianceButton.addActionListener(e -> {
            double varianza = Double.parseDouble(varianceTextArea.getText());
            if (!(varianza <= Variables.VARIANCE_MIN)) {
                varianza = varianza - 0.25;
                varianceTextArea.setText(Double.toString(varianza));
                controller.updateGraph(-99, varianza);
                controller.setVarianzaPuntos(varianza);
            }
        });
    }

    public void deshabilitarElementosGaussianos(){
        decreaseMeanButton.setEnabled(false);
        increaseMeanButton.setEnabled(false);
        decreaseVarianceButton.setEnabled(false);
        increaseVarianceButton.setEnabled(false);
    }

    public void habilitarElementosGaussianos(){
        decreaseMeanButton.setEnabled(true);
        increaseMeanButton.setEnabled(true);
        decreaseVarianceButton.setEnabled(true);
        increaseVarianceButton.setEnabled(true);
    }
}
