let questionList;
let questionnaireId;

onload = () => {
    let questionnaire=$util.getPageParam("questionnaire");
    let id = questionnaire.id;

    let params = {};
    if (id != null && id !== '') {
        questionnaireId = id;
        console.log(id)
        $.ajax({
            url: API_BASE_URL + '/queryTemplateQuestionList',
            type: "POST",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success(res) {
                questionList = res.data
                showQuestionnaire(questionList)
            }
        })
    }

}

const showQuestionnaire = (questionList) => {
    for (let i = 0; i < questionList.length; i++) {
        if (questionList[i].type == '1') {
            singleChoiceView(questionList[i], i + 1);
        } else if (questionList[i].type == '2') {
            multipleChoiceView(questionList[i], i + 1);
        } else if (questionList[i].type == '3') {
            blankView(questionList[i], i + 1);
        } else if (questionList[i].type == '4') {
            matrixView(questionList[i], i + 1);
        } else if (questionList[i].type == '5') {
            gaugeView(questionList[i], i + 1);
        } else {

        }
    }
}

const singleChoiceView = (question, index) => {
    $('#problem').append(`
    
    <div class="question" id="question${index}" data-type="1" data-problemIndex="${index}">
      <div class="top">
        <span class="question-title" id="questionTitle">${index}.${question.problemName}</span>
      </div>
      <div class="checkbox-container">
      <label class="checkbox-inline">
        <input type="checkbox" name="selectImportQuestion${index}" value="${questionList[index-1].id}">
      </label>
    </div>
      <div class="bottom"></div>
    </div>
  `)

    for (let i = 0; i < question.option.length; i++) {
        let option = question.option[i];
        $('#question' + index + " .bottom ").append(`
        <div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="radio" name="choice${index}" value="${i}" disabled>${option.chooseTerm ? option.chooseTerm : ''}
          </label>
        </div>
    `)
    }
}

const multipleChoiceView = (question, index) => {
    $('#problem').append(`
    <div class="question" id="question${index}" data-type="2" data-problemIndex="${index}">
      <div class="top">
        <span class="question-title" id="questionTitle">${index}.${question.problemName}</span>
      </div>
      <div class="checkbox-container">
      <label class="checkbox-inline">
        <input type="checkbox" name="selectImportQuestion${index}" value="${questionList[index-1].id}">
      </label>
    </div>
      <div class="bottom"></div>
    </div>
  `)

    for (let i = 0; i < question.option.length; i++) {
        let option = question.option[i];
        $('#question' + index + " .bottom ").append(`
        <div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="checkbox-inline">
            <input type="checkbox" name="chooseTerm${option.order}" value="${i}" disabled>${option.chooseTerm ? option.chooseTerm : ''}
          </label>
        </div>
    `)
    }
}

const blankView = (question, index) => {
    $('#problem').append(`
    <div class="question" id="question${index}" data-type="3" data-problemIndex="${index}">
      <div class="top">
        <span class="question-title" id="questionTitle">${index}.${question.problemName}</span>
      </div>
      <div class="checkbox-container">
      <label class="checkbox-inline">
        <input type="checkbox" name="selectImportQuestion${index}" value="${questionList[index-1].id}">
      </label>
    </div>
      <div class="bottom"></div>
    </div>
  `)

    $('#question' + index + " .bottom ").append(`
     <div style="border: 1px solid #CCCCCC; width: 50%; height: 70px;">
          <textarea class="form-control" id="blankContent" placeholder="请输入答案" rows="4" disabled></textarea>
      </div>
  `)
}

const matrixView = (question, problemIndex) => {
    $('#problem').append(`
    <div class="question" id="question${problemIndex}" data-type="4" data-problemIndex="${problemIndex}">
      <div class="top">
        <span class="question-title" id="questionTitle">${problemIndex}.${question.problemName}</span>
      </div>
      <div class="checkbox-container">
      <label class="checkbox-inline">
        <input type="checkbox" name="selectImportQuestion${problemIndex}" value="${questionList[problemIndex-1].id}">
      </label>
    </div>
      <div class="bottom">
        <table class="table">
          <thead>
            <tr>
              <th></th>
            </tr>
          </thead>
          <tbody>
            
          </tbody>
        </table>
      </div>
    </div>
  `)

    let trs = question.leftTitle ? question.leftTitle.split(',') : [];
    trs.map((item, index) => {
        $(`#question${problemIndex} .bottom tbody`).append(`
      <tr class="tr${index}">
        <td>${item}</td>
      </tr>
    `)
        question.option.map((item, radioIndex) => {
            $(`#question${problemIndex} .bottom tbody .tr${index}`).append(`
        <td>
          <input type="radio" name="matrx${problemIndex}radio${index}" value="${radioIndex}" disabled>
        </td>
      `)
        })
    })
    question.option.map(item => {
        $(`#question${problemIndex} .bottom thead tr`).append(`
      <th>${item.chooseTerm}</th>
    `)
    })
}

const gaugeView = (question, problemIndex) => {
    $('#problem').append(`
    <div class="question" id="question${problemIndex}" data-type="5" data-problemIndex="${problemIndex}">
      <div class="top">
        <span class="question-title" id="questionTitle">${problemIndex}.${question.problemName}</span>
      </div>
      <div class="checkbox-container">
      <label class="checkbox-inline">
        <input type="checkbox" name="selectImportQuestion${problemIndex}" value="${questionList[problemIndex-1].id}">
      </label>
    </div>
      <div class="bottom" style="display: flex; align-items: center; justify-content: space-between;">
      </div>
    </div>
  `)

    $(`#question${problemIndex} .bottom`).append(`
    <div>${question.option[0].chooseTerm}</div>
  `)
    question.option.map((item, index) => {
        $(`#question${problemIndex} .bottom`).append(`
      <div>
        <label class="radio-inline">
          <input type="radio" name="fraction${problemIndex}" value="${index} " disabled/>${item.fraction}
        </label>
      </div>
    `)
    })
    $(`#question${problemIndex} .bottom`).append(`
    <div>${question.option[question.option.length - 1].chooseTerm}</div>
  `)
}

const onImportQuestion = () => {
    let problem=$util.getPageParam("problem");

    var checkboxes = document.querySelectorAll("input[type='checkbox']:checked");
    var selectedValues = [];

    // 遍历选中的多选按钮元素，获取每个按钮的值并存储在数组中
    for (var i = 0; i < checkboxes.length; i++) {
        selectedValues.push(checkboxes[i].value);
    }

    console.log(selectedValues);
    console.log(questionnaireId);

    for(let i=0;i<selectedValues.length;i++) {
        let params={};
        params.id = selectedValues[i];
        $.ajax({
            url: API_BASE_URL + '/queryTemplateQuestionList',
            type: "POST",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success(res) {
                let question = res.data[0]
                question.questionnaireid=questionnaireId;
                question.mustAnswer=true;
                problem.push(question);
            }
        })
    }

    $util.setPageParam('problem', problem);
    $util.setPageParam('questionnaire', $util.getPageParam('questionnaire'));

    location.href = '/pages/designQuestionnaire/index.html';
}
