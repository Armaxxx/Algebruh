var CANVAS_WIDTH = 800;
var CANVAS_HEIGHT = 400;
var CANVAS_MIDDLE = 400;
var mensaje = document.getElementById('mensaje');

var goal = {xLeft: 1, uLeft: 2, xRight: 0, uRight: 3};
var solution = 1;
var current = {xLeft: 0, uLeft: 0, xRight: 0, uRight: 0};
document.getElementById('goal').innerHTML = equationToString(goal);
document.getElementById('current').innerHTML = equationToString(current);


var optionsId = ['addX', 'addUnit', 'subX', 'subUnit'];
var rectangles = ['blue', 'green', 'red', 'purple'];
var selectedOpt = 0;
var step = 0; //paso del ejercicio 0 para representación, 1 para despeje.

var canvas = new fabric.Canvas('c');
canvas.selection = false;
var separator =  new fabric.Line([CANVAS_MIDDLE, 0, CANVAS_MIDDLE, CANVAS_HEIGHT], {
		fill: 'black',
		stroke: 'black',
		strokeWidth: 5,
		selectable: false
});
canvas.add(separator);

function areEquivalent(eq1, eq2){
	return 	eq1.xLeft == eq2.xLeft
		&&	eq1.uLeft == eq2.uLeft
		&&	eq1.xRight == eq2.xRight
		&&	eq1.uRight == eq2.uRight
}

function isSolved(eq){
	return 	eq.xLeft == 1
		&&	eq.uLeft == 0
		&&	eq.xRight == 0
		&&	eq.uRight == solution;
}

function equationToString(eq){
	var strXL = '';
	var strXR = '';
	if(eq.xLeft == 0){
		strXL = '';
	}else if(eq.xLeft == 1){
		strXL = 'X';
	}else if(eq.xLeft == -1){
		strXL = '-X';
	}else{
		strXL = eq.xLeft + 'X';
	}
	if(eq.xRight == 0){
		strXR = '';
	}else if(eq.xRight == 1){
		strXR = 'X';
	}else if(eq.xRight == -1){
		strXR = '-X';
	}else{
		strXR = eq.xRight + 'X';
	}
	var opL = '';
	if(eq.xLeft == 0 && eq.uLeft < 0 ){
		opL = '-';
	}
	else if(eq.xLeft != 0 && eq.uLeft != 0){
		opL = eq.uLeft > 0 ? '+' : '-';
	}
	var opR = '';
	if(eq.xRight == 0 && eq.uRight < 0 ){
		opR = '-';
	}
	else if(eq.xRight != 0 && eq.uRight != 0){
		opR = eq.uRight > 0 ? '+' : '-';
	}
	var uLeft = '';
	if(eq.uLeft == 0 && eq.xLeft == 0){
		uLeft = '0';
	}else if(eq.uLeft != 0){
		uLeft = eq.uLeft < 0 ? eq.uLeft*(-1) : eq.uLeft;
	} 
	var uRight = '';
	if(eq.uRight == 0 && eq.xRight == 0){
		uRight = '0';
	}else if(eq.uRight != 0){
		uRight = eq.uRight < 0 ? eq.uRight*(-1) : eq.uRight;
	} 
	if(step == 0){
		//Compara las ecuaciones
		if(areEquivalent(current, goal)){
			mensaje.innerHTML = "<span style='color: green'>!Genial! ahora resuévela</span>";
			step = 1;
		}
	}else if(step == 1){
		if(isSolved(current)){
			mensaje.innerHTML = "<span style='color: green'>¡Perfecto!</span>";
			step = 2;
		}
	}

	return strXL + ' ' + opL + ' ' + uLeft + ' = ' + strXR + ' ' + opR + ' ' + uRight;
}

function changeOption(newOption){
	document.getElementById(optionsId[selectedOpt]).classList.toggle('active');
	document.getElementById(optionsId[newOption]).classList.toggle('active');

	selectedOpt = newOption;
}

