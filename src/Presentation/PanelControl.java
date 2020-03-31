/* Created by andreea on 29/03/2020 */
package Presentation;

import Application.DCController;
import Presentation.Botones.BotonesAlgoritmo;
import Presentation.Botones.BotonesGraph;
import Presentation.Botones.BotonesSorter;
import Presentation.Graph.Graph;
import Presentation.Graph.GraphPanel;
import Presentation.Graph.PlotSettings;

import javax.swing.*;
import java.awt.*;

public class PanelControl extends JPanel {

    public BotonesAlgoritmo botonesAlgoritmoPanel;
    public BotonesSorter botonesSorterPanel;
    public BotonesGraph botonesGraphPanel;
    protected GraphPanel graphPanel;

    private DCController controller;

    private int xMinPlotSettings = -20;
    private int xMaxPlotSettings = 20;
    private int yMinPlotSettings = 0;
    private int yMaxPlotSettings = 1;

    private double definedAverage = 0;
    private double definedVariance = 1;

    public PanelControl(DCController controller){
        this.controller = controller;
        initComponents();
    }

    private void initComponents(){
        this.setLayout(new GridBagLayout());

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

        //endregion

        //region BOTONES SORTER
        botonesSorterPanel = new BotonesSorter(controller);

        GridBagConstraints constraintsBotonesSorterPanel = new GridBagConstraints();

        constraintsBotonesSorterPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsBotonesSorterPanel.gridwidth = 3;
        constraintsBotonesSorterPanel.gridx = 0;
        constraintsBotonesSorterPanel.gridy = 2;
        //endregion

        this.add(botonesAlgoritmoPanel, constraintsAlgoritmoPanel);
        this.add(botonesSorterPanel, constraintsBotonesSorterPanel);
        this.add(addGraphToPanel(), constraintsGraphWrapperPanel);
        this.setVisible(true);

    }

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
}
