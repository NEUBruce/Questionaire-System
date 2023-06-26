onload = () => {
    $('#headerUsername').text($util.getItem('userInfo').username)
    $('#headerDivB').text('问卷详情')
    let projectId = $util.getPageParam('seeProject')
    fetchProjectInfo(projectId)
}
