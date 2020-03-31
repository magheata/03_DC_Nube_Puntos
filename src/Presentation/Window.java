/* Created by andreea on 22/03/2020 */
package Presentation;

import Application.DCController;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    private PanelControl panelControl;
    private PanelPuntos panelPuntos;
    public Window(){
        initComponents();
    }

    private void initComponents() {
        DCController controller = new DCController();
        panelControl = new PanelControl(controller);
        controller.setPanelControl(panelControl);
        panelPuntos = new PanelPuntos(controller);
        panelPuntos.setPreferredSize(new Dimension(600, 450));
        panelControl.setPreferredSize(new Dimension(460, 450));

        this.add(panelControl, BorderLayout.EAST);
        this.add(new JSeparator(SwingConstants.VERTICAL));
        this.add(panelPuntos, BorderLayout.WEST);
        this.setVisible(true);
        this.setResizable(false);
    }
}
