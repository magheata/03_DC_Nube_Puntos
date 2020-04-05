/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Presentation;

import Application.DCController;
import Domain.Variables;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del programa que contiene todos los otros elementos gráfico. Los elementos gráficos están
 * divididos en:
 * PanelPuntos: este panel contiene el gráfico de los puntos que se van a examinar
 * PanelControl: panel que contiene los elementos de control de los puntos y del programa
 */
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
        panelPuntos.setPreferredSize(new Dimension(Variables.WIDTH_PANELPUNTOS, Variables.HEIGHT_PANELPUNTOS));
        controller.setPanelPuntos(panelPuntos);
        messageOptionPane = new JOptionPane();
        messageOptionPane.setSize(new Dimension(200, 50));
        this.add(panelControl, BorderLayout.EAST);
        this.add(new JSeparator(SwingConstants.VERTICAL));
        this.add(panelPuntos, BorderLayout.WEST);
        this.setVisible(true);
        this.setResizable(false);
    }

    /**
     * Método que se utiliza para poder mostrar mensajes al usuario
     * @param mensaje
     * @param isError
     */
    public void UserMsg(String mensaje, boolean isError){
        if (isError){
            messageOptionPane.showMessageDialog(getContentPane(), mensaje, Variables.TEXTO_ERROR, JOptionPane.ERROR_MESSAGE);
        } else {
            messageOptionPane.showMessageDialog(getContentPane(), mensaje, Variables.TEXTO_SOLUCION, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
