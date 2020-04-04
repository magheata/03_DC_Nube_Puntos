/* Created by andreea on 22/03/2020 */
package Presentation;

import Application.DCController;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    private PanelControl panelControl;
    private PanelPuntos panelPuntos;
    private JOptionPane messageOptionPane;

    public Window(){
        initComponents();
    }

    private void initComponents() {
        DCController controller = new DCController();
        controller.setWindow(this);
        panelControl = new PanelControl(controller);
        controller.setPanelControl(panelControl);
        panelPuntos = new PanelPuntos(controller);
        panelPuntos.setPreferredSize(new Dimension(560, 610));
        //panelControl.setPreferredSize(new Dimension(460, 450));
        controller.setPanelPuntos(panelPuntos);
        messageOptionPane = new JOptionPane();
        messageOptionPane.setSize(new Dimension(200, 50));
        this.add(panelControl, BorderLayout.EAST);
        this.add(new JSeparator(SwingConstants.VERTICAL));
        this.add(panelPuntos, BorderLayout.WEST);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void UserMsg(String mensaje){
        messageOptionPane.showMessageDialog(getContentPane(), mensaje, "Solución", JOptionPane.INFORMATION_MESSAGE);
    }
}
