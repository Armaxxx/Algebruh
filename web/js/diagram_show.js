//AJUSTAR CANVAS A PANTALLA
var CANVAS_WIDTH = window.innerWidth - 100;
var CANVAS_HEIGHT = window.innerHeight - 150;

document.getElementById('c').width = CANVAS_WIDTH;
document.getElementById('c').height = CANVAS_HEIGHT;

//Fabric init
var canvas = new fabric.Canvas('c');
var verbEx = ['Despejar', 'Sustituir', 'Expander', 'Factorizar'];

function expressionToString(s, l, c){
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
    
    if(s == 0 && l == 1){
        lStr = "X";
    }else if(s == 0 && l == -1){
        lStr = "-X";
    }else if(l == 1){
        op1 = " + ";
        lStr = "X";
    }else if(l == -1){
        op1 = " -";
        lStr = "X";
    }else if(l < -1){
        if(s != 0){
            op1 = " - ";
            l *= -1;
            lStr = l + "X"
        }else{
            lStr = l+"X";
        }
    }else if(l > 1){
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

function parseSolve(eq, ans){
    var ret = ["", ""];
    //Ecuacion
    ret[0] = expressionToString(0, eq[0], eq[1]) + " = " + expressionToString(0, eq[2], eq[3]);
    //Solucion
    ret[1] = "X = " + ans[0];
    
    return ret;
}

function parseSubstitute(eq, ans){
    var ret = ["", ""];
    //Ecuacion
    ret[0] = "X = " + eq[0] + ", en " + expressionToString(0, eq[1], eq[2]);
    //Solucion
    ret[1] = ans[0];
    
    return ret;
}

function parseExpand(eq, ans){
    var ret = ["", ""];
    //Ecuacion
    ret[0] = "(" + expressionToString(0, eq[0], eq[1]) + ")(" + expressionToString(0, eq[2], eq[3]) + ")";
    //Solucion
    ret[1] = expressionToString(ans[0], ans[1], ans[2]);
    
    return ret;
}

function parseFactor(eq, ans){
    var ret = ["", ""];
    //Ecuacion
    ret[0] = expressionToString(eq[0], eq[1], eq[2]);
    //Solucion
    ret[1] = "(" + expressionToString(0, ans[0], ans[1]) + ")(" + expressionToString(0, ans[2], ans[3]) + ")";
    
    return ret;
}

function init(data, type, rawEquation, rawSolution){
    
    //Cargamos los objetos en el cambias para solo vista
    canvas.loadFromJSON(data, function(){
        canvas.renderAll();
    });
    canvas.selection = false;
    canvas.hoverCursor = 'default';
    var objects = canvas.getObjects();
    for(var i=0; i< objects.length; i++){
        objects[i].set('selectable', false);
    }
    
    //Cargamos la ecuacion y la respuesta del ejercicio
    
    var eqElements = rawEquation.split(";");
    var solElements = rawSolution.split(";");
    var parsedStrings = ["", ""];
    if(type == 1){
        parsedStrings = parseSolve(eqElements, solElements);
    }else if(type == 2){
        parsedStrings = parseSubstitute(eqElements, solElements);
    }else if(type == 3){
        parsedStrings = parseExpand(eqElements, solElements);
    }else if(type == 4){
        parsedStrings = parseFactor(eqElements, solElements);
    }
    var equationString = parsedStrings[0];
    var answerString = parsedStrings[1];
    
    document.getElementById('equation').innerHTML = verbEx[type-1] + ": " + equationString;
    document.getElementById('answer').innerHTML = "Soluci&oacute;n : " + answerString;
}
