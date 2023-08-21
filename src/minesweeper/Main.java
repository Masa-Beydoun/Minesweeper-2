package minesweeper;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {

        File file=new File("lastGame.txt");
        if(file.exists()){
            try {
                FileReader red=new FileReader(file);
                if(red.read()!=-1) {
                    new GameGUI(null,null,file);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"There is no saved game");
                new SignIn();
            }catch (NoSuchElementException e){
                new SignIn();
            }
        }
         else


             new SignIn();

    }
}
