package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class AddAnswerController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(AddAnswerController.class);
	
	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		String writer = ServletRequestUtils.getRequiredStringParameter(request, "writer");
		String contents = ServletRequestUtils.getRequiredStringParameter(request, "contents");
		logger.debug("questionId : {}", questionId);
		
		// 답글 추가 
		Answer answer = new Answer(writer, contents, questionId);
		AnswerDao.INSTANCE.insert(answer);
		// 답글 수 추가 
		Question question = QuestionDao.INSTANCE.findById(questionId);
		question.addCountOfComment();
		QuestionDao.INSTANCE.update(question);
		//
		question = QuestionDao.INSTANCE.findById(questionId);
		List<Answer> answers = AnswerDao.INSTANCE.findAllByQuestionId(questionId);
		
		ModelAndView mav = jsonView();
		mav.addObject("question", question);
		mav.addObject("answers", answers);
		return mav;
	}
}
