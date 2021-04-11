package org.rebelalliance.entities;

import org.rebelalliance.constants.SatelliteNames;

public class Satellite {

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

	public static Satellite createDefault(String name) {
		Satellite satellite = new Satellite();
		satellite.name = name;
		switch (name) {
		case SatelliteNames.KENOBI:
			satellite.distance = 100.0F;
			satellite.message = new String[] { "este", "", "", "mensaje", "" };
			break;
		case SatelliteNames.SKYWALKER:
			satellite.distance = 115.5F;
			satellite.message = new String[] { "", "es", "", "", "secreto" };
			break;
		case SatelliteNames.SATO:
			satellite.distance = 142.7F;
			satellite.message = new String[] { "este", "", "un", "", "" };
			break;
		default:
			break;
		}
		return satellite;
	}
}
