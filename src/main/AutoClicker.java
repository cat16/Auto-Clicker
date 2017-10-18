package main;

import main.io.NativeInput;
import main.io.RobotOutput;

public class AutoClicker implements Runnable {

	private Window window;
	private RobotOutput robot;
	private NativeInput input;
	
	private boolean stopThread = false;
	private Thread clickThread;
	
	private boolean typeNow = false;

	private void update() {
		if(input.keyDown("RAlt") && input.keyPressed("C")) {
			if(clickThread.isAlive()) {
				stopThread = true;
			}else {
				stopThread = false;
				clickThread = new Thread(this);
				clickThread.start();
			}
		}
		if(input.keyDown("RAlt") && input.keyPressed("H")) {
			robot.mouseDown(1);
		}
		if(input.keyDown("RAlt") && input.keyPressed("T")) {
			typeNow = !typeNow;
		}
		if(input.nothingPressed()) {
			if(typeNow) {
				robot.type(window.toType(), window.getAPS(), window.shiftEnter());
				typeNow = false;
			}
		}
	}

	public static void main(String[]args) {
		new AutoClicker();
	}

	public AutoClicker() {
		try {
			input = new NativeInput();
			//create the robot
			robot = new RobotOutput();
			clickThread = new Thread(this);

			window = new Window();
			start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void start() {
		while(true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			update();
			input.update();
		}
	}

	@Override
	public void run() {
		while(!stopThread) {
			robot.mouseClick(1);
			sleep(1000/window.getAPS());
		}
	}
	
	private void sleep(int ms) {
		try {
			robot.mouseClick(1);
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
