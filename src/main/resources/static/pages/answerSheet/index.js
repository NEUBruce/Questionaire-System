let questionList;
let questionnaireId;
let questionnaireName;
let timeLimit;

onload = () => {
    let id = new URL(location.href).searchParams.get('id');

    let params = {};
    params.id = id;
    if (id != null && id !== '') {
        questionnaireId = id;
        $.ajax({
            url: API_BASE_URL + '/queryQuestionnaireList',
            type: "POST",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success(res) {
                let questionnaire = res.data[0];
                timeLimit = questionnaire.answerTimeLimit;

                $('.questionnaire-title').text(questionnaire.questionnaireName)
                questionnaireName = questionnaire.questionnaireName;
                $('.questionnaire-description').text("用途: " + questionnaire.questionnaireDescription)
                questionList = questionnaire.questionEntityList;
                showQuestionnaire(questionList)

            }
        })
    } else {
        questionnaireId = $util.getPageParam('questionnaireId')
        $('.questionnaire-title').text($util.getPageParam('questionnaireTitle'))
        questionnaireName = $util.getPageParam('questionnaireTitle');
        $('.questionnaire-description').text("用途:" + $util.getPageParam('questionnaireDescription'))
        questionList = $util.getPageParam('questionList');
        showQuestionnaire(questionList)
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
            <input type="radio" name="choice${index}" value="${i}">${option.chooseTerm ? option.chooseTerm : ''}
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
            <input type="checkbox" name="chooseTerm${option.order}" value="${i}">${option.chooseTerm ? option.chooseTerm : ''}
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
          <textarea class="form-control" id="blankContent" placeholder="请输入答案" rows="4"></textarea>
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
          <input type="radio" name="matrx${problemIndex}radio${index}" value="${radioIndex}">
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
          <input type="radio" name="fraction${problemIndex}" value="${index}"/>${item.fraction}
        </label>
      </div>
    `)
    })
    $(`#question${problemIndex} .bottom`).append(`
    <div>${question.option[question.option.length - 1].chooseTerm}</div>
  `)
}

function collectFormData() {
    const answererName = document.getElementById('answerer').value;
    const formData = {
        answererName: answererName,
        answers: []
    };

    //用于使提示只提示一次
    let isPrompted = false;

    questionList.forEach((item, index) => {
        const problemIndex = index + 1;
        const problemType = item.type;

        //用于判断是否作答
        let isAnswer = false;

        if (problemType === '1') {
            let selectedOptionValue = $(`#question${problemIndex} input[type=radio]:checked`).val();
            if (questionList[problemIndex - 1].mustAnswer) {
                if (!selectedOptionValue) {
                    if (!isPrompted) {
                        alert("第" + problemIndex + "题为必答题，请输入答案!");
                        isPrompted = true;
                    }
                    return;
                } else {
                    isAnswer = true;
                }
            } else {
                if (selectedOptionValue) {
                    isAnswer = true;
                }
            }


            if (isAnswer) {
                const answer = {
                    problemIndex: problemIndex,
                    answerType: '1',
                    selectedOption: selectedOptionValue
                };
                formData.answers.push(answer);
            }
        } else if (problemType === '2') {
            const selectedOptions = $(`#question${problemIndex} input[type=checkbox]:checked`);
            const selectedOptionValues = selectedOptions.map(function () {
                return this.value;
            }).get();

            if (questionList[problemIndex - 1].mustAnswer) {
                if (selectedOptionValues.length === 0) {
                    if (!isPrompted) {
                        alert("第" + problemIndex + "题为必答题，请输入答案!");
                        isPrompted = true;
                    }
                    return;
                } else {
                    isAnswer = true;
                }
            } else {
                if (selectedOptionValues.length !== 0) {
                    isAnswer = true;
                }
            }

            if(isAnswer){
                const answer = {
                    problemIndex: problemIndex,
                    answerType: '2',
                    selectedOptions: selectedOptionValues
                };
                formData.answers.push(answer);
            }
        } else if (problemType === '3') {
            const inputValue = $(`#question${problemIndex} textarea`).val();

            if (questionList[problemIndex - 1].mustAnswer) {
                if (inputValue.trim() === '') {
                    if (!isPrompted) {
                        alert("第" + problemIndex + "题为必答题，请输入答案!");
                        isPrompted = true;
                    }
                    return;
                }else{
                    isAnswer = true;
                }
            }else{
                if (inputValue.trim() !== '') {
                    isAnswer = true;
                }
            }

            if(isAnswer){
                const answer = {
                    problemIndex: problemIndex,
                    answerType: '3',
                    inputValue: inputValue,
                };
                formData.answers.push(answer);
            }

        } else if (problemType === '4') {
            const selectedOptionValues = [];

            questionList[problemIndex - 1].option.forEach((_, optionIndex) => {
                let selectedOptionValue = '';
                questionList[problemIndex - 1].leftTitle.split(',').forEach(() => {
                    selectedOptionValue = $(`#question${problemIndex} input[name="matrx${problemIndex}radio${optionIndex}"]:checked`).val();

                });
                if (selectedOptionValue) {
                    selectedOptionValues.push(selectedOptionValue);
                }
            })

            if (questionList[problemIndex - 1].mustAnswer) {
                if (selectedOptionValues.length !== 3) {
                    if (!isPrompted) {
                        alert("第" + problemIndex + "题为必答题，请输入答案!");
                        isPrompted = true;
                    }
                    return;
                }else{
                    isAnswer=true;
                }
            }else{
                if (selectedOptionValues.length === 3) {
                    isAnswer=true;
                }
            }

            if(isAnswer)
            {
                const answer = {
                    problemIndex: problemIndex,
                    answerType: '4',
                    selectedOptions: selectedOptionValues,
                };
                formData.answers.push(answer);
            }

        } else if (problemType === '5') {
            const selectedOptionValue = $(`#question${problemIndex} input[type=radio]:checked`).val();

            if (questionList[problemIndex - 1].mustAnswer) {
                if (!selectedOptionValue) {
                    if (!isPrompted) {
                        alert("第" + problemIndex + "题为必答题，请输入答案!");
                        isPrompted = true;
                    }
                    return;
                }else{
                    isAnswer=true;
                }
            }else{
                if (selectedOptionValue) {
                    isAnswer=true;
                }
            }

            if(isAnswer)
            {
                const answer = {
                    problemIndex: problemIndex,
                    answerType: '5',
                    selectedOption: selectedOptionValue
                };
                formData.answers.push(answer);
            }
        }
    });

    if (isPrompted) {
        return null;
    }

    console.log(formData)
    return formData;
}



const onSubmitQuestionnaire = () => {
    if (!answerLimitCheck()){
        return;
    }

    console.log(1)

    let formData = collectFormData();
    if(formData == null)
    {
        return;
    }
    let record = {};
    record.answeredBy = formData.answererName;
    record.questionnaireId = questionnaireId;
    let answerList = [];
    for (let i = 0; i < formData.answers.length; i++) {
        let input = formData.answers[i];
        if (input.answerType == '1') {
            let answer = {};
            answer.type = input.answerType;
            answer.questionIndex = input.problemIndex;
            answer.chooseTerm = input.selectedOption;
            answerList.push(answer);
        } else if (input.answerType == '2') {
            for (let i = 0; i < input.selectedOptions.length; i++) {
                let answer = {};
                answer.type = input.answerType;
                answer.questionIndex = input.problemIndex;
                answer.chooseTerm = input.selectedOptions[i];
                answerList.push(answer);
            }
        } else if (input.answerType == '3') {
            let answer = {};
            answer.type = input.answerType;
            answer.questionIndex = input.problemIndex;
            answer.chooseTerm = input.inputValue;
            answerList.push(answer);
        } else if (input.answerType == '4') {
            for (let i = 0; i < input.selectedOptions.length; i++) {
                let answer = {};
                answer.type = input.answerType;
                answer.questionIndex = input.problemIndex;
                answer.chooseTerm = input.selectedOptions[i];
                answer.row = i;
                answerList.push(answer);
            }
        } else if (input.answerType == '5') {
            let answer = {};
            answer.type = input.answerType;
            answer.questionIndex = input.problemIndex;
            answer.chooseTerm = input.selectedOption;
            answerList.push(answer);
        } else {

        }
    }

    record.answerEntityList = answerList;

    $.ajax({
        url: API_BASE_URL + '/addRecord',
        type: "POST",
        data: JSON.stringify(record),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            alert('提交成功!')
        }
    })
}

const answerLimitCheck = ()=>{
    let answererName = document.getElementById('answerer').value;
    if (answererName != null && answererName.trim() != '') {

        let record = {};
        record.questionnaireId = questionnaireId;
        record.answeredBy = answererName;
        $.ajax({
            url: API_BASE_URL + '/queryRecordList',
            type: "POST",
            data: JSON.stringify(record),
            dataType: "json",
            contentType: "application/json",
            success(res) {
                if (res.data) {
                    if (res.data.length >= timeLimit) {
                        alert('您的回答次数已经超过上限!')
                        return false;
                    }
                }
                return true;
            }
        })

    } else {
        alert('请输入答卷人姓名!');
        return false;
    }
}