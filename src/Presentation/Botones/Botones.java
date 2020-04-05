/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Presentation.Botones;

import javax.swing.*;
import java.awt.*;

/**
 * Clase padre que define las funciones comunes que implementan las clases BotonesSorter, BotonesAlgoritmo y BotonesGraph
 */
public abstract class Botones extends JPanel{

    public Botones(){
        this.setLayout(new BorderLayout());
    }

    /**
     *
     * @param button
     */
    protected void desactivarBoton(JButton button){
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
    }

    /**
     *
     * @param button
     */
    protected void activarButton(JButton button){
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
    }
}
