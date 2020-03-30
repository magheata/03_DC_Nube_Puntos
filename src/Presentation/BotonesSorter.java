/* Created by andreea on 29/03/2020 */
package Presentation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BotonesSorter extends Botones{

    private JButton quicksortButton, mergesortButton, javasortButton;
    public JButton[] botones = {quicksortButton, mergesortButton, javasortButton};
    public String[] nombresBotones = {"Quicksort", "Mergesort", "Collections.sort()"};

    private JLabel sorterLabel;

    public BotonesSorter(){
        super();
        sorterLabel = new JLabel();
        sorterLabel.setVisible(true);
        sorterLabel.setText("ALGORITMO DE ORDENACIÓN");
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        this.add(sorterLabel, BorderLayout.NORTH);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
        for(int i = 0; i < botones.length; i++){
            botones[i] = new JButton();
            botones[i].setText(nombresBotones[i]);
            // Añadimos la imagen correpsondiente a la pieza
            botones[i].addActionListener(e -> {
                Object source = e.getSource();
                new Thread(() -> {
                    for (int j = 0; j < botones.length; j++) {
                        if (botones[j] == source) {
                            activarButton(j, botones[j]);
                        } else {
                            desactivarBoton(botones[j]);
                        }
                    }
                }).start();
            });
            buttonsPanel.add(botones[i]);
        }
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}

