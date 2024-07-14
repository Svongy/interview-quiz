function checkUncheckAll(source, checkboxName) {
    const checkboxes = document.querySelectorAll(`input[type="checkbox"][name="${checkboxName}"]`);
    checkboxes.forEach(checkbox => {
        checkbox.checked = source.checked;
    });
}

function checkAnswers(source) {
    const parent = source.closest(`.form-container`);
    const answerText = parent.querySelector(`details div`).textContent.toLowerCase();
    const options = parent.querySelectorAll(`.options-container input`);
    const answerParts = answerText.split(';').map(part => part.trim());
    const details = parent.querySelector('details');
    const isOpen = details.getAttribute(`open`) == null;

    options.forEach(option => {
        if (isOpen) {
            if (option.checked) {
                const matches = answerParts.some(answerPart => {
                    return option.value.toLowerCase().includes(answerPart);
                });

                if (matches) {
                    option.parentNode.classList.add('checkmark');
                } else {
                    option.parentNode.classList.add('cross');
                }
            } 
        } else {
            options.forEach(option => {
                option.parentNode.classList.remove('checkmark', 'cross');
            });
        }
    });
}

function filterQuestions(source, value) {
    const buttons = document.querySelectorAll('.selected');
    buttons.forEach(btn => btn.classList.remove('selected'));
    source.classList.add('selected');

    const questionsContainer = document.querySelector('.questions-container');
    const questions = Array.from(document.querySelectorAll('.question'));

    const shuffledQuestions = shuffleArray(questions);

    shuffledQuestions.forEach((question, index) => {        
        let displayStyle = 'none';

        if (value === 'NEW') {
            const isNewQuestion = question.querySelector('.new-question-text') !== null;
            displayStyle = isNewQuestion ? 'inherit' : 'none';
        } else if (value === 'SEEN ON INTERVIEW') {
            const seenOnInterviewQuestion = question.querySelector('span[data-seen-value="true"]') !== null;
            displayStyle = seenOnInterviewQuestion ? 'inherit' : 'none';
        } else if (value === 'ALL') {
            displayStyle = 'inherit';
        } else {
            const questionsNumber = parseInt(value);
            displayStyle = index < questionsNumber ? 'inherit' : 'none';
        }

        question.style.display = displayStyle;
        questionsContainer.appendChild(question);
    });
}

function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

function toggleStatisticBox() {
    var statisticBox = document.querySelector(".statistics");
    statisticBox.classList.toggle("expanded");
}

function filterQuestionAttribures(source) {
    var filterValue = source.value.toLowerCase();
    var checkboxes = document.querySelectorAll(`.checkbox-tag input`);

    checkboxes.forEach(function (checkbox) {
        var checkboxValue = checkbox.value.toLowerCase();
        var checkboxLabel = checkbox.parentElement;

        checkboxLabel.style.display = checkboxValue.includes(filterValue) ? 'inline' : 'none';    
    });
}

function clearSearch() {
    var searchField = document.getElementById('input-search'); 
    searchField.value = '';
    filterQuestionAttribures(searchField);
}

if (window.location.pathname.includes('/build')) {
    document.addEventListener('keydown', function(event) {
    // handle 'Esc' button press
        if (event.keyCode === 27) { 
            clearSearch();
        }
    });
}