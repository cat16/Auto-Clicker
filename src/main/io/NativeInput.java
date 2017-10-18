package main.io;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class NativeInput implements NativeKeyListener {
	
	private ArrayList<String> keys = new ArrayList<String>();
	private ArrayList<String> keysp = new ArrayList<String>();
	
	public NativeInput() throws NativeHookException {
		//make it not spit out everything you do
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);
		//register the keyhandler
		if(!GlobalScreen.isNativeHookRegistered())
			GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(this);
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		String key = getKey(e);
		if(!keysp.contains(key)) {
			keysp.add(key);
		}
		if(!keys.contains(key)) {
			keys.add(key);
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		String key = getKey(e);
		keys.remove(key);
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {}
	
	private String getKey(NativeKeyEvent e) {
		String key = NativeKeyEvent.getKeyText(e.getKeyCode());
		if(key.equals("Unknown keyCode: 0xe36")) key = "Shift";
		if(e.getKeyLocation() == 2) key = "L"+key;
		if(e.getKeyLocation() == 3) key = "R"+key;
		return key;
	}
	
	/**
	 * Determines if the key is currently held down
	 * @param key
	 * @return self exclamatory
	 */
	public boolean keyDown(String key) {
		return keys.contains(key);
	}
	
	/**
	 * Determines if the key is currently held down and an update hasn't happened
	 * @param key
	 * @return self exclamatory
	 */
	public boolean keyPressed(String key) {
		return keysp.contains(key);
	}
	
	/**
	 * Clears the array of keys pressed, but not held down; use with keyPressed in an update loop to make key checks easier
	 */
	public void update() {
		keysp.clear();
	}
	
	/**
	 * Determines if there are no keys pressed or held down
	 * @return self exclamatory
	 */
	public boolean nothingPressed() {
		return keys.isEmpty() && keysp.isEmpty();
	}
	
}
