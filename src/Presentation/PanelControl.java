/* Created by andreea on 29/03/2020 */
package Presentation;

import Presentation.Graph.Graph;
import Presentation.Graph.GraphPanel;
import Presentation.Graph.PlotSettings;

import javax.swing.*;
import java.awt.*;

public class PanelControl extends JPanel {

    public JLabel algoritmoLabel;
    public JLabel sorterLabel;

    public BotonesAlgoritmo botonesAlgoritmoPanel;
    public BotonesSorter botonesSorterPanel;
    public BotonesGraph botonesGraphPanel;
    protected GraphPanel graphPanel;

    public PanelControl(){
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
        botonesAlgoritmoPanel = new BotonesAlgoritmo();

        GridBagConstraints constraintsAlgoritmoPanel = new GridBagConstraints();
        constraintsAlgoritmoPanel.fill = GridBagConstraints.HORIZONTAL;
        constraintsAlgoritmoPanel.gridwidth = 3;
        constraintsAlgoritmoPanel.gridx = 0;
        constraintsAlgoritmoPanel.gridy = 1;

        //endregion

        //region BOTONES SORTER
        botonesSorterPanel = new BotonesSorter();

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

        botonesGraphPanel = new BotonesGraph();

        PlotSettings plotSettings = new PlotSettings(-20, 20, 0, 1);
        plotSettings.setPlotColor(Color.RED);
        plotSettings.setGridSpacingX(0.5);
        plotSettings.setGridSpacingY(0.5);

        graphPanel = new GraphPanel();
        graphPanel.setGraph(new Graph(plotSettings, 5, 2));
        // default size of the window, the Graph Panel will be slightly smaller.
        graphPanel.setVisible(true);

        BotonesGraph botonesGraph = new BotonesGraph();

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

        graphPanelWrapper.add(botonesGraph, constraintsGraphButtons);
        graphPanelWrapper.add(graphPanel, constraintsGraphWrapperPanel);
        return graphPanelWrapper;
    }
}
