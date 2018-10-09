import ecs100.*;
import java.io.*;
import java.lang.Math.*;
/**
 * A program for our Robot arm.
 * Using robot arm 4.
 * by An Engineer Do
 */
public class RobotArm
{
    private PrintStream out;
    private double rightMotorAngle;
    private double leftMotorAngle;

    private double motorLeftDEF=230;
    private double motorRightDEF=400;
    private double motorYDEF=460;

    private double R=200;

    /**
     * Constructor for objects of class RobotArm
     */
    public RobotArm()
    {
        /*try{
        out=new PrintStream(UIFileChooser.save());
        }
        catch (IOException e){UI.println(e);}
         */
        findAngleLeft(200,200);
        findAngleRight(200,200);
    }

    /**
     * Returns the voltage for the given angle 
     */ 
    public double leftInput(double angle){return ((angle-90)*(-10)+1750); } 

    /**
     * Returns the voltage for the given angle 
     */
    public double rightInput(double angle){return ((angle-90)*(-10)+1250);}

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

            UI.println(angle);
            UI.println(leftInput(angle));
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
            UI.println(angle);
            UI.println(rightInput(angle));
        }
        else{UI.println("Out of bounds Right");}
    }

    public static final void main(String args[]){
        RobotArm r = new RobotArm();
    }
}
