package minesweeper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SignIn {
    JFrame frame;
    private JPanel panel1,panel2;
    private JComboBox gameModeComboBox,gameSizeComboBox;
    private JLabel photo;
    private JButton savedGameButton,newGameButton;
    private JTextField firstName,secondName;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JLabel mod1;
    private JLabel size1;
    private JLabel enterName1;
    private JLabel enterName2;
    private int n,m;

    int mood;
    String gameSize,name1,name2;
    Grid grid;
    GameGUI gui;
    File file=new File("lastGame.txt");

    public SignIn() {

        savedGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileReader reader=new FileReader(file);
                    if(reader.read()!=-1){
                        SignIn.this.gui=new GameGUI(null,null,file);
                        frame.dispose();
                        reader.close();
                    }
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null,"There is no saved game");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,"There is no saved game");
                }

            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mood = gameModeComboBox.getSelectedIndex();
                //gameMode=gameModeComboBox.getSelectedItem().toString();
                gameSize = gameSizeComboBox.getSelectedItem().toString();

                //System.out.println(gameMode);
                System.out.println(gameSize);

                name1=firstName.getText();
                name2=secondName.getText();

                System.out.println(name1);
                System.out.println(name2);

                if(gameSize=="modify") {
                    String string = "9";
                    try {
                        while (true) {
                            string = JOptionPane.showInputDialog(null, "Enter height dimension,(between 4 and 18)",10);
                            SignIn.this.n = Integer.parseInt(string);
                            if (n <= 30 && n >= 4) break;
                        }
                    } catch (NumberFormatException ex) {
                        SignIn.this.n = 10;
                    }
                    try {
                        while (true) {
                            string = JOptionPane.showInputDialog(null, "Enter width dimension,(between 4 and 30)",10);
                            SignIn.this.m = Integer.parseInt(string);
                            if (n <= 30 && n >= 4) break;
                        }
                    } catch (NumberFormatException ex) {
                        SignIn.this.m = 10;
                    }
                }
                if(gameSize=="9×9") {
                    n=9;
                    m=9;
                }
                if(gameSize=="16×16") {
                    n=16;
                    m=16;
                }
                if(gameSize=="16×30") {
                    n=16;
                    m=30;
                }
                grid=new Grid(n,m);

                new GameGUI(grid,SignIn.this,null);
                frame.dispose();
            }
        });
        frame=new JFrame("Minesweeper");
        frame.setIconImage(new ImageIcon("gameIcon.png").getImage());
        frame.add(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    public SignIn(Grid grid,String name1,String name2,int mood){
        n= grid.getHeight();
        m= grid.getWidth();
        this.name1=name1;
        this.name2=name2;
        this.mood=mood;
    }

    public JTextField getFirstName() {
        return firstName;
    }
    public void setFirstName(JTextField firstName) {
        this.firstName = firstName;
    }
    public JTextField getSecondName() {
        return secondName;
    }
    public void setSecondName(JTextField secondName) {
        this.secondName = secondName;
    }
    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }
    public int getM() {
        return m;
    }
    public void setM(int m) {
        this.m = m;
    }
    public String getGameSize() {
        return gameSize;
    }
    public void setGameSize(String gameSize) {
        this.gameSize = gameSize;
    }
    public String getName1() {
        return name1;
    }
    public void setName1(String name1) {
        this.name1 = name1;
    }
    public String getName2() {
        return name2;
    }
    public void setName2(String name2) {
        this.name2 = name2;
    }
    public Grid getGrid() {
        return grid;
    }
    public void setGrid(Grid grid) {
        this.grid = grid;
    }
    public GameGUI getGui() {
        return gui;
    }
    public void setGui(GameGUI gui) {
        this.gui = gui;
    }

}
