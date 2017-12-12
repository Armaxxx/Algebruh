//Ecuación objetivo
var goal = {xLeft: 0, uLeft: 0, xRight: 0, uRight: 0};
//Solución
var solution = {squared: 0, lineal: 0, constant: 0};
//Ecuación actual, actualizada mediante cambios en el canvas
var current = {xLeft: 0, uLeft: 0, xRight: 0, uRight: 0, squared: 0, lineal: 0, constant: 0};
currentEq.innerHTML = equationToString(current);

function init(xL, uL, xR, uR, s, l, c) {
    goal.xLeft = xL;
    goal.uLeft = uL;
    goal.xRight = xR;
    goal.uRight = uR;
    solution.squared = s;
    solution.lineal = l;
    solution.constant = c;
    document.getElementById('goal').innerHTML = equationToString(goal);
}

var separator_v = new fabric.Line([100, 0, 100, CANVAS_HEIGHT], {
    fill: 'black',
    stroke: 'black',
    strokeWidth: 5,
    selectable: false
});
canvas.add(separator_v);
var separator_h = new fabric.Line([0, 100, CANVAS_WIDTH, 100], {
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
            &&  eq1.uRight == eq2.uRight;
}

//Evalúa si la ecuación ya está resuelta
function isSolved(eq) {
    return 	areEquivalent(goal, eq)
            &&  eq.squared == solution.squared
            &&  eq.lineal == solution.lineal
            &&  eq.constant == solution.constant;
}

function cuadraticExpressionToString(s, l, c){
    var sStr = "";
    var op1 = "";
    var lStr = "";
    var op2 = "";
    var cStr = "";
    if(s == 1){
        sStr = "X&sup2;";
    }else if(s == -1){
        sStr = "-X&sup2;";
    }else if(s != 0){
        sStr = s + "X&sup2;";
    }
    
    if(l == 1){
        if(s != 0){
            op1 = " + ";
        }
        lStr = "X";
    }else if(l == -1){
        op1 = " -";
        lStr = "X";
    }else if(l < 0){
        if(s != 0){
            op1 = " - ";
            l *= -1;
            lStr = l + "X"
        }else{
            lStr = l+"X";
        }
    }else if(l > 0){
        if(s != 0){
            op1 = " + ";
        }
        lStr = l + "X";
    }
    
    if(c == 0 && s == 0 && l == 0){
        cStr = "0";
    }else if(c < 0){
        if(s == 0 && l == 0){
            cStr = c;
        }else{
            op2 = " - ";
            cStr = c*(-1);
        }
    }else if(c > 0){
        if(s != 0 || l != 0){
            op2 = " + ";
        }
        cStr = c;
    }
    
    return sStr + op1 + lStr + op2 + cStr;
}

//Convierte la ecuación del objeto definido a su representación en cadena
function equationToString(eq) {
    var exprLeft = expressionToString(eq.xLeft, eq.uLeft);
    var exprRight = expressionToString(eq.xRight, eq.uRight);

    var finalExpr = "(" + exprLeft + ")(" + exprRight + ")";
    if(eq == current && step == 1){
        finalExpr += " = " + cuadraticExpressionToString(eq.squared, eq.lineal, eq.constant);
    }
    return  finalExpr;
}

//Actualiza la ecuación, de acuerdo a cambios en el canvas
function updateCurrentEquation() {
    var qty = [1,1,1,-1,-1,-1];
    var values = [[0,0],[0,0],[0,0,0]];
    var objects = canvas.getObjects();
    var i;
    var misplaced = false;
    for(i=2; i< objects.length; i++){
        var zone = -1;
        var tile = objects[i];
        var top = tile.get('top');
        var left = tile.get('left');
        if(top > 100 && left < 100)
            zone = 0;
        else if(top < 100 && left > 100)
            zone = 1;
        else if(top > 100 && left > 100)
            zone = 2;
        
        var qtyIndex = rectangles.indexOf(tile.get('fill'));
        if(zone == 0 || zone == 1){
            var index = 1 - (qtyIndex%3);
            if(index >= 0)
                values[zone][index] += qty[qtyIndex];
            else
                misplaced = true;
        }else if(zone == 2){
            var index = 2 - (qtyIndex%3);
            values[zone][index] += qty[qtyIndex];
        }else
            misplaced = true;
    }
    if(misplaced){
        alert('Algunos elementos no estan en la zona correcta. Corrigelo')
    }
    current.xLeft = values[0][0];
    current.uLeft = values[0][1];
    current.xRight = values[1][0];
    current.uRight = values[1][1];
    current.squared = values[2][0];
    current.lineal = values[2][1];
    current.constant = values[2][2];
    currentEq.innerHTML = equationToString(current);
    
    if (step == 0) {
        //Compara las ecuaciones
        if (areEquivalent(current, goal)) {
            mensaje.innerHTML = "<span style='color: green'>&iexcl;Genial! ahora expande la expresi&oacute;n</span>";
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
        current.squared = 0;
        current.lineal = 0;
        current.constant = 0;
        currentEq.innerHTML = equationToString(current);
        canvas.add(separator_v);
        canvas.add(separator_h);
    }
}