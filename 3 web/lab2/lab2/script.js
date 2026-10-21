const formXYZ = document.getElementById("inputXYZform");

formXYZ.addEventListener("submit", async (e) => {
    clearError();
    e.preventDefault();

    let x, y, r;

    var errorText = "";


    const selectedX = document.getElementById("x");
    if (selectedX == null) {
        errorText += "Х не выбран.\n"
    } else {
        x = Number(selectedX.value);
        const allowedX = [-3, -2, -1, 0, 1, 2, 3, 4, 5];

        if (!allowedX.includes(x)) {
            errorText += "Некорректное значение X.\n"
        }
    }

    const selectedY = document.getElementById("y");
    y = selectedY.value.replace(",", ".");
    if (!isNumber(y) || hasChars(y)) {
        errorText += "Y не число.\n"
    } else {
        y = Number(y);
        if (y < -5 || y > 5) {
            errorText += "Y не входит в диапазон [-5, 5].\n"
        }
    }

    const selectedR = document.getElementById("r");

    if (selectedR == null) {
        errorText += "R не выбран.\n"
    } else {
        r = Number(selectedR.value);
        const allowedR = [1, 2, 3, 4, 5];

        if (!allowedR.includes(r)) {
            errorText += "Некорректное значение R.\n"
        }
    }


    if (errorText != "") {
        writeError(errorText);
        return;
    }


    const submitButton = formXYZ.querySelector('input[type="submit"]');
    submitButton.disabled = true;
    try {
        const body = new URLSearchParams();

        body.append("x", x);
        body.append("y", y);
        body.append("r", r);

        const response = await fetch("/api/check", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: body
        });
        const data = await response.json();

        console.log(data.result);

        if (!response.ok) {
            writeError(data.error ?? "Ошибка сервера");
            return;
        }

        writeTable(data.resultsList);

        if (data.result.hit) {
            console.log("Попадание в область");
        } else {
            console.log("Miss");
        }

    } catch (error) {
        console.error("Ошибка при отправке запроса:", error);
        writeError("Ошибка при отправке запроса");
    } finally {
        submitButton.disabled = false;
    }

});


function isNumber(a) {
    if (isFinite(a) && !isNaN(a) && a.trim() != "") {
        return true;
    }
    return false;
}

function hasChars(s) {
    const chars = "0123456789-.,";
    for (let i = 0; i < s.length; i++) {
        if (!chars.includes(s.charAt(i))) {
            return true;
        }
    }

    return false;
}

function writeError(s) {
    const err = document.getElementById("error_message");
    err.innerText = s;
}

function clearError() {
    const err = document.getElementById("error_message");
    err.innerText = "";
}

function writeTable(arr) {
    const table = document.getElementById("result-table");

    table.innerHTML = `
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Результат</th>
            <th>Текущее время</th>
            <th>Время выполнения</th>
        </tr>
    `;

    for (const result of arr) {
        const row = table.insertRow();

        const cellX = row.insertCell();
        const cellY = row.insertCell();
        const cellR = row.insertCell();
        const cellHit = row.insertCell();
        const cellCurrentTime = row.insertCell();
        const cellExecutionTime = row.insertCell();

        cellX.textContent = result.x;
        cellY.textContent = result.y;
        cellR.textContent = result.r;

        cellHit.textContent =
            result.hit ? "Попадание" : "Промах";

        cellCurrentTime.textContent =
            result.currentTime;

        cellExecutionTime.textContent =
            `${result.scriptTime.toFixed(3)} мс`;
    }
}