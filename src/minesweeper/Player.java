package minesweeper;

import java.awt.*;
import java.util.Random;

public class Player {
    String name;
    int score;
    Color color;
    public Player(String name,int score,Color c){
        this.score=score;
        this.name=name;
        color=c;
    }
    public void autoPlayerMove(GameGUI game) {
        Random r=new Random();
        int ranX=0,ranY=0;
        boolean flag1=true;
        // while the cell is open keep finding a cell
        while(flag1){
            ranX =r.nextInt(game.grid.getHeight()-1);
            ranY =r.nextInt(game.grid.getWidth()-1);
            flag1=game.grid.squares[ranX][ranY].isOpen();
        }
        // if the cell is a mine try two more times
        if(game.grid.squares[ranX][ranY].getStatus()==-1){
            flag1=true;
            while(flag1){
                ranX =r.nextInt(game.grid.getHeight());
                ranY =r.nextInt(game.grid.getWidth());
                flag1=game.grid.squares[ranX][ranY].isOpen();
            }
        }
        if(game.grid.squares[ranX][ranY].getStatus()==-1){
            flag1=true;
            while(flag1){
                ranX =r.nextInt(game.grid.getHeight());
                ranY =r.nextInt(game.grid.getWidth());
                flag1=game.grid.squares[ranX][ranY].isOpen();
            }
        }

        this.move(game, ranX, ranY,"open");
    }
    public void changeScore(GameGUI game, int x, int y, String status){
        // if the user won
        if(status=="win"){
            for(int i=0;i< game.grid.getHeight();i++){
                for(int j=0;j<game.grid.getWidth();j++){
                    if(game.grid.squares[i][j].getStatus()==-1 && game.grid.squares[i][j].isFlagged()!=true){
                        score+=100; // every mine that had not been flagged
                    }
                }
            }
        }
        // adding a flag to a mine
        else if(status=="addMine" && game.grid.squares[x][y].getStatus()==-1  && game.grid.squares[x][y].isFlagged()==false) score+=5;
        //removing a flag from a mine cell
        else if(status=="removeMine" && game.grid.squares[x][y].isFlagged() && game.grid.squares[x][y].getStatus()==-1) score-=5;
        // adding a flag to an non mine cell
        else if(status=="addMine" && game.grid.squares[x][y].getStatus()!=-1 && game.grid.squares[x][y].isFlagged()==false) score--;
        //removing a flag from a non mine cell
        else if(status=="removeMine" && game.grid.squares[x][y].isFlagged() && game.grid.squares[x][y].getStatus()!=-1) score++;
        // opening a cell with a value
        else if(status=="open"){
            int temp=game.grid.squares[x][y].getStatus();
            if (temp != 0) score+=temp;
            else score++;
        }
    }

