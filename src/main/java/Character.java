package main.java;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to store states of the characters in the program.
 * You will need to declare other variables depending on your implementation.
 */
public class Character {
    
	
    
    private MainApplet parent;
    private int state;
    // declare varibles
    // original position
    private float originX , originY , diameter = 30 ;
    // current position
    private float x , y ;
    // position in circle
    private float circleX , circleY ;
    // color using r, g, b
    private int r , g , b  ;
    
    private String name ;
    private int showName ;
    private int showLink;
    private HashMap<Character,Integer> targets;
    
    
    

    public Character(MainApplet parent, String name, float x, float y , int r , int g , int b ){
        this.parent = parent;
        this.name = name ;
        
        this.originX = x;
        this.originY = y;
        
        this.x = x;
        this.y = y;
        
        this.r = r;
        this.g = g ;
        this.b = b ;
        targets = new HashMap<Character,Integer>();
    }
    
    public void display(){
    	// state : 1. dragged  2.inCircle  3.inOrigin  4.Do nothing until Animation Done
        if( state == 1 ) drag() ;
        else if( state == 2 ) inCircle() ;
        else if( state == 3 ) inOrigin() ;
        else if( state == 4 ) noThing() ;
        
        parent.stroke(255);
        parent.strokeWeight(1);
        
        if(showName == 1){
            // Draw the Bigger ellipse
            parent.fill(r, g, b, 80);
            parent.ellipse(x, y, 50, 50);
            
            // Show name
            parent.fill(30,144,255,150);
            float textWidth = (name.length() > 5) ? name.length() * 14.5f : 80;
            parent.rect(parent.mouseX, parent.mouseY - 17, textWidth,35,90);
            parent.fill(255);
            parent.textSize(20);
            parent.text(name, parent.mouseX + 10, parent.mouseY + 7.5f);
        }
        
        // Draw the smaller ellipse
        parent.fill(r, g, b, 80);
        parent.ellipse(x, y, 40, 40);
        parent.fill(255);
        
        
        
        
    }
    
    //method
    public void setCX(float f){
        circleX = f;
    }
    public void setCY(float f){
        circleY = f;
    }
    public void setState(int s){
        state = s;
    }
    public void setShowName(int i){
        showName = i;
    }
    public void setShowLink(int i) {
        showLink = i ;
    }
    public float getCX(){
        return circleX ;
    }
    public float getCY(){
        return circleY ;
    }
    public int getState(){
        return state;
    }
    public int getShowName(){
        return showName ;
    }
    public int getShowLink(){
        return showLink ;
    }
    
    // Just add the connected targets and the weights.
    public void addTarget(Character ch, Integer i){
        targets.put(ch, i);
    }
    public HashMap<Character,Integer> getTarget(){
        return this.targets;
    }
    
    public String getName() {
        return name;
    }
    
    public float getOriginX() {
        return originX;
    }
    
    public float getOriginY() {
        return originY;
    }
    
    public void drag() {
        x = parent.mouseX ;
        y = parent.mouseY ;
    }
    
    public void inCircle() {
        x = circleX ;
        y = circleY ;
    }
    
    public void inOrigin() {
        x = originX ;
        y = originY ;
    }
    
    public void noThing(){}
    
    public void setX(float x){
        this.x = x;
    }
    
    public float getX(){
        return this.x;
    }
    
    public void setY(float y){
        this.y = y;
    }
    
    public float getY(){
        return this.y;
    }
    
    public void setOGX(float x){
        this.originX = x;
    }
    
    public float getOGX(){
        return this.originX;
    }
    
    public void setOGY(float y){
        this.originY = y;
    }
    
    public float getOGY(){
        return this.originY;
    }
    
    
    
}
