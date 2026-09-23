const formXYZ = document.getElementById("inputXYZform");
var storage = JSON.parse(localStorage.getItem('storage'));
if (storage == null) {
    storage = [];
}
writeTable(storage);
function writeTable(arr) {
    const table = document.getElementById("result-table");
    table.innerHTML = "<tr>\n" +
        "        <td>\n" +
        "            <div id=\"table_x\">X</div>\n" +
        "        </td>\n" +
        "        <td>\n" +
        "            <div id=\"table_y\">Y</div>\n" +
        "        </td>\n" +
        "        <td>\n" +
        "            <div id=\"table_r\">R</div>\n" +
        "        </td>\n" +
        "        <td>\n" +
        "            <div id=\"table_hit\">Результат</div>\n" +
        "        </td>\n" +
        "        <td>\n" +
        "            <div id=\"table_time\">Время :)</div>\n" +
        "        </td>\n" +
        "    </tr>";
    for (var i = 0; i < arr.length; i++) {
        const row = table.insertRow();
        const cellX = row.insertCell(0);
        const cellY = row.insertCell(1);
        const cellR = row.insertCell(2);
        const cellHit = row.insertCell(3);
        const cellTime = row.insertCell(4);
        cellX.innerHTML = arr[i].x;
        cellY.innerHTML = arr[i].y;
        cellR.innerHTML = arr[i].r;
        cellHit.innerHTML = arr[i].hit ? "Попадание" : "Промах";
        cellTime.innerHTML = new Date(arr[i].time).toLocaleString("ru-RU")
    }
}
formXYZ.addEventListener("submit", (e) => {
    clearError();
    e.preventDefault();
    var x,y,r;
    var errorText = "";
    const selectedX = document.querySelector("input[name='x']:checked");

    if (selectedX == null) {
        console.log("X не выбран");
        errorText += "Х не выбран.\n"
    } else {
        x = Number(selectedX.value);
        console.log(x);
    }
    const arr = [-4,-3,-2,-1,0,1,2,3,4];
    let flag = false;
    for (const el of arr) {
        if (x === el) {
            flag = true;
            break;
        }
    }
    if (!flag) {
        errorText += "Х не попал."
    }
    const selectedY = document.getElementById("y");
    y = selectedY.value.replace(",",".");
    if (!isNumber(y) || hasChars(y)) {
        console.log("Y не число");
        errorText += "Y не число.\n"
    }
    const temp_y = y;
    y = Number(y);
    if (y < -3 || y > 5) {
        console.log("Y не входит в диапазон [-3, 5]");
        errorText += "Y не входит в диапазон [-3, 5].\n"
    } else {
        console.log(y);
    }

    const selectedR = document.querySelector("input[name='r']:checked");

    if (selectedR == null) {
        console.log("R не выбран");
        errorText += "R не выбран.\n"
    } else {
        r = Number(selectedR.value);
        console.log(r);
    }
    if (errorText != "") {
        writeError(errorText);
        return;
    } else {
        var t = checkHit(x,y,r);
        if (t) {
            alert("Попадание в область");
        } else {
            alert("Промах");
        }
    }

    var result = {
        x: x,
        y: temp_y,
        r: r,
        hit: t,
        time: Date.now()
    }
    storage.push(result);
    localStorage.setItem('storage', JSON.stringify(storage));
    writeTable(storage);

});

function checkHit(x,y,r) {
    const circle = (x >= 0 && y >= 0) && ((x*x+y*y) <= (r*r));
    const rect = (x >= 0 && y <= 0) && (x <= r && y >= -r/2);
    const triangle = (x <= 0 && y <= 0) && (-2*x - r <= y);
    if (circle || rect ||triangle) {
        return true;
    }
    return false;
}

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

function clearStorage() {
    localStorage.removeItem('storage');
    localStorage.clear();
    console.log("done");
    storage = [];
    writeTable(storage);
}

document.getElementById("asd").textContent = "123";