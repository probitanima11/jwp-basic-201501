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

public class DelAnswerController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(DelAnswerController.class);
	
	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long answerId = ServletRequestUtils.getRequiredLongParameter(request, "answerId");
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		logger.debug("answerId : {}", answerId);
		
		// 답글 제거  
		AnswerDao.INSTANCE.delete(answerId);
		
		// 답글 수 추가 
		Question question = QuestionDao.INSTANCE.findById(questionId);
		question.delCountOfComment();
		QuestionDao.INSTANCE.update(question);
		
		// 다시 받아오기
		question = QuestionDao.INSTANCE.findById(questionId);
		List<Answer> answers = AnswerDao.INSTANCE.findAllByQuestionId(questionId);
		
		ModelAndView mav = jsonView();
		mav.addObject("countOfComment", question.getCountOfComment());
		mav.addObject("question", question);
		mav.addObject("answers", answers);
		return mav;
	}
}
