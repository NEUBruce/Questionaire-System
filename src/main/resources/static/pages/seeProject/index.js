onload = () => {
    $('#headerUsername').text($util.getItem('userInfo').username)
    $('#headerDivB').text('项目详情')
    let projectId = $util.getPageParam('seeProject')
    fetchProjectInfo(projectId)
}

const fetchProjectInfo = (id) => {
    let params = {
        id: id,
        createdBy: $util.getItem('userInfo').username
    }
    $.ajax({
        url: API_BASE_URL + '/queryProjectList',
        type: "POST",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            let info = res.data[0]
            const formattedCreateTime = new Date(info.creationDate).toLocaleString();
            $('#projectName').text(info.projectName)
            $('#createTime').text(formattedCreateTime)
            $('#projectDescribe').text(info.projectContent)
            $('#personInCharge').text(info.createdBy)
            $('#projectDescription').text(info.projectContent)
        }
    })

    params = {
        projectId: id,
    }

    $.ajax({
        url: API_BASE_URL + '/queryQuestionnaireList',
        type: "POST",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            $('#content').html('')
            for (let i = 0; i < res.data.length; i++) {
                let item = res.data[i];
                const formattedCreationDate = new Date(item.creationDate).toLocaleString();
                const index=i+1;
                $('#content').append(`
            <tr>
               <td>${index}</td>
               <td>${item.questionnaireName}</td>
               <td>${formattedCreationDate}</td>
               <td>
               <button type="button" class="btn btn-link" onclick="onPreview('${item.id}')">预览</button>
               <button type="button" class="btn btn-link" onclick="onRelease('${item.id}')">发布</button>
               <button type="button" class="btn btn-link" onclick="onDelQuestionnaire('${item.id}','${item.startTime}','${item.stopTime}')">删除</button>
               <button type="button" class="btn btn-link" onclick="">统计</button>
               </td>
             </tr>
        `)
            }

        }
    })
}

const onPreview = (id)=>{

    let params = {
        id: id
    }
    console.log(params);
    $.ajax({
        url: API_BASE_URL + '/queryQuestionnaireList',
        type: "POST",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            let questionnaire = res.data[0];
            if (res.code === '666') {
                $util.setPageParam('questionList', questionnaire.questionEntityList)
                $util.setPageParam('questionnaireTitle', questionnaire.questionnaireName)
                $util.setPageParam('questionnaireDescription', questionnaire.questionnaireDescription)
                $util.setPageParam('questionnaireId', questionnaire.id)
                location.href = '/pages/answerSheet/index.html'
            }

        }
    })



}

const onDelQuestionnaire = (id,startTime,stopTime) => {
    let state = confirm("确认删除该问卷吗？")
    let projectId = $util.getPageParam('seeProject')

    if (state) {
        let params = {
            id:id
        }
        console.log(params);

        const currentTime = new Date();
        const startDateTime = new Date(startTime);
        const stopDateTime = new Date(stopTime);

        if (currentTime >= startDateTime && currentTime <= stopDateTime) {
            alert("该问卷正在发布中，删除失败");
            return;
        }

        $.ajax({
            url: API_BASE_URL + '/deleteQuestionnaire',
            type: "POST",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success(res) {
                alert(res.message)
                fetchProjectInfo(projectId)
            }
        })
    }
}

function redirectToPage(url) {
    window.location.href = url;
}

const onRelease = (id) => {
    alert("成功发布该项目!\n项目链接为:" + "http://127.0.0.1:8085/pages/answerSheet/index.html?id=" + id);

}