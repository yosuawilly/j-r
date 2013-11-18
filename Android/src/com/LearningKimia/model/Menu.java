package com.LearningKimia.model;

/**
 * @author Dwidasa

 */

public class Menu {

	private String menuTextTop;
	private String menuTextBottom;
	private String menuTextRight;
	
	public Menu() {
		// TODO Auto-generated constructor stub
	}
	
	public Menu(String menuTextTop) {
		this.menuTextTop = menuTextTop;
	}
	
	public Menu(String menuTextTop, String menuTextBottom) {
		this.menuTextTop = menuTextTop;
		this.menuTextBottom = menuTextBottom;
	}
	
	public Menu(String menuTextTop, String menuTextBottom, String menuTextRight) {
		this.menuTextTop = menuTextTop;
		this.menuTextBottom = menuTextBottom;
		this.menuTextRight = menuTextRight;
	}

	public String getMenuTextTop() {
		return menuTextTop;
	}

	public void setMenuTextTop(String menuTextTop) {
		this.menuTextTop = menuTextTop;
	}

	public String getMenuTextBottom() {
		return menuTextBottom;
	}

	public void setMenuTextBottom(String menuTextBottom) {
		this.menuTextBottom = menuTextBottom;
	}

	public String getMenuTextRight() {
		return menuTextRight;
	}

	public void setMenuTextRight(String menuTextRight) {
		this.menuTextRight = menuTextRight;
	}
	
}
