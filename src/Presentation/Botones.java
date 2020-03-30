/* Created by andreea on 29/03/2020 */
package Presentation;

import javax.swing.*;
import java.awt.*;

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
     * @param botonSeleccionado
     * @param button
     */
    protected void activarButton(int botonSeleccionado, JButton button){
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
    }


    protected GridBagConstraints getConstraintsLabel(){
        GridBagConstraints constraintsalgoritmoLabel = new GridBagConstraints();
        constraintsalgoritmoLabel.fill = GridBagConstraints.HORIZONTAL;
        constraintsalgoritmoLabel.gridwidth = 3;
        constraintsalgoritmoLabel.gridx = 0;
        constraintsalgoritmoLabel.gridy = 0;
        return constraintsalgoritmoLabel;
    }

    protected GridBagConstraints getConstraintsButtonsPanel(){
        GridBagConstraints constraintsbuttonsPanel = new GridBagConstraints();
        constraintsbuttonsPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsbuttonsPanel.weightx = 0;
        constraintsbuttonsPanel.gridx = 0;
        constraintsbuttonsPanel.gridy = 2;
        return constraintsbuttonsPanel;
    }

    protected GridBagConstraints getConstraintsSeparator(){
        GridBagConstraints constraintsSeparator = new GridBagConstraints();
        constraintsSeparator.fill = GridBagConstraints.HORIZONTAL;
        constraintsSeparator.weightx = 0;
        constraintsSeparator.gridx = 0;
        constraintsSeparator.gridy = 1;
        return constraintsSeparator;
    }
}
