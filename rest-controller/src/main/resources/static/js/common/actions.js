function checkUncheckAll(source, checkboxName) {
    const checkboxes = document.querySelectorAll(`input[type="checkbox"][name="${checkboxName}"]`);
    checkboxes.forEach(checkbox => {
        checkbox.checked = source.checked;
    });
}

function checkAnswers(source) {
    const parent = source.closest(`.form-container`);
    const answerText = parent.querySelector(`details p`).textContent.toLowerCase();
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