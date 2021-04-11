package org.rebelalliance.controllers;

import org.rebelalliance.entities.LoadCarrier;
import org.rebelalliance.entities.Satellite;
import org.rebelalliance.entities.SatelliteWrapper;
import org.rebelalliance.exceptions.MessageNotObtainedException;
import org.rebelalliance.exceptions.SatelliteNotFoundException;
import org.rebelalliance.services.interfaces.ISatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RebelController {

	@Autowired
	ISatelliteService satelliteService;

	@PostMapping(value = "/topsecret")
	public ResponseEntity<LoadCarrier> topSecret(@RequestBody SatelliteWrapper satellites)
			throws MessageNotObtainedException {

		LoadCarrier carrier = satelliteService.getLoadCarrier(satellites.getSatellites());
		return ResponseEntity.ok(carrier);
	}

    @GetMapping(value = "/topsecret_split")
    public LoadCarrier topSecret() throws SatelliteNotFoundException, MessageNotObtainedException {
    	
		return satelliteService.getLoadCarrier();
    }    

	@PostMapping(value = "/topsecret_split/{name}")
	public Satellite topSecret(@PathVariable("name") String name, @RequestBody Satellite satellite)
			throws SatelliteNotFoundException, MessageNotObtainedException {
		
		satellite.setName(name);
		
		return satelliteService.createOrUpdateSatellite(satellite);
	}
}
