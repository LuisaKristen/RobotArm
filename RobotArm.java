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
public class RobotArm
{
    private PrintStream out;

    private double motorLeftDEF=230;
    private double motorRightDEF=400;
    private double motorYDEF=460;

    private double right90=1350;
    private double left90=1800;
    private int up=1600;
    private int down=1900;
    
    private double R=200;
    private ArrayList<Integer> xValues = new ArrayList<Integer>();
    private ArrayList<Integer> yValues = new ArrayList<Integer>();
    private ArrayList<Integer> penUpValues = new ArrayList<Integer>();
    /**
     * Constructor for objects of class RobotArm
     */
    public RobotArm()
    {
        try{
            out=new PrintStream("sky.txt");
        }
        catch (IOException e){UI.println(e);}
        setupGUI();
    }

    /**
     * Initialise the interface
     */
    public void setupGUI(){
        UI.addButton("Read File", this::readFile);
        UI.setDivider(1.0);
    }

    /**
     * Reads a file of x,y coords and pen up/down signals to be fed into the system
     */
    public void readFile(){
        File read;
        Scanner sc = null;
        try{
            read = new File(UIFileChooser.open());
            sc = new Scanner(read);
        }
        catch(Exception e){
            UI.println("Error opening file");
        }
        while(sc.hasNext()){
            int x = sc.nextInt();
            int y = sc.nextInt();
            int penUp = sc.nextInt();
            xValues.add(x);
            yValues.add(y);
            penUpValues.add(penUp);
        }
        for(int i = 0; i < xValues.size(); i++){
            UI.println("Point: " + (i+1));
            findAngleLeft(xValues.get(i),yValues.get(i));
            findAngleRight(xValues.get(i),yValues.get(i));
            if(penUpValues.get(i) == 0){
                out.print(down); //Replace with correct value for pen down motor
                out.println("");
            }
            else{
                out.print(up); //Replace with correct value for pen up motor
                out.println("");
            }
        }
    }

    /**
     * Returns the voltage for the given angle
     */
    public double leftInput(double angle){return ((angle-90)*(-10)+left90); }

    /**
     * Returns the voltage for the given angle
     */
    public double rightInput(double angle){return ((angle-90)*(-10)+right90);}

    /**
     * Given an x,y value, computes the angle of motors.
     */
    public void findAngleLeft(double x,double y){
        double distance=Math.sqrt(Math.pow(x-motorLeftDEF,2)+Math.pow(y-motorYDEF,2));
        if (distance<2*R){
            double xA=(motorLeftDEF+x)/2; //middle point of two circles
            double yA=(motorYDEF+y)/2;

            double h=Math.sqrt(Math.pow(R,2) - Math.pow(distance/2,2)); //difference between h and intersecion points
            double cos=(motorLeftDEF-x)/distance;
            double sin=(motorYDEF-y)/distance;

            double x3=xA+(h*sin);
            double y3=yA-(h*cos);
            double x4=xA-(h*sin);
            double y4=yA+(h*cos);

            double angle;
            if (x3<x4)angle=Math.toDegrees(Math.atan2(motorYDEF-y3, x3-motorLeftDEF));
            else angle=Math.toDegrees(Math.atan2(motorYDEF-y4, x4-motorLeftDEF));

            UI.println("Angle left: " + angle);
            UI.println("Voltage left: " + leftInput(angle));
            out.print((int)leftInput(angle));
            out.print(",");
        }
        else{UI.println("Out of bounds Left");}
    }

    /**
     * Given an x,y value, computes the angle of motors.
     */
    public void findAngleRight(double x,double y){
        double distance=Math.sqrt(Math.pow(x-motorRightDEF,2)+Math.pow(y-motorYDEF,2));
        if (distance<2*R){
            double xA=(motorRightDEF+x)/2;
            double yA=(motorYDEF+y)/2;

            double h=Math.sqrt(Math.pow(R,2) - Math.pow(distance/2,2)); //difference between h and intersecion points
            double cos=(motorRightDEF-x)/distance;
            double sin=(motorYDEF-y)/distance;

            double x3=xA+(h*sin);
            double y3=yA-(h*cos);
            double x4=xA-(h*sin);
            double y4=yA+(h*cos);

            double angle;
            if (x3>x4)angle=Math.toDegrees(Math.atan2(motorYDEF-y3, x3-motorRightDEF));
            else angle=Math.toDegrees(Math.atan2(motorYDEF-y4, x4-motorRightDEF));
            UI.println("Angle right: " + angle);
            UI.println("Voltage right: " + rightInput(angle));
            out.print((int)rightInput(angle));
            out.print(",");
        }
        else{UI.println("Out of bounds Right");}
    }

    public static final void main(String args[]){
        RobotArm r = new RobotArm();
    }
}
