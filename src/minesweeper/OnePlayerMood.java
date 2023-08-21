package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


public class OnePlayerMood extends GameGUI {

    public OnePlayerMood(Grid grid, SignIn in,File file){

        // no saved game
        if(file==null) {
            counter = 0;
            this.grid = grid;
            players = new Player[2];
            modeNum = in.mood;
            height = in.getN();
            width = in.getM();
            numOfCells = (in.getN() * in.getM()) - grid.getNumOfMines();
            players[0] = new Player(in.name1, 0,Color.LIGHT_GRAY);
            players[1] = new Player(in.name2, 0, StaticObject.c2);
            createFrame();
        }

        else{
            SaveLoad.LoadGame(this,file);
            numOfCells = this.grid.getHeight() * this.grid.getWidth() - this.grid.getNumOfMines();
            createFrame();
        }
    }
    public void createFrame(){

        super.createFrame();

        face.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Grid newGrid = new Grid(height,width);
                new GameGUI(newGrid,new SignIn(newGrid,players[0].name,players[1].name,0),null);
            }
        });

        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                int finalI = i;
                int finalJ = j;
                button[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        System.out.println("counter"+counter+" "+cnt);
                        if(gameOver>2) return;
                        if (SwingUtilities.isLeftMouseButton(e)) {

                            if(counter==0 ) grid.distributeMines(finalI,finalJ);
                            if (grid.squares[finalI][finalJ].isFlagged()) return;

                            players[0].move( OnePlayerMood.this, finalI, finalJ, "open");
                        }
                        else {
                            if (counter == 0) return;
                            if (grid.squares[finalI][finalJ].isOpen()) return;
                            if (grid.squares[finalI][finalJ].isFlagged()) players[0].move(OnePlayerMood.this, finalI, finalJ, "removeMine");
                            else players[0].move(OnePlayerMood.this, finalI, finalJ, "addMine");

                        }

                        if(counter>=numOfCells || gameOver>2) {
                            return;
                        }
                        SaveLoad.writeToFile(OnePlayerMood.this,0);
                        tries.setText(gameOver+ " / 3 ");
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
            }
        }
        //addition
        //side Panel
        rightSidePanel.add(name);
        rightSidePanel.add(tries);
        rightSidePanel.add(newGame);
        Container mai=new JPanel(new BorderLayout(1,1));
        mai.add(face,BorderLayout.NORTH);
        mai.add(gamePanel,BorderLayout.CENTER);

        frame.add(mai);
        frame.add(rightSidePanel);

        con = frame.getContentPane();

        con.setLayout(new BorderLayout(2, 2));

        con.add(mai,BorderLayout.CENTER);
        con.add(rightSidePanel,BorderLayout.EAST);
        frame.pack();



    }
    public static boolean isItOver(GameGUI game,int x,int y){
        if(game.counter== game.numOfCells ) {
            SaveLoad.writeToFile(game,1);
            game.face.setIcon(StaticObject.resizeImage(StaticObject.winFace, 30, 30));
            for (int i = 0; i < game.height; i++) {
                for (int j = 0; j < game.width; j++) {
                    game.grid.squares[i][j].setOpen(true);
                    if (game.grid.squares[i][j].getStatus() == -1) {
                        game.button[i][j].setIcon(StaticObject.resizeImage(StaticObject.flagIcon, 30, 30));
                    }
                }
            }
            game.tries.setText("");
            game.tries.setIcon(StaticObject.youWin);
            return true;
        }
        else if(game.grid.squares[x][y].getStatus()==-1 && game.gameOver>=2 ) {
            SaveLoad.writeToFile(game,1);
            game.tries.setText("");
            game.tries.setIcon(StaticObject.gameOver);
            game.gameOver++;
            game.face.setIcon(StaticObject.resizeImage(StaticObject.diedFace,30,30));
            for (int i = 0; i < game.height; i++) {
                for (int j = 0; j < game.width; j++) {
                    game.grid.squares[i][j].setOpen(true);
                    game.grid.squares[i][j].setFlagged(true);
                    if (game.grid.squares[i][j].getStatus() == -1) {
                        game.button[i][j].setIcon(StaticObject.resizeImage(new ImageIcon("bomb.jpg"),30,30));
                    }
                }
            }
            return true;
        }

        return false;
    }
    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void run() {}
    public int getCnt() {
        return cnt;
    }
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }


}


