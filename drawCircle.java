import ecs100.*;
import java.io.*;
import java.lang.Math.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A program for our Robot arm.
 * Using robot arm 4.
 * by An Engineer Do
 */
public class drawCircle
{
    private PrintStream out;
    private double rad;
    private double centrex;
    private double centrey;

    /**
     * Constructor for objects of class RobotArm
     */
    public drawCircle()
    {
        UI.setDivider(1.0);
        rad = UI.askDouble ("Radius:");
        centrex = UI.askDouble ("X:");
        centrey = UI.askDouble ("Y:");
        try{
            out=new PrintStream("circle"+rad+".txt");
        }
        catch (IOException e){UI.println(e);}
        writeCircle();
    }

    /**
     * writes a file of x,y coords and pen up/down signals to be fed into the system
     */
    public void writeCircle(){
        
        for (double angle=0; angle<3*Math.PI;angle=angle+0.1){
            out.print((int) (centrex+rad*Math.cos(angle)));
            out.print(" ");
            out.print((int ) (centrey+rad*Math.sin(angle)));
            out.print(" ");
            out.print("0");
            out.println();
        }
        out.flush();
        out.close();
    }

    public static final void main(String args[]){
        drawCircle r = new drawCircle();
    }
}
