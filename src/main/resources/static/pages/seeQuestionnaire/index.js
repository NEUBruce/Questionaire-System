let recordList;
onload = () => {
    $('#headerUsername').text($util.getItem('userInfo').username)
    $('#headerDivB').text('问卷详情')


}

const fetchRecordList = ()=>{
    let questionnaireList = $util.getPageParam('questionnaireList');
    for (let i = 0; i < questionnaireList.length; i++) {

    }

}
