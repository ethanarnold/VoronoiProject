import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Voronoi {
    JFrame frame;
    DrawingPanel d;
    JButton b1;

    // JFrame BorderLayout Example
    public Voronoi() {
        // 1. Create the frame (the window that will pop up)
        frame = new JFrame("Voronoi");
        // This line is optional, because BorderLayout is the default
        // layouts for JFrames
//        frame.setLayout(new BorderLayout());
        // 2. Choose what happens when you click the exit button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 3. Create components and put them in the frame

        d = new DrawingPanel();
        frame.add(d);

        b1 = new JButton("Reload");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.repaint();
            }
        });
        frame.add(b1, BorderLayout.EAST);


        // 4. Size the frame
        frame.pack();
        // 5. Show the frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Voronoi b = new Voronoi();
    }

    class Point {
        private Color color;
        private int x;
        private int y;

        public Point(int x1, int y1) {
            color = randomColor();
            x = x1;
            y = y1;
        }

        public Color getColor() {
            return color;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        private Color randomColor() {
            Random rand = new Random();
            int r = rand.nextInt();
            int g = rand.nextInt();
            int b = rand.nextInt();
            return new Color(r, g, b);
        }
    }


    class DrawingPanel extends JPanel implements MouseListener {
        private boolean[][] isPainted;
        private Color[][] colors;
        private Point[][] points;

        // These are constant values
        private static final int WIDTH = 500;
        private static final int HEIGHT = 500;

        // Constructor sets up DrawingPanel
        // ** You should never need to edit this code **
        public DrawingPanel() {
            // Set background color
            setBackground(Color.WHITE);

            // Add mouse listeners
            addMouseListener(this);

            // Initialize instance variables
            isPainted = new boolean[WIDTH][HEIGHT];
            colors = new Color[WIDTH][HEIGHT];
            points = new Point[WIDTH][HEIGHT];
        }

        // Can be called to clear the canvas
        public void clear() {
            for (int row = 0; row < isPainted.length; row++) {
                for (int col = 0; col < isPainted[0].length; col++) {
                    isPainted[col][row] = false;
                }
            }
        }

        // Sets the size of the DrawingPanel, so frame.pack() considers
        // its preferred size when sizing the JFrame
        // ** You should never need to edit this code **
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH, HEIGHT);
        }

        // This is the method that draws everything
        // ** Code to be edited in Part C **
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Loop through the 2D array and draw a 1x1 rectangle
            // on each pixel that is currently painted
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (isPainted[x][y]) {
                        g.setColor(colors[x][y]);
                        g.drawRect(x, y, 1, 1);
                    }
                }
            }
        }
        public Point closestPoint(int x, int y) {
            Point result = null;
            double currentDistance = 0.0;
            for (Point[] manyPoints : points) {
                for (Point aPoint : manyPoints) {
                    if (aPoint != null) {
                        if (result == null) {
                            result = aPoint;
                            currentDistance = distance(x, y, aPoint);
                        }
                        else if (distance(x, y, aPoint) < currentDistance) {
                            result = aPoint;
                            currentDistance = distance(x, y, aPoint);
                        }
                    }
                }
            }
            return result;
        }

        // MouseListener methods
        // This is the method that is called when the mouse
        // is pressed. This is where most of your code will go
        // ** Code to be edited in Part B **
        public void mousePressed(MouseEvent e) {
            // Check that mouse is in bounds of panel
            if (e.getX() >= 0 && e.getX() < WIDTH &&
                    e.getY() >= 0 && e.getY() < HEIGHT) {
                // Set current pixel as painted
                colors[e.getX()][e.getY()] = Color.BLACK;
                isPainted[e.getX()][e.getY()] = true;
                points[e.getX()][e.getY()] = new Point(e.getX(), e.getY());
            }

            // We need to manually tell the panel to repaint
            // and call the paintComponent method
            repaint();
        }

        // The remaining MouseListener and MouseMotionLister
        // methods are left blank
        // ** You should never need to edit this code **
        public void mouseReleased(MouseEvent e) {
            // This method is intentionally blank
        }
        // ** You should never need to edit this code **
        public void mouseEntered(MouseEvent e) {
            // This method is intentionally blank
        }
        // ** You should never need to edit this code **
        public void mouseExited(MouseEvent e) {
            // This method is intentionally blank
        }
        // ** You should never need to edit this code **
        public void mouseClicked(MouseEvent e) {
            // This method is intentionally blank
        }
        // ** You should never need to edit this code **
        public void mouseMoved(MouseEvent e) {
            // This method is intentionally blank
        }
    }

    public double distance(int x, int y, Point p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }

}

