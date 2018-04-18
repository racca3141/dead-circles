import java.util.*;
import java.awt.*;

public class Circle{

	private int currentRecord;
	private int radiusCircle;
	private String colorCircle;

	public Circle(int r, String c){
		radiusCircle = r;
		colorCircle = c;	
	}

	public void modify(int r, String c){
		radiusCircle = r;
		colorCircle = c;
	} 

	public int getRadius(){
		return radiusCircle;
	}

	public String getColor(){
		return colorCircle;
	}

	public String toString(){
		String str = "Radius:  " + radiusCircle + "   Color:  " + colorCircle;
		return str;
	}
	
}