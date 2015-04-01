package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class DeleteController extends AbstractController {
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		System.out.println(request.getRequestURL());
		if (!QuestionDao.INSTANCE.delete(questionId)) {
			String errorMessage = "삭제할 수 없습니다.";
			List<Answer> answers = AnswerDao.INSTANCE.findAllByQuestionId(questionId);
			Question question = QuestionDao.INSTANCE.findById(questionId);
			ModelAndView mav = jstlView("show.jsp?questionId" + questionId);
			mav.addObject("errorMessage", errorMessage);
			mav.addObject("answers", answers);
			mav.addObject("question", question);
			return mav;
		}

		List<Question> questions = QuestionDao.INSTANCE.findAll();

		
		ModelAndView mav = jstlView("list.jsp");
		if (!"delete.next".equals(request.getRequestURL().toString().split("/")[3]))
			 mav = jsonView();
		mav.addObject("questions", questions);
		return mav;
	}
}
