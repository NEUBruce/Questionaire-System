const handleConfirmModify = () => {
  $('#modifyTitleModal').modal('hide')
  $('.questionnaire-title > span').text(questionnaireTitle)
  $('.questionnaire-description > span').text(questionnaireDescription)
  let params= {
    id: $util.getPageParam('questionnaire').id,
    questionnaireName: questionnaireTitle,
    questionnaireDescription: questionnaireDescription
  }
  $.ajax({
    url: API_BASE_URL + '/modifyQuestionnaireInfo',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
    }
  })
}

const onQuestionnaireTitleInput = (e) => {
  questionnaireTitle = e.value
}

const onQuestionnaireDescriptionInput = (e) => {
  questionnaireDescription = e.value
}
