/* Created by andreea on 29/03/2020 */
package Presentation.Graph;

import java.awt.*;

public class GaussianFunctionPlotter {

    protected double stdDeviation, variance, mean;

    public GaussianFunctionPlotter(double mean, double stdDeviation) {
        this.stdDeviation = stdDeviation;
        variance = stdDeviation * stdDeviation;
        this.mean = mean;
    }

    public GaussianFunctionPlotter() {

    }

    public double getY(double x) {
        return Math.pow(Math.exp(-(((x - mean) * (x - mean)) / ((2 * variance)))), 1 / (stdDeviation * Math.sqrt(2 * Math.PI)));
    }

    public String getName() {
        return "Gaussian Curve";
    }

    public void plot(Graph graph, Graphics g, int chartWidth) {

        /**
         * Record the last two points. Plotting works by drawing lines between consecutive points
         * This ensures there are no gaps.
         */
        double prevX = graph.plotSettings.getMarginLeft(), prevY = 100;

        /**
         * Flag to make sure the first point is not drawn (there is not previous point to connect the dots to)
         */
        boolean first = true;

        double xRange = graph.plotSettings.getRangeX();

        /**
         * Plot for every pixel going across the chart
         */
        for (int ax = 0; ax < chartWidth; ax++) {

            // figure out what X is
            double x = graph.plotSettings.getMinX() + ((ax / (double) chartWidth) * xRange);

            /**
             * For this value of X, get the value of Y (via the abstract method)
             */
            double y = getY(x);

            /**
             * Draw a line between two points
             */
            if (!first && y <= graph.plotSettings.getMaxY() && y >= graph.plotSettings.getMinY()) graph.drawLine(g, prevX, prevY, x, y);

            /**
             * Remember the last two values
             */
            prevX = x;
            prevY = y;

            /**
             * To stop the first point being drawn
             */
            first = false;
        }
    }

    public void setStdDeviation(double stdDeviation) {
        this.stdDeviation = stdDeviation;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }
}
