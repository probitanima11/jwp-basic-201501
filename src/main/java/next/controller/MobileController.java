package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class MobileController extends AbstractController {
	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 답글 수 추가 
		List<Question> questions = QuestionDao.INSTANCE.findAll();
		
		ModelAndView mav = jsonView();
		mav.addObject("question", questions);
		return mav;
	}
}
