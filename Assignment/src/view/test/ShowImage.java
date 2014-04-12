/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.test;

import java.awt.FlowLayout;
import java.net.MalformedURLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Benjamin
 */
public class ShowImage {
    public static void main(String[] args) throws MalformedURLException {

//        URL url = new URL("bled2-onion-head-emoticon.gif");
        Icon icon = new ImageIcon("Images/Emoticons/1.gif");
        JLabel label = new JLabel(icon);
        Icon icon1 = new ImageIcon("Images/Emoticons/2.gif");
        JLabel label1 = new JLabel(icon1);
        Icon icon2 = new ImageIcon("Images/Emoticons/3.gif");
        JLabel label2 = new JLabel(icon2);
        Icon icon3 = new ImageIcon("Images/Emoticons/4.gif");
        JLabel label3 = new JLabel(icon3);
        Icon icon4 = new ImageIcon("Images/Emoticons/5.gif");
        JLabel label4 = new JLabel(icon4);
        Icon icon5 = new ImageIcon("Images/Emoticons/6.gif");
        JLabel label5 = new JLabel(icon5);
        Icon icon6 = new ImageIcon("Images/Emoticons/7.gif");
        JLabel label6 = new JLabel(icon6);
        Icon icon7 = new ImageIcon("Images/Emoticons/8.gif.gif");
        JLabel label7 = new JLabel(icon7);
        Icon icon8 = new ImageIcon("Images/Emoticons/9.gif");
        JLabel label8 = new JLabel(icon8);
        Icon icon9 = new ImageIcon("Images/Emoticons/10.gif");
        JLabel label9 = new JLabel(icon9);
        Icon icon10 = new ImageIcon("Images/Emoticons/11.gif");
        JLabel label10 = new JLabel(icon10);
        Icon icon11 = new ImageIcon("Images/Emoticons/12.gif");
        JLabel label11 = new JLabel(icon11);
        Icon icon12 = new ImageIcon("Images/Emoticons/13.gif");
        JLabel label12 = new JLabel(icon12);
        Icon icon13 = new ImageIcon("Images/Emoticons/14.gif");
        JLabel label13 = new JLabel(icon13);
        Icon icon14 = new ImageIcon("Images/Emoticons/15.gif");
        JLabel label14 = new JLabel(icon14);
        Icon icon15 = new ImageIcon("Images/Emoticons/16.gif");
        JLabel label15 = new JLabel(icon15);

        JFrame f = new JFrame("Animation");
        f.setLayout(new FlowLayout());
        f.getContentPane().add(label);
        f.getContentPane().add(label1);
        f.getContentPane().add(label2);
        f.getContentPane().add(label3);
        f.getContentPane().add(label4);
        f.getContentPane().add(label5);
        f.getContentPane().add(label6);
        f.getContentPane().add(label7);
        f.getContentPane().add(label8);
        f.getContentPane().add(label9);
        f.getContentPane().add(label10);
        f.getContentPane().add(label11);
        f.getContentPane().add(label12);
        f.getContentPane().add(label13);
        f.getContentPane().add(label14);
        f.getContentPane().add(label15);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
