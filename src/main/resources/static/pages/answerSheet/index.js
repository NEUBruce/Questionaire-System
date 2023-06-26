let questionList;
onload = () => {
    let id = new URL(location.href).searchParams.get('id');

    let params = {};
    params.id = id;
    if (id != null && id !== '') {
        $.ajax({
            url: API_BASE_URL + '/queryQuestionnaireList',
            type: "POST",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success(res) {
                let questionnaire = res.data[0];
                console.log(questionnaire);

                $('.questionnaire-title').text(questionnaire.questionnaireName)
                $('.questionnaire-description').text("用途: " + questionnaire.questionnaireDescription)
                questionList = questionnaire.questionEntityList;
                showQuestionnaire(questionList)
            }
        })
    } else {
        $('.questionnaire-title').text($util.getPageParam('questionnaireTitle'))
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
        question.option.map(() => {
            $(`#question${problemIndex} .bottom tbody .tr${index}`).append(`
        <td>
          <input type="radio" name="radio${index}">
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
    question.option.map(item => {
        $(`#question${problemIndex} .bottom`).append(`
      <div>
        <label class="radio-inline">
          <input type="radio" name="fraction" />${item.fraction}
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

    questionList.forEach((item, index) => {
        const problemIndex = index + 1;
        const problemType = item.type;


        if (problemType === '1') {
            const selectedOptionValue = $(`#question${problemIndex} input[type=radio]:checked`).val();
            const answer = {
                problemIndex: problemIndex,
                answerType: 'singleChoice',
                selectedOption: selectedOptionValue
            };
            formData.answers.push(answer);
        } else if (problemType === '2') {
            const selectedOptions = $(`#question${problemIndex} input[type=checkbox]:checked`);
            const selectedOptionValues = selectedOptions.map(function () {
                return this.value;
            }).get();

            const answer = {
                problemIndex: problemIndex,
                answerType: 'multipleChoice',
                selectedOptions: selectedOptionValues
            };
            formData.answers.push(answer);
        } else if (problemType === '3') {
            const inputValue = $(`#question${problemIndex} textarea`).val();
            const answer = {
                problemIndex: problemIndex,
                answerType: 'blank',
                inputValue: inputValue,
            };
            formData.answers.push(answer);
        } else if (problemType === '4') {
            const selectedOptionValues = [];
            questionList.forEach((_, optionIndex) => {
                const selectedOptionValue = $(`#question${problemIndex} input[name="radio${optionIndex}"]:checked`).val();
                if (selectedOptionValue) {
                    selectedOptionValues.push(selectedOptionValue);
                }
            });

            const answer = {
                problemIndex: problemIndex,
                answerType: 'matrix',
                selectedOptions: selectedOptionValues,
            };
            formData.answers.push(answer);
            console.log(formData);
        } else if (problemType === '5') {
            // Logic for gaugeView
            // Modify this part according to your requirements
        }
    });

    console.log(formData);
}