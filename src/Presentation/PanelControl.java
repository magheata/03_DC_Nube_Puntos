/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Presentation;

import Application.DCController;
import Domain.Variables;
import Presentation.Botones.BotonesAlgoritmo;
import Presentation.Botones.BotonesGraph;
import Presentation.Botones.BotonesSorter;
import Presentation.Graph.Graph;
import Presentation.Graph.GraphPanel;
import Presentation.Graph.PlotSettings;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que contiene los botones de control de los puntos y de los elementos del proceso. Dentro de este panel se puede:
 * 1. Elegir el tipo de algoritmo según su coste computaciona
 * 2. Elegir el tipo de algoritmo de ordención que se quiere utilizar para ordenar los puntos. Hay 4 tipos se ordenación:
 *    la de Java (Collections.sort), el Mergesort, el Quicksort y el Bucketsort.
 * 3. Elegir la media y la desviación de los puntos de la distribución gaussiana
 * También consta de un gráfico de densidad con el que se puede ver la distribución de los puntos gaussianos
 */
public class PanelControl extends JPanel {

    public BotonesAlgoritmo botonesAlgoritmoPanel;
    public BotonesSorter botonesSorterPanel;
    public BotonesGraph botonesGraphPanel;
    protected GraphPanel graphPanel;
    protected JProgressBar BarraPointsIterative;
    protected JProgressBar BarraPointsRecursive;

    private DCController controller;

    // Rango del gráfico de densidad de los puntos
    private int xMinPlotSettings = Variables.xMinPlotSettings;
    private int xMaxPlotSettings = Variables.xMaxPlotSettings;
    private int yMinPlotSettings = Variables.yMinPlotSettings;
    private int yMaxPlotSettings = Variables.yMaxPlotSettings;

    private double definedAverage = 0;
    private double definedVariance = 1;

    public void setBarraRecursiva(boolean barraRecursiva) {
        this.barraRecursiva = barraRecursiva;
    }

    private boolean barraRecursiva;

    private GridBagConstraints constraintsBarraPoints;

    public PanelControl(DCController controller){
        this.controller = controller;
        initComponents();
    }

    private void initComponents(){
        this.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();

        GridBagConstraints constraintsPanel = new GridBagConstraints();

        constraintsPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsPanel.gridwidth = 3;
        constraintsPanel.gridheight = 2;
        constraintsPanel.gridx = 0;
        constraintsPanel.gridy = 0;

        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraintsGraphWrapperPanel = new GridBagConstraints();
        constraintsGraphWrapperPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsGraphWrapperPanel.anchor = GridBagConstraints.PAGE_START;
        constraintsGraphWrapperPanel.gridwidth = 3;
        constraintsGraphWrapperPanel.gridx = 0;
        constraintsGraphWrapperPanel.gridy = 0;

        //region BOTONES ALGORITMO
        botonesAlgoritmoPanel = new BotonesAlgoritmo(controller);

        GridBagConstraints constraintsAlgoritmoPanel = new GridBagConstraints();
        constraintsAlgoritmoPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsAlgoritmoPanel.gridwidth = 3;
        constraintsAlgoritmoPanel.gridx = 0;
        constraintsAlgoritmoPanel.gridy = 1;

        BarraPointsIterative = new JProgressBar();
        BarraPointsRecursive = new JProgressBar();

        //endregion

        constraintsBarraPoints = new GridBagConstraints();
        constraintsBarraPoints.fill = GridBagConstraints.HORIZONTAL;
        constraintsBarraPoints.gridwidth = 3;
        constraintsBarraPoints.gridheight = 1;
        constraintsBarraPoints.gridx = 0;
        constraintsBarraPoints.gridy = 2;

        //region BOTONES SORTER
        botonesSorterPanel = new BotonesSorter();

        GridBagConstraints constraintsBotonesSorterPanel = new GridBagConstraints();

        constraintsBotonesSorterPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsBotonesSorterPanel.gridwidth = 3;
        constraintsBotonesSorterPanel.gridx = 0;
        constraintsBotonesSorterPanel.gridy = 2;
        //endregion

        panel.add(botonesAlgoritmoPanel, constraintsAlgoritmoPanel);
        panel.add(botonesSorterPanel, constraintsBotonesSorterPanel);
        panel.add(addGraphToPanel(), constraintsGraphWrapperPanel);
        this.add(panel, constraintsPanel);
        if (controller.getAlgoritmoElegido() == 0){
            this.add(BarraPointsIterative, constraintsBarraPoints);
        } else if (controller.getAlgoritmoElegido() == 0 && controller.getPuntosNube().length == 100000){
            this.add(BarraPointsRecursive, constraintsBarraPoints);
        } else {
            this.add(BarraPointsRecursive, constraintsBarraPoints);
        }
        this.setVisible(true);
    }

