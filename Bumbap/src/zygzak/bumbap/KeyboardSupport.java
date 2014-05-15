package zygzak.bumbap;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardSupport implements KeyListener{

class Key{
	public boolean isPressed = false;
	public Key(){}
	public void setKeyStatus(boolean status){
		this.isPressed = status;
	}
}
	public KeyboardSupport(){
		Key W = new Key();
		Key S = new Key();
		Key A = new Key();
		Key D = new Key();
		
		Key ESC = new Key();
		Key F1 = new Key();
		Key F2 = new Key();
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent c) {
		System.out.println("Pressed key "+c.getKeyChar());
		
	}

}
