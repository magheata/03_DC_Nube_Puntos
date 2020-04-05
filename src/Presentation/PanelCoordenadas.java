/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Presentation;

import Application.DCController;
import Domain.Punto;
import Domain.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Panel en el que se pintan los puntos de la Nube. Se ha separado del PanelPuntos para poder manejrar las coordenadas de
 * forma más sencilla
 */
public class PanelCoordenadas extends JPanel {

    /* Variable que sirve para saber si se tienen que pintar los ejes con las coordenadas positivas y negativas o sólo
    positivas */
    private boolean isGaussianDistribution = false;
    private int BORDER_GAP = Variables.COORDENADAS_BORDER_GAP;
    private final int SIZE_GRAPH = Variables.COORDENADAS_SIZE_GRAPH;
    private boolean pintarPuntos = false;
    private DCController controlador;

    public PanelCoordenadas(DCController controlador){
        this.controlador = controlador;
        initComponents();
    }

    private void initComponents() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(Variables.WIDTH_PANELCOORDENADAS, Variables.HEIGHT_PANELCOORDENADAS));
        this.setSize(new Dimension(Variables.WIDTH_PANELCOORDENADAS, Variables.HEIGHT_PANELCOORDENADAS));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Insets insets = getInsets();
        // Inveritmos el origen de coordenadas abajo a la izquierda
        int h = getHeight() - insets.top - insets.bottom;
        g2.scale(1.0, -1.0);
        g2.translate(0, -h - insets.top);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Si es distribución gaussiana pintamos las coordenadas negativas y positivas
        if (isGaussianDistribution){
            g.setColor(Color.GRAY);
            g.drawLine((SIZE_GRAPH /2) + BORDER_GAP, BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP, getHeight() - BORDER_GAP);
            g.drawLine(BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP, getWidth() - BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP);
            drawAxisLines(g, BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP, (SIZE_GRAPH /2) + BORDER_GAP, BORDER_GAP, true);
        // Si es distribución no gaussiana pintamos las coordenadas positivas
        } else {
            g.setColor(Color.GRAY);
            g.drawLine(BORDER_GAP, SIZE_GRAPH + BORDER_GAP, BORDER_GAP, BORDER_GAP);
            g.drawLine(BORDER_GAP, BORDER_GAP, SIZE_GRAPH + BORDER_GAP, BORDER_GAP);
            drawAxisLines(g, BORDER_GAP, BORDER_GAP, BORDER_GAP, BORDER_GAP, false);
        }
        // En caso de que se quieran pintar los puntos se pintan (sólo se pintan cuando se ha empezado a ejecutar el proceso
        // de cálculo)
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
        Graphics2D g2d = (Graphics2D) g.create();
        if (!controlador.isGaussianDistribution()){
            double x;
            double y;
            for (int i = 0; i < puntos.length; i++) {
                x = (puntos[i].getX() * 5) / maxX;
                y = (puntos[i].getY()  * 5) / maxY;
                Ellipse2D dot = new Ellipse2D.Double(BORDER_GAP + (x * 100), BORDER_GAP + (y * 100), 10, 10);
                if (puntos[i].isSolucion()){
                    g2d.setColor(Variables.colorPuntosSolucion);
                } else {
                    g2d.setColor(Variables.colorPuntos);
                }
                g2d.fill(dot);
            }
        } else {
            for (int i = 0; i < puntos.length; i++) {
                Ellipse2D dot = new Ellipse2D.Double(BORDER_GAP + (SIZE_GRAPH /2) + (puntos[i].getX() * 50),
                        BORDER_GAP + (SIZE_GRAPH /2) + (puntos[i].getY() * 50), 10, 10);
                if (puntos[i].isSolucion()){
                    g2d.setColor(Variables.colorPuntosSolucion);
                } else {
                    g2d.setColor(Variables.colorPuntos);
                }
                g2d.fill(dot);
            }
        }
        g2d.dispose();
    }

    public void setPintarPuntos(boolean pintarPuntos) {
        this.pintarPuntos = pintarPuntos;
    }

    public void setGaussianDistribution(boolean gaussianDistribution) { isGaussianDistribution = gaussianDistribution; }
}
