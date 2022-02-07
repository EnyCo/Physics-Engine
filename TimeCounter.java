
/**
 * Write a description of class TimeCounter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TimeCounter implements Runnable
{
    public void run(){
        while (Main.gameOver == false){
            try{
                Thread.sleep(100);
            }
            catch(Exception e){
                System.out.println("Oops time isn't working");
            }
            //actions every second
        }
    }
}
