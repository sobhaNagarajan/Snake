/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sneke;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author sobha
 */
public class Snakee extends JFrame implements KeyListener,Runnable{
    JPanel p1,p2;
    JButton[] lb=new JButton[200];
    JButton bounsfood;
    JTextArea t;
    int x=1000, y=500 , gu=2,directionx=1,directiony=0, speed=100,different=0,oldx,oldy,score=0; 
    int[]lbx=new int[600];
    int [] lby=new int [600];
    Point[] lbp=new Point[600];
    Thread myt;
    boolean food=false ,runl=false ,runr=true ,runu=true, rund=true , boundsflag=true;
    Random r=new Random();
    JMenu game,help,level ;
    Point bfp=new Point();
    JMenuBar mymbar;
    
    public void initializevalue(){
        gu=3;
        lbx[0]=300;
        lby[0]=300;
        directionx=10;
        directiony=0;
        different=0;
        score=0;
        food=false;
        runl=true;
        runr=true;
        runu=true;
        rund=true;
        boundsflag =true;
           
    }   

     Snakee() {
         super("snakee");
         setSize(1000,590);
         createbar();
         initializevalue();
         p1=new JPanel();
         p2=new JPanel();
         
         t=new JTextArea("score==>"+score);
         t.setEnabled(false);
         t.setBackground(Color.BLACK);
         
         bounsfood=new JButton();
         bounsfood.setEnabled(false);
     
         CreatFirstSnake();
         p1.setLayout(null);
         p2.setLayout(new GridLayout(0,1));
         p1.setBounds(0,0,x,y);
         p1.setBackground(Color.blue);
         p2.setBounds(0,y,x,30);
         p2.setBackground(Color.red);
         
         p2.add(t);
         
         getContentPane().setLayout(null);
         getContentPane().add(p1);
         getContentPane().add(p2);
         show();
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         addKeyListener(this);
         
         myt=new Thread(this);
         myt.start();
        
   
}
     public  void CreatFirstSnake(){
         for(int i=0 ;i<3;i++){
         lb[i]=new JButton("lb"+i);
         lb[i].setEnabled(false);
         p1.add(lb[i]);
         lb[i].setBounds(lbx[i], lby[i], 10, 10);
         lbx[i+1]=lbx[i]-10;
         lby[i+1]=lby[i];
     }
     }
     public void createbar(){
         mymbar=new JMenuBar();
         game=new JMenu("Game");
        JMenuItem newgame=new JMenuItem("New Game");
        JMenuItem exit=new JMenuItem("Exit");
        newgame.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 reset();
                 
             }
         });
        exit.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 System.exit(0);
             }
         });
        game.add(newgame);
        game.addSeparator();
        game.add(exit);
        mymbar.add(game);
        level=new JMenu("Level");
        mymbar.add(level);
        help=new JMenu("help");
        JMenuItem creator=new JMenuItem("creator");
        JMenuItem instruction=new JMenuItem("instruction");
        creator.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 JOptionPane.showMessageDialog(p2, "Name "+" ");
             }
         });
        help.add(creator);
        help.add(instruction);
        mymbar.add(help);
        setJMenuBar(mymbar);
     }
     void reset(){
         initializevalue();
         p1.removeAll();
         myt.stop();
         CreatFirstSnake();
         t.setText("score==>"+score);
         myt=new Thread(this); 
         myt.start();
     }

   void growup(){
       lb[gu]=new JButton();
       lb[gu].setEnabled(false);
       p1.add(lb[gu]);
       int a=10+(10*r.nextInt(48));
       int b=10+(10*r.nextInt(23));
       lbx[gu]=a;
       lby[gu]=b;
       lb[gu].setBounds(a, b, 10, 10);
       gu++;
   }
   void  moveFrd(){
       for(int i=0;i<gu;i++){
           lbp[i]=lb[i].getLocation();
           
       }
       lbx[0]+=directionx;
       lby[0]+=directiony;
       lb[0].setBounds(lbx[0], lby[0], 10, 10);
       for(int i=1;i<gu;i++){
           lb[i].setLocation(lbp[i-1]);
           
           
       }
       if(lbx[0]==x){
           lbx[0]=10;
       }
       else if(lbx[0]==0){           lbx[0]=x-10;
       }
       else if(lbx[0]==y){
           lby[0]=10;
       }
       else if(lby[0]==0){
           lby[0]=y-10;
       }
       if(lbx[0]==lbx[gu-1]&&lby[0]==lby[gu-1]){
           food=false;
           score+=5;
           t.setText("score==>"+score);
           if(score % 50==0 && boundsflag==true){
               p1.add(bounsfood);
               bounsfood.setBounds((10*r.nextInt(50)),(10*r.nextInt(25)),15,15);
               bfp=bounsfood.getLocation();
               boundsflag=false;   
           }
       }
       if(boundsflag==false){
           if(bfp.x<=lbx[0] && bfp.y<=lby[0]&& bfp.x+10>=lbx[0]&&bfp.y+10>=lby[0]){
               p1.remove(bounsfood);
               score+=100;
               t.setText("score==>"+score);
               boundsflag=true;
               
               
           }
       }
       if(food==false){
           growup();
           food=true;
           
       }
       else{
           lb[gu-1].setBounds(lbx[gu-1], lby[gu-1], 10, 10);
           
       }
       for(int i=1;i<gu;i++){
           if(lbp[0]==lbp[i]){
               t.setText("gameover  ==>"+score);
               try{
                   myt.join();
                   
               }
               catch(InterruptedException ie){
                   break;
               }
           }
           p1.repaint();
           show();
       }
   }

    @Override
    public void keyTyped(KeyEvent e) { 
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if( runl==true && e.getKeyCode()==37){
           directionx= -10;
           directiony=0;
           runr=false;
           runu=true;
           rund=true;
       }
       if(runu==true && e.getKeyCode()==38){
           directionx=0;
           directiony= -10;
           rund=false;
           runr=true;
           runl=true;
       }
       if(runr=true && e.getKeyCode()==39){
           directionx+=10;
           directiony=0;
           runl=false;
           runu=true;
           rund=true;  
       }
       if(rund==true && e.getKeyCode()==40){
           directionx=0;
           directiony+=10;
           runu=false;
           runr=true;
           runl=true;
           
       }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void run() {
         for(;;){
             moveFrd();
             try{
                 Thread.sleep(speed);
             }
             catch(InterruptedException ai){
                 
             }
         }
    }
}