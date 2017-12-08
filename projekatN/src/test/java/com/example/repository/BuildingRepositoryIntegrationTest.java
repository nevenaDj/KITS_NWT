package com.example.repository;

import static com.example.constants.BuildingConstatnts.STREET;
import static com.example.constants.BuildingConstatnts.NUMBER;
import static com.example.constants.BuildingConstatnts.CITY;
import static com.example.constants.BuildingConstatnts.BUILDING_ID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.Building;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class BuildingRepositoryIntegrationTest {
	@Autowired
	BuildingRepository buildingRepository;
	
	@Test
	public void testFindByAddress(){
		Building building = buildingRepository.findByAddress(STREET, NUMBER, CITY);
		
		assertNotNull(building);
		assertEquals(STREET, building.getAddress().getStreet());
		assertEquals(NUMBER, building.getAddress().getNumber());
		assertEquals(CITY, building.getAddress().getCity());
		assertEquals(BUILDING_ID_1, building.getId());

	}

}
