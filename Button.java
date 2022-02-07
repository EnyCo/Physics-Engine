
/**
 * Write a description of class Button here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*;
public class Button
{
    Point loc = new Point();
    int width = 0;
    int height = 0;
    boolean active = false;
    Button(Point l, int w, int h, boolean a){
        loc = l;
        width = w;
        height = h;
        active = a;
    }
        
    public Point getLoc()
    {
        return loc; 
    }
    public int getWidth()
    {
        return width; 
    }
    public int getHeight()
    {
        return height; 
    }
    public boolean getActive()
    {
        return active; 
    }
        
    public void setActive(boolean a)
    {
        active = a;
    }        
    public void setLoc(Point l)
    {
        loc = l;
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
