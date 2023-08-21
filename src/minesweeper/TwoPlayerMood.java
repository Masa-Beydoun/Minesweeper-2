package minesweeper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Random;

public class TwoPlayerMood extends GameGUI {


    public TwoPlayerMood(Grid grid, SignIn in,File file){

        // no saved game
        if(file==null) {
            counter = 0;
            cnt=0;
            this.grid = grid;
            players = new Player[2];
            modeNum = in.mood;
            height = in.getN();
            width = in.getM();
            numOfCells = (in.getN() * in.getM()) - grid.getNumOfMines();
            players[0] = new Player(in.name1, 0, Color.LIGHT_GRAY);
            players[1] = new Player(in.name2, 0, Color.LIGHT_GRAY);
            createFrame(in);
        }
        // load game
        else {
            SaveLoad.LoadGame(this, file);
            numOfCells = this.grid.getWidth() * this.grid.getHeight() - this.grid.getNumOfMines();
            createFrame(null);
        }
    }

    public void createFrame(SignIn in){

        super.createFrame();

        //first name panel
        firstNamePanel = new JPanel();
        firstNamePanel.setBorder(new LineBorder(Color.WHITE, 3));
        firstNamePanel.setLayout(new GridLayout(2,1));
        firstNamePanel.setBackground(StaticObject.c1);
        firstNamePanel.setVisible(true);

        //second name panel
        secondNamePanel = new JPanel();
        secondNamePanel.setBorder(new LineBorder(Color.WHITE, 3));
        secondNamePanel.setLayout(new GridLayout(2,1));
        secondNamePanel.setBackground(StaticObject.c2);
        secondNamePanel.setVisible(true);

        //for name one
        tries = new JLabel(" Your turn  ");
        tries.setForeground(Color.WHITE);
        tries.setFont(StaticObject.font);

        //addition
        //side Panel
        firstNamePanel.add(name);
        firstNamePanel.add(tries);
        secondNamePanel.add(name2);
        secondNamePanel.add(tries2);

        rightSidePanel.add(firstNamePanel);
        rightSidePanel.add(secondNamePanel);
        rightSidePanel.add(newGame);

        face.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Grid newGrid = new Grid(height,width);
                new GameGUI(newGrid,new SignIn(newGrid,players[0].name,players[1].name,1),null);
            }
        });

        if(in != null ){
            int ranX=new Random().nextInt(height);
            int ranY=new Random().nextInt(width);
            grid.distributeMines(ranX,ranY);
            new Player("comp",0,Color.LIGHT_GRAY).move(this,ranX,ranY,"open");
            SaveLoad.writeToFile(TwoPlayerMood.this,0);
        }

        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                int finalI = i;
                int finalJ = j;
                button[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {}
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(gameOver>0) return;
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if ( grid.squares[finalI][finalJ].isFlagged())
                                return;
                            if(grid.squares[finalI][finalJ].isOpen() && !aroundMines(finalI,finalJ))
                                return;
                            //index of the player
                            int r = 0;
                            if (cnt % 2 != 0) r = 1;

                            players[r].move(TwoPlayerMood.this, finalI, finalJ, "open");
                            cnt++;
                        } else {
                            if (grid.squares[finalI][finalJ].isOpen()) return;
                            //removing mine
                            if (grid.squares[finalI][finalJ].isFlagged()) {
                                if (cnt % 2 != 0) {
                                    players[1].move(TwoPlayerMood.this, finalI, finalJ, "removeMine");
                                } else {
                                    players[0].move(TwoPlayerMood.this, finalI, finalJ, "removeMine");
                                }
                            }
                            //adding mine
                            else {
                                if (cnt % 2 != 0) {
                                    players[1].move(TwoPlayerMood.this, finalI, finalJ, "addMine");
                                } else {
                                    players[0].move(TwoPlayerMood.this, finalI, finalJ, "addMine");
                                }
                                cnt++;
                            }

                        }
                        if(gameOver>0) return;
                        if (cnt % 2 != 0) {
                            tries.setText(" ");
                            tries2.setText(" YOUR TURN");
                        } else {
                            tries.setText(" YOUR TURN ");
                            tries2.setText(" ");
                        }
                        SaveLoad.writeToFile(TwoPlayerMood.this,0);

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
            }
        }

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

    public static boolean isItOver(GameGUI game,int x,int y,Player player) {
        //all cells had been opened
        if (game.counter ==game.numOfCells ) {
            SaveLoad.writeToFile(game,1);
            game.gameOver++;
            game.face.setIcon(StaticObject.resizeImage(StaticObject.winFace, 30, 30));
            for (int i = 0; i < game.height; i++) {
                for (int j = 0; j < game.width; j++) {
                    game.grid.squares[i][j].setOpen(true);
                    if (game.grid.squares[i][j].getStatus() == -1) {
                        game.button[i][j].setIcon(StaticObject.resizeImage(StaticObject.flagIcon, 30, 30));
                    }
                }
            }

            if(game.players[0].score > game.players[1].score) {
                game.tries.setText("");
                game.tries.setIcon(StaticObject.youWin);

                game.tries2.setText("");
                game.tries2.setIcon(StaticObject.youLose);
            }
            else if(game.players[0].score < game.players[1].score) {

                game.tries.setText("");
                game.tries.setIcon(StaticObject.youLose);

                game.tries2.setText("");
                game.tries2.setIcon(StaticObject.youWin);
            }
            else {
                game.tries2.setText("");
                game.tries2.setIcon(StaticObject.youWin);

                game.tries.setText("");
                game.tries.setIcon(StaticObject.youLose);
            }
            return true;
        }
        // open a mine && score less than 250
        if( game.grid.squares[x][y].getStatus()==-1 && player.score<250 ){
            SaveLoad.writeToFile(game,1);
            game.gameOver++;
            game.face.setIcon(StaticObject.resizeImage(StaticObject.diedFace,30,30));
            for (int i = 0; i < game.height; i++) {
                for (int j = 0; j < game.width; j++) {
                    game.grid.squares[i][j].setOpen(true);
                    game.grid.squares[i][j].setFlagged(true);
                    if (game.grid.squares[i][j].getStatus() == -1) {
                        game.button[i][j].setIcon(StaticObject.resizeImage(StaticObject.mine,30,30));
                    }
                }
            }
            if(game.players[0]==player){
                game.tries2.setText("");
                game.tries2.setIcon(StaticObject.youWin);

                game.tries.setText("");
                game.tries.setIcon(new ImageIcon("lose.png"));

            }
            else{
                game.tries.setText("");
                game.tries.setIcon(StaticObject.youWin);

                game.tries2.setText("");
                game.tries2.setIcon(new ImageIcon("lose.png"));
            }
            return true;
        }
        // open a mine && score more than 250

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
