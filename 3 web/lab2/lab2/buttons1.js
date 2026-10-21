setInterval(() => {
    const buttons = document.querySelectorAll('input[type="radio"]:checked');
    buttons.forEach(button => {
        if (!button.matches(':hover')) {
        button.style.accentColor = '#' + Math.floor(Math.random() * 16777215).toString(16).padStart(6, '0');
    // } else {
    //         button.style.accentColor = 'blueviolet';
        }
    });
}, 600);

addEventListener("mouseover", (e) => {
    const button = e.target;
    button.style.accentColor = 'blueviolet';
})

addEventListener("mouseout", (e) => {
    const button = e.target;
    button.style.accentColor = '#' + Math.floor(Math.random() * 16777215).toString(16).padStart(6, '0');
})