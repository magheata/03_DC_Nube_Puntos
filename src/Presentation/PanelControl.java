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
    public BotonesGraph botonesGraphXPanel;
    public BotonesGraph botonesGraphYPanel;
    protected GraphPanel graphXPanel, graphYPanel;
    protected JProgressBar BarraPointsIterative;
    protected JProgressBar BarraPointsRecursive;
    protected JTabbedPane grafosDensidadTabbedPane;

    private DCController controller;

    private JLabel puntosTotalesLabel, graphLabel;
    private JTextArea totalPointsTextArea;
    private JButton incrementarPuntosButton, decrementarPuntosButton;

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

        JPanel mainPanel = new JPanel();

        inicializarElementosPuntos();

        GridBagConstraints constraintsPanel = new GridBagConstraints();

        constraintsPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsPanel.gridwidth = 3;
        constraintsPanel.gridheight = 2;
        constraintsPanel.gridx = 0;
        constraintsPanel.gridy = 0;

        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraintsGraphWrapperPanel = new GridBagConstraints();
        constraintsGraphWrapperPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsGraphWrapperPanel.anchor = GridBagConstraints.PAGE_START;
        constraintsGraphWrapperPanel.gridwidth = 3;
        constraintsGraphWrapperPanel.gridx = 0;
        constraintsGraphWrapperPanel.gridy = 0;

        //region ALGORITMO PANEL
        botonesAlgoritmoPanel = new BotonesAlgoritmo(controller);

        GridBagConstraints constraintsAlgoritmoPanel = new GridBagConstraints();
        constraintsAlgoritmoPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsAlgoritmoPanel.gridwidth = 3;
        constraintsAlgoritmoPanel.gridx = 0;
        constraintsAlgoritmoPanel.gridy = 1;
        //endregion

        //region SORTER PANEL
        botonesSorterPanel = new BotonesSorter();

        GridBagConstraints constraintsBotonesSorterPanel = new GridBagConstraints();

        constraintsBotonesSorterPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsBotonesSorterPanel.gridwidth = 3;
        constraintsBotonesSorterPanel.gridx = 0;
        constraintsBotonesSorterPanel.gridy = 2;
        //endregion

        mainPanel.add(botonesAlgoritmoPanel, constraintsAlgoritmoPanel);
        mainPanel.add(botonesSorterPanel, constraintsBotonesSorterPanel);
        mainPanel.add(addDefinicionDatosElements(), constraintsGraphWrapperPanel);

        this.add(mainPanel, constraintsPanel);

        //region PROGRESS BARS
        BarraPointsIterative = new JProgressBar();
        BarraPointsRecursive = new JProgressBar();

        constraintsBarraPoints = new GridBagConstraints();
        constraintsBarraPoints.fill = GridBagConstraints.HORIZONTAL;
        constraintsBarraPoints.gridwidth = 3;
        constraintsBarraPoints.gridheight = 1;
        constraintsBarraPoints.gridx = 0;
        constraintsBarraPoints.gridy = 2;

        if (controller.getAlgoritmoElegido() == 0){
            this.add(BarraPointsIterative, constraintsBarraPoints);
        } else if (controller.getAlgoritmoElegido() == 0 && controller.getPuntosNube().length == 100000){
            this.add(BarraPointsRecursive, constraintsBarraPoints);
        } else {
            this.add(BarraPointsRecursive, constraintsBarraPoints);
        }
        //endregion
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


    private JPanel addDefinicionDatosElements(){
        JPanel panelDefinicionDatos = new JPanel();
        panelDefinicionDatos.setLayout(new BorderLayout());
        graphLabel = new JLabel();
        graphLabel.setVisible(true);
        graphLabel.setText(Variables.LABEL_GRAPH);

        JPanel panelLabel = new JPanel();
        panelLabel.setLayout(new BorderLayout());
        panelLabel.add(graphLabel, BorderLayout.NORTH);
        panelLabel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

        panelDefinicionDatos.add(panelLabel, BorderLayout.NORTH);
        panelDefinicionDatos.add(addPanelTotalPuntos(), BorderLayout.CENTER);
        panelDefinicionDatos.add(addGraphToPanel(), BorderLayout.SOUTH);
        return panelDefinicionDatos;
    }

    private JPanel addPanelTotalPuntos(){
        JPanel puntosTotalesPanel = new JPanel();
        puntosTotalesPanel.setLayout(new FlowLayout());

        puntosTotalesPanel.add(puntosTotalesLabel);
        puntosTotalesPanel.add(decrementarPuntosButton);
        puntosTotalesPanel.add(totalPointsTextArea);
        puntosTotalesPanel.add(incrementarPuntosButton);

        this.add(puntosTotalesPanel);
        return puntosTotalesPanel;
    }

    private void inicializarElementosPuntos(){
        totalPointsTextArea = new JTextArea();
        totalPointsTextArea.setText("1000");
        totalPointsTextArea.setEditable(false);
        totalPointsTextArea.setOpaque(false);
        puntosTotalesLabel = new JLabel();
        puntosTotalesLabel.setText(Variables.LABEL_PUNTOS_TOTALES);
        inicializarBotonesPuntos();
    }

    private void inicializarBotonesPuntos(){
        decrementarPuntosButton = new JButton();
        decrementarPuntosButton.setText(Variables.LABEL_DECREMENTAR);
        decrementarPuntosButton.addActionListener(e -> {
            int puntosTotales = Integer.parseInt(totalPointsTextArea.getText());
            if (!(puntosTotales <= Variables.PUNTOS_MIN)) {
                puntosTotales = puntosTotales / 10;
                totalPointsTextArea.setText(Integer.toString(puntosTotales));
                controller.setTotalPuntos(puntosTotales);
            }
        });

        incrementarPuntosButton = new JButton();
        incrementarPuntosButton.setText(Variables.LABEL_INCREMENTAR);
        incrementarPuntosButton.addActionListener(e -> {
            int puntosTotales = Integer.parseInt(totalPointsTextArea.getText());
            if (!(puntosTotales >= Variables.PUNTOS_MAX)) {
                puntosTotales = puntosTotales * 10;
                totalPointsTextArea.setText(Integer.toString(puntosTotales));
                controller.setTotalPuntos(puntosTotales);
            }
        });
    }
    /**
     * Función que añade el gráfico de densidad a la ventana
     * @return
     */
    private JPanel addGraphToPanel(){

        grafosDensidadTabbedPane = new JTabbedPane();

        JPanel graphXPanelWrapper = new JPanel();
        graphXPanelWrapper.setLayout(new GridBagLayout());
        botonesGraphXPanel = new BotonesGraph(controller, true);
        graphXPanel = new GraphPanel();
        controller.setGraphXPanel(graphXPanel);

        JPanel graphYPanelWrapper = new JPanel();
        graphYPanelWrapper.setLayout(new GridBagLayout());
        botonesGraphYPanel = new BotonesGraph(controller, false);
        graphYPanel = new GraphPanel();
        controller.setGraphYPanel(graphYPanel);

        grafosDensidadTabbedPane.addTab("Coordenada X", graphXPanelWrapper);

        grafosDensidadTabbedPane.addTab("Coordenada Y", graphYPanelWrapper);

        GridBagConstraints constraintsGraphButtons = new GridBagConstraints();
        constraintsGraphButtons.fill = GridBagConstraints.HORIZONTAL;
        constraintsGraphButtons.gridwidth = 3;
        constraintsGraphButtons.gridheight = 1;
        constraintsGraphButtons.gridx = 0;
        constraintsGraphButtons.gridy = 0;

        JPanel panel = new JPanel();

        //region GraphPanel
        PlotSettings plotSettingsX = new PlotSettings(xMinPlotSettings, xMaxPlotSettings, yMinPlotSettings, yMaxPlotSettings);
        plotSettingsX.setPlotColor(Color.RED);
        plotSettingsX.setGridSpacingX(0.5);
        plotSettingsX.setGridSpacingY(0.5);

        PlotSettings plotSettingsY = new PlotSettings(xMinPlotSettings, xMaxPlotSettings, yMinPlotSettings, yMaxPlotSettings);
        plotSettingsY.setPlotColor(Color.BLUE);
        plotSettingsY.setGridSpacingX(0.5);
        plotSettingsY.setGridSpacingY(0.5);

        graphXPanel.setGraph(new Graph(plotSettingsX, definedAverage, definedVariance));
        // default size of the window, the Graph Panel will be slightly smaller.
        graphXPanel.setVisible(true);

        graphYPanel.setGraph(new Graph(plotSettingsY, definedAverage, definedVariance));
        // default size of the window, the Graph Panel will be slightly smaller.
        graphYPanel.setVisible(true);
        //endregion

        //region GridBagConstraints

        GridBagConstraints constraintsGraphWrapperPanel = new GridBagConstraints();
        constraintsGraphWrapperPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsGraphWrapperPanel.anchor = GridBagConstraints.CENTER;
        constraintsGraphWrapperPanel.gridwidth = 3;
        constraintsGraphWrapperPanel.gridheight = 2;
        constraintsGraphWrapperPanel.gridx = 0;
        constraintsGraphWrapperPanel.gridy = 1;
        //endregion

        graphXPanelWrapper.add(graphXPanel, constraintsGraphWrapperPanel);
        graphYPanelWrapper.add(graphYPanel, constraintsGraphWrapperPanel);
        graphXPanelWrapper.add(botonesGraphXPanel, constraintsGraphButtons);
        graphYPanelWrapper.add(botonesGraphYPanel, constraintsGraphButtons);

        panel.add(grafosDensidadTabbedPane);
        return panel;
    }

    public void disableGaussianElements(){
        botonesGraphXPanel.deshabilitarElementosGaussianos();
        botonesGraphYPanel.deshabilitarElementosGaussianos();
    }

    public void enableGaussianElements(){
        botonesGraphXPanel.habilitarElementosGaussianos();
        botonesGraphYPanel.habilitarElementosGaussianos();
    }

    public void disableBotonesSorter(){ botonesSorterPanel.disableBotonesSorter(); }

    public void enableBotonesSorter(){ botonesSorterPanel.enableBotonesSorter(); }

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
