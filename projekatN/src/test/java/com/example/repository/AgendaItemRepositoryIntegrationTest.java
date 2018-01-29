package com.example.repository;

import static com.example.constants.MeetingConstants.ID_MEETING;
import static com.example.constants.AgendaItemConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.AgendaItem;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class AgendaItemRepositoryIntegrationTest {
	@Autowired
	AgendaItemRepository agendaItemRepository;

	@Test
	public void testFindAllAgendaItems() {
		List<AgendaItem> item = (List<AgendaItem>) agendaItemRepository.findAllAgendaItems(ID_MEETING);

		assertNotNull(item);
		assertEquals(item.size(), 1);
		assertEquals(NUMBER2, item.get(0).getNumber());
		assertEquals(TITLE2, item.get(0).getTitle());
		assertEquals(ID_MEETING, item.get(0).getMeeting().getId());

	}
}
