const handleConfirmModify = () => {
  $('#modifyTitleModal').modal('hide')
  if(questionnaireTitle!=='' && questionnaireTitle!=null)
  {
    $('.questionnaire-title > span').text(questionnaireTitle)
  }else{
    alert('问卷标题不能为空');
    location.reload();
  }
  if(questionnaireDescription!=='' && questionnaireDescription!=null)
  {
    $('.questionnaire-description > span').text(questionnaireDescription)
  }else{
    alert('问卷说明不能为空');
    location.reload();
  }
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
