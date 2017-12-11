//Ecuación objetivo
var goal = {xLeft: 0, uLeft: 0, xRight: 0, uRight: 0, xBottom: 0, uBottom: 0};
//Solución
var solution = 0;
//Ecuación actual, actualizada mediante cambios en el canvas
var current = {xLeft: 0, uLeft: 0, xRight: 0, uRight: 0, xBottom: 0, uBottom: 0};
currentEq.innerHTML = equationToString(current);

function init(uR, xB, uB, sol) {
    goal.xLeft = 1;
    goal.uLeft = 0;
    goal.xRight = 0;
    goal.uRight = uR;
    goal.xBottom = xB;
    goal.uBottom = uB;
    solution = sol;
    document.getElementById('goal').innerHTML = equationToString(goal);
}

var separator_v = new fabric.Line([CANVAS_MIDDLE_WIDTH, 0, CANVAS_MIDDLE_WIDTH, CANVAS_MIDDLE_HEIGHT], {
    fill: 'black',
    stroke: 'black',
    strokeWidth: 5,
    selectable: false
});
canvas.add(separator_v);
var separator_h = new fabric.Line([0, CANVAS_MIDDLE_HEIGHT, CANVAS_WIDTH, CANVAS_MIDDLE_HEIGHT], {
    fill: 'black',
    stroke: 'black',
    strokeWidth: 5,
    selectable: false
});
canvas.add(separator_h);

//Evalúa si dos ecuaciones son equivalentes
function areEquivalent(eq1, eq2) {
    return      eq1.xLeft == eq2.xLeft
            &&  eq1.uLeft == eq2.uLeft
            &&  eq1.xRight == eq2.xRight
            &&  eq1.uRight == eq2.uRight
            &&  eq1.xBottom == eq2.xBottom
            &&  eq1.uBottom == eq2.uBottom;
}

//Evalúa si la ecuación ya está resuelta
function isSolved(eq) {
    return 	eq.xLeft == goal.xLeft
            &&  eq.uLeft == goal.uLeft
            &&  eq.xRight == goal.xRight
            &&  eq.uRight == goal.uRight
            &&  eq.xBottom == 0
            &&  eq.uBottom == solution;
}

//Convierte la ecuación del objeto definido a su representación en cadena
function equationToString(eq) {
    var exprLeft = expressionToString(eq.xLeft, eq.uLeft);
    var exprRight = expressionToString(eq.xRight, eq.uRight);
    var exprBottom = expressionToString(eq.xBottom, eq.uBottom);

    return exprLeft + " = " + exprRight + "<br />" + exprBottom;
}

//Actualiza la ecuación, de acuerdo a cambios en el canvas
function updateCurrentEquation() {
    var qty = [1,1,-1,-1];
    var values = [[0,0],[0,0],[0,0]];
    var objects = canvas.getObjects();
    var i;
    for(i=2; i< objects.length; i++){
        var zone = -1;
        var tile = objects[i];
        if(tile.get('top') < CANVAS_MIDDLE_HEIGHT){
            if(tile.get('left') < CANVAS_MIDDLE_WIDTH ) 
                zone = 0;
            else
                zone = 1;
        }else
            zone = 2;
        var index = rectangles.indexOf(tile.get('fill'));
        values[zone][index%2] += qty[index];
    }
    current.xLeft = values[0][0];
    current.uLeft = values[0][1];
    current.xRight = values[1][0];
    current.uRight = values[1][1];
    current.xBottom = values[2][0];
    current.uBottom = values[2][1];
    currentEq.innerHTML = equationToString(current);
    
    if (step == 0) {
        //Compara las ecuaciones
        if (areEquivalent(current, goal)) {
            mensaje.innerHTML = "<span style='color: green'>&iexcl;Genial! ahora sustituye el valor en la ecuaci&oacute;n</span>";
            step = 1;
        }
    } else if (step == 1) {
        if (isSolved(current)) {
            mensaje.innerHTML = "<span style='color: green'>&iexcl;Terminaste! Ahora puedes guardar</span>";
            step = 2;
        }
    }
}

//Borra el canvas
function removeAll() {
    if (confirm('&iquest;Estas seguro?')) {
        clearCanvas();
        
        current.xLeft = 0;
        current.uLeft = 0;
        current.xRight = 0;
        current.uRight = 0;
        current.xBottom = 0;
        current.uBottom = 0;
        currentEq.innerHTML = equationToString(current);
        canvas.add(separator_v);
        canvas.add(separator_h);
    }
}