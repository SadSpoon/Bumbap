package zygzak.bumbap;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardSupport implements KeyListener{

class Key{
	public boolean isPressed = false;
	public Key(){}
}
	Key W,S,A,D,ESC,F1,F2;
	
	public KeyboardSupport(){
		W = new Key();
		S = new Key();
		A = new Key();
		D = new Key();
		
		ESC = new Key();
		F1 = new Key();
		F2 = new Key();
	}
	
	
	public void keyPressed(KeyEvent c) {
		if(c.getKeyCode() == KeyEvent.VK_W) W.isPressed = true;
		if(c.getKeyCode() == KeyEvent.VK_S) S.isPressed = true;
		if(c.getKeyCode() == KeyEvent.VK_A) A.isPressed = true;
		if(c.getKeyCode() == KeyEvent.VK_D) D.isPressed = true;
		if(c.getKeyCode() == KeyEvent.VK_ESCAPE) ESC.isPressed = true;
		if(c.getKeyCode() == KeyEvent.VK_F1) F1.isPressed = true;
		if(c.getKeyCode() == KeyEvent.VK_F2) F2.isPressed = true;
	}

	
	public void keyReleased(KeyEvent c) {
		if(c.getKeyCode() == KeyEvent.VK_W) W.isPressed = false;
		if(c.getKeyCode() == KeyEvent.VK_S) S.isPressed = false;
		if(c.getKeyCode() == KeyEvent.VK_A) A.isPressed = false;
		if(c.getKeyCode() == KeyEvent.VK_D) D.isPressed = false;
		if(c.getKeyCode() == KeyEvent.VK_ESCAPE) ESC.isPressed = false;
		if(c.getKeyCode() == KeyEvent.VK_F1) F1.isPressed = false;
		if(c.getKeyCode() == KeyEvent.VK_F2) F2.isPressed = false;
		
	}

	
	public void keyTyped(KeyEvent c) {
		//System.out.println("Pressed key "+c.getKeyChar());
		
	}

}
