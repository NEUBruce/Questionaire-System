let recordList = [];
onload = () => {
    $('#headerUsername').text($util.getItem('userInfo').username)
    $('#headerDivB').text('问卷详情')

    fetchRecordList();


}

const fetchRecordList = ()=>{
    let questionnaireList = $util.getPageParam('questionnaireList');
    for (let i = 0; i < questionnaireList.length; i++) {
        let questionnaire = questionnaireList[i];
        let params = {};
        params.questionnaireId = questionnaire.id;
        $.ajax({
            url: API_BASE_URL + '/queryRecordList',
            type: "POST",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success(res) {

                // list指的是当前questionnaire的所有记录
                let list = res.data;
                for (let j = 0; j < list.length; j++) {
                    let item = list[j];
                    console.log(item)
                    $('#recordList #content').append(`
                    <tr>
                        <td>${questionnaire.questionnaireName}</td>
                        <td>${item.answeredBy}</td>
                        <td>${item.answerDate}</td>
                        <td><button>明细</button></td>
                    </tr>
                `)
                }

                for (let record in list) {
                    recordList.push(record)
                }
            }
        })

    }

}
