/* Created by andreea on 29/03/2020 */
package Presentation.Botones;

import Application.DCController;

import javax.swing.*;
import java.awt.*;

public class BotonesAlgoritmo extends Botones{

    private JButton naiveButton, nlogn2Button, nlognButton;
    public JButton[] botones = {naiveButton, nlogn2Button, nlognButton};
    public String[] nombresBotones = {"O(n^2)", "O((n·logn)^2)", "O(n·logn)"};
    private JLabel algoritmoLabel;

    public int getAlgoritmoElegido() {
        return algoritmoElegido;
    }

    private int algoritmoElegido;

    private DCController controller;

    public BotonesAlgoritmo(DCController controller){
        super();
        this.controller = controller;
        algoritmoLabel = new JLabel();
        algoritmoLabel.setText("COMPLEJIDAD DEL ALGORITMO");

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
}
