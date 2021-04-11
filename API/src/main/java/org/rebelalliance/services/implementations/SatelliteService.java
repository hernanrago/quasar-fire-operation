package org.rebelalliance.services.implementations;

import java.util.*;
import org.rebelalliance.daos.SatelliteDAO;
import org.rebelalliance.entities.LoadCarrier;
import org.rebelalliance.entities.Satellite;
import org.rebelalliance.exceptions.MessageNotObtainedException;
import org.rebelalliance.exceptions.SatelliteNotFoundException;
import org.rebelalliance.services.interfaces.ISatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SatelliteService implements ISatelliteService {
	
	@Autowired
	private SatelliteDAO satelliteDAO;

	@Override
	public float[] getLocation(float... distances) {
		float kenobiX, kenobiY, skywalkerX, skaywalkerY, satoX, satoY, carrierX, carrierY, kenobiDistance,
				skywalkerDistance, satoDistance;

		kenobiX = -500.0f;
		kenobiY = -200f;
		skywalkerX = 100f;
		skaywalkerY = -100f;
		satoX = 500f;
		satoY = 100f;

		kenobiDistance = distances[0];
		skywalkerDistance = distances[1];
		satoDistance = distances[2];

		float a1Sq = kenobiX * kenobiX, a2Sq = skywalkerX * skywalkerX, a3Sq = satoX * satoX, b1Sq = kenobiY * kenobiY,
				b2Sq = skaywalkerY * skaywalkerY, b3Sq = satoY * satoY, r1Sq = kenobiDistance * kenobiDistance,
				r2Sq = skywalkerDistance * skywalkerDistance, r3Sq = satoDistance * satoDistance;

		float numerator1 = (skywalkerX - kenobiX) * (a3Sq + b3Sq - r3Sq) + (kenobiX - satoX) * (a2Sq + b2Sq - r2Sq)
				+ (satoX - skywalkerX) * (a1Sq + b1Sq - r1Sq);

		float denominator1 = 2
				* (satoY * (skywalkerX - kenobiX) + skaywalkerY * (kenobiX - satoX) + kenobiY * (satoX - skywalkerX));

		carrierY = numerator1 / denominator1;

		float numerator2 = r2Sq - r1Sq + a1Sq - a2Sq + b1Sq - b2Sq - 2 * (kenobiY - skaywalkerY) * carrierY;

		float denominator2 = 2 * (kenobiX - skywalkerX);

		carrierX = numerator2 / denominator2;

		float[] coordinates = { carrierX, carrierY };

		return coordinates;
	}

	@Override
	public String getMessage(String[]... messages) throws MessageNotObtainedException {
		String[] messArr = new String[getSize(messages)];

		for (int i = 0; i < messArr.length; i++) {
			for (String[] arr : messages) {
				int messageDelay = arr.length - messArr.length;
				if (!arr[i + messageDelay].isEmpty())
					messArr[i] = arr[i + messageDelay];
			}
		}
		
		if(Arrays.stream(messArr).anyMatch(s -> s == null || s.isEmpty()))
			throw new MessageNotObtainedException();

		return String.join(" ", messArr);
	}

	private int getSize(String[]... messages) {
		if (messages.length == 1)
			return messages[0].length;
		else {
			int min = messages[0].length < messages[1].length ? messages[0].length : messages[1].length;
			for (String[] m : messages) {
				if (m.length < min)
					min = m.length;
			}
			return min;
		}
	}

	@Override
	public LoadCarrier getLoadCarrier() throws SatelliteNotFoundException, MessageNotObtainedException {
		
	    List<Satellite> satellites = new ArrayList<Satellite>();
		satelliteDAO.findAll().forEach(satellites::add);
		
		int satellitesSize = satellites.size();
		if(satellitesSize < 3)
			throw new SatelliteNotFoundException("There are not enough satellites to determine the position and discover the carrier's message.");
		
		
		return getLoadCarrier(satellites.subList(satellitesSize-3, satellitesSize).toArray(new Satellite[0]));
	}
	
	@Override
	public LoadCarrier getLoadCarrier(Satellite[] satellites) throws MessageNotObtainedException{
		float[] distances = new float[satellites.length];
		ArrayList<String[]> messages = new ArrayList<String[]>();
		for(int i = 0; i < satellites.length; i++) {
			 distances[i] = satellites[i].getDistance();
			 messages.add(satellites[i].getMessage());
		}
		
		float[] location = getLocation(distances);
		
		String message = getMessage(messages.toArray(new String[0][]));
		
		LoadCarrier carrier = new LoadCarrier();
		carrier.setMessage(message);
		carrier.setPosition(location);
		
		return carrier;
	}

	@Override
	public Satellite createOrUpdateSatellite(Satellite satellite) {
		List<Satellite> satellites = satelliteDAO.findByName(satellite.getName());

		Satellite satelliteToCreateOrUpdate = !satellites.isEmpty() ? satelliteToCreateOrUpdate = satellites.get(0)
				: new Satellite(satellite.getName());
		
		satelliteToCreateOrUpdate.setDistance(satellite.distance);
		satelliteToCreateOrUpdate.setMessage(satellite.message);

		return satelliteDAO.save(satelliteToCreateOrUpdate);
	}
}