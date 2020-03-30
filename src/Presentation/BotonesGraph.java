/* Created by andreea on 30/03/2020 */
package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotonesGraph extends Botones{

    private JButton increaseMeanButton, decreaseMeanButton, increaseVarianceButton, decreaseVarianceButton;
    protected JLabel puntosTotalesLabel;
    protected JButton incrementarPuntosButton, decrementarPuntosButton;
    private JLabel graphLabel, meanLabel, varianceLabel;
    private JTextArea totalPointsTextArea, meanTextArea, varianceTextArea;

    public BotonesGraph(){
        super();
        graphLabel = new JLabel();
        graphLabel.setVisible(true);
        graphLabel.setText("DEFINICIÓN DE LOS DATOS");

        meanLabel = new JLabel();
        meanLabel.setText("μ: ");

        varianceLabel = new JLabel();
        varianceLabel.setText("σ: ");

        totalPointsTextArea = new JTextArea();
        totalPointsTextArea.setText("1000");
        totalPointsTextArea.setOpaque(false);

        meanTextArea = new JTextArea();
        meanTextArea.setText("0");
        meanTextArea.setOpaque(false);

        varianceTextArea = new JTextArea();
        varianceTextArea.setText("1");
        varianceTextArea.setOpaque(false);

        puntosTotalesLabel = new JLabel();
        puntosTotalesLabel.setText("Total puntos: ");

        decrementarPuntosButton = new JButton();
        decrementarPuntosButton.setText("-");
        decrementarPuntosButton.addActionListener(e -> {
            int puntosTotales = Integer.parseInt(totalPointsTextArea.getText());
            if (!(puntosTotales <= 100)) {
                puntosTotales = puntosTotales / 10;
                totalPointsTextArea.setText(Integer.toString(puntosTotales));
            }
        });

        incrementarPuntosButton = new JButton();
        incrementarPuntosButton.setText("+");
        incrementarPuntosButton.addActionListener(e -> {
            int puntosTotales = Integer.parseInt(totalPointsTextArea.getText());
            if (!(puntosTotales >= 10000000 )) {
                puntosTotales = puntosTotales * 10;
                totalPointsTextArea.setText(Integer.toString(puntosTotales));
            }
        });

        JPanel puntosTotalesPanel = new JPanel();
        puntosTotalesPanel.setLayout(new FlowLayout());

        puntosTotalesPanel.add(puntosTotalesLabel);
        puntosTotalesPanel.add(decrementarPuntosButton);
        puntosTotalesPanel.add(totalPointsTextArea);
        puntosTotalesPanel.add(incrementarPuntosButton);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        this.add(graphLabel, BorderLayout.NORTH);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

        increaseMeanButton = new JButton();
        increaseMeanButton.setText("+");
        decreaseMeanButton = new JButton();
        decreaseMeanButton.setText("-");

        increaseVarianceButton = new JButton();
        increaseVarianceButton.setText("+");
        decreaseVarianceButton = new JButton();
        decreaseVarianceButton.setText("-");

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

        this.add(buttonsWrapperPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}
