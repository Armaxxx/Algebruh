//Ecuación objetivo
var goal = {xLeft: 0, uLeft: 0, xRight: 0, uRight: 0};
//Solución
var solution = 0;
//Ecuación actual, actualizada mediante cambios en el canvas
var current = {xLeft: 0, uLeft: 0, xRight: 0, uRight: 0};
currentEq.innerHTML = equationToString(current);

function init(xL, uL, xR, uR, sol) {
    goal.xLeft = xL;
    goal.uLeft = uL;
    goal.xRight = xR;
    goal.uRight = uR;
    solution = sol;
    document.getElementById('goal').innerHTML = equationToString(goal);
}

var separator = new fabric.Line([CANVAS_MIDDLE_WIDTH, 0, CANVAS_MIDDLE_WIDTH, CANVAS_HEIGHT], {
    fill: 'black',
    stroke: 'black',
    strokeWidth: 5,
    selectable: false
});
canvas.add(separator);

//Evalúa si dos ecuaciones son equivalentes
function areEquivalent(eq1, eq2) {
    return 	eq1.xLeft == eq2.xLeft
            && eq1.uLeft == eq2.uLeft
            && eq1.xRight == eq2.xRight
            && eq1.uRight == eq2.uRight
}

//Evalúa si la ecuación ya está resuelta
function isSolved(eq) {
    return 	eq.xLeft == 1
            && eq.uLeft == 0
            && eq.xRight == 0
            && eq.uRight == solution;
}

//Convierte la ecuación del objeto definido a su representación en cadena
function equationToString(eq) {
    return expressionToString(eq.xLeft, eq.uLeft)
            + " = " 
            + expressionToString(eq.xRight, eq.uRight);
}

//Actualiza la ecuación, de acuerdo a cambios en el canvas
function updateCurrentEquation(x) {
    var qty = [1,1,1,-1,-1,-1];
    var values = [[0,0],[0,0]];
    var objects = canvas.getObjects();
    for(var i=1; i<objects.length; i++){
        var tile = objects[i];
        var zone = tile.get('left') < CANVAS_MIDDLE_WIDTH ? 0 : 1;
        var index = rectangles.indexOf(tile.get('fill'));
        values[zone][1-(index%3)] += qty[index];
    }
    current.xLeft = values[0][0];
    current.uLeft = values[0][1];
    current.xRight = values[1][0];
    current.uRight = values[1][1];
    currentEq.innerHTML = equationToString(current);
    
    if (step == 0) {
        //Compara las ecuaciones
        if (areEquivalent(current, goal)) {
            mensaje.innerHTML = "<span style='color: green'>&iexcl;Genial! Ahora resu&eacute;lvela</span>";
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
        currentEq.innerHTML = equationToString(current);
        canvas.add(separator);
    }
}