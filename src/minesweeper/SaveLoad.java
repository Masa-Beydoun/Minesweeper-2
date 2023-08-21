package minesweeper;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class SaveLoad {

    public static void createFile(){
        File saveFile=new File("lastGame.txt");
        try{
            saveFile.createNewFile();
        }catch (IOException ex){

        }
    }

    public static void writeToFile(GameGUI game,int done){
        File lastGame=new File("lastGame.txt");
        int height=game.grid.getHeight();
        int width=game.grid.getWidth();
        int counter=game.counter;
        int cnt=game.getCnt();
        try {

            String s=new String(done +"\n"+game.gameOver +"\n"+game.modeNum +"\n"+game.counter+"\n"+game.getCnt()+"\n"+
                    game.players[0].name+"\n" +game.players[0].score+"\n"+
                    game.players[1].name+"\n" +game.players[1].score+"\n"+
                    game.grid.getHeight()+"\n"+game.grid.getWidth()+"\n");

            FileOutputStream out = new FileOutputStream(lastGame);
            out.write(s.getBytes());
            //writing status of every cell
            for (int i=0;i<height ;i++){
                for (int j=0;j<width;j++){
                    out.write(String.valueOf(game.grid.squares[i][j].getStatus()).getBytes());
                    out.write(' ');
                }
                out.write('\n');
            }
            //writing open or not
            for (int i=0;i<height;i++){
                for (int j=0;j<width ;j++){
                    if(game.grid.squares[i][j].isOpen()) out.write('1');
                    else out.write('0');
                    out.flush();
                    out.write(' ');
                }
                out.write('\n');
            }
            //writing flags
            for (int i=0;i<height;i++){
                for (int j=0;j<width;j++){
                    if(game.grid.squares[i][j].isFlagged()) out.write('1');
                    else out.write('0');
                    out.flush();
                    out.write(' ');
                }
                out.write('\n');
            }
            out.close();
            //catch
        } catch (FileNotFoundException e) {
            createFile();
            writeToFile(game,done);
        } catch (IOException e) {

        }
    }

    public static void LoadGame(GameGUI game,File file){
        Scanner scan= null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        game.players=new Player[2];

        //saved game
        int temp = Integer.parseInt(scan.nextLine());
        System.out.println(temp);

        game.gameOver= Integer.parseInt(scan.nextLine());
        // game mode
        game.modeNum = Integer.parseInt(scan.nextLine());
        //how many open cells
        game.counter = Integer.parseInt(scan.nextLine());
        //who's turn
        game.cnt = Integer.parseInt(scan.nextLine());

        //first player information
        String s1 = scan.nextLine();
        int score1 = Integer.parseInt(scan.nextLine());
        if(game.modeNum ==0) game.players[0]=new Player(s1,score1, Color.LIGHT_GRAY);
        else game.players[0]=new Player(s1,score1,Color.LIGHT_GRAY);

        //second player information
        String s2 = scan.nextLine();
        int score2 = Integer.parseInt(scan.nextLine());
        game.players[1]=new Player(s2,score2, Color.LIGHT_GRAY);
        if(game.modeNum ==2) game.players[1].color=StaticObject.c2;

        // get dimension of grid
        game.height = Integer.parseInt(scan.nextLine());
        game.width = Integer.parseInt(scan.nextLine());

        game.grid = new Grid(game.height, game.width);

        //Read Status
        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                game.grid.squares[i][j].setStatus(Integer.parseInt(scan.next()));
                if(game.grid.squares[i][j].getStatus()==-1) game.grid.setNumOfMines(game.grid.getNumOfMines()+1);
            }
        }
        //open or not
        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                int c = Integer.parseInt(scan.next());
                if (c == 0) game.grid.squares[i][j].setOpen(false);
                else game.grid.squares[i][j].setOpen(true);

            }
        }
        //flagged or not
        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                int c = Integer.parseInt(scan.next());
                if (c == 0) game.grid.squares[i][j].setFlagged(false);
                else game.grid.squares[i][j].setFlagged(true);
            }
        }
        scan.close();
    }
}
