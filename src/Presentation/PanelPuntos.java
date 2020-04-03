/* Created by andreea on 29/03/2020 */
package Presentation;

import Application.DCController;
import Domain.Punto;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.GridPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PanelPuntos extends JPanel {

    public JRadioButton generarRandomDistribucionNormalButton;
    public JRadioButton generarRandomButton;
    private ButtonGroup tipoDistribucionButtons;
    private JButton startButton;
    private boolean isDistribucionGaussiana;
    private boolean nubePuntosCreada;
    private JFXPanel fxPanel;
    private NumberAxis xAxis;
    double x;
    double y;

    private DCController controller;

    public PanelPuntos(DCController controller){
        initComponents();
        this.controller = controller;
        isDistribucionGaussiana = false;
        nubePuntosCreada = false;
    }

    private void initComponents(){
        startButton = new JButton();
        startButton.setText("Empezar ejecución");
        startButton.addActionListener(e -> {
            controller.setearParametrosElegidos();
            controller.inicializarPuntos();
            controller.start();
        });
        generarRandomButton = new JRadioButton();
        generarRandomButton.setText("Distribución Aleatoria");
        generarRandomButton.setSelected(true);
        generarRandomButton.addActionListener(e -> controller.disableGaussianElements());

        generarRandomDistribucionNormalButton = new JRadioButton();
        generarRandomDistribucionNormalButton.setText("Distribución Gaussiana");
        generarRandomDistribucionNormalButton.addActionListener(e -> controller.enableGaussianElements());

        tipoDistribucionButtons = new ButtonGroup();
        this.add(generarRandomButton);
        tipoDistribucionButtons.add(generarRandomButton);
        this.add(generarRandomDistribucionNormalButton);
        tipoDistribucionButtons.add(generarRandomDistribucionNormalButton);
        this.add(startButton);
        this.setVisible(true);
    }

    public void initFxComponents() {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 500.0D, 400.0D);
        NumberAxis yAxis = new NumberAxis(0.0D, 5.0D, 1.0D);
        NumberAxis xAxis = new NumberAxis(0.0D, 5.0D, 1.0D);
        ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        Punto[] p = this.controller.getPuntos();

        for(int i = 0; i < this.controller.getPuntos().length; ++i) {
            this.x = p[i].getX();
            this.y = p[i].getY();
            series.getData().add(this.getData(this.x, this.y));
        }

        grid.add(scatterChart, 0, 0);
        this.fxPanel.setScene(scene);
    }

    private XYChart.Data getData(double x, double y) {
        XYChart.Data data = new XYChart.Data();
        data.setXValue(x);
        data.setYValue(y);
        return data;
    }
}
