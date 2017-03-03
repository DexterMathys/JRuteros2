package com.model;

public enum Difficulty {
	EASY("E"),
	MEDIUM("M"),
	HARD("H"),
	VERYHARD("VH"),
	EXPERT("EX");
	private String difficulty;
	Difficulty(String difficulty){
		this.difficulty = difficulty;
	}
	public String difficulty(){
		return difficulty;
	}
}