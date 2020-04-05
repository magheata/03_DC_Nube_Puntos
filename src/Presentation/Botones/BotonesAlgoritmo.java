/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Presentation.Botones;

import Application.DCController;
import Domain.Variables;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que contiene los botones de control de los algoritmos que se pueden utilizar para encontrar la distancia mínima
 */
public class BotonesAlgoritmo extends Botones{

    private JButton naiveButton, nlogn2Button, nlognButton;
    public JButton[] botones = {naiveButton, nlogn2Button, nlognButton};
    public String[] nombresBotones = Variables.nombresBotonesAlgoritmo;
    private JLabel algoritmoLabel;
    private int algoritmoElegido = -1;
    private DCController controller;

    public BotonesAlgoritmo(DCController controller){
        super();
        this.controller = controller;
        algoritmoLabel = new JLabel();
        algoritmoLabel.setText(Variables.LABEL_ALGORITMO);

        algoritmoLabel.setVisible(true);
        this.add(algoritmoLabel, BorderLayout.NORTH);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        for (int i = 0; i < botones.length; i++){
            botones[i] = new JButton();
            botones[i].setText(nombresBotones[i]);
            botones[i].addActionListener(e -> {
                Object source = e.getSource();
                new Thread(() -> {
                    for (int j = 0; j < botones.length; j++) {
                        if (botones[j] == source) {
                            activarButton(botones[j]);
                            algoritmoElegido = j;
                        } else {
                            desactivarBoton(botones[j]);
                        }
                    }
                }).start();
            });
            if (i == 0){
                botones[i].addActionListener(e -> controller.deshabilitarBotonesSorter());
            } else {
                botones[i].addActionListener(e -> controller.habilitarBotonesSorter());
            }
            buttonsPanel.add(botones[i]);
        }
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public int getAlgoritmoElegido() {
        return algoritmoElegido;
    }

}
