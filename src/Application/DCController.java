/* Created by andreea on 22/03/2020 */
package Application;

import Domain.Interfaces.IController;
import Presentation.Graph.GraphPanel;

public class DCController implements IController {
    private GraphPanel graphPanel;

    @Override
    public void updateGraph(double mean, double stdDeviation) {
        graphPanel.updateGraphWithNewValues(mean, stdDeviation);
    }

    public void setGraphPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
    }
}
