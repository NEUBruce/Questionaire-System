onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').username)
  $('#headerDivB').text('创建调查问卷')
  $('#selectField').append("<option value=\"0\">面向公众</option>")
  $('#selectField').append("<option value=\"1\">面向指定群组</option>")
  $('#selectStyle').append("<option value=\"0\">123</option>")
  $('#selectStyle').append("<option value=\"1\">456</option>")
  $('#startTime').datetimepicker({
    language: 'zh-CN', // 显示中文
    format: 'yyyy-mm-dd', // 显示格式
    minView: "month", // 设置只显示到月份
    initialDate: new Date(), // 初始化当前日期
    autoclose: true, // 选中自动关闭
    todayBtn: true // 显示今日按钮
  })
  $('#endTime').datetimepicker({
    language: 'zh-CN', // 显示中文
    format: 'yyyy-mm-dd', // 显示格式
    minView: "month", // 设置只显示到月份
    initialDate: new Date(), // 初始化当前日期
    autoclose: true, // 选中自动关闭
    todayBtn: true // 显示今日按钮
  })
}

const handleCreateQuestionnaire = () => {

  if (!$('#surveyName').val()) return alert('调查名称不能为空！')
  if (!$('#surveyDescription').val()) return alert('调查描述不能为空！')
  if (!($('#startDate').val() && new Date($('#startDate').val()).getTime())) return alert('开始时间不能为空！')
  if (!($('#endDate').val() && new Date($('#endDate').val()).getTime())) return alert('结束时间不能为空！')
  if (new Date($('#startDate').val()).getTime() > new Date($('#endDate').val()).getTime()) return alert('开始时间不能大于结束时间! ')

  let project = $util.getPageParam('project');
  let questionnaire = {};
  questionnaire.questionnaireName = $('#surveyName').val()
  questionnaire.questionnaireDescription = $('#surveyDescription').val();
  questionnaire.startTime = $('#startDate').val() && new Date($('#startDate').val()).getTime()
  questionnaire.stopTime = $('#endDate').val() && new Date($('#endDate').val()).getTime()
  questionnaire.createdBy = $util.getItem('userInfo').username
  questionnaire.lastUpdatedBy = $util.getItem('userInfo').username
  questionnaire.type = $util.getPageParam('type')
  questionnaire.projectId = project.id

  $.ajax({
    url: API_BASE_URL + '/addQuestionnaire',
    type: 'POST',
    data: JSON.stringify(questionnaire),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      if (res.code === "666") {
        $util.setPageParam('questionnaire', res.data)
        location.href = '/pages/designQuestionnaire/index.html'
      } else {
        alert(res.message)
      }
    }
  })

}
