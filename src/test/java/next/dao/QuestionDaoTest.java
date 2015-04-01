package next.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import next.model.Question;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;

public class QuestionDaoTest {
	@Before
	public void setup() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
	}

	@Test
	public void crud() throws Exception {
		Question expected = new Question("자바지기", "title", "contents");
		QuestionDao.INSTANCE.insert(expected);
		
		List<Question> questions = QuestionDao.INSTANCE.findAll();
		assertTrue(questions.size() > 0);
	}
	
	@Test
	public void deleteQuestion() throws Exception {
		Question expected = new Question("aaa", "title", "contents");
		QuestionDao.INSTANCE.delete(expected.getQuestionId());
		QuestionDao.INSTANCE.insert(expected);
		assertEquals(true, QuestionDao.INSTANCE.delete(expected.getQuestionId()));
	}
}
