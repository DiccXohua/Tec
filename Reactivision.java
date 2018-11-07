import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;

import TUIO.*;
public class Reactivision extends PApplet{
	TuioProcessing tuioClient;
	PFont font;
	boolean verbose = false; // print console debug messages
	boolean callback = true; // updates only after callbacks

	public void setup()
	{
	  // GUI setup
	  noCursor();
	  size(displayWidth,displayHeight);
	  noStroke();
	  fill(0);
	  font = createFont("Arial", 18);
	  
	  // finally we create an instance of the TuioProcessing client
	  // since we add "this" class as an argument the TuioProcessing class expects
	  // an implementation of the TUIO callback methods in this class (see below)
	  tuioClient  = new TuioProcessing(this);
	}

	// within the draw method we retrieve an ArrayList of type <TuioObject>, <TuioCursor> or <TuioBlob>
	// from the TuioProcessing client and then loops over all lists to draw the graphical feedback.
	public void draw()
	{
	  background(0);
	  textFont(font,18);

	  ArrayList<TuioObject> tuioObjectList = tuioClient.getTuioObjectList();
	  for (int i=0;i<tuioObjectList.size();i++) {
	     TuioObject tobj = tuioObjectList.get(i);
	     stroke(0); 
	     fill(255);
	     rect(tobj.getScreenX(width), tobj.getScreenY(height),80,80);
	     fill(150);
	     text(""+tobj.getSymbolID(), tobj.getScreenX(width), tobj.getScreenY(height));
	     rect(tobj.getScreenX(width), tobj.getScreenY(height),80,80);
	     text("XPos:"+tobj.getScreenX(width)+" YPos:"+tobj.getScreenY(height),tobj.getScreenX(width), tobj.getScreenY(height));
	     
	   }
	}

	// --------------------------------------------------------------
	// these callback methods are called whenever a TUIO event occurs
	// there are three callbacks for add/set/del events for each object/cursor/blob type
	// the final refresh callback marks the end of each TUIO frame

	// called when an object is added to the scene
	public void addTuioObject(TuioObject tobj) {
	  if (verbose) println("add obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());
	}

	// called when an object is moved
	public void updateTuioObject (TuioObject tobj) {
	  if (verbose) println("set obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()
	          +" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel());
	}

	// called when an object is removed from the scene
	public void removeTuioObject(TuioObject tobj) {
	  if (verbose) println("del obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");
	}

	// --------------------------------------------------------------
	// called when a cursor is added to the scene
	public void addTuioCursor(TuioCursor tcur) {
	  if (verbose) println("add cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+ ") " +tcur.getX()+" "+tcur.getY());
	  //redraw();
	}

	// called when a cursor is moved
	public void updateTuioCursor (TuioCursor tcur) {
	  if (verbose) println("set cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+ ") " +tcur.getX()+" "+tcur.getY()
	          +" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
	  //redraw();
	}

	// called when a cursor is removed from the scene
	public void removeTuioCursor(TuioCursor tcur) {
	  if (verbose) println("del cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+")");
	  //redraw()
	}

	// --------------------------------------------------------------
	// called when a blob is added to the scene
	public void addTuioBlob(TuioBlob tblb) {
	  if (verbose) println("add blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle()+" "+tblb.getWidth()+" "+tblb.getHeight()+" "+tblb.getArea());
	  //redraw();
	}

	// called when a blob is moved
	public void updateTuioBlob (TuioBlob tblb) {
	  if (verbose) println("set blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle()+" "+tblb.getWidth()+" "+tblb.getHeight()+" "+tblb.getArea()
	          +" "+tblb.getMotionSpeed()+" "+tblb.getRotationSpeed()+" "+tblb.getMotionAccel()+" "+tblb.getRotationAccel());
	  //redraw()
	}

	// called when a blob is removed from the scene
	public void removeTuioBlob(TuioBlob tblb) {
	  if (verbose) println("del blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+")");
	  //redraw()
	}

	// --------------------------------------------------------------
	// called at the end of each TUIO frame
	public void refresh(TuioTime frameTime) {
	  if (verbose) println("frame #"+frameTime.getFrameID()+" ("+frameTime.getTotalMilliseconds()+")");
	  if (callback) redraw();
	}
}
