/* Created by andreea on 22/03/2020 */
package Presentation;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    private PanelControl panelControl;
    private PanelPuntos panelPuntos;
    public Window(){
        initComponents();
    }

    private void initComponents() {
        panelControl = new PanelControl();
        panelPuntos = new PanelPuntos();
        panelPuntos.setPreferredSize(new Dimension(600, 450));

        this.add(panelControl, BorderLayout.EAST);
        this.add(new JSeparator(SwingConstants.VERTICAL));
        this.add(panelPuntos, BorderLayout.WEST);
        this.setVisible(true);
        this.setResizable(false);
    }
}
