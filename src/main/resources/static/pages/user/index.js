onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').username)
  $('#headerDivB').text('用户管理')
  fetchUserList()
}

let pageNum = 1
let userList = []

const fetchUserList = () => {
  let params = {
    pageNum,
    pageSize: 10,
    username: $('#username').val()
  }
  $.ajax({
    url: API_BASE_URL + '/admin/queryUserList',
    type: 'POST',
    data: JSON.stringify(params),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      $('#table #tbody').html('')
      userList = res.data
      res.data.map((item, index) => {
        const formattedStartTime = new Date(item.startTime).toLocaleString();
        const formattedStopTime = new Date(item.stopTime).toLocaleString();
        $('#table #tbody').append(`
          <tr>
            <td>${index + 1}</td>
            <td>${item.username}</td>
            <td>${item.password}</td>
            <td>${formattedStartTime}</td>
            <td>${formattedStopTime}</td>
            <td>
              <button type="button" class="btn btn-link" onclick="resetPassword('${item.id}')">重置密码</button>
              <button type="button" class="btn btn-link" onclick="handleEdit('${item.id}')">编辑</button>
              <button type="button" class="btn btn-link btn-red" onclick="closeUser('${item.id}')">关闭</button>
              <button type="button" class="btn btn-link btn-red" onclick="deleteUser('${item.id}')">删除</button>
            </td>
          </tr>
        `)
      })
    }
  })
}
const deleteUser = (id) => {
  let params = {
    id: id
  }
  $.ajax({
    url: API_BASE_URL + '/admin/deleteUserinfo',
    type: 'POST',
    data: JSON.stringify(params),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      fetchUserList()
    }
  })
}
const handleTableChange = (page) => {
  if (page === 1) {
    if (pageNum === 1) return
    pageNum--
  } else if (page === 2) {
    pageNum++
  } else if (page === 3) {
    pageNum = +$('#goNum').val()
  }
  $('#currentPage').text(pageNum)
  fetchUserList()
}

const handleCreateUser = () => {
  $util.setPageParam('user', undefined)
  location.href = '/pages/createUser/index.html'
}

const handleEdit = (id) => {
  let user = userList.filter(item => item.id === id)[0]
  $util.setPageParam('user', user)
  location.href = '/pages/createUser/index.html'
}

const closeUser = (id) => {
  let user = userList.filter(item => item.id === id)[0]
  user.status = '0';
  user.lastUpdatedBy = $util.getItem('userInfo').username;
  $.ajax({
    url: API_BASE_URL + '/admin/modifyUserInfo',
    type: 'POST',
    data: JSON.stringify(user),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      fetchUserList()
    }
  })

}


const resetPassword = (id) => {
  let user = userList.filter(item => item.id === id)[0]
  user.password = '123';
  user.lastUpdatedBy = $util.getItem('userInfo').username;
  $.ajax({
    url: API_BASE_URL + '/admin/modifyUserInfo',
    type: 'POST',
    data: JSON.stringify(user),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      alert("已将该用户密码重置为123!")
      fetchUserList()
    }
  })

}

