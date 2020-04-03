/* Created by andreea on 29/03/2020 */
package Presentation.Botones;

import Application.DCController;

import javax.swing.*;
import java.awt.*;

public class BotonesSorter extends Botones{

    private JButton quicksortButton, mergesortButton, javasortButton, bucketSort;
    private JButton[] botones = {javasortButton, quicksortButton, mergesortButton, bucketSort};
    private String[] nombresBotones = {"Collections.sort()", "Quicksort", "Mergesort", "Bucketsort"};
    private JLabel sorterLabel;

    private DCController controller;

    public int getSorterElegido() {
        return sorterElegido;
    }

    private int sorterElegido;

    public BotonesSorter(DCController controller){
        super();
        this.controller = controller;
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
        sorterLabel.setText("ALGORITMO DE ORDENACIÓN");

        this.add(sorterLabel, BorderLayout.NORTH);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.setVisible(true);
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

