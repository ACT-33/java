import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE =640;
    private final int DOT_SIZE=64;
    private final int ALL_DOTS = 100;
    private Image lrDot;
    private Image udDot;
    private Image ulDot;
    private Image urDot;
    private Image dlDot;
    private Image drDot;
    private Image chery;
    private Image head;
    private Image stend;
    private Image emir;
    private int cheryY;
    private int cheryX;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private boolean turn = true;
    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++){
            x[i]=192-i*DOT_SIZE;
            y[i]=192;
        }
        timer = new Timer(250,this);
        timer.start();
        createChery();
    }
    public void createChery(){
        cheryX =new Random().nextInt(9)*DOT_SIZE;
        cheryY =new Random().nextInt(9)*DOT_SIZE;
    }
    public void loadImages(){
        ImageIcon iia=new ImageIcon("chery.png");
        chery = iia.getImage();
        ImageIcon iid1=new ImageIcon("dot.png");
        lrDot = iid1.getImage();
        ImageIcon iih=new ImageIcon("head.png");
        head = iih.getImage();
        ImageIcon iis=new ImageIcon("stend.png");
        stend = iis.getImage();
        ImageIcon iid2=new ImageIcon("ud.png");
        udDot = iid2.getImage();
        ImageIcon iid3=new ImageIcon("ul.png");
        ulDot = iid3.getImage();
        ImageIcon iid4=new ImageIcon("ur.png");
        urDot = iid4.getImage();
        ImageIcon iid5=new ImageIcon("dl.png");
        dlDot = iid5.getImage();
        ImageIcon iid6=new ImageIcon("dr.png");
        drDot = iid6.getImage();
        ImageIcon em=new ImageIcon("emir.png");
        emir = em.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(chery, cheryX, cheryY,this);
            g.drawImage(head,x[0],y[0],this);
            g.drawImage(stend,x[1],y[1],this);
            for (int i = 2; i < dots; i++) {
                if (x[i]!=x[i+1] && x[i]!=x[i-1] && y[i]==y[i+1]&& y[i]==y[i-1]){g.drawImage(lrDot,x[i],y[i],this);}
                if (x[i]==x[i+1] && x[i]==x[i-1] && y[i]!=y[i+1]&& y[i]!=y[i-1]){g.drawImage(udDot,x[i],y[i],this);}
                if ((x[i]<=x[i+1] && x[i]==x[i-1] && y[i]==y[i+1]&& y[i]>=y[i-1]) | (x[i]==x[i+1] && x[i]<=x[i-1] && y[i]>=y[i+1]&& y[i]==y[i-1])){g.drawImage(urDot,x[i],y[i],this);}
                if ((x[i]>=x[i+1] && x[i]==x[i-1] && y[i]==y[i+1]&& y[i]>=y[i-1]) | (x[i]==x[i+1] && x[i]>=x[i-1] && y[i]>=y[i+1]&& y[i]==y[i-1])){g.drawImage(ulDot,x[i],y[i],this);}
                if ((x[i]>=x[i+1] && x[i]==x[i-1] && y[i]==y[i+1]&& y[i]<=y[i-1]) | (x[i]==x[i+1] && x[i]>=x[i-1] && y[i]<=y[i+1]&& y[i]==y[i-1])){g.drawImage(dlDot,x[i],y[i],this);}
                if ((x[i]<=x[i+1] && x[i]==x[i-1] && y[i]==y[i+1]&& y[i]<=y[i-1]) | (x[i]==x[i+1] && x[i]<=x[i-1] && y[i]<=y[i+1]&& y[i]==y[i-1])){g.drawImage(drDot,x[i],y[i],this);}
                if(x[0]==x[i]&&y[0]==y[i]){
                    inGame=false;
                    String str = "Game Over";
                    g.setColor(Color.white);
                    g.drawString(str,SIZE/2,SIZE/2);
                }
            }
            g.drawImage(emir,x[dots-1],y[dots-1],this);
            turn=true;
        } else {
            String str = "Game Over";
            g.setColor(Color.white);
            g.drawString(str,SIZE/2,SIZE/2);

        }
    }

    public void move(){
        for (int i = dots; i > 0 ; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if (right){
            x[0]+=DOT_SIZE;
        }
        if (up){
            y[0]-=DOT_SIZE;
        }
        if (left){
            x[0]-=DOT_SIZE;
        }
        if (down){
            y[0]+=DOT_SIZE;
        }
    }
public void checkChery(){
        if(x[0]== cheryX && y[0]== cheryY){
            dots++;
            createChery();
        }
}
public void checkCollisions(){
    for (int i = dots; i >0; i--) {
        if(i>4 && x[0]==x[i]&&y[0]==y[i]){
            inGame=false;
        }
    }
    if(x[0]>SIZE){
        inGame=false;
    }
    if(x[0]<0){
        inGame=false;
    }
    if(y[0]>SIZE){
        inGame=false;
    }
    if(y[0]<0){
        inGame=false;
    }
}
    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkChery();
            checkCollisions();
            move();
        }
        repaint();
    }
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right && turn){
                left = true;
                up = false;
                down = false;
                turn = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left && turn){
                right = true;
                up = false;
                down = false;
                turn = false;
            }

            if(key == KeyEvent.VK_UP && !down && turn){
                right = false;
                up = true;
                left = false;
                turn = false;
            }
            if(key == KeyEvent.VK_DOWN && !up && turn ){
                right = false;
                down = true;
                left = false;
                turn = false;
            }
        }
    }
}
