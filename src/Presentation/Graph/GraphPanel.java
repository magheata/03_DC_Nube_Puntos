package Presentation.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GraphPanel extends JPanel implements PropertyChangeListener {

    /**
     * The graph object renders charts and graphs.
     */
    protected Graph graph;

    public void setNuevasDimeniones(int width, int height) {
        dimensiones.width = width;
        dimensiones.height = height;
    }

    protected Dimension dimensiones;

    public GraphPanel(){
        dimensiones = new Dimension(450, 150);
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

    /**
     * Returns an image of the graph which can be saved to disk.
     */
    public BufferedImage getImage() {
        return graph != null? graph.getImage(getWidth(), getHeight()) : null;
    }

    /**
     * Provides access to the graph object.
     * @return
     */
    public Graph getGraph() {
        return graph;
    }

    public void setGraphPanelSize(int width, int height){
        setSize(width, height);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}