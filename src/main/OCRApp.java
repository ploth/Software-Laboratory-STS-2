package main;

import gui.Workbench;

public class OCRApp {

	private static final int WORKBENCH_WIDTH = 1152;
	private static final int WORKBENCH_HEIGHT = 864;
	
	public static void main(String[] args) {
		new Workbench("OCR Workbench", WORKBENCH_WIDTH, WORKBENCH_HEIGHT);
	}
}
