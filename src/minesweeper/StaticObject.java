package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StaticObject {


    static JFrame endGame=new JFrame("Game Finished");
    static JPanel panel;
    static JLabel string,string2;
    static ImageIcon gameIcon=new ImageIcon("gameIcon.png");
    static ImageIcon cell=new ImageIcon("cell.png");
    static ImageIcon mine=new ImageIcon("bomb.jpg");
    static ImageIcon flagIcon=new ImageIcon("flag.jpg");
    static ImageIcon youLose=new ImageIcon("lose.png");
    static ImageIcon diedFace=new ImageIcon("diedFace.jpg");
    static ImageIcon smileFace=new ImageIcon("smileFace.png");
    static ImageIcon winFace=new ImageIcon("winFace.png");
    static ImageIcon youWin=new ImageIcon("R.png");
    static ImageIcon gameOver=new ImageIcon("GameOver.png");
    static ImageIcon question=new ImageIcon("R.gif");
    static ImageIcon newGame=new ImageIcon("newGame.jpg");

    static Color c1=new Color(45,173,140);
    static Color c2=new Color(8,46,57);
    static Font font=new Font("Copperplate Gothic Bold",17,20);

    StaticObject(){
        string=new JLabel();
        string2=new JLabel();
        endGame=new JFrame();

    }
    public static ImageIcon resizeImage(ImageIcon image,int x,int y){
        BufferedImage newImage=new BufferedImage(30,30,BufferedImage.TRANSLUCENT);
        Graphics2D gd=newImage.createGraphics();
        gd.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
        gd.drawImage(image.getImage(),0,0,x,y,null);
        gd.dispose();
        return new ImageIcon(newImage);

    }
    public static void changeIcon(JButton button,Square square,Color color){

        if(square.isFlagged()==true){
            button.setIcon(resizeImage(flagIcon,30,30));
            button.setBackground(color);
            return;
        }
        if(square.isOpen()) {
            int temp=square.getStatus();
            if(temp==0) button.setIcon(StaticObject.resizeImage(new ImageIcon("0.png"),30,30));
            if(temp==1) button.setIcon(StaticObject.resizeImage(new ImageIcon("1.png"),30,30));
            if(temp==2) button.setIcon(StaticObject.resizeImage(new ImageIcon("2.png"),30,30));
            if(temp==3) button.setIcon(StaticObject.resizeImage(new ImageIcon("3.png"),30,30));
            if(temp==4) button.setIcon(StaticObject.resizeImage(new ImageIcon("4.png"),30,30));
            if(temp==5) button.setIcon(StaticObject.resizeImage(new ImageIcon("5.png"),30,30));
            if(temp==6) button.setIcon(StaticObject.resizeImage(new ImageIcon("6.png"),30,30));
            if(temp==7) button.setIcon(StaticObject.resizeImage(new ImageIcon("7.png"),30,30));
            if(temp==8) button.setIcon(StaticObject.resizeImage(new ImageIcon("8.png"),30,30));
            button.setBackground(color);
        }
        else {
            button.setIcon(resizeImage(cell,30,30));
            button.setBackground(color);
        }

    }

}
