package main.java;

import java.awt.Color;
import java.util.ArrayList;

import controlP5.Button;
import controlP5.CColor;
import controlP5.ControlFont;
import controlP5.ControlP5;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import processing.data.*;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private ArrayList<String> file = new ArrayList<String>();
	private ArrayList<Network> net = new ArrayList<Network>();
	private Network currentNet;
	private int episode;
	private PFont font;
	
	// UI
	private ControlP5 button;
	private Button addAll;
	private Button clear;
	
	private final static int width = 1200, height = 650;
	
	public void setup() {
		
		size(width, height);
		
		loadData();
		smooth();
		initial();
		buttons();

		Ani.init(this);
		
	}
	public void buttons(){
		button = new ControlP5(this);		
		button.setColorBackground( color(0,255,255) );
		addAll = button.addButton("addAll") .setLabel("Add All").setPosition(950, 150) .setSize(200, 70);
		addAll.getCaptionLabel().setFont(new ControlFont(createFont("Verdana",20)));
		clear = button.addButton("clear") .setLabel("Clear").setPosition(950, 250) .setSize(200, 70);
		clear.getCaptionLabel().setFont(new ControlFont(createFont("Verdana",20)));
	}
	public void addAll()
	{
		for(Character ch : currentNet.characters)
			if(!this.currentNet.circleCh.contains(ch))
			{
				ch.setShowLink(1);
				this.currentNet.addOne(ch);	
			}
				
		
		for(Character ch : currentNet.characters) 
		{
			
			ch.setState(4);
			Ani ani = Ani.to(ch,1.3f,"x",ch.getCX());
			Ani.to(ch,1.3f,"y",ch.getCY());
			
			ani.setCallback("onEnd:addAniDone");
		}	
	}
	public void clear()
	{
		for(Character ch : currentNet.characters)
		{
			if(this.currentNet.circleCh.contains(ch))
			{
				this.currentNet.removeOne(ch);
				ch.setShowLink(0);
				
			
				ch.setState(4);
				Ani ani = Ani.to(ch,1.3f,"x",ch.getOGX());
				Ani.to(ch,1.3f,"y",ch.getOGY());
				
				ani.setCallback("onEnd:removeAniDone");
			}
		}	
	}
	public void initial(){
		currentNet = net.get(0);
		episode = 1;
		font = createFont("Arial", 50);
	}
	public void draw() {
		background(255);
		currentNet.display();
		
		fill(0,50,0);

		textFont(font);
		text("Star Wars " + episode,450,50);
	}
	
	public void keyPressed()
	{
		if(key > '0' && key < '8')	
		{
			currentNet = net.get(key - '1');
			episode = key - '0';
		}
	}
	
	private void loadData(){
		int k, a, b;
		for(int i=1; i<8; i++){
			file.add("starwars-episode-"+i+"-interactions.json");
			net.add(new Network(this));
		}
		for(int i=0; i<7; i++){
			currentNet = net.get(i);
			JSONObject json = loadJSONObject(path + file.get(i));
			JSONArray nodes = json.getJSONArray("nodes");
			JSONArray links = json.getJSONArray("links");
			
			for(int j=0; j<nodes.size(); j++){
				JSONObject tmp = nodes.getJSONObject(j);
				String name = tmp.getString("name");
				String colour = tmp.getString("colour");
			    k = Integer.parseInt(colour.substring(3,5),16);
				a = Integer.parseInt(colour.substring(5,7),16);
				b = Integer.parseInt(colour.substring(7,9),16);
				
				Character ch = new Character(this,name,0,0,k,a,b);
				

				currentNet.characters.add(ch);
			}
			
			setChXY();
			

			for(int j=0 ; j<links.size() ; j++)
			{
				JSONObject link = links.getJSONObject(j);
				int source = link.getInt("source");
				int target = link.getInt("target");
				Integer value = link.getInt("value");
				

				Character src = currentNet.characters.get(source);
				src.addTarget(currentNet.characters.get(target), value);
			}
		}
	}
	public void setChXY()
	{
		int ogx = 30,ogy = 50;
		for(Character ch : currentNet.characters)
		{
			ch.setOGX(ogx);
			ch.setOGY(ogy);
			ch.setState(3);
			ogx += 80;
			if(ogx > 320)
			{
				ogy += 50;
				ogx = 30;
			}
		}
	}
	
}
