package minesweeper;

import java.util.Random;

public class Grid {
    Square[][]squares;
    private int height ;
    private int width;
    private int numOfMines;
    private Random random;
    private boolean flag[][];
    Grid(int height,int width){
        random = new Random();
        this.width=width;
        this.height=height;
        this.numOfMines=(height)*(width)/4;
        this.squares=new Square[this.height][this.width];
        this.flag=new boolean[this.height][this.width];
        for(int i = 0; i< this.height; i++){
            for(int j = 0; j< this.width; j++){
                flag[i][j]=false;
                squares[i][j]=new Square(i,j,8);
            }
        }
        for(int i = 1; i< this.height -1; i++){
            squares[i][0].place=5;
            squares[i][this.width -1].place=5;
        }
        for(int i = 1; i< this.width -1; i++){
            squares[0][i].place=squares[this.height -1][i].place=5;
        }
        squares[0][0].place=squares[this.height -1][this.width -1].place=squares[0][this.width -1].place=squares[this.height -1][0].place=3;


    }



    public void distributeMines(int n, int m){
        // no mine in the pressed cell and the 8 cells around it.
        for(int i=0;i<numOfMines;i++){
            int randomx=random.nextInt(height-1);
            int randomy=random.nextInt(width-1);
            if (randomx == n && randomy == m ) i--;
            else if (randomx - 1 == n && randomy - 1 == m) i--;
            else if (randomx + 1 == n && randomy + 1 == m) i--;

            else if (randomx == n && randomy - 1 == m) i--;
            else if (randomx - 1 == n && randomy == m) i--;

            else if (randomx == n && randomy + 1 == m) i--;
            else if (randomx + 1 == n && randomy == m) i--;

            else if (randomx + 1 == n && randomy - 1 == m) i--;
            else if (randomx - 1 == n && randomy + 1 == m) i--;

            else if(flag[randomx][randomy]==false) {
                squares[randomx][randomy].setStatus(-1);
                flag[randomx][randomy]=true;
            }else {
                i--;
            }
        }
        // counting mines around every cell
        for (int i = 0; i <height ; i++) {
            for (int j = 0; j < width; j++) {
                if (flag[i][j] == false) {
                    int cnt = 0;
                    if (i - 1 >= 0 && j - 1 >= 0) if (flag[i - 1][j - 1] == true) cnt++;
                    if (i >= 0 && j - 1 >= 0) if (flag[i][j - 1] == true) cnt++;
                    if (i - 1 >= 0 && j >= 0) if (flag[i - 1][j] == true) cnt++;
                    if (i + 1 < height && j + 1 < width) if (flag[i + 1][j + 1] == true) cnt++;
                    if (i >= 0 && j + 1 < width) if (flag[i][j + 1] == true) cnt++;
                    if (i + 1 < height) if (flag[i + 1][j] == true) cnt++;
                    if (i + 1 < height && j - 1 >= 0) if (flag[i + 1][j - 1] == true) cnt++;
                    if (i - 1 >= 0 && j + 1 < width) if (flag[i - 1][j + 1] == true) cnt++;
                    squares[i][j].setStatus(cnt);
                }
            }
        }
        // display the grid in console
        for (int i = 0; i <height ; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(squares[i][j].getStatus()+" ");
            }
            System.out.println();
        }

    }
    public void getSolution(GameGUI gui,String s){
        for(int i=0;i<this.getHeight();i++){
            for(int j=0;j<this.getWidth();j++){
                if(this.flag[i][j]==true){
                    // if the user lost
                    if(s=="lose")gui.button[i][j].setIcon(StaticObject.resizeImage(StaticObject.mine,30,30));
                    // if the user win
                    else gui.button[i][j].setIcon(StaticObject.resizeImage(StaticObject.flagIcon,30,30));
                }
            }
        }
    }

    public Grid() {
    }

    public Square[][] getSquares() {
        return squares;
    }
    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getNumOfMines() {
        return numOfMines;
    }
    public void setNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }
}
