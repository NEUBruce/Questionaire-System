onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').username)
  $('#headerDivB').text('创建问卷')



}

const onCreateTemplate = () => {
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

// 获取项目列表
const fetchProjectList = () => {
  let params = {
    createdBy: $util.getItem('userInfo').username,
    projectName: $('#projectName').val()
  }
  $.ajax({
    url: API_BASE_URL + '/queryProjectList',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      projectList = res.data
      $('#content').html('')

      res.data.map(item => {
        $('#content').append(`
          <div class="list">
            <div class="list-header">
              <div>${item.projectName}</div>
              <div>
                <button type="button" class="btn btn-link" onclick="onCreateQuestionnaire()">创建问卷</button>
                <button type="button" class="btn btn-link" onclick="onSeeProject('${item.id}')">查看</button>
                <button type="button" class="btn btn-link" onclick="onEditProject('${item.id}')">编辑</button>
                <button type="button" class="btn btn-link" onclick="onDelProject('${item.id}')">删除</button>
              </div>
            </div>
            <div class="list-footer">
              <div>暂无调查问卷或问卷已过期</div>
            </div>
          </div>
        `)
      })
    }
  })
}
