onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').username)

  $('#startTime').datetimepicker({
    language: 'zh-CN', // 显示中文
    format: 'yyyy-mm-dd hh:mm:ss', // 显示格式
    // minView: "month", // 设置只显示到月份
    minView: 0,  //0表示可以选择小时、分钟   1只可以选择小时
    minuteStep: 1,//分钟间隔1分钟
    initialDate: new Date(), // 初始化当前日期
    autoclose: true, // 选中自动关闭
    todayBtn: true // 显示今日按钮
  })
  $('#endTime').datetimepicker({
    language: 'zh-CN', // 显示中文
    format: 'yyyy-mm-dd hh:mm:ss', // 显示格式
    // minView: "month", // 设置只显示到月份
    minView: 0,  //0表示可以选择小时、分钟   1只可以选择小时
    minuteStep: 1,//分钟间隔1分钟
    initialDate: new Date(), // 初始化当前日期
    autoclose: true, // 选中自动关闭
    todayBtn: true // 显示今日按钮
  })

  let user = $util.getPageParam('user')
  // console.log('--- 用户信息 ---');
  // console.log(user);
  if (user) {
    $('#username').val(user.username)
    $('#password').val(user.password)
    $('#startDate').val(user.startTime)
    $('#endDate').val(user.stopTime)
    $('#headerDivB').text('修改用户')
    $('#submit-user').text("修改用户")
  }else {
    $('#headerDivB').text('创建用户')
    $('#submit-user').text("创建用户")
  }

}

const handleCreateUser = () => {

  if (!$('#username').val()) return alert('账号不能为空！')
  if (!$('#password').val()) return alert('密码不能为空！')
  if (!($('#startDate').val() && new Date($('#startDate').val()).getTime())) return alert('开始时间不能为空！')
  if (!($('#endDate').val() && new Date($('#endDate').val()).getTime())) return alert('结束时间不能为空！')
  if (new Date($('#startDate').val()).getTime() > new Date($('#endDate').val()).getTime()) return alert('开始时间不能大于结束时间! ')

  let user = $util.getPageParam('user');
  console.log('--- user ---')
  console.log(user);
  if(!user) {
    user = {};
  }
 
  user.username = $('#username').val();
  user.password = $('#password').val();
  user.startTime = $('#startDate').val() && new Date($('#startDate').val()).getTime();
  user.stopTime = $('#endDate').val() && new Date($('#endDate').val()).getTime();
  user.lastUpdatedBy = $util.getItem('userInfo').username;

  // 修改
  if(user.id) {

    $.ajax({
      url: API_BASE_URL + '/admin/modifyUserInfo',
      type: 'POST',
      data: JSON.stringify(user),
      dataType: 'json',
      contentType: 'application/json',
      success(res) {
        if (res.code === "666") {
          location.href = '/pages/user/index.html'
        } else {
          alert(res.message)
        }
      }
    })

  } else {
    user.createdBy = $util.getItem('userInfo').username;
    // 新建
    $.ajax({
      url: API_BASE_URL + '/admin/addUser',
      type: 'POST',
      data: JSON.stringify(user),
      dataType: 'json',
      contentType: 'application/json',
      success(res) {
        if (res.code === "666") {
          location.href = '/pages/user/index.html'
        } else {
          alert(res.message)
        }
      }
    })
  }
}
