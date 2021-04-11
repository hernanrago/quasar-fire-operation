package org.rebelalliance.entities;

public class LoadCarrier {
	
	private Position position;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Position getPosition() {
		return position;
	}


	public void setPosition(float[] location) {
		this.position.setX(location[0]);
		this.position.setY(location[1]);
	}
	
	public LoadCarrier() {
		position = new Position();
	}
}


class Position{
	private float x;
	
	private float y;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}