package org.rebelalliance.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="satellites")
public class Satellite {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public String name;
	public float distance;
	public String[] message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}
	
	public Satellite() {
	}
	
	public Satellite(String name) {
		this.name = name;
	}
}
