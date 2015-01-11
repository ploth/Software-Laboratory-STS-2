package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.Workbench;

public class OCRApp {
	public static void main(String[] args) {
		
		 try {
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // TODO handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // TODO handle exception
	    }
	    catch (InstantiationException e) {
	       // TODO handle exception
	    }
	    catch (IllegalAccessException e) {
	       // TODO handle exception
	    }
	       
		
		new Workbench("OCR Workbench");
	}
}
