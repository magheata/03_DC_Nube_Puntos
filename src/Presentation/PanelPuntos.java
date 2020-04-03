/* Created by andreea on 29/03/2020 */
package Presentation;

import Application.DCController;
import Domain.Punto;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.util.Collections;

public class PanelPuntos extends JPanel {

    public JRadioButton generarRandomDistribucionNormalButton;
    public JRadioButton generarRandomButton;
    private ButtonGroup tipoDistribucionButtons;
    private JButton startButton;
    private boolean isDistribucionGaussiana;
    private boolean nubePuntosCreada;
    private JFXPanel fxPanel;
    private GridPane grid;
    private Scene scene;
    private ScatterChart scatterChart;
    private XYChart.Series series;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
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
        fxPanel = new JFXPanel();
        fxPanel.setVisible(true);
        startButton = new JButton();
        startButton.setText("Empezar ejecución");
        startButton.addActionListener(e -> {
            controller.setearParametrosElegidos();
            controller.inicializarPuntos();
            controller.pintarPuntos();
            controller.start();
        });
        generarRandomButton = new JRadioButton();
        generarRandomButton.setText("Distribución Aleatoria");
        generarRandomButton.setSelected(true);
        generarRandomButton.addActionListener(e -> {
            controller.disableGaussianElements();
            cambiarGrafo(false);
        });

        generarRandomDistribucionNormalButton = new JRadioButton();
        generarRandomDistribucionNormalButton.setText("Distribución Gaussiana");
        generarRandomDistribucionNormalButton.addActionListener(e -> {
            controller.enableGaussianElements();
            cambiarGrafo(true);
        });

        tipoDistribucionButtons = new ButtonGroup();
        this.add(generarRandomButton);
        tipoDistribucionButtons.add(generarRandomButton);
        this.add(generarRandomDistribucionNormalButton);
        tipoDistribucionButtons.add(generarRandomDistribucionNormalButton);
        this.add(startButton);
        this.add(fxPanel);
        Platform.runLater(() -> initFxComponents(fxPanel));
        this.setVisible(true);
    }

    private void cambiarGrafo(boolean grafoGaussiano) {
        Platform.runLater(() -> {
            grid = new GridPane();
            if (grafoGaussiano) {
                yAxis = new NumberAxis(-5.5D, 5.5D, 0.25D);
                xAxis = new NumberAxis(-5.5D, 5.5D, 0.25D);
            } else {
                yAxis = new NumberAxis(0, 5D, 0.25D);
                xAxis = new NumberAxis(0, 5D, 0.25D);
            }
             scatterChart = new ScatterChart(xAxis, yAxis);
            scatterChart.setOpacity(0.5);
            scatterChart.setLegendVisible(false);
            grid.add(scatterChart, 0, 0);
            scene = new Scene(grid, 500.0D, 400.0D);
            this.fxPanel.setScene(scene);
        });
    }

    private void initFxComponents(JFXPanel fxPanel) {
        grid = new GridPane();
        scene = createScene();
        this.fxPanel.setScene(scene);
    }

    private Scene createScene(){
        Group root = new Group();
        grid = new GridPane();
        scene = new Scene(grid, 500.0D, 400.0D);
        yAxis = new NumberAxis(0, 5D, 0.25D);
        xAxis = new NumberAxis(0, 5D, 0.25D);
        scatterChart = new ScatterChart(xAxis, yAxis);
        scatterChart.setOpacity(0.5);
        scatterChart.setLegendVisible(false);
        series = new XYChart.Series();
        grid.add(scatterChart, 0, 0);
        scatterChart.getData().add(series);
        return scene;
    }

    public void addPuntos(Punto[] puntos, double maxX, double maxY){
        Platform.runLater(() -> {

            series.getData().removeAll();
            for(int i = 0; i < puntos.length; ++i) {
                x = (puntos[i].getX() * 5) / maxX;
                y = (puntos[i].getY()  * 5) / maxY;
                series.getData().add(getData(x, y));
            }
        });
    }
 public void quitarPuntos() {
     series.getData().removeAll(Collections.singleton(series.getData().setAll()));

 }

    private XYChart.Data getData(double x, double y) {
        XYChart.Data data = new XYChart.Data();
        data.setXValue(x);
        data.setYValue(y);
        return data;
    }

    public int getTam(){ return series.getData().size(); }
}
