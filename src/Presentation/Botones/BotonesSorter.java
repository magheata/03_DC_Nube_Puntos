/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Presentation.Botones;

import Domain.Variables;

import javax.swing.*;
import java.awt.*;

/**
 * Panel en el que se definen los botones de control para seleccionar el tipo de algoritmo de ordenación que se desea
 * utilizar
 */
public class BotonesSorter extends Botones{

    private JButton quicksortButton, mergesortButton, javasortButton, bucketSort;
    private JButton[] botones = {javasortButton, quicksortButton, mergesortButton, bucketSort};
    private String[] nombresBotones = Variables.nombresBotonesSorter;
    private JLabel sorterLabel;
    private int sorterElegido = -1;

    public BotonesSorter(){
        super();
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
                            sorterElegido = j;
                        } else {
                            desactivarBoton(botones[j]);
                        }
                    }
                }).start();
            });
            buttonsPanel.add(botones[i]);
        }

        sorterLabel = new JLabel();
        sorterLabel.setVisible(true);
        sorterLabel.setText(Variables.LABEL_SORTER);

        this.add(sorterLabel, BorderLayout.NORTH);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public int getSorterElegido() {
        return sorterElegido;
    }

    public void disableBotonesSorter(){
        for(int i = 0; i < botones.length; i++){
            botones[i].setEnabled(false);
        }
    }

    public void enableBotonesSorter(){
        for(int i = 0; i < botones.length; i++){
            botones[i].setEnabled(true);
        }
    }
}

