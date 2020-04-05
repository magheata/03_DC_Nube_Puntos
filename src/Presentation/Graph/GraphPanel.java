package Presentation.Graph;

import Domain.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel en el que se define el gráfico de densidad
 */
public class GraphPanel extends JPanel{

    /**
     * The graph object renders charts and graphs.
     */
    protected Graph graph;
    protected Dimension dimensiones;

    public GraphPanel(){
        dimensiones = new Dimension(Variables.WIDTH_GRAFICO_DENSIDAD, Variables.HEIGHT_GRAFICO_DENSIDAD);
    }
    /**
     * Initialises the panel with a graph object
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
        repaint();
    }

    public void updateGraphWithNewValues(double mean, double stdDeviation){
        graph.updateGraphValues(mean, stdDeviation);
        repaint();
    }

    /**
     * Draws the graph directly onto the JPanel
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph != null) graph.draw(g, dimensiones.width, dimensiones.height);
    }

    @Override
    public Dimension getMaximumSize() {
        return dimensiones;
    }

    @Override
    public Dimension getMinimumSize() {
        return dimensiones;
    }

    @Override
    public Dimension getPreferredSize() {
        return dimensiones;
    }
}