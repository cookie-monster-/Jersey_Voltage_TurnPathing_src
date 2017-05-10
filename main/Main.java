package main;

import java.io.FileWriter;
import java.io.IOException;


public class Main {
	// Get everything set up
	
	public static void main(String[] args){
	    String filename = "turn360Path1";
	    String filepath = "C:/Users/Drew/Desktop/"+filename+".txt";
		double totalDegrees = 360;
		double acceleration = 6;
		double wheelbase = 26.5;
		double totalDistance;
		double velLast=0.0;
		double velNow;
		double posNow;
		double posLast=0.0;
		double acc;
		double totalTime;
		double radians;
		double lineNum = 0;
		double timeStep = 0.02;
		double x;
		double y;
		
		totalDistance = wheelbase * Math.PI /360 * totalDegrees / 12; // divide 12 = ft
		totalTime = Math.sqrt(4*totalDistance/acceleration);
		double timeOff = totalTime % timeStep;
		totalTime -= timeOff;
		if(timeOff >= timeStep/2){
			totalTime+=timeStep;
		}
		lineNum = totalTime / timeStep + 1;// +1 = line of 0's
		FileWriter m_writer;
	    try {
			m_writer = new FileWriter(filepath, false);
			m_writer.write(filename + "\n" + (int)lineNum + "\n");
		} catch ( IOException e ) {
			System.out.println(e);
			m_writer = null;
		}
	    
	    //first side
	    x=-wheelbase/24;//-1.125;
	    y=0.0;
		for(int line = 0;line < lineNum;line++){
			if(line != 0){
				if (line<(lineNum-1)/2){
					acc=acceleration;
				}else if(line>(lineNum-1)/2){
					acc=-acceleration;
				}else{
					acc=3;
				}
				velNow = velLast + acc * timeStep;
				posNow = posLast + (velLast + velNow)/2 * timeStep;
				radians = posNow*24/wheelbase;//360/(wheelbase*Math.PI)/4.77464829275769;//magic num??????
				x+=Math.sin(radians)*(posNow-posLast);
				y+=Math.cos(radians)*(posNow-posLast);

				velLast = velNow;
				posLast = posNow;
				
			    if(m_writer != null){try{
						m_writer.write(posNow + " "+velNow+" "+(int)acc+" 0 "+radians+" "+timeStep+" "+x+" "+y+"\n");// jerk, x, y = 0
					}catch(Exception e){}}
			}else{
				 if(m_writer != null){try{
						m_writer.write("0 0 0 0 0 "+timeStep+" "+x+" "+y+"\n");//first line 0 everything
					}catch(Exception e){}}
			}
		}
		//second side
	    x=wheelbase/24;//1.125;
	    y=0.0;
		posLast =0.0;
		velLast=0.0;
		for(int line = 0;line < lineNum;line++){
			if(line != 0){
			if (line<lineNum/2){
				acc=-acceleration;
			}else if(line>lineNum/2){
				acc=acceleration;
			}else{
				acc=0;
			}
			velNow = velLast + acc * timeStep;
			posNow = posLast + (velLast + velNow)/2 * timeStep;
			radians = -posNow*24/wheelbase;//360/(wheelbase*Math.PI)/4.77464829275769;//magic num??????
			x+=Math.sin(radians)*(posNow-posLast);
			y+=Math.cos(radians)*(posNow-posLast);

			velLast = velNow;
			posLast = posNow;
			
		    if(m_writer != null){try{
					m_writer.write(posNow + " "+velNow+" "+(int)acc+" 0 "+radians+" "+timeStep+" "+x+" "+y+"\n");// jerk, x, y = 0
				}catch(Exception e){}}
			}else{
				 if(m_writer != null){try{
						m_writer.write("0 0 0 0 0 "+timeStep+" "+x+" "+y+"\n");//first line 0 everything
					}catch(Exception e){}}
			}
		}
		
    	try{m_writer.close();System.out.println("Wrote to "+filepath);}catch(Exception e){}
    	
	}
}
