package ui;

import convert.Converter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {
    private static final int MIN_SZ = GroupLayout.PREFERRED_SIZE;
    private static final int MAX_SZ = GroupLayout.DEFAULT_SIZE;

    private JPanel mainPanel;
    private JPanel controlPanel;
    private JPanel colourDotPanel;
    private JPanel colourGraphPanel;
    private JPanel colourDerivativePanel;

    private JLabel lbXMin;
    private JLabel lbXMax;
    private JLabel lbYMin;
    private JLabel lbYMax;
    private JLabel coordinates;

    private JSpinner spinXMin;
    private JSpinner spinXMax;
    private JSpinner spinYMin;
    private JSpinner spinYMax;

    private SpinnerNumberModel snmXMin;
    private SpinnerNumberModel snmXMax;
    private SpinnerNumberModel snmYMin;
    private SpinnerNumberModel snmYMax;

    private JCheckBox checkDot;
    private JCheckBox checkGraph;
    private JCheckBox checkDerivative;

    private JButton clearButton;

    private final CartesianPainter cartesianPainter;
    private final FunctionPainter functionPainter;
    private final Converter initialConverter;

    private void initComponents() {
        snmXMin = new SpinnerNumberModel(-5.1, -100.0, 4.9, 0.1);
        snmXMax = new SpinnerNumberModel(5.1, -4.9, 100.0, 0.1);
        snmYMin = new SpinnerNumberModel(-5.1, -100.0, 4.9, 0.1);
        snmYMax = new SpinnerNumberModel(5.1, -4.9, 100, 0.1);

        ChangeListener listener = e -> {
            updateConverter();
            mainPanel.repaint();
        };

        snmXMin.addChangeListener(e ->
                snmXMax.setMinimum((double)snmXMin.getValue() + 0.1));
        snmXMax.addChangeListener(e->
                snmXMin.setMaximum((double)snmXMax.getValue() - 0.1));
        snmYMin.addChangeListener(e ->
                snmYMax.setMinimum((double)snmYMin.getValue() + 0.1));
        snmYMax.addChangeListener(e->
                snmYMin.setMaximum((double)snmYMax.getValue() - 0.1));

        snmXMin.addChangeListener(listener);
        snmXMax.addChangeListener(listener);
        snmYMin.addChangeListener(listener);
        snmYMax.addChangeListener(listener);

        spinXMin = new JSpinner(snmXMin);
        spinXMax = new JSpinner(snmXMax);
        spinYMin = new JSpinner(snmYMin);
        spinYMax = new JSpinner(snmYMax);

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                updateConverter();
                cartesianPainter.paint(g);
                if(checkGraph.isSelected()) {
                    g.setColor(colourGraphPanel.getBackground());
                    functionPainter.paint(g);
                }
                if(checkDot.isSelected())
                    functionPainter.paintDot(g, colourDotPanel.getBackground());
                if(checkDerivative.isSelected())
                    functionPainter.paintDerivativeGraph(g,colourDerivativePanel.getBackground());
            }
        };

        mainPanel.setBackground(Color.WHITE);

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    if(!functionPainter.addPointToPolynomial(e.getX(), e.getY()))
                        JOptionPane.showMessageDialog(mainPanel,"Select another point",
                                "Warning",JOptionPane.WARNING_MESSAGE);
                }
                else functionPainter.deleteNearestPoint(e.getX(), e.getY());
                mainPanel.repaint();
            }
        });

        coordinates = new JLabel();
        coordinates.setForeground(Color.gray);
        coordinates.setSize(coordinates.getPreferredSize());
        mainPanel.add(coordinates);
        mainPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double x = initialConverter.xScr2Crt(e.getX());
                double y = initialConverter.yScr2Crt(e.getY());

                String text = String.format("X: %.2f, Y: %.2f", x, y);
                coordinates.setText(text);

                coordinates.setSize(coordinates.getPreferredSize());
                coordinates.setLocation(e.getX() + 15, e.getY() + 15);

                coordinates.setVisible(true);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setBorder(
                new TitledBorder(
                        new EtchedBorder(),
                        "Settings"
                )
        );

        colourDotPanel = new JPanel();
        colourDotPanel.setBackground(Color.green);
        colourDotPanel.setBorder(new LineBorder(Color.black));
        colourDotPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var result = JColorChooser.showDialog(colourDotPanel, "Цвет точек", Color.GREEN);
                colourDotPanel.setBackground(result);
                mainPanel.repaint();
            }
        });

        colourGraphPanel = new JPanel();
        colourGraphPanel.setBackground(Color.blue);
        colourGraphPanel.setBorder(new LineBorder(Color.black));
        colourGraphPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var result = JColorChooser.showDialog(colourGraphPanel, "Цвет графика", Color.BLUE);
                colourGraphPanel.setBackground(result);
                mainPanel.repaint();
            }
        });
        colourDerivativePanel = new JPanel();
        colourDerivativePanel.setBackground(Color.red);
        colourDerivativePanel.setBorder(new LineBorder(Color.black));
        colourDerivativePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var result = JColorChooser.showDialog(colourDerivativePanel, "Цвет производной", Color.RED);
                colourDerivativePanel.setBackground(result);
                mainPanel.repaint();
            }
        });

        lbXMin = new JLabel("Min x: ");
        lbXMax = new JLabel("Max x: ");
        lbYMin = new JLabel("Min y: ");
        lbYMax = new JLabel("Max y: ");

        checkDot = new JCheckBox("Отображать точки",true);
        checkGraph = new JCheckBox("Отображать график функций",true);
        checkDerivative = new JCheckBox("Отображать производную");

        checkDot.addItemListener(e -> mainPanel.repaint());
        checkGraph.addItemListener(e -> mainPanel.repaint());
        checkDerivative.addItemListener(e -> mainPanel.repaint());

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e ->{
            functionPainter.clearGraph();
            mainPanel.repaint();
        });
    }

    private void updateConverter() {
        double xMin = (Double)spinXMin.getValue();
        double xMax = (Double)spinXMax.getValue();
        double yMin = (Double)spinYMin.getValue();
        double yMax = (Double)spinYMax.getValue();

        initialConverter.setIntervalX(xMin,xMax);
        initialConverter.setIntervalY(yMin,yMax);
        initialConverter.setImageWidth(mainPanel.getWidth());
        initialConverter.setImageHeight(mainPanel.getHeight());
    }

    public MainWindow() {
        setMinimumSize(new Dimension(800,1000));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();

        initialConverter = new Converter((double) spinXMin.getValue(), (double) spinXMax.getValue(),
                (double) spinYMin.getValue(), (double) spinYMax.getValue(),
                mainPanel.getWidth(), mainPanel.getHeight());
        cartesianPainter = new CartesianPainter(initialConverter);
        functionPainter = new FunctionPainter(initialConverter);

        GroupLayout gl = new GroupLayout(getContentPane());
        setLayout(gl);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(10)
                .addComponent(mainPanel, MAX_SZ, MAX_SZ, MAX_SZ)
                .addGap(10)
                .addComponent(controlPanel, MIN_SZ, MIN_SZ, MIN_SZ)
        );
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(10)
                .addGroup(gl.createParallelGroup()
                        .addComponent(mainPanel, MAX_SZ, MAX_SZ, MAX_SZ)
                        .addComponent(controlPanel, MAX_SZ, MAX_SZ, MAX_SZ)
                )
                .addGap(10)
        );

        GroupLayout cp = new GroupLayout(controlPanel);
        controlPanel.setLayout(cp);

        cp.setVerticalGroup(cp.createSequentialGroup()
                .addGap(10)
                .addGroup(cp.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(cp.createSequentialGroup()
                            .addComponent(lbXMin, MIN_SZ, MIN_SZ, MIN_SZ)
                            .addGap(10)
                            .addComponent(lbYMin, MIN_SZ, MIN_SZ, MIN_SZ)
                    )
                    .addGroup(cp.createSequentialGroup()
                            .addComponent(spinXMin, MIN_SZ, MIN_SZ, MIN_SZ)
                            .addGap(10)
                            .addComponent(spinYMin, MIN_SZ, MIN_SZ, MIN_SZ)
                    )
                    .addGroup(cp.createSequentialGroup()
                            .addComponent(lbXMax, MIN_SZ, MIN_SZ, MIN_SZ)
                            .addGap(10)
                            .addComponent(lbYMax, MIN_SZ, MIN_SZ, MIN_SZ)
                    )
                    .addGroup(cp.createSequentialGroup()
                            .addComponent(spinXMax, MIN_SZ, MIN_SZ, MIN_SZ)
                            .addGap(10)
                            .addComponent(spinYMax, MIN_SZ, MIN_SZ, MIN_SZ)
                    )
                    .addGroup(cp.createSequentialGroup()
                            .addGroup(cp.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(checkDot, MIN_SZ, MIN_SZ, MIN_SZ)
                                    .addComponent(colourDotPanel, 20, 20, 20)
                            )
                            .addGap(5)
                            .addGroup(cp.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(checkGraph, MIN_SZ, MIN_SZ, MIN_SZ)
                                    .addComponent(colourGraphPanel, 20, 20, 20)
                            )
                            .addGap(5)
                            .addGroup(cp.createParallelGroup(GroupLayout.Alignment.CENTER)
                                    .addComponent(checkDerivative, MIN_SZ, MIN_SZ, MIN_SZ)
                                    .addComponent(colourDerivativePanel, 20, 20, 20)
                            )
                    )
                    .addComponent(clearButton, MAX_SZ, MAX_SZ, MAX_SZ)
                )
                .addGap(10)
        );
        cp.setHorizontalGroup(cp.createSequentialGroup()
                .addGap(10)
                .addGroup(cp.createParallelGroup()
                        .addComponent(lbXMin, MIN_SZ, MIN_SZ, MIN_SZ)
                        .addComponent(lbYMin, MIN_SZ, MIN_SZ, MIN_SZ)
                )
                .addGap(10)
                .addGroup(cp.createParallelGroup()
                        .addComponent(spinXMin, 100, MIN_SZ, MIN_SZ)
                        .addComponent(spinYMin, 100, MIN_SZ, MIN_SZ)
                )
                .addGap(10)
                .addGroup(cp.createParallelGroup()
                        .addComponent(lbXMax, MIN_SZ, MIN_SZ, MIN_SZ)
                        .addComponent(lbYMax, MIN_SZ, MIN_SZ, MIN_SZ)
                )
                .addGap(10)
                .addGroup(cp.createParallelGroup()
                        .addComponent(spinXMax, 100, MIN_SZ, MIN_SZ)
                        .addComponent(spinYMax, 100, MIN_SZ, MIN_SZ)
                )
                .addGap(8, 8, Integer.MAX_VALUE)
                .addGroup(cp.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cp.createSequentialGroup()
                                .addGroup(cp.createParallelGroup()
                                        .addComponent(checkDot, MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(checkGraph, MIN_SZ, MIN_SZ, MIN_SZ)
                                        .addComponent(checkDerivative, MIN_SZ, MIN_SZ, MIN_SZ)
                                )
                                .addGap(5)
                                .addGroup(cp.createParallelGroup()
                                        .addComponent(colourDotPanel,20,20,20)
                                        .addComponent(colourGraphPanel, 20, 20, 20)
                                        .addComponent(colourDerivativePanel, 20, 20, 20)

                                )
                        )
                )
                .addGap(10)
                .addComponent(clearButton,MAX_SZ, MAX_SZ, MAX_SZ)
                .addGap(10)
        );

    }
}
