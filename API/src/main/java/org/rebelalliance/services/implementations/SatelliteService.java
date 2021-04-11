package org.rebelalliance.services.implementations;

import java.util.*;

import org.rebelalliance.constants.SatelliteNames;
import org.rebelalliance.entities.LoadCarrier;
import org.rebelalliance.entities.Satellite;
import org.rebelalliance.exceptions.MessageNotObtainedException;
import org.rebelalliance.exceptions.SatelliteNotFoundException;
import org.rebelalliance.services.interfaces.ISatelliteService;
import org.springframework.stereotype.Service;

@Service
public class SatelliteService implements ISatelliteService {

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
	public LoadCarrier getLoadCarrier(String name, Satellite satellite) throws SatelliteNotFoundException, MessageNotObtainedException {
		
		Satellite kenobi = Satellite.createDefault(SatelliteNames.KENOBI);
		Satellite skywalker = Satellite.createDefault(SatelliteNames.SKYWALKER);
		Satellite sato = Satellite.createDefault(SatelliteNames.SATO);
		Satellite[]satellites = {kenobi, skywalker, sato};
		
		Optional<Satellite> selSat = Arrays.stream(satellites).filter(s -> s.getName().equalsIgnoreCase(name)).findFirst();
		
		if(!selSat.isPresent())
			throw new SatelliteNotFoundException(name);
		
		selSat.get().distance = satellite.distance;
		selSat.get().message = satellite.message;
		
		return getLoadCarrier(satellites);
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
}