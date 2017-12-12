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

//IDs de los botones en orden
var optionsId = ['addX', 'addUnit', 'subX', 'subUnit'];
//Colores de los botones en orden, para identificación
var rectangles = ['blue', 'green', 'red', 'purple'];
//Botón seleccionado actualmente
var selectedOpt = 0;
//Estado del ejercicio
var step = 0;

//Fabric init
var canvas = new fabric.Canvas('c');

//Listeners para detectar cambios en el canvas
var _isMouseDown = false;
var _isDragging = false;
var _isObjectSelected = false;
var _wasObjectSelected = false;

canvas.on('selection:created', function (event) {
    _isObjectSelected = true;
});

canvas.on('before:selection:cleared', function () {
    _isObjectSelected = false;
    _wasObjectSelected = true;
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

    if (!wasDragging && !_isObjectSelected && !_wasObjectSelected) {
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
    if(_wasObjectSelected){
        updateCurrentEquation();
        _wasObjectSelected = false;
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

function save(){
    if(step == 2){
        document.getElementById('saveData:jsondata').value = JSON.stringify(canvas.toJSON());
        return true;
    }else{
        alert('Aun no has terminado');
        return false;
    }
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

//Cambia el botón activo
function changeOption(newOption) {
    document.getElementById(optionsId[selectedOpt]).classList.toggle('active');
    document.getElementById(optionsId[newOption]).classList.toggle('active');

    selectedOpt = newOption;
}

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
    _isObjectSelected = false;
    _wasObjectSelected = false;
    updateCurrentEquation();
}

function clearCanvas(){
    var objects = canvas.getObjects();
    while (objects.length != 0) {
        canvas.remove(objects[0]);
    }
    _isObjectSelected = false;
    _wasObjectSelected = false;
}