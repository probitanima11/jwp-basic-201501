package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import next.model.Answer;
import next.model.Question;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;

public enum QuestionDao {
	INSTANCE;
	public void insert(Question question) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfComment) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, question.getWriter(), question.getTitle(), question.getContents(), new Timestamp(
				question.getTimeFromCreateDate()), question.getCountOfComment());
	}

	public void update(Question question) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "UPDATE QUESTIONS SET countOfComment = ? WHERE questionId = ?";
		jdbcTemplate.update(sql, question.getCountOfComment(), question.getQuestionId());
	}

	public boolean delete(long questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		Question question = QuestionDao.INSTANCE.findById(questionId);
		String writer = question.getWriter();
		List<Answer> answers = AnswerDao.INSTANCE.findAllByQuestionId(questionId);

		for (Answer answer : answers) {
			if (answer.getWriter() != writer) {
				return false;
			}
		}
		
		String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
		jdbcTemplate.update(sql, questionId);
		return true;
	}

	public List<Question> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, createdDate, countOfComment FROM QUESTIONS "
				+ "order by questionId desc";

		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
						rs.getTimestamp("createdDate"), rs.getInt("countOfComment"));
			}

		};

		return jdbcTemplate.query(sql, rm);
	}

	public Question findById(long questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
				+ "WHERE questionId = ?";

		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfComment"));
			}

		};

		return jdbcTemplate.queryForObject(sql, rm, questionId);
	}
}
