//AJUSTAR CANVAS A PANTALLA
var CANVAS_WIDTH = window.innerWidth - 100;
var CANVAS_HEIGHT = window.innerHeight - 150;
var CANVAS_MIDDLE_WIDTH = CANVAS_WIDTH / 2;
var CANVAS_MIDDLE_HEIGHT = CANVAS_HEIGHT / 2;

document.getElementById('c').width = CANVAS_WIDTH;
document.getElementById('c').height = CANVAS_HEIGHT;

//Elementos de la pantalla con actualizaciones constantes
var mensaje = document.getElementById('mensaje');
var currentEq = document.getElementById('current');

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

//IDs de los botones en orden
var optionsId = ['addX', 'addUnit', 'subX', 'subUnit'];
//Colores de los botones en orden, para identificación
var rectangles = ['blue', 'green', 'red', 'purple'];
//Botón seleccionado actualmente
var selectedOpt = 0;
/**
 * Proceso de la resolución por pasos
 * 0 para representar la equación
 * 1 para resolver
 * 2 para terminado
 * @type Number
 */
var step = 0;

//Fabric init
var canvas = new fabric.Canvas('c');
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

function expressionToString(x, u){
    var strX = "";
    var op = "";
    var strU ="";
    if(x == 0){
        strX = "";
    }else if(x == 1){
        strX = "X";
    }else if(x == -1){
        strX = "-X";
    }else{
        strX = x + "X";
    }
    
    if(u > 0){
        op = x == 0 ? "" : " + ";
        strU = u;
    }
    else if(u < 0){
        op = " - ";
        strU = -u;
    }
    else{
        op = "";
        strU = x == 0? "0" : "";
    }
    return strX + op + strU;
}
//Convierte la ecuación del objeto definido a su representación en cadena
function equationToString(eq) {
    var exprLeft = expressionToString(eq.xLeft, eq.uLeft);
    var exprRight = expressionToString(eq.xRight, eq.uRight);
    var exprBottom = expressionToString(eq.xBottom, eq.uBottom);
    if (step == 0) {
        //Compara las ecuaciones
        if (areEquivalent(current, goal)) {
            mensaje.innerHTML = "<span style='color: green'>&iexcl;Genial! ahora sustituye el valor en la ecuaci&oacute;n</span>";
            step = 1;
        }
    } else if (step == 1) {
        if (isSolved(current)) {
            mensaje.innerHTML = "<span style='color: green'>&iexcl;Perfecto!</span>";
            step = 2;
        }
    }

    return exprLeft + " = " + exprRight + "<br />" + exprBottom;
}

//Cambia el botón activo
function changeOption(newOption) {
    document.getElementById(optionsId[selectedOpt]).classList.toggle('active');
    document.getElementById(optionsId[newOption]).classList.toggle('active');

    selectedOpt = newOption;
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
}

//Listeners para detectar cambios en el canvas

var _isMouseDown = false;
var _isDragging = false;
var _isObjectSelected = false;

function deleteObject() {
    var activeObject = canvas.getActiveObject();
    if (activeObject.type === 'activeSelection') {
        var objects = activeObject.getObjects();
        for(var i = 0; i<objects.length; i++){
            canvas.remove(objects[i]);
        }
        canvas.discardActiveObject().renderAll();
    } else{
        canvas.remove(activeObject);
    }
    updateCurrentEquation();
}

//Borra el canvas
function removeAll() {
    if (confirm('¿Estas seguro?')) {
        var objects = canvas.getObjects();
        while (objects.length != 0) {
            canvas.remove(objects[0]);
        }
        
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

canvas.on('object:selected', function (event) {
    _isObjectSelected = true;
});

canvas.on('before:selection:cleared', function () {
    _isObjectSelected = false;
});

canvas.on('object:modified', function (event) {
    updateCurrentEquation();
});

canvas.on('mouse:down', function () {
    _isMouseDown = true;
});

canvas.on('mouse:move', function () {
    _isDragging = _isMouseDown;
    // other stuff
});

canvas.on('mouse:up', function (event) {
    _isMouseDown = false;
    var wasDragging = _isDragging;
    _isDragging = false;

    //Si estaba arrastrando el mouse no se agrega el rectangulo
    if (!wasDragging && !_isObjectSelected) {
        var pointer = canvas.getPointer(event.e);
        var x = pointer.x;
        var y = pointer.y;

        newRect = new fabric.Rect({
            left: x,
            top: y,
            fill: rectangles[selectedOpt],
            width: 50,
            height: 50
        });
        newRect.hasControls = false;
        canvas.add(newRect);

        updateCurrentEquation();
    }   
});
//Detecta si se presiono supr para borrar un tile
document.addEventListener("keydown", function (e) {
    var keynum;

    if (window.event) { // IE                    
        keynum = e.keyCode;
    } else if (e.which) { // Netscape/Firefox/Opera                   
        keynum = e.which;
    }
    if (keynum == 46) {
        deleteObject();
    }
});