/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.*;
import java.awt.geom.Point2D.Double.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.*;
import javax.sound.sampled.*;
import java.awt.datatransfer.*;
import javafx.scene.media.*;
public class Main extends JFrame
{
    static String OS = System.getProperty("os.name"); 
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Canvas canvas;

    static int HEIGHT = screenSize.height;
    static int WIDTH = screenSize.width;
    
    static KeyboardInput keyboard = new KeyboardInput();
    static MouseInput1 mouse; 
    
    static boolean gameOver = false;
    
    static boolean exitPopUp = false;
    static boolean pause = false;
    class Entity{
        double mass = 0;
        Point2D.Double position = new Point2D.Double();
        Point2D.Double velocity = new Point2D.Double();
        Point2D.Double acceleration = new Point2D.Double();
        int width = 0;
        int height = 0;
        
        Entity(double m, Point2D.Double p, Point2D.Double v, Point2D.Double a, int w, int h){
            mass = m;
            position = p;
            velocity = v;
            acceleration = a;
            width = w;
            height = h;
        }
        
        public double getMass()
        {
            return mass; 
        }
        public Point2D.Double getPosition()
        {
            return position; 
        }
        public Point2D.Double getVelocity()
        {
            return velocity; 
        }
        public Point2D.Double getAcceleration()
        {
            return acceleration; 
        }
        public int getWidth()
        {
            return width; 
        }
        public int getHeight()
        {
            return height; 
        }   
        
        public void setMass(int m)
        {
            mass = m;
        }
        public void setPosition(Point2D.Double p)
        {
            position = p;
        }
        public void setVelocity(Point2D.Double v)
        {
            velocity = v;
        }
        public void setAcceleration(Point2D.Double a)
        {
            acceleration = a;
        }
        public void setWidth(int w)
        {
            width = w;
        }
        public void setHeight(int h)
        {
            height = h;
        }
    }
    //public Entity player = new Entity( 2, new Point2D.Double(WIDTH*2/3, HEIGHT/2), new Point2D.Double(-2, -6), new Point2D.Double(0, .1), 25,25);
    ArrayList<Entity> entities = new ArrayList<Entity>();
    
    Button exitPU1 = new Button(new Point(WIDTH/2 -WIDTH/16 - WIDTH/32,HEIGHT/2),WIDTH/16,HEIGHT/16, false);// yes I want to quit
    Button exitPU2 = new Button(new Point(WIDTH/2+ WIDTH/32,HEIGHT/2),WIDTH/16,HEIGHT/16, false);// no I don't want to quit
    
    public Main() 
    {
        setIgnoreRepaint( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        canvas = new Canvas();
        canvas.setIgnoreRepaint( true );
        canvas.setSize( WIDTH, HEIGHT );
        add( canvas );
        pack();
        
        // for keyboard using
        addKeyListener( keyboard );
        canvas.addKeyListener( keyboard );
        
        // for mouse using
        mouse = new MouseInput1();
        addMouseListener( mouse );
        addMouseMotionListener( mouse );
        canvas.addMouseListener( mouse );
        canvas.addMouseMotionListener( mouse );
    }
    
    static final int width = 25;
    static final int height = 25;
    static final int rows = 4;
    static final int cols = 4;
    static int w = 0;
    static int h = 0;
    
    public static void main(String[] args) throws UnsupportedFlavorException,InterruptedException, IOException
    { 
        if (OS.indexOf("Windows") != -1)
        {
            HEIGHT = HEIGHT - 60;//60 refers to pixel size of bottom bar
        }        
        Main app = new Main();
        app.setTitle( "Main" );
        app.setVisible( true );
        app.run();
        System.exit( 0 ); 
    }
    
    public void run() throws UnsupportedFlavorException,InterruptedException, IOException
    {
        canvas.createBufferStrategy( 2 );
        BufferStrategy buffer = canvas.getBufferStrategy();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage bi = gc.createCompatibleImage( WIDTH, HEIGHT );
        
        Graphics graphics = null;
        Graphics2D g2d = null;
        Color background = Color.WHITE;
        
        
        Thread t1 = new Thread(new TimeCounter());
        t1.start();
        
        entities.add(new Entity(1, new Point2D.Double(WIDTH/3, HEIGHT/2), new Point2D.Double(2, 0), new Point2D.Double(0, .1), 25,25));
        entities.add(new Entity(1, new Point2D.Double(WIDTH/2, HEIGHT/2), new Point2D.Double(0, 0), new Point2D.Double(0, .1), 25,25));
        entities.add(new Entity(1, new Point2D.Double(WIDTH*2/3, HEIGHT/4), new Point2D.Double(0, 3), new Point2D.Double(0, .1), 25,25));
        while(gameOver == false) {
            try {
                keyboard.poll();// Poll the keyboard
                mouse.poll();// poll the mouse
                
                // Clear the back buffer
                g2d = bi.createGraphics();
                g2d.setColor( background );
                g2d.fillRect( 0, 0, WIDTH, HEIGHT );
                
                g2d.setColor(Color.DARK_GRAY);//g2d.setFont(new Font("TimeRoman",Font.PLAIN, 15));//change
                for (int i =0;i < entities.size();i++){
                    g2d.fillRect((int)entities.get(i).getPosition().getX(), (int)entities.get(i).getPosition().getY(),(int)entities.get(i).getWidth(), (int)entities.get(i).getHeight());
                }
                g2d.setColor(Color.BLACK);
                //g2d.fillRect((int)player.getPosition().getX(), (int)player.getPosition().getY(),(int)player.getWidth(), (int)player.getHeight());
                
                if (exitPopUp==true){//exit exitPopUp
                    g2d.setColor(Color.WHITE);//redundant if background white
                    g2d.fillRoundRect(WIDTH*3/8,HEIGHT*3/8,WIDTH/4,HEIGHT/4,15,15);
                    
                    g2d.setColor(Color.RED);//exit
                    g2d.fillRoundRect((int)exitPU1.getLoc().getX(),(int)exitPU1.getLoc().getY(),(int)exitPU1.getWidth(),(int)exitPU1.getHeight(),5,5);
                    
                    g2d.setColor(Color.GREEN);//stay
                    g2d.fillRoundRect((int)exitPU2.getLoc().getX(),(int)exitPU2.getLoc().getY(),(int)exitPU2.getWidth(),(int)exitPU2.getHeight(),5,5);
                    
                    g2d.setColor(Color.BLACK);
                    g2d.drawRoundRect(WIDTH*3/8,HEIGHT*3/8,WIDTH/4,HEIGHT/4,15,15);
                    g2d.drawRoundRect((int)exitPU1.getLoc().getX(),(int)exitPU1.getLoc().getY(),(int)exitPU1.getWidth(),(int)exitPU1.getHeight(),5,5);
                    g2d.drawRoundRect((int)exitPU2.getLoc().getX(),(int)exitPU2.getLoc().getY(),(int)exitPU2.getWidth(),(int)exitPU2.getHeight(),5,5);
                    
                    g2d.drawString("Do you want to exit?", WIDTH/2-55, HEIGHT*3/7);
                    g2d.drawString("YES",(int)exitPU1.getLoc().getX()+(int)exitPU1.getWidth()/2-10,(int)exitPU1.getLoc().getY()+(int)exitPU1.getHeight()/2+6);
                    g2d.drawString("NO",(int)exitPU2.getLoc().getX()+(int)exitPU2.getWidth()/2-7,(int)exitPU2.getLoc().getY()+(int)exitPU2.getHeight()/2+6);
                    
                }
                graphics = buffer.getDrawGraphics();
                graphics.drawImage( bi, 0, 0, null );
                if( !buffer.contentsLost() )
                    buffer.show();
                
                Thread.sleep(10);
            } finally {
                // Release resources
                if( graphics != null ) 
                    graphics.dispose();
                if( g2d != null ) 
                    g2d.dispose();
            }
            processInput();
        }
    }
         
    protected void processInput() throws UnsupportedFlavorException,InterruptedException 
    { 
        Point p = MouseInfo.getPointerInfo().getLocation();
        int x = p.x-9;//constants? 9
        int y = p.y-32;//constants? 32
        
        /*player.setVelocity(new Point2D.Double((player.getVelocity().getX()+player.getAcceleration().getX()),(player.getVelocity().getY()+player.getAcceleration().getY())));
        
        if(pause == false && player.getPosition().getX()+player.getVelocity().getX()+player.getWidth() < WIDTH && player.getPosition().getX()+player.getVelocity().getX() > 0){
            player.setPosition(new Point2D.Double((player.getPosition().getX()+player.getVelocity().getX()),(player.getPosition().getY()))); 
        }  
        else{
            player.setVelocity(new Point2D.Double(-1*player.getVelocity().getX(),player.getVelocity().getY()));
        }
        if(pause == false && player.getPosition().getY()+player.getVelocity().getY()+player.getHeight() < HEIGHT && player.getPosition().getY()+player.getVelocity().getY() > 0){
            player.setPosition(new Point2D.Double((player.getPosition().getX()),(player.getPosition().getY()+player.getVelocity().getY()))); 
        } */
        //else{
        //    player.setVelocity(new Point2D.Double((int)player.getVelocity().getX(),-1*(int)player.getVelocity().getY()));
        //}
        
        for (int i =0;i < entities.size();i++){
            entities.get(i).setVelocity(new Point2D.Double((entities.get(i).getVelocity().getX()+entities.get(i).getAcceleration().getX()),(entities.get(i).getVelocity().getY()+entities.get(i).getAcceleration().getY())));
            
            if(entities.get(i).getPosition().getX()+entities.get(i).getVelocity().getX()+entities.get(i).getWidth() < WIDTH && entities.get(i).getPosition().getX()+entities.get(i).getVelocity().getX() > 0){
                entities.get(i).setPosition(new Point2D.Double((entities.get(i).getPosition().getX()+entities.get(i).getVelocity().getX()),(entities.get(i).getPosition().getY()))); 
            }
            else{
                entities.get(i).setVelocity(new Point2D.Double(-1*entities.get(i).getVelocity().getX(),entities.get(i).getVelocity().getY()));
            }
            if(entities.get(i).getPosition().getY()+entities.get(i).getVelocity().getY()+entities.get(i).getHeight()< HEIGHT && entities.get(i).getPosition().getY()+entities.get(i).getVelocity().getY() > 0){
                entities.get(i).setPosition(new Point2D.Double((entities.get(i).getPosition().getX()),(entities.get(i).getPosition().getY()+entities.get(i).getVelocity().getY()))); 
            }
            
            for (int j = i+1; j < entities.size(); j++) {
                // compare list.get(i) and list.get(j)
                if (entities.get(j).getPosition().getX() + entities.get(j).getWidth() > entities.get(i).getPosition().getX() && 
                  entities.get(j).getPosition().getX() < entities.get(i).getPosition().getX() + entities.get(i).getWidth() && 
                  entities.get(j).getPosition().getY() + entities.get(j).getHeight() > entities.get(i).getPosition().getY() && //-5
                  entities.get(j).getPosition().getY()  < entities.get(i).getPosition().getY() + entities.get(i).getHeight()) {//-5
                    double tempX = entities.get(j).getVelocity().getX();
                    double tempY = entities.get(j).getVelocity().getY();
                    entities.get(j).setVelocity(entities.get(i).getVelocity()); // only when mass the same
                    entities.get(i).setVelocity(new Point2D.Double(tempX,tempY)); //only when mass the same
                }
              }
        }
             
        /*for (int i =0; i<entities.size(); i++){
           if (player.getPosition().getX() + player.getWidth() > entities.get(i).getPosition().getX() && 
                  player.getPosition().getX() < entities.get(i).getPosition().getX() + entities.get(i).getWidth() && 
                  player.getPosition().getY() + player.getHeight() > entities.get(i).getPosition().getY() && //-5
                  player.getPosition().getY()  < entities.get(i).getPosition().getY() + entities.get(i).getHeight()) {//-5
                    double tempX = player.getVelocity().getX();
                    double tempY = player.getVelocity().getY();
                    player.setVelocity(entities.get(i).getVelocity()); // only when mass the same
                    entities.get(i).setVelocity(new Point2D.Double(tempX,tempY)); //only when mass the same
                }
        }*/
        
        /*for (int i = 0; i < entities.size(); i++) {
            for (int j = i+1; j < entities.size(); j++) {
                // compare list.get(i) and list.get(j)
                if (entities.get(j).getPosition().getX() + entities.get(j).getWidth() > entities.get(i).getPosition().getX() && 
                  entities.get(j).getPosition().getX() < entities.get(i).getPosition().getX() + entities.get(i).getWidth() && 
                  entities.get(j).getPosition().getY() + entities.get(j).getHeight() > entities.get(i).getPosition().getY() && //-5
                  entities.get(j).getPosition().getY()  < entities.get(i).getPosition().getY() + entities.get(i).getHeight()) {//-5
                    double tempX = entities.get(j).getVelocity().getX();
                    double tempY = entities.get(j).getVelocity().getY();
                    entities.get(j).setVelocity(entities.get(i).getVelocity()); // only when mass the same
                    entities.get(i).setVelocity(new Point2D.Double(tempX,tempY)); //only when mass the same
                }
              }  
        }*/
        
        if (keyboard.keyDownOnce( KeyEvent.VK_ESCAPE ) && exitPopUp == true){ //exitPopUp closes
            exitPopUp = false;
            exitPU1.setActive(false);
            exitPU2.setActive(false);
            pause = false;
        }
        else if (keyboard.keyDownOnce( KeyEvent.VK_ESCAPE ) && exitPopUp == false){ //exitPopUp opens
            exitPopUp = true;
            exitPU1.setActive(true);
            exitPU2.setActive(true);
            pause = true;
        }
        if (mouse.buttonDownOnce( 1 ) && exitPU2.getActive() == true && x > (int)exitPU2.getLoc().getX() && x < (int)exitPU2.getLoc().getX() + (int)exitPU2.getWidth() && y > (int)exitPU2.getLoc().getY() && y < (int)exitPU2.getLoc().getY() + (int)exitPU2.getHeight()){//buttons on exitPopUp
            exitPopUp = false;
            exitPU2.setActive(false);
            pause = false;
        }
        else if(mouse.buttonDownOnce( 1 ) && exitPU1.getActive() == true && x > (int)exitPU1.getLoc().getX() && x < (int)exitPU1.getLoc().getX() + (int)exitPU1.getWidth() && y > (int)exitPU1.getLoc().getY() && y < (int)exitPU1.getLoc().getY() + (int)exitPU1.getHeight()){
            gameOver = true;
        }// exitPopUp button actions
    }
    
}