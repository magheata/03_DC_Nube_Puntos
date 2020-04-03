/* Created by andreea on 29/03/2020 */
package Presentation;

import Application.DCController;
import Domain.Punto;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.GridPane;

import javafx.scene.chart.*;

import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;


public class PanelPuntos extends JPanel {

    public JRadioButton generarRandomDistribucionNormalButton;
    public JRadioButton generarRandomButton;
    private ButtonGroup tipoDistribucionButtons;
    private JButton startButton;
    private JFXPanel fxPanel;
    private boolean isDistribucionGaussiana;
    private boolean nubePuntosCreada;
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
        fxPanel = new JFXPanel();
        this.add(fxPanel,BorderLayout.EAST);
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
    public void initFxComponents(){

                GridPane grid = new GridPane();

                Scene scene = new Scene(grid, 500, 400);

                NumberAxis yAxis = new NumberAxis(0.0,5.0,1.0);

                NumberAxis xAxis = new NumberAxis(0.0,5.0,1.0);

                ScatterChart scatterChart = new ScatterChart<>(xAxis,yAxis);
                XYChart.Series series = new XYChart.Series<>();
                Punto[] p = controller.getPuntos();
                for (int i = 0; i<controller.getPuntos().length;i++){
                    x= p[i].getX();
                    y= p[i].getY();
                    series.getData().add(getData(x,y));
                }

                grid.add(scatterChart,0,0);

                fxPanel.setScene(scene);
                fxPanel.repaint();
            }


    private XYChart.Data getData(double x, double y){

        XYChart.Data data = new XYChart.Data<>();

        data.setXValue(x);

        data.setYValue(y);

        return data;

    }





}
