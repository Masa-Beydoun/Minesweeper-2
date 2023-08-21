package minesweeper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Square {

        private int x;
        int place;
        private int y;
        private int status; // mine or cell
        private boolean isOpen; // open or not
        private boolean isFlagged,isOpenMine; //marked as flag or not

        Square(){
            x=0;
            y=0;
            status=0;
            isOpen=false;
            isFlagged=false;
            isOpenMine=false;
        }
        public boolean isFlagged() {
            return isFlagged;
        }
        public boolean isOpenMine() {
        return isOpenMine;
    }
        public void setOpenMine(boolean openMine) {
        isOpenMine = openMine;
    }
        public void setFlagged(boolean flagged) {
            isFlagged = flagged;
        }
        public boolean isOpen() {
            return isOpen;
        }
        public void setOpen(boolean open) {
            isOpen = open;
        }
        public Square(int x, int y,int place){
            this.x=x;
            this.y=y;
            isOpen=false;
            isFlagged=false;
            this.place=place;
        }
        public int getY() {
            return y;
        }
        public void setY(int y) {
            this.y = y;
        }
        public int getX() {
            return y;
        }
        public void setX(int x) {
            this.x = x;
        }
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
        public void setInfo(int x,int y,int status){
            this.x=x;
            this.y=y;
            this.status=status;
        }
}