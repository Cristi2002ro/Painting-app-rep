package com.company;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//this class represents the area where the user can draw
public class DrawArea extends JComponent implements ChangeListener {
    Color colorChoose = Color.BLACK;
    JButton changeColor = new JButton();
    JSlider slider = new JSlider(0, 100, 25);
    Style style;
    Image image;
    Graphics2D g2;
    int w = slider.getValue();
    int h = slider.getValue();
    // coordonatele mouse-ului
    int currentX, currentY, oldX, oldY;

    //shapes
    JRadioButton triangle = new JRadioButton("Triangle");
    JRadioButton ovalShape = new JRadioButton("Oval");
    JRadioButton square = new JRadioButton("Square");
    JRadioButton rectangle = new JRadioButton("Rectangle");
    JRadioButton none = new JRadioButton("None");


    ButtonGroup group1 = new ButtonGroup();
    JRadioButton shapes[] = {none, ovalShape, triangle, square, rectangle};


    public DrawArea() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //salveaza coordonatele lui x si y cand se apasa mouse ul
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        changeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorChoose = JColorChooser.showDialog(null, "Change the color", Color.black);
                g2.setPaint(colorChoose);
            }
        });
        changeColor.setPreferredSize(new Dimension(100, 70));
        changeColor.setFocusable(false);
        changeColor.setIcon(new ImageIcon("C:\\Users\\Alexandru Duna\\IdeaProjects\\Painting App\\res\\3.png"));

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // coord x,y when drag mouse
                currentX = e.getX();
                currentY = e.getY();
                //setStroke- seteaza grosimea marginilor
                if (g2 != null) {
                    if (none.isSelected()) {
                        if (style == Style.LINE) {
                            g2.drawLine(oldX, oldY, currentX, currentY);
                            g2.setStroke(new BasicStroke(slider.getValue()/10));
                            g2.setPaint(colorChoose);
                            repaint();
                            oldX = currentX;
                            oldY = currentY;
                        } else if (style == Style.OVAL) {
                            g2.fillOval(currentX, currentY, w, h);
                            g2.setPaint(colorChoose);
                            repaint();
                        } else if (style == Style.ERASER) {
                            g2.fillOval(currentX, currentY, w, h);
                            g2.setPaint(Color.white);
                            repaint();
                        }
                        //daca o forma este selectata:
                    }else if(ovalShape.isSelected()){
                        g2.drawOval(currentX, currentY, w, h);
                        g2.setStroke(new BasicStroke(5));
                        g2.setPaint(colorChoose);
                        repaint();
                    }else if(rectangle.isSelected()){
                        g2.drawRect(currentX, currentY, h*2, h);
                        g2.setStroke(new BasicStroke(5));
                        g2.setPaint(colorChoose);
                        repaint();
                    }else if(square.isSelected()){
                        g2.drawRect(currentX, currentY, w, h);
                        g2.setStroke(new BasicStroke(5));
                        g2.setPaint(colorChoose);
                        repaint();
                    }else if(triangle.isSelected()){
                        g2.drawPolygon(new int[] {currentX,currentX- slider.getValue(),currentX+ slider.getValue()}, new int[] {currentY,currentY+ slider.getValue()*2,currentY+ slider.getValue()*2}, 3);
                        g2.setStroke(new BasicStroke(5));
                        g2.setPaint(colorChoose);
                        repaint();
                    }
                }
            }
        });

        slider.setPreferredSize(new Dimension(100, 100));
        slider.setOrientation(SwingConstants.VERTICAL);
        slider.setFont(new Font("MV Boli", Font.BOLD, 10));
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(25);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.addChangeListener(this);

        //shapes:
        group1.add(none);
        group1.add(ovalShape);
        group1.add(triangle);
        group1.add(square);
        group1.add(rectangle);

    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            //imaginea pe care urmeaza sa se deseneze este goala- se creeaza una
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            // antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
        }

        g.drawImage(image, 0, 0, null);
    }

    public void clear() {
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        w = slider.getValue();
        h = slider.getValue();
    }
}
