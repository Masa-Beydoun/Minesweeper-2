package minesweeper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class GameGUI implements ActionListener,Runnable {
    JPanel rightSidePanel,gamePanel,firstNamePanel,secondNamePanel;
    JFrame frame;

    JLabel tries,tries2,name, name2;
    JButton newGame,face;
    Grid grid;
    Player[] players;
    protected int cnt = 0;
    public int counter;
    JButton button[][];
    Container con;
    protected int numOfCells;
    int modeNum,height,width,gameOver;

    public GameGUI() {
    }


    public GameGUI(Grid grid, SignIn in,File file){
    //    file = new File("lastGame.txt");

        // no saved game
        if(file==null) {
                if(in.mood==0) new OnePlayerMood(grid,in,file);
                else if(in.mood==1) new TwoPlayerMood(grid,in,file);
                else new VsComputerMood(grid,in,file);
        }
        else{

            Scanner scan;
            try {
                scan = new Scanner(file);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "there is no saved game");
                new SignIn();
                return;
            }

            //saved game
            players=new Player[2];
            // game mode
            int temp=Integer.parseInt(scan.nextLine());
            gameOver=Integer.parseInt(scan.nextLine());
            modeNum = Integer.parseInt(scan.nextLine());
            scan.close();
            System.out.println("temp = "+temp);
            if(temp==1 || gameOver > 2){
                scan.close();
                JOptionPane.showMessageDialog(null,"There is no saved game");
                new SignIn();
                return;
            }
            if(modeNum ==0) new OnePlayerMood(null,null,file);
            else if(modeNum ==1) new TwoPlayerMood(null,null,file);
            else new VsComputerMood(null,null,file);
        }
    }

    public void createFrame(){

        frame = new JFrame("Minesweeper");
        frame.setIconImage(StaticObject.gameIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2));
     //   frame.setSize(new Dimension(grid.getWidth()*80, grid.getHeight()*25));
        frame.setVisible(true);
        button = new JButton[grid.getHeight()][grid.getWidth()];

        // panels

        //side panel
        rightSidePanel = new JPanel();
        rightSidePanel.setBorder(new LineBorder(StaticObject.c2, 3));
        rightSidePanel.setBackground(StaticObject.c2);
        rightSidePanel.setLayout(new GridLayout(3, 1, 3, 3));
        rightSidePanel.setVisible(true);

        //game panel
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(grid.getHeight(), grid.getWidth()));
        gamePanel.setBackground(StaticObject.c2);
        gamePanel.setVisible(true);

        // labels

        // name
        name = new JLabel(" Name : " + players[0].name);
        name.setForeground(Color.WHITE);
        name.setFont(StaticObject.font);

        //tries
        tries=new JLabel( gameOver + " / 3");
        tries.setForeground(Color.WHITE);
        tries.setFont(StaticObject.font);

        //name2
        name2 = new JLabel(" Name : " + players[1].name);
        name2.setForeground(Color.WHITE);
        name2.setFont(StaticObject.font);

        //for name two
        tries2 = new JLabel("   ");
        tries2.setForeground(Color.WHITE);
        tries2.setFont(StaticObject.font);

        //new Game
        newGame = new JButton();
        newGame.setBorder(new LineBorder(Color.WHITE,4));
        newGame.setIcon(StaticObject.newGame);
        newGame.setFont(StaticObject.font);
        newGame.setBackground(StaticObject.c2);
        newGame.setBorder(new LineBorder(Color.WHITE,4));
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SignIn();
            }
        });

        //face
        face = new JButton(StaticObject.resizeImage(StaticObject.smileFace,30,30));
        face.setBackground(StaticObject.c2);
        face.setForeground(Color.WHITE);


        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                button[i][j] = new JButton();
                StaticObject.changeIcon(button[i][j], grid.squares[i][j], Color.LIGHT_GRAY);
                gamePanel.add(button[i][j]);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void run() {}
    public boolean aroundMines(int x, int y){
        int count=0,count2=0;
        if(x + 1 < grid.getHeight() && y + 1 < grid.getWidth()){
            if(grid.squares[x+1][y+1].isFlagged() && !grid.squares[x+1][y+1].isOpen()) count++;
            if(grid.squares[x+1][y+1].isOpen()) count2++;
        }
        if(x - 1 >= 0 && y - 1 >= 0 ){
            if(grid.squares[x-1][y-1].isFlagged() && !grid.squares[x-1][y-1].isOpen())count++;
            if(grid.squares[x-1][y-1].isOpen()) count2++;
        }

        if(x + 1 < grid.getHeight()  ){
            if(grid.squares[x+1][y].isFlagged() && !grid.squares[x+1][y].isOpen())count++;
            if(grid.squares[x+1][y].isOpen()) count2++;
        }
        if(y + 1 < grid.getWidth()){
            if(grid.squares[x][y+1].isFlagged() && !grid.squares[x][y+1].isOpen())count++;
            if(grid.squares[x][y+1].isOpen()) count2++;
        }

        if(y - 1 >= 0  ){
            if(grid.squares[x][y-1].isFlagged() && !grid.squares[x][y-1].isOpen())count++;
            if(grid.squares[x][y-1].isOpen()) count2++;
        }
        if(x - 1 >= 0 ){
            if(grid.squares[x-1][y].isFlagged() && !grid.squares[x-1][y].isOpen())count++;
            if(grid.squares[x-1][y].isOpen()) count2++;
        }

        if(x + 1 < grid.getHeight() && y - 1 >= 0 ){
            if(grid.squares[x+1][y-1].isFlagged() && !grid.squares[x+1][y-1].isOpen())count++;
            if(grid.squares[x+1][y-1].isOpen()) count2++;
        }
        if(x - 1 >= 0 && y + 1 < grid.getWidth() ){
            if(grid.squares[x-1][y+1].isFlagged() && !grid.squares[x-1][y+1].isOpen())count++;
            if(grid.squares[x-1][y+1].isOpen()) count2++;
        }
        return ( count == grid.squares[x][y].getStatus() && count2!=grid.squares[x][y].place-grid.squares[x][y].getStatus());
    }


    public int getCnt() {
        return cnt;
    }
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }




}


