package org.rebelalliance.services.interfaces;

import org.rebelalliance.entities.LoadCarrier;
import org.rebelalliance.entities.Satellite;
import org.rebelalliance.exceptions.MessageNotObtainedException;
import org.rebelalliance.exceptions.SatelliteNotFoundException;

public interface ISatelliteService {
	float[] getLocation(float... distances);

	String getMessage(String[]... messages) throws MessageNotObtainedException;
	
	LoadCarrier getLoadCarrier(String name, Satellite satellite) throws SatelliteNotFoundException, MessageNotObtainedException;
	
	LoadCarrier getLoadCarrier(Satellite[] satellites) throws MessageNotObtainedException;
}