package Presentation.Graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;



public class Scatterplot extends JPanel {

    private List points = new ArrayList();

    public Scatterplot() {
        //lista de puntos
        points.add(new Point2D.Float(1, 4));
        points.add(new Point2D.Float(2, 10));
        points.add(new Point2D.Float(3, 12));
        //points.add(new Point2D.Float(3, 10));
        // points.add(new Point2D.Float(4, 12));
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
//                g.setColor(Color.RED);
//                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(Color.BLACK);
        for (Iterator i = points.iterator(); i.hasNext(); ) {
            Point2D.Float pt = (Point2D.Float) i.next();
            Ellipse2D dot = new Ellipse2D.Float(pt.x - 1, pt.y - 1, 2, 2);
            g2d.fill(dot);
        }
        g2d.dispose();
    }
}