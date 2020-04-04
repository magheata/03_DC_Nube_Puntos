/* Created by andreea on 04/04/2020 */
package Presentation;

import Application.DCController;
import Domain.Punto;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class PanelCoordenadas extends JPanel {

    private boolean isGaussianDistribution = false;
    private int BORDER_GAP = 30;
    private final int SIZE_GRAPH = 500;
    private Color colorPuntos = new Color(0.5f,0f,1f,.25f );
    private Color colorPuntosSolucion = new Color(0f,1f,0f);

    public void setPintarPuntos(boolean pintarPuntos) {
        this.pintarPuntos = pintarPuntos;
    }

    private boolean pintarPuntos = false;
    private DCController controlador;

    public PanelCoordenadas(DCController controlador){
        this.controlador = controlador;
        initComponents();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Insets insets = getInsets();
        int h = getHeight() - insets.top - insets.bottom;
        g2.scale(1.0, -1.0);
        g2.translate(0, -h - insets.top);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (isGaussianDistribution){
            g.setColor(Color.GRAY);
            g.drawLine((SIZE_GRAPH /2) + BORDER_GAP, BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP, getHeight() - BORDER_GAP);
            g.drawLine(BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP, getWidth() - BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP);
            drawAxisLines(g, BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP, BORDER_GAP, true);
        } else {
            g.setColor(Color.GRAY);
            g.drawLine(BORDER_GAP, SIZE_GRAPH + BORDER_GAP, BORDER_GAP, BORDER_GAP);
            g.drawLine(BORDER_GAP, BORDER_GAP, SIZE_GRAPH + BORDER_GAP, BORDER_GAP);
            drawAxisLines(g, BORDER_GAP, BORDER_GAP, BORDER_GAP, BORDER_GAP, false);
        }
        if (pintarPuntos){
            addPuntos(g, controlador.getPuntosNube(), controlador.getMaxX(), controlador.getMaxY());
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void revalidate() {
        super.revalidate();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(560, 560));
        this.setDoubleBuffered(true);
        this.setSize(new Dimension(560, 560));
    }

    public void setGaussianDistribution(boolean gaussianDistribution) {
        isGaussianDistribution = gaussianDistribution;
    }

    private void drawAxisLines(Graphics g, int xAxisX, int xAxisY, int yAxisX, int yAxisY, boolean isGaussian){
        int min = 1;
        if (isGaussian){
            min = 0;
        }
        for (int i = min; i <= SIZE_GRAPH / 50; i++){
            int auxGraphMajorAxis = i * 50;
            g.drawLine(xAxisX + auxGraphMajorAxis, xAxisY - 5, xAxisX + auxGraphMajorAxis, xAxisY + 5);
            g.drawLine(yAxisX - 5, yAxisY + auxGraphMajorAxis, yAxisX + 5, yAxisY + auxGraphMajorAxis);
            for (int j = 1; j <= 5; j++){
                int auxGraphMinorAxis = (i - 1) * 50;
                g.drawLine(auxGraphMinorAxis + xAxisX + (j * 10), xAxisY - 2, auxGraphMinorAxis + xAxisX + (j * 10), xAxisY + 2);
                g.drawLine(yAxisX - 2, auxGraphMinorAxis + yAxisY + (j * 10), yAxisX + 2, auxGraphMinorAxis + yAxisY + (j * 10));
            }
        }
    }

    public void addPuntos(Graphics g, Punto[] puntos, double maxX, double maxY){
        double x;
        double y;
        Color c = new Color(0.5f,0f,1f,.25f );
        Graphics2D g2d = (Graphics2D) g.create();
        if (!controlador.isGaussianDistribution()){
            for (int i = 0; i < puntos.length; i++) {
                x = (puntos[i].getX() * 5) / maxX;
                y = (puntos[i].getY()  * 5) / maxY;
                Ellipse2D dot = new Ellipse2D.Double(BORDER_GAP + (x * 100), BORDER_GAP + (y * 100), 10, 10);
                if (puntos[i].isSolucion()){
                    g2d.setColor(colorPuntosSolucion);
                } else {
                    g2d.setColor(colorPuntos);
                }
                g2d.fill(dot);
            }
        } else {
            for (int i = 0; i < puntos.length; i++) {
                x = (puntos[i].getX() * 1) ;/// maxX;
                y = (puntos[i].getY() * 1) ;/// maxY;
                Ellipse2D dot = new Ellipse2D.Double(BORDER_GAP + (SIZE_GRAPH /2) + (x * 50), BORDER_GAP + (SIZE_GRAPH /2) + (y * 50), 10, 10);
                if (puntos[i].isSolucion()){
                    g2d.setColor(colorPuntosSolucion);
                } else {
                    g2d.setColor(colorPuntos);
                }
                g2d.fill(dot);
            }
        }
        g2d.dispose();
    }
}