    public void modificarBarra(){
        if (barraRecursiva){
            this.remove(BarraPointsIterative);
            this.add(BarraPointsRecursive, constraintsBarraPoints);
        } else {
            this.remove(BarraPointsRecursive);
            this.add(BarraPointsIterative, constraintsBarraPoints);
        }
    }
    /**
     * Función que añade el gráfico de densidad a la ventana
     * @return
     */
    private JPanel addGraphToPanel(){

        JPanel graphPanelWrapper = new JPanel();

        graphPanelWrapper.setLayout(new GridBagLayout());

        botonesGraphPanel = new BotonesGraph(controller);

        //region GraphPanel
        PlotSettings plotSettings = new PlotSettings(xMinPlotSettings, xMaxPlotSettings, yMinPlotSettings, yMaxPlotSettings);
        plotSettings.setPlotColor(Color.RED);
        plotSettings.setGridSpacingX(0.5);
        plotSettings.setGridSpacingY(0.5);

        graphPanel = new GraphPanel();
        controller.setGraphPanel(graphPanel);
        graphPanel.setGraph(new Graph(plotSettings, definedAverage, definedVariance));
        // default size of the window, the Graph Panel will be slightly smaller.
        graphPanel.setVisible(true);
        //endregion

        //region GridBagConstraints
        GridBagConstraints constraintsGraphButtons = new GridBagConstraints();
        constraintsGraphButtons.fill = GridBagConstraints.HORIZONTAL;
        constraintsGraphButtons.gridwidth = 3;
        constraintsGraphButtons.gridheight = 1;
        constraintsGraphButtons.gridx = 0;
        constraintsGraphButtons.gridy = 0;

        GridBagConstraints constraintsGraphWrapperPanel = new GridBagConstraints();
        constraintsGraphWrapperPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsGraphButtons.anchor = GridBagConstraints.CENTER;
        constraintsGraphWrapperPanel.gridwidth = 3;
        constraintsGraphWrapperPanel.gridheight = 2;
        constraintsGraphWrapperPanel.gridx = 0;
        constraintsGraphWrapperPanel.gridy = 1;
        //endregion

        graphPanelWrapper.add(botonesGraphPanel, constraintsGraphButtons);
        graphPanelWrapper.add(graphPanel, constraintsGraphWrapperPanel);
        return graphPanelWrapper;
    }

    public void disableGaussianElements(){
        botonesGraphPanel.deshabilitarElementosGaussianos();
    }

    public void enableGaussianElements(){
        botonesGraphPanel.habilitarElementosGaussianos();
    }

    public void disableBotonesSorter(){
        botonesSorterPanel.disableBotonesSorter();
    }

    public void enableBotonesSorter(){
        botonesSorterPanel.enableBotonesSorter();
    }

    public int getAlgoritmoElegido(){
        return botonesAlgoritmoPanel.getAlgoritmoElegido();
    }

    public int getSorterElegido(){
        return botonesSorterPanel.getSorterElegido();
    }

    public void barraGo(){
        BarraPointsRecursive.setIndeterminate(true);
    }

    public void barraStop(){
        BarraPointsRecursive.setIndeterminate(false);
    }

    public void barraAct(){
        int v = BarraPointsIterative.getValue();
        BarraPointsIterative.setValue(++v);
    }

    public void setmax(int v){
        BarraPointsIterative.setMaximum(v);
        BarraPointsIterative.setValue(0);
    }
}
