onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').username)
  $('#headerDivB').text('创建调查问卷')
  $('#selectField').append("<option value=\"面向公众\">面向公众</option>")
  $('#selectField').append("<option value=\"面向指定群组\">面向指定群组</option>")
  $('#selectStyle').append("<option value=\"简约\">简约</option>")
  $('#selectStyle').append("<option value=\"科技\">科技</option>")
  $('#selectStyle').append("<option value=\"几何\">几何</option>")
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
  questionnaire.target=$('#selectField option:selected').val()
  questionnaire.group=$('#selectGroup option:selected').val()
  questionnaire.style=$('#selectStyle option:selected').val()
  questionnaire.answerTimeLimit=$('#answerTimes').val()


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


const selectField = document.getElementById('selectField');
const selectGroup = document.getElementById('selectGroup');

selectField.addEventListener('change', function() {
  const selectedValue = $('#selectField option:selected').val();
  console.log(selectedValue);


  // 根据第一个下拉列表的选择值更新第二个下拉列表的选项
  updateSelectGroupOptions(selectedValue);

});

function updateSelectGroupOptions(selectedValue) {

  // 清空第二个下拉列表的选项
  selectGroup.innerHTML = '';

  // 创建新的选项并添加到第二个下拉列表中
  const options = getOptionsBySelectedValue(selectedValue);
  options.forEach(function(option) {
    const optionElement = document.createElement('option');
    optionElement.value = option.value;
    optionElement.textContent = option.label;
    selectGroup.appendChild(optionElement);
  });
}

function getOptionsBySelectedValue(selectedValue) {
  // 根据第一个下拉列表的选择值获取相应的选项列表数据
  // 根据 selectedValue 返回适当的选项列表数据
  console.log(selectedValue);
  if (selectedValue === "面向公众") {
    return[];
  } else if (selectedValue === "面向指定群组") {
    //学生
    if($util.getPageParam("type")==="0")
    {
      return [
        { value: '计院学生', label: '计院学生' },
        { value: '软院学生', label: '软院学生' }
      ];
    }
    else{
      return [
        { value: '计院老师', label: '计院老师' },
        { value: '软院老师', label: '软院老师' }
      ];
    }
  }

  return [];
}