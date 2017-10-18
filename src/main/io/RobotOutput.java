package main.io;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RobotOutput {
	
	private Robot robot;
	
	public RobotOutput() throws AWTException {
		robot = new Robot();
	}
	
	public void type(String s, int lps, boolean shiftEnter) {
	    for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	        
	        //for things you need to hold shift for that won't normally work
	        boolean shift = true;
	        switch(c) {
	        //shift + number
	        case '!': c = '1'; break;
	        case '@': c = '2'; break;
	        case '#': c = '3'; break;
	        case '$': c = '4'; break;
	        case '%': c = '5'; break;
	        case '^': c = '6'; break;
	        case '&': c = '7'; break;
	        case '*': c = '8'; break;
	        case '(': c = '9'; break;
	        case ')': c = '0'; break;
	        //other
	        case '?': c = '/'; break;
	        case '\'': c = '"'; shift = false; break;
	        case '"': break;
	        //normally
	        default: shift = false; break;
	        }
	        
	        if(((int)c) == 10 && shiftEnter) {
	        	shift = true;
	        }
	        
	        if (Character.isUpperCase(c) || shift) {
	            robot.keyPress(KeyEvent.VK_SHIFT);
	        }
	        
	        try {
	        	switch(c) {
	        	case '"':
	        		robot.keyPress(KeyEvent.VK_QUOTE);
	        		robot.keyRelease(KeyEvent.VK_QUOTE);
	        		break;
	        	}
	        	if(((int)c) == 10) {
	        		robot.keyPress(KeyEvent.VK_ENTER);
	        		robot.keyRelease(KeyEvent.VK_ENTER);
	        	}else {
	        		robot.keyPress(Character.toUpperCase(c));
	        		robot.keyRelease(Character.toUpperCase(c));
	        	}
	        }catch(IllegalArgumentException ex) {
	        	System.out.println(c+" could not be typed");
	        }

	        if (Character.isUpperCase(c) || shift) {
	            robot.keyRelease(KeyEvent.VK_SHIFT);
	        }
	        
	        robot.delay(1000/lps);
	    }
	}
	
	public void mouseClick(int button) {
		mouseDown(button);
		mouseUp(button);
	}
	
	public void mouseDown(int button) {
		robot.mousePress(InputEvent.getMaskForButton(button));
	}
	
	public void mouseUp(int button) {
		robot.mouseRelease(InputEvent.getMaskForButton(button));
	}
	
}
