var formList = document.querySelectorAll('.answerWrite input[type=submit]');
for (var j = 0; j < formList.length; j++) {
	formList[j].addEventListener('click', writeAnswers, false);
}
var commentList = document.querySelectorAll('.comments a');
for (var j = 0; j < commentList.length; j++) {
	commentList[j].addEventListener('click', deleteComent, false);
}

function writeAnswers(e) {
	e.preventDefault();

	var answerForm = e.currentTarget.form;
	var url = "/api/addanswer.next";
	var params = "questionId=" + answerForm[0].value + "&writer="
			+ answerForm[1].value + "&contents=" + answerForm[2].value;

	var request = new XMLHttpRequest();
	request.open("POST", url, true);
	request.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");

	request.onreadystatechange = function() {
		console.log("request.readyState: " + request.readyState
				+ " request.status: " + request.status);
		if (request.readyState == 4 && request.status == 200) {
			location.reload(true);
		}
	}

	request.send(params);
}

function deleteComent(e) {
	e.preventDefault();
	var answerId = e.currentTarget.getAttribute('data-answerId');
	var questionId = e.currentTarget.getAttribute('data-questionId');
	var url = "/api/delanswer.next";
	var params = "answerId=" + answerId + "&questionId=" + questionId;

	var request = new XMLHttpRequest();
	request.open("POST", url, true);
	request.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");

	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			var comments = document.querySelector('.comments h3');
			comments.innerHTML="댓글 수 : " + JSON.parse(request.response).countOfComment;
			var element = document.getElementById(answerId);
			element.parentNode.removeChild(element);
		}
	}

	request.send(params);
}