    public boolean move(GameGUI game,int x,int y,String status){
        if (x < 0 || x >= game.grid.getHeight() || y < 0 || y >= game.grid.getWidth() ) {
            return false;
        }
        if(game.grid.squares[x][y].isOpen() && game.grid.squares[x][y].getStatus()!=-1) {
            boolean flag=game.aroundMines(x,y);
            if(flag){
                if(x + 1 < game.grid.getHeight() && y + 1 < game.grid.getWidth() && !game.grid.squares[x+1][y+1].isOpen()
                        && !game.grid.squares[x+1][y+1].isFlagged()) move(game,x+1,y+1,"open");
                if(x - 1 >= 0 && y - 1 >= 0 && !game.grid.squares[x-1][y-1].isOpen()
                        && !game.grid.squares[x-1][y-1].isFlagged()) move(game,x-1,y-1,"open");

                if(x + 1 < game.grid.getHeight() && !game.grid.squares[x+1][y].isOpen()
                        && !game.grid.squares[x+1][y].isFlagged()) move(game,x+1,y,"open");
                if(y + 1 < game.grid.getWidth()&& !game.grid.squares[x][y+1].isOpen()
                        && !game.grid.squares[x][y+1].isFlagged()) move(game,x,y+1,"open");

                if(y - 1 >= 0 && !game.grid.squares[x][y-1].isOpen()
                        && !game.grid.squares[x][y-1].isFlagged()) move(game,x,y-1,"open");
                if(x - 1 >= 0 && !game.grid.squares[x-1][y].isOpen()
                        && !game.grid.squares[x-1][y].isFlagged()) move(game,x-1,y,"open");

                if(x + 1 < game.grid.getHeight() && y - 1 >= 0 && !game.grid.squares[x+1][y-1].isOpen()
                        && !game.grid.squares[x+1][y-1].isFlagged()) move(game,x+1,y-1,"open");
                if(x - 1 >= 0 && y + 1 < game.grid.getWidth() && !game.grid.squares[x-1][y+1].isOpen()
                        && !game.grid.squares[x-1][y+1].isFlagged()) move(game,x-1,y+1,"open");
            }
            else return false;
        }

        // the cell is a mine
        if (game.grid.squares[x][y].getStatus() == -1 && game.gameOver<2 && game.modeNum ==0 && status=="open" && !game.grid.squares[x][y].isFlagged()) {
            game.gameOver++;
            game.button[x][y].setIcon(StaticObject.resizeImage(StaticObject.mine,30,30));
            game.grid.squares[x][y].setFlagged(true);

            return true;
        }
        // the cell is a mine but the score is there is still tries
        else if(game.grid.squares[x][y].getStatus()==-1 && status=="open"  && game.modeNum ==0 && !game.grid.squares[x][y].isFlagged()){
            game.gameOver++;
            game.grid.getSolution(game,"lose");
            boolean flag=false;
            if(game.modeNum ==0) flag=OnePlayerMood.isItOver(game,x,y);
            if(game.modeNum ==1) flag=TwoPlayerMood.isItOver(game,x,y,this);
            if(game.modeNum ==2) flag=VsComputerMood.isItOver(game,x,y,this);

            //StaticObject.endGameFrame(game,game.grid.squares[x][y]);
            if(flag==true){
                return true;
            }
        }
        else if(game.grid.squares[x][y].getStatus()==-1 && status=="open" && !game.grid.squares[x][y].isFlagged()){
            game.button[x][y].setIcon(StaticObject.resizeImage(StaticObject.mine,30,30));
            game.grid.squares[x][y].setFlagged(true);
            if(game.modeNum ==0) OnePlayerMood.isItOver(game,x,y);
            if(game.modeNum ==1) TwoPlayerMood.isItOver(game,x,y,this);
            if(game.modeNum ==2) VsComputerMood.isItOver(game,x,y,this);
            return true;
        }
        else if(status=="addMine" && game.grid.squares[x][y].getStatus()==-1  && game.grid.squares[x][y].isFlagged()==false){
            game.button[x][y].setIcon(StaticObject.resizeImage(StaticObject.flagIcon,30,30));
            game.grid.squares[x][y].setFlagged(true);
            game.button[x][y].setBackground(color);
        }
        //removing a flag from a mine cell
        else if(status=="removeMine" && game.grid.squares[x][y].isFlagged() && game.grid.squares[x][y].getStatus()==-1){
            game.button[x][y].setIcon(StaticObject.resizeImage(StaticObject.question,30,30));
            game.grid.squares[x][y].setFlagged(false);
            game.button[x][y].setBackground(Color.LIGHT_GRAY);
        }
        // adding a flag to an non mine cell
        else if(status=="addMine" && game.grid.squares[x][y].getStatus()!=-1 && game.grid.squares[x][y].isFlagged()==false){
            game.button[x][y].setIcon(StaticObject.resizeImage(StaticObject.flagIcon,30,30));
            game.grid.squares[x][y].setFlagged(true);
            game.button[x][y].setBackground(color);
        }
        //removing a flag from a non mine cell
        else if(status=="removeMine" && game.grid.squares[x][y].isFlagged() && game.grid.squares[x][y].getStatus()!=-1){
            game.button[x][y].setIcon(StaticObject.resizeImage(StaticObject.question,30,30));
            game.grid.squares[x][y].setFlagged(false);
            game.button[x][y].setBackground(Color.LIGHT_GRAY);
        }
        // opening a cell with a value
        else if(status=="open"){
            StaticObject.changeIcon(game.button[x][y],game.grid.squares[x][y],color);
            floodFill(game,x,y);
            if(game.modeNum ==0) OnePlayerMood.isItOver(game,x,y);
            if(game.modeNum ==1) TwoPlayerMood.isItOver(game,x,y,this);
            if(game.modeNum ==2) VsComputerMood.isItOver(game,x,y,this);
        }
        return true;
    }
    public void floodFill(GameGUI game, int x, int y){

        if (x < 0 || x >= game.grid.getHeight() || y < 0 || y >= game.grid.getWidth() ) {
            return;
        }
        if(game.grid.squares[x][y].isOpen()){
            return ;
        }
        game.grid.squares[x][y].setOpen(true);
        StaticObject.changeIcon(game.button[x][y],game.grid.squares[x][y],color);
        changeScore(game,x,y,"open");

        game.counter++;

        // check if the game is over
        if(game.modeNum ==0) OnePlayerMood.isItOver(game,x,y);
        if(game.modeNum ==1) TwoPlayerMood.isItOver(game,x,y,this);
        if(game.modeNum ==2) VsComputerMood.isItOver(game,x,y,this);
        // if the cell has a value ,stop the flood fill
        if (game.grid.squares[x][y].getStatus()!=0) return ;

        if(x + 1 < game.grid.getHeight() && y + 1 < game.grid.getWidth() ) floodFill(game,x+1,y+1);
        if(x - 1 >= 0 && y - 1 >= 0) floodFill(game,x-1,y-1);

        if(x + 1 < game.grid.getHeight() ) floodFill(game,x+1,y);
        if(y + 1 < game.grid.getWidth()) floodFill(game,x,y+1);

        if(y - 1 >= 0) floodFill(game,x,y-1);
        if(x - 1 >= 0 ) floodFill(game,x-1,y);

        if(x + 1 < game.grid.getHeight() && y - 1 >= 0) floodFill(game,x+1,y-1);
        if(x - 1 >= 0 && y + 1 < game.grid.getWidth()) floodFill(game,x-1,y+1);
        }
}
