onload = () => {
    $('#headerUsername').text($util.getItem('userInfo').username)
    handleHeaderLoad()
    fetchProjectList()
}

let projectList = []

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

            for (let i = 0; i < res.data.length; i++) {
                let item = res.data[i];
                $('#content').append(`
                  <div class="list">
                    <div class="list-header">
                      <div>${item.projectName}</div>
                      <div>
                        <button type="button" class="btn btn-link" onclick="onCreateQuestionnaire('${i}')">创建问卷</button>
                        <button type="button" class="btn btn-link" onclick="onSeeProject('${item.id}')">查看</button>
                        <button type="button" class="btn btn-link" onclick="onEditProject('${item.id}')">编辑</button>
                        <button type="button" class="btn btn-link" onclick="onDelProject('${item.id}')">删除</button>
                        <button type="button" class="btn btn-link" onclick="onStatisticsProject('${item.id}')">统计</button>
                      </div>
                    </div>
                    <div id="div-content${item.id}"></div>
         
                  </div>
        `       )
            }

            for (let i = 0; i < res.data.length; i++) {
                let item = res.data[i];
                if ($('#div-content' + item.id).html() === '') {
                    $('#div-content' + item.id).append(`
                        <div class="list-footer">
                            <div>暂无调查问卷或问卷已过期</div>
                        </div>
                `);
                }
            }
        }
    })

    $.ajax({
        url: API_BASE_URL + '/queryQuestionnaireList',
        type: "POST",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            console.log(res.data)
            for (let i = 0; i < res.data.length; i++) {
                let item = res.data[i];
                if ($('#div-content' + item.projectId).find('.list-footer').length) {
                    $('#div-content' + item.projectId).html('')
                }
                $('#div-content' + item.projectId).append(`
              <div class="questionnaire-name">问卷名称 : ${item.questionnaireName}</div>
        `)
            }

        }
    })
}

const onStatisticsProject = (id) => {
    let params = {};
    params.projectId = id;

    $.ajax({
        url: API_BASE_URL + '/queryQuestionnaireList',
        type: "POST",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            console.log(res.data);
            $util.setPageParam('questionnaireList', res.data);
            location.href = "/pages/seeQuestionnaire/index.html"
        }
    })


}


const onCreateProject = () => {
    location.href = "/pages/createProject/index.html"
}

const
    onCreateQuestionnaire = (index) => {
        $util.setPageParam('projectList', projectList)
        $util.setPageParam('defaultIndex', index)
        location.href = "/pages/createQuestionnaire/index.html"
    }

const onSeeProject = (id) => {
    $util.setPageParam('seeProject', id)
    location.href = "/pages/seeProject/index.html"
}

const onEditProject = (id) => {
    let project = projectList.filter(item => item.id === id)[0]
    $util.setPageParam('editProject', project)
    location.href = "/pages/editProject/index.html"
}

const onDelProject = (pid) => {
    let state = confirm("确认删除该项目吗？")

    if (state) {
        let params = {
            id: pid
        }
        $.ajax({
            url: API_BASE_URL + '/deleteProjectById',
            type: "POST",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success(res) {
                alert(res.message)
                fetchProjectList()
            }
        })
    }

}
