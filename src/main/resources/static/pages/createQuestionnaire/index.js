onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').username)
  $('#headerDivB').text('创建问卷')
  $('#selectKind').append("<option value=\"0\">学生</option>")
  $('#selectKind').append("<option value=\"1\">教师</option>")
  let projectList = $util.getPageParam('projectList');
  let defaultIndex = $util.getPageParam('defaultIndex');
  console.log(projectList)
  console.log(defaultIndex)

  for (let i = 0; i < projectList.length; i++) {
    if (i != defaultIndex) {
      $('#selectProject').append("<option value=\"" + i + "\">" + projectList[i].projectName + "</option>")
    }else {
      $('#selectProject').append("<option selected value=\"" + i + "\">" + projectList[i].projectName + "</option>")
    }
  }



}

const onCreateTemplate = () => {
  let projectList = $util.getPageParam('projectList')
  // 将选中的project传入下一个页面
  $util.setPageParam('project', projectList[$('#selectProject option:selected').val()])
  // 把选中的类型传入下一个页面
  $util.setPageParam('type', $('#selectKind option:selected').val())
  $util.setPageParam('problem', null);
  location.href = "/pages/createNewQuestionnaire/index.html"
}

const importHistoryQuestionnaire = () => {
  $('#divider').css('display', 'flex')
  $('#templateB').html('')
  $('#templateB').append(`
    <div class="template-item">
      <div class="item-t">
        <img class="img" src="../../static/images/blank_template.png">
        <div>
          <div class="title">测试</div>
          <div>页面测试数据</div>
        </div>
      </div>
      <div class="item-b">
        <button type="button" class="btn btn-default">导 入</button>
      </div>
    </div>
  `)
}

const surveyTypeTemplate = () => {
  $('#divider').css('display', 'flex')
  $('#templateB').html('')
  $('#templateB').append(`
    <div class="template-item">
      <div class="item-t">
        <img class="img" src="../../static/images/blank_template.png">
        <div>
          <div class="title">创建模板</div>
          <div>题库抽题，限时作答，成绩查询，自动阅卷</div>
        </div>
      </div>
      <div class="item-b">
        <button type="button" class="btn btn-default" onclick="createTemplate()">创 建</button>
      </div>
    </div>
    <div class="template-item">
      <div class="item-t">
        <img class="img" src="../../static/images/blank_template.png">
        <div>
          <div class="title">测试</div>
          <div></div>
        </div>
      </div>
      <div class="item-b">
        <button type="button" class="btn btn-default" onclick="handleEdit()" style="margin-right: 10px;">编 辑</button>
        <button type="button" class="btn btn-default">导 入</button>
      </div>
    </div>
  `)
}

const createTemplate = () => {
  $('#createTemplateModal').modal('show')
}

const handleEdit = () => {
  open('/pages/designQuestionnaire/index.html')
}