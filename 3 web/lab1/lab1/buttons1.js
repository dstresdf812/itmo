setInterval(() => {
    const buttons = document.querySelectorAll('input[type="radio"]:checked');
    console.log(buttons);
    buttons.forEach(button => {
        if (!button.matches(':hover')) {
        button.style.accentColor = '#' + Math.floor(Math.random() * 16777215).toString(16).padStart(6, '0');
    } else {
            button.style.accentColor = 'blueviolet';
        }
    });
}, 600);