const canvas = document.getElementById("canvas");
const ctx = canvas.getContext("2d");
const R = 200;
// ctx.fillRect(0, 0, canvas.width, canvas.height);
ctx.beginPath();
ctx.fillStyle = "#3588f6";
ctx.moveTo(canvas.width/2-R/2,canvas.height/2);
ctx.lineTo(canvas.width/2,canvas.height/2+R);
ctx.lineTo(canvas.width/2,canvas.height/2);

ctx.moveTo(canvas.width/2,canvas.height/2);
ctx.lineTo(canvas.width/2,canvas.height/2+R/2);
ctx.lineTo(canvas.width/2+R,canvas.height/2+R/2);
ctx.lineTo(canvas.width/2+R,canvas.height/2);

ctx.arc(canvas.width/2,canvas.height/2,R,0,-Math.PI/2,1);
ctx.fill();
ctx.closePath();

ctx.fillStyle = "#000";
ctx.beginPath();
ctx.moveTo(canvas.width/2, canvas.height);
ctx.lineTo(canvas.width/2, 0);
ctx.stroke();
ctx.closePath();


ctx.beginPath();
ctx.moveTo(canvas.width, canvas.height/2);
ctx.lineTo(0, canvas.height/2);
ctx.stroke();
ctx.closePath();



const size = 8;
ctx.font = "14px sans-serif";
draw_mark(size,canvas.width/2+R/2,canvas.height/2,0,"R/2");
draw_mark(size,canvas.width/2+R,canvas.height/2,0,"R");
draw_mark(size,canvas.width/2-R,canvas.height/2,0,"-R");
draw_mark(size,canvas.width/2-R/2,canvas.height/2,0,"-R/2");


draw_mark(size,canvas.width/2,canvas.height/2+R/2,1,"-R/2");
draw_mark(size,canvas.width/2,canvas.height/2+R,1,"-R");
draw_mark(size,canvas.width/2,canvas.height/2-R/2,1,"R/2");
draw_mark(size,canvas.width/2,canvas.height/2-R,1,"R");
function draw_mark(size,x,y,R_direction, text) {
    ctx.beginPath();
    ctx.moveTo(x,y);
    ctx.lineTo(x+size,y);
    ctx.lineTo(x-size,y);
    ctx.lineTo(x,y);
    ctx.lineTo(x,y+size);
    ctx.lineTo(x,y-size);
    ctx.stroke();
    ctx.closePath();
    if (R_direction==1){
        ctx.fillText(text,x-30,y);
    } else {
        ctx.fillText(text,x,y+20);
    }
}

const arrowSize = 10;

ctx.beginPath();
ctx.moveTo(canvas.width/2, arrowSize);
ctx.lineTo(canvas.width/2 - 5, arrowSize + 10);

ctx.moveTo(canvas.width/2, arrowSize);
ctx.lineTo(canvas.width/2 + 5, arrowSize + 10);

ctx.stroke();
ctx.closePath();

ctx.beginPath();
ctx.moveTo(canvas.width - arrowSize, canvas.height/2);
ctx.lineTo(canvas.width - arrowSize - 10, canvas.height/2 - 5);

ctx.moveTo(canvas.width - arrowSize, canvas.height/2);
ctx.lineTo(canvas.width - arrowSize - 10, canvas.height/2 + 5);

ctx.stroke();
ctx.closePath();

ctx.font = "16px sans-serif";
ctx.fillStyle = "#000";

ctx.fillText("Y", canvas.width/2 + 8, 18);
ctx.fillText("X", canvas.width - 20, canvas.height/2 - 10);