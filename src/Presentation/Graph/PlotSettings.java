package Presentation.Graph;

import java.awt.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;

/**
 * Clase que sirve para definir las características del gráfico de densidad
 */
public class PlotSettings implements Serializable {

    /**
     * Area Parameters
     */
    protected double minX = -5, maxX = 5, minY = -5, maxY = 5;

    /**
     * Margin
     */
    protected int marginTop = 10, marginBottom = 20, marginLeft = 20, marginRight = 10;

    /**
     * The colour of the axes
     */
    protected Color axisColor = Color.BLACK;

    /**
     * The colour of line graphs
     */
    protected Color plotColor = Color.BLACK;

    /**
     * The colour of the background
     */
    protected Color backgroundColor = Color.WHITE;

    /**
     * The colour of the grid
     */
    protected Color gridColor = Color.LIGHT_GRAY;

    /**
     * The font colour (title and labels)
     */
    protected Color fontColor = Color.BLACK;

    /**
     * The length (in pixels) of each noch on the horizontal and vertical axes
     */
    protected int notchLength = 4;

    /**
     * The distance in pixels between the end of a notch and the corresponding label.
     * Increase this value to move text further away from the notch.
     */
    protected int notchGap = 4;

    /**
     * Display the horizontal grid?
     */
    protected boolean horizontalGridVisible = false;

    /**
     * Display the vertical grid?
     */
    protected boolean verticalGridVisible = true;

    /**
     * Formats the numbers displayed beneath each notch.
     */
    protected Format numberFormatter = new DecimalFormat("0.00");

    /**
     * How many notches in the X direction
     */
    protected double gridSpacingX = 0.25;

    /**
     * How many notches in the Y direction
     */
    protected double gridSpacingY = 0.25;

    /**
     * The title of the graph
     */
    protected String title = null;

    public PlotSettings() {
        // use defaults.
    }

    public PlotSettings(double xMin, double xMax, double yMin, double yMax) {
        this.minX = xMin;
        this.maxX = xMax;
        this.minY = yMin;
        this.maxY = yMax;
    }

    /**
     * Gets the minimum X value for plotting
     */
    public double getMinX() {
        return minX;
    }

    /**
     * Gets the maximum X value for plotting.
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * Gets the minimum Y value for plotting
     */
    public double getMinY() {
        return minY;
    }

    /**
     * Sets the minimum Y value for plotting
     */

    /**
     * Gets the maximum Y value for plotting
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * Sets the maximum Y value for plotting
     */

    public double getRangeX() {
        return maxX - minX;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public Color getPlotColor() {
        return plotColor;
    }

    public void setPlotColor(Color plotColor) {
        this.plotColor = plotColor;
    }

    public void setGridSpacingX(double gridSpacingX) {
        this.gridSpacingX = gridSpacingX;
    }


    public void setGridSpacingY(double gridSpacingY) {
        this.gridSpacingY = gridSpacingY;
    }

}
