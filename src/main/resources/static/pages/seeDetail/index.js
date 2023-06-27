let questionList;
let questionnaireName;
let formAnswer = {};
onload = () => {

    let params = {};
    params.id = $util.getPageParam('recordId');
    $.ajax({
        url: API_BASE_URL + '/queryRecordList',
        type: "POST",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            let record = res.data[0];
            formAnswer.answererName = record.answeredBy;
            formAnswer.answers = [];
            params.id = record.questionnaireId;
            $.ajax({
                url: API_BASE_URL + '/queryQuestionnaireList',
                type: "POST",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success(res) {
                    formatRecordAnswer(record.answerEntityList);
                    $('#answerer').val(formAnswer.answererName);
                    let questionnaire = res.data[0];
                    $('.questionnaire-title').text(questionnaire.questionnaireName)
                    questionnaireName = questionnaire.questionnaireName;
                    $('.questionnaire-description').text("用途: " + questionnaire.questionnaireDescription)
                    questionList = questionnaire.questionEntityList;
                    showQuestionnaire(questionList)

                }
            })

        }
    })

}

const showQuestionnaire = (questionList) => {
    for (let i = 0; i < questionList.length; i++) {
        if (questionList[i].type == '1') {
            singleChoiceView(questionList[i], i + 1);
            setSingleSelectedRadioButton(i+1,formAnswer.answers[i+1].selectedOption)
        } else if (questionList[i].type == '2') {
            multipleChoiceView(questionList[i], i + 1);
            setMultiSelectedRadioButton(i+1, formAnswer.answers[i+1].selectedOptions)
        } else if (questionList[i].type == '3') {
            blankView(questionList[i], i + 1);
            setBlankValue(i+1, formAnswer.answers[i+1].inputValue)
        } else if (questionList[i].type == '4') {
            matrixView(questionList[i], i + 1);
            setMatrixSelectedRadioButton(i+1, formAnswer.answers[i+1].selectedOptions, questionList[i].leftTitle.length)
        } else if (questionList[i].type == '5') {
            gaugeView(questionList[i], i + 1);
            setSingleSelectedRadioButton(i+1,formAnswer.answers[i+1].selectedOption)
        } else {

        }
    }
}

const singleChoiceView = (question, index) => {
    $('#problem').append(`
    <div class="question" id="question${index}" data-type="1" data-problemIndex="${index}">
      <div class="top">
        <span class="question-title" id="questionTitle">${index}.${question.problemName}</span>
        <span class="must-answer" id="mustAnswer"></span>
      </div>
      <div class="bottom"></div>
    </div>
  `)
    if (question.mustAnswer) {
        $('#question' + index + " #mustAnswer").text('必答题')
    } else {
        $('#question' + index + " #mustAnswer").text('非必答题')
    }
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
        <span class="must-answer" id="mustAnswer"></span>
      </div>
      <div class="bottom"></div>
    </div>
  `)
    if (question.mustAnswer) {
        $('#question' + index + " #mustAnswer").text('必答题')
    } else {
        $('#question' + index + " #mustAnswer").text('非必答题')
    }
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
        <span class="must-answer" id="mustAnswer"></span>
      </div>
      <div class="bottom"></div>
    </div>
  `)
    if (question.mustAnswer) {
        $('#question' + index + " #mustAnswer").text('必答题')
    } else {
        $('#question' + index + " #mustAnswer").text('非必答题')
    }
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
        <span class="must-answer" id="mustAnswer"></span>
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
    if (question.mustAnswer) {
        $('#question' + problemIndex + " #mustAnswer").text('必答题')
    } else {
        $('#question' + problemIndex + " #mustAnswer").text('非必答题')
    }
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
        <span class="must-answer" id="mustAnswer"></span>
      </div>
      <div class="bottom" style="display: flex; align-items: center; justify-content: space-between;">
      </div>
    </div>
  `)
    if (question.mustAnswer) {
        $('#question' + problemIndex + " #mustAnswer").text('必答题')
    } else {
        $('#question' + problemIndex + " #mustAnswer").text('非必答题')
    }
    $(`#question${problemIndex} .bottom`).append(`
    <div>${question.option[0].chooseTerm}</div>
  `)
    question.option.map((item, index) => {
        $(`#question${problemIndex} .bottom`).append(`
      <div>
        <label class="radio-inline">
          <input type="radio" name="fraction${problemIndex}" value="${index}" disabled/>${item.fraction}
        </label>
      </div>
    `)
    })
    $(`#question${problemIndex} .bottom`).append(`
    <div>${question.option[question.option.length - 1].chooseTerm}</div>
  `)
}


const formatRecordAnswer = (answers)=>{
    for (let i = 0; i < answers.length; i++) {
        let item = answers[i];
        if (item.type == '1') {
            let answer = {};
            answer.problemIndex = item.questionIndex;
            answer.selectedOption = item.chooseTerm;
            answer.answerType = item.type;
            formAnswer.answers[item.questionIndex] = answer;
        } else if (item.type == '2') {

            let exist = formAnswer.answers[item.questionIndex];
            if (exist) {
                exist.selectedOptions.push(item.chooseTerm);
            } else {
                let answer = {};
                answer.problemIndex = item.questionIndex;
                answer.selectedOptions = [];
                answer.answerType = item.type;
                answer.selectedOptions.push(item.chooseTerm);
                formAnswer.answers[item.questionIndex] = answer;
            }
        } else if (item.type == '3') {
            let answer = {};
            answer.problemIndex = item.questionIndex;
            answer.inputValue = item.chooseTerm;
            answer.answerType = item.type;
            formAnswer.answers[item.questionIndex] = answer;
        } else if (item.type == '4') {
            let exist = formAnswer.answers[item.questionIndex];
            if (exist) {
                exist.selectedOptions[parseInt(item.row)] = item.chooseTerm;
            } else {
                let answer = {};
                answer.problemIndex = item.questionIndex;
                answer.selectedOptions = [];
                answer.answerType = item.type;
                answer.selectedOptions[parseInt(item.row)] = item.chooseTerm;
                formAnswer.answers[item.questionIndex] = answer;
            }

        } else if (item.type == '5') {
            let answer = {};
            answer.problemIndex = item.questionIndex;
            answer.selectedOption = item.chooseTerm;
            answer.answerType = item.type;
            formAnswer.answers[item.questionIndex] = answer;
        } else {

        }
    }



}

const setSingleSelectedRadioButton = (problemIndex, value) => {
    $(`#question${problemIndex} input[type=radio][value="${value}"]`).prop('checked', true);
};

const setBlankValue = (problemIndex, value) => {
    $(`#question${problemIndex} textarea`).val(value);
};

const setMatrixSelectedRadioButton = (problemIndex, values) => {
    console.log(values)
    for (let matrixIndex = 0; matrixIndex < values.length; matrixIndex++) {
        let value = values[matrixIndex];
        console.log(value)
        let radioButton = $(`#question${problemIndex} input[name="matrx${problemIndex}radio${matrixIndex}"][value="${value}"]`);
        radioButton.prop('checked', true);
    }
};


const setMultiSelectedRadioButton = (problemIndex, values) => {
    values.forEach((value) => {
        $(`#question${problemIndex} input[type="checkbox"][value="${value}"]`).prop('checked', true);
    });
};