function updateCurrentEquation(x){
	//lado izquierdo de la igualdad
	if(x <= CANVAS_MIDDLE){
		switch(selectedOpt){
			case 0: //sumar X
				current.xLeft++;
				break;
			case 1: //sumar unidad
				current.uLeft++;
				break;
			case 2: //restar X
				current.xLeft--;
				break;
			case 3: //restar unidad
				current.uLeft--;
				break;
		}
	}
	//lado derecho de la igualdad
	else{
		switch(selectedOpt){
			case 0: //sumar X
				current.xRight++;
				break;
			case 1: //sumar unidad
				current.uRight++;
				break;
			case 2: //restar X
				current.xRight--;
				break;
			case 3: //restar unidad
				current.uRight--;
				break;
		}
	}
	document.getElementById('current').innerHTML = equationToString(current);
}

var _isMouseDown = false;
var _isDragging = false;
var _isObjectSelected = false;
//guarda el lado de la ecuación actual del objeto antes de moverlo. True para izquierdo. False para derecho.
var previousSide = true;

function moveObject(object, side){
	//Arreglo de tipos opuestos
	var oppositeTypes = [2, 3, 0, 1];
	//Guardamos para más tarde
	var tmpOpt = selectedOpt;
	//Lado de la ecuacion
	selectedOpt = rectangles.indexOf(object.get('fill'));
	var x = side ? 200 : 600;
	//actualizamos el primer lado
	updateCurrentEquation(x);
	//seleccionamos el otro lado
	selectedOpt = oppositeTypes[selectedOpt];
	x = side ? 600 : 200;
	//actualizamos el segundo lado
	updateCurrentEquation(x);

	//Dejamos la opción que estaba elegida
	selectedOpt = tmpOpt;
}

function deleteObject(object){
	var oppositeTypes = [2, 3, 0, 1];
	//Guardamos para restaurar mas tarde
	var tmpOpt = selectedOpt;
	var type = rectangles.indexOf(object.get('fill'));
	selectedOpt = oppositeTypes[type];
	//Evaluamos si está de lado izquierdo o derecho
	var x = object.get('left') <= CANVAS_MIDDLE ? 200 : 600;
	updateCurrentEquation(x);
	canvas.remove(object);

	//Restauramos la opción seleccionada
	selectedOpt = tmpOpt;
}

function removeAll(){
	if(confirm('¿Está seguro?')){
		var objects = canvas.getObjects();
		while(objects.length != 0){
			canvas.remove(objects[0]);
		}
		current.xLeft = 0;
		current.uLeft = 0;
		current.xRight = 0;
		current.uRight = 0;
		document.getElementById('current').innerHTML = equationToString(current);
		canvas.add(separator);
	}
}

canvas.on('object:selected', function(event){
	_isObjectSelected = true;
	var modifiedObject = event.target;
	var x = modifiedObject.get('left');
	previousSide = x <= CANVAS_MIDDLE;
});

canvas.on('before:selection:cleared', function(){
	_isObjectSelected = false;
});

canvas.on('object:modified', function(event){
	var modifiedObject = event.target;
	var x = modifiedObject.get('left');
	var newSide = x <= CANVAS_MIDDLE;
	if(newSide != previousSide){
		moveObject(modifiedObject, newSide);
	}
});

canvas.on('mouse:down', function(){
	_isMouseDown = true;
});

canvas.on('mouse:move', function (){
	_isDragging = _isMouseDown;
	// other stuff
});

canvas.on('mouse:up', function(event){
	_isMouseDown = false;
	var wasDragging = _isDragging;
	_isDragging = false;

	//Si estaba arrastrando el mouse no se agrega el rectangulo
	if(!wasDragging && !_isObjectSelected){
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

		updateCurrentEquation(x);
	}
});
document.addEventListener("keydown", function(e){
    var keynum;

    if(window.event) { // IE                    
      keynum = e.keyCode;
    } else if(e.which){ // Netscape/Firefox/Opera                   
      keynum = e.which;
    }
    if(keynum == 46){
    	deleteObject(canvas.getActiveObject());
    }
});