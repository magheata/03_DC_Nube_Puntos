/* Created by andreea on 29/03/2020 */
package Presentation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.concurrent.Flow;

public class BotonesAlgoritmo extends Botones{

    private JButton naiveButton, nlogn2Button, nlognButton;
    public JButton[] botones = {naiveButton, nlogn2Button, nlognButton};
    public String[] nombresBotones = {"O(n^2)", "O((n·logn)^2)", "O(n·logn)"};

    private JLabel algoritmoLabel;

    public BotonesAlgoritmo(){
        super();
        algoritmoLabel = new JLabel();
        algoritmoLabel.setVisible(true);
        this.add(algoritmoLabel, BorderLayout.NORTH);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        algoritmoLabel.setText("COMPLEJIDAD DEL ALGORITMO");
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
