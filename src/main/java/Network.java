package main.java;

import java.util.ArrayList;

import controlP5.ControlP5;
import de.looksgood.ani.Ani;
import processing.core.PApplet;

/**
* This class is used for the visualization of the network.
* Depending on your implementation, you might not need to use this class or create a class on your own.
* I used the class to draw the circle and re-arrange nodes and links.
* You will need to declare other variables.
*/
public class Network {
	
	private int x;
	private int y;
	private PApplet parent;
	ArrayList<Character> characters;
	 ArrayList<Character> circleCh;
	private int diameter;
	private int mouse = 0;
	private double pos = 0;
	
	public Network(PApplet parent){

		this.parent = parent;
		this.characters = new ArrayList<Character>();
		this.circleCh = new ArrayList<Character>();
		
		this.x = 600;
		this.y = 300;
		this.diameter = 530;
	}
	
	public void removeOne(Character ch)
	{
		circleCh.remove(ch);
		pos = 0;
		for(Character cha : circleCh){
			cha.setCX((float)(x + diameter/2*Math.cos(Math.toRadians(pos))));
			cha.setCY((float)(y + diameter/2*Math.sin(Math.toRadians(pos))));
			pos += (double)360 / (double)circleCh.size();
		}
	}
	
	
	
	public void drawLink()
	{
		for(Character ch : circleCh)
		{
			for(Character tar: ch.getTarget().keySet())
			{
				if(tar.getShowLink() == 1)
				{
					int intense = ch.getTarget().get(tar);
					parent.strokeWeight(intense / 2);
					parent.stroke(0,77,173,250);
					parent.line(ch.getX(), ch.getY(), tar.getX(), tar.getY());
				}
			}
		}
	}
	public void addOne(Character ch)
	{	
		circleCh.add(ch);
		pos = 0;
		for(Character cha : circleCh){
			cha.setCX((float)(x + diameter/2*Math.cos(Math.toRadians(pos))));
			cha.setCY((float)(y + diameter/2*Math.sin(Math.toRadians(pos))));
			pos += (double)360 / (double)circleCh.size();
		}
	}
	public void display(){
		parent.stroke(204,250,0);
		parent.strokeWeight(4);
		parent.fill(255);
		parent.ellipse(x,y,diameter,diameter);
		
	
		for(Character ch : characters)
		{
			if((parent.mouseX <= ch.getX()+20 && parent.mouseX >= ch.getX()-20) && (parent.mouseY <= ch.getY()+20 && parent.mouseY >= ch.getY()-20))
			{
				ch.setShowName(1);
			
				if(parent.mousePressed == true)
				{
					if(mouse==0)
					{
						mouse = 1;
						ch.setState(1);
					}
				}

				else
				{

					if(ch.getState() == 1)
					{
						if((ch.getX()-x)*(ch.getX()-x) + (ch.getY()-y)*(ch.getY()-y)- diameter*diameter/4 < 0.01)
						{
							ch.setState(2);
							ch.setShowLink(1);
							
							if(!circleCh.contains(ch))
								addOne(ch);
						}
		
						else
						{
							ch.setState(3);
							ch.setShowLink(0);
							removeOne(ch);
						}
						pos = 0;
						for(Character cha : circleCh){
							cha.setCX((float)(x + diameter/2*Math.cos(Math.toRadians(pos))));
							cha.setCY((float)(y + diameter/2*Math.sin(Math.toRadians(pos))));
							pos += (double)360 / (double)circleCh.size();
						}
					}
				
					mouse = 0;
				}
			}
			else ch.setShowName(0);	
			ch.display();
		}
		
		drawLink();
	}
}
