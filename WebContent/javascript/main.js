window.onload = init;

var windowWidth, windowHeight;
var counterDivHeight;
var controlsDivHeight;
var ball;
var hole;

var mouseDownInsideBall;
var touchDownInsideBall;

var lastMouse, lastTouch, lastOrientation;

var paused;
var started;

var scored;

var counter;
var timer;

var scores;

function init(){
	lastOrientation = {};
	window.addEventListener('resize', loadLayoutSpecs, false);
	
	window.addEventListener('deviceorientation', deviceOrientationTest, false);
	lastMouse = {x:0,y:0};
	lastTouch = {x:0,y:0};
	
	mouseDownInsideBall = false;
	touchDownInsideBall = false;
	loadLayoutSpecs(document);
	
	paused = false;
	started = false;
	scored = false;
	
	counter = {};
	counter.hours = 0;
	counter.minutes = 0;
	counter.seconds = 0;
	
	scores = new Array();
}
function playPauseButtonAction(){
	if(paused){
		addListeners();
		startTimer();
		paused = false;
		document.getElementById("playPauseButtonImg").src="res/images/ic_pause.png";	
	}
	else if(!paused && !started){
		addListeners();
		startTimer();
		started = true;
		document.getElementById("playPauseButtonImg").src="res/images/ic_pause.png";	
	}
	else if(!paused && started){
		removeListeners();
		stopTimer();
		paused = true;
		document.getElementById("playPauseButtonImg").src="res/images/ic_play.png";
	}
	
}
function startTimer(){
	timer = setInterval(addSecond,1000);
}
function stopTimer(){
	clearInterval(timer);
}
function addSecond(){
	addTime(0,0,1);
}
function addTime(hours, minutes, seconds){
	var s = counter.seconds + seconds;
	var m = Math.floor(s/60);
	s = s - m*60;
	
	m += counter.minutes + minutes;
	var h = Math.floor(m/60);
	m = m - h*60;
	
	h += counter.hours + hours;
	
	counter.seconds =s;
	counter.minutes =m;
	counter.hours = h;
	
	updateCounter();
}
function updateCounter(){
	var counterDiv = document.getElementById("counter");
	while(counterDiv.childNodes.length >= 1) {
		counterDiv.removeChild(counterDiv.firstChild);
	}
	counterDiv.appendChild(counterDiv.ownerDocument.createTextNode(formatTime(counter.hours, counter.minutes,counter.seconds)));
}
function formatTime(hours, minutes, seconds){
	var string = "";
	if(hours < 10){
		string += "0";
	}
	string += hours.toString() + ":";
	
	if(minutes < 10){
		string += "0";
	}
	string += minutes.toString() + ":";
	
	if(seconds < 10){
		string += "0";
	}
	string += seconds.toString();
	
	return string;
}
function stopButtonAction(){
	stopTimer();
	resetGame();
}
function resetGameElements(){
	var radius = 20;
	ball = {
			radius:radius,
			x: 50,
			y: 50,
			colour:"rgba(255,255,255,255)"
	};
	hole = {
			radius:radius+5,
			x: surface.width-100,
			y: surface.height-100,
			colour:"rgba(0,0,0,255)"
	};
	renderGameElements(ball, hole);
}
function addListeners(){
	document.body.addEventListener('mousemove', onMouseMove, false);
	document.body.addEventListener('mousedown', onMouseDown, false);
	document.body.addEventListener('mouseup', onMouseUp, false);
	document.body.addEventListener('touchmove', onTouchMove, false);
	document.body.addEventListener('touchstart', onTouchDown, false);
	document.body.addEventListener('touchend', onTouchUp, false);
}
function removeListeners(){
	document.body.removeEventListener('mousemove', onMouseMove, false);
	document.body.removeEventListener('mousedown', onMouseDown, false);
	document.body.removeEventListener('mouseup', onMouseUp, false);
	document.body.removeEventListener('touchmove', onTouchMove, false);
	document.body.removeEventListener('touchstart', onTouchDown, false);
	document.body.removeEventListener('touchend', onTouchUp, false);
}
function loadLayoutSpecs(event){
	windowWidth = window.innerWidth;
	windowHeight = window.innerHeight;
	
	var counterDiv = document.getElementById("counter");
	counterDivHeight = counterDiv.offsetHeight;
	
	var controlsDiv = document.getElementById("controls");
	controlsDivHeight = controlsDiv.offsetHeight;
	
	var surface = document.getElementById("surface");
	surface.width = windowWidth;
	surface.height = windowHeight-counterDivHeight-controlsDivHeight+8;
	
	resetGameElements();
	
}
function renderGameElements(ball, hole){
	var surface = document.getElementById("surface");
	var context = surface.getContext("2d");
	context.clearRect(0, 0, surface.width, surface.height);
	
	context = renderHole(context, hole);
	context = renderBall(context, ball);
}
function renderBall(context,ball){
	context.beginPath();
	context.arc(ball.x,ball.y, ball.radius, 0,2*Math.PI, false);
	context.fillStyle = ball.colour;
	context.fill();
	return context;
}
function renderHole(context,hole){
	context.beginPath();
	context.arc(hole.x,hole.y, hole.radius+5, 0,2*Math.PI, false);
	context.fillStyle = "rgba(100,100,100,255)";
	context.fill();
	
	context.beginPath();
	context.arc(hole.x,hole.y, hole.radius, 0,2*Math.PI, false);
	context.fillStyle = hole.colour;
	context.fill();

	return context;
}
function onRenderUpdate(event) {
	var xDelta, yDelta;
	switch (window.orientation) {
		case 0:
			xDelta = lastOrientation.gamma;
			yDelta = lastOrientation.beta;
			break;
		case 180:
			xDelta = lastOrientation.gamma * -1;
			yDelta = lastOrientation.beta * -1;
			break;
		case 90:
			xDelta = lastOrientation.beta;
			yDelta = lastOrientation.gamma * -1;
			break;
		case -90:
			xDelta = lastOrientation.beta * -1;
			yDelta = lastOrientation.gamma;
			break;
		default:
			xDelta = lastOrientation.gamma;
			yDelta = lastOrientation.beta;
	}
	moveBall(xDelta, yDelta);
}

function moveBall(ball, xDelta, yDelta) {
	var surface = document.getElementById("surface");
	ball.x += xDelta;
	ball.y += yDelta;
	if(ball.x < ball.radius){
		ball.x = ball.radius;
	}
	else if(ball.x >surface.width-ball.radius){
		ball.x = surface.width-ball.radius;
	}
	if(ball.y < ball.radius){
		ball.y = ball.radius;
	}
	else if(ball.y > surface.height-ball.radius){
		ball.y =surface.height-ball.radius;
	}
	renderGameElements(ball, hole);
	checkIfBallInHole(ball, hole);
}
function checkIfBallInHole(ball, hole){
	if(	ball.x+ball.radius < hole.x+hole.radius &&
		ball.x-ball.radius > hole.x-hole.radius &&
		ball.y+ball.radius < hole.y+hole.radius &&
		ball.y-ball.radius > hole.y-hole.radius
		){
		scoreAction();
	}
}
function sortScores(){
	scores = bubbleSort(scores);
}
function bubbleSort(array){
	var i, j, temp;
	for(j = 0;j<array.length;j++){
		for(i = 1;i<array.length-j;i++){
			if(!compareScores(array[i-1],array[i])){
				temp = array[i];
				array[i] = array[i-1];
				array[i-1] = temp;
			}
		}
	}
	return array;
}
function compareScores(score1, score2){
	var bool = false;
	if(score1.hours < score2.hours){
		bool = true;
	}
	else if(score1.hours == score2.hours){
		if(score1.minutes < score2.minutes){
			bool = true;
		}
		else if(score1.minutes == score2.minutes){
			if(score1.seconds < score2.seconds){
				bool = true;
			}
		}
	}
	
	return bool;
}
function scoreAction(){
	addScore(counter.hours, counter.minutes, counter.seconds);
	
	scored = true;
	showScoreboard();
	
	stopTimer();
	resetGame();
	scored = false;
}
function addScore(hours, minutes, seconds){
	scores[scores.length] = {hours:hours,minutes:minutes,seconds:seconds};
}
function showScoreboard(){
	sortScores();
	var playerScoreIndex = 0;
	
	var stringSub = "";
	for(var i = 0;i<scores.length;i++){
		if(scores[i].hours == counter.hours && scores[i].minutes == counter.minutes && scores[i].seconds == counter.seconds){
			playerScoreIndex = i+1;
		}
		stringSub += (i+1).toString() +". " +formatTime(scores[i].hours,scores[i].minutes, scores[i].seconds) + "\n";
	}
	
	var stringMain = 	"Scoreboard\n" +
					"-----------------\n";
	
	if(scored){
		stringMain += playerScoreIndex.toString() + ". " + formatTime(counter.hours,counter.minutes, counter.seconds) + "\n" +
					"-----------------\n";
	}
	
	stringMain += stringSub;
	
	alert(stringMain);
}
function resetCounter(){
	counter.hours = 0;
	counter.minutes = 0;
	counter.seconds = 0;
	
	updateCounter();
}
function resetGame(){
	resetGameElements();
	resetCounter();
	
	started = false;
	paused = false;
	removeListeners();
	document.getElementById("playPauseButtonImg").src="res/images/ic_play.png";
}
function onMouseMove(event) {
	if(mouseDownInsideBall){
		var xDelta, yDelta;
		xDelta = event.clientX - lastMouse.x;
		yDelta = event.clientY - lastMouse.y;
		moveBall(ball, xDelta, yDelta);
		lastMouse.x = event.clientX;
		lastMouse.y = event.clientY;
	}
}

function onMouseDown(event) {
	var x = event.clientX;
	var y = event.clientY;
	if(	x > ball.x - ball.radius &&
		x < ball.x + ball.radius &&
		y-counterDivHeight > ball.y - ball.radius &&
		y-counterDivHeight < ball.y + ball.radius){
		mouseDownInsideBall = true;
		lastMouse.x = x;
		lastMouse.y = y;
	} else {
		mouseDownInsideBall = false;
	}
} 

function onMouseUp(event) {
	mouseDownInsideBall = false;
}

function onTouchMove(event) {
	event.preventDefault();	
	if(touchDownInsideBall){
		var touches = event.changedTouches;
		var xav = 0;
		var yav = 0;
		for (var i=0; i < touches.length; i++) {
			var x = touches[i].pageX;
			var y =	touches[i].pageY;
			xav += x;
			yav += y;
		}
		xav /= touches.length;
		yav /= touches.length;
		var xDelta, yDelta;

		xDelta = xav - lastTouch.x;
		yDelta = yav - lastTouch.y;
		moveBall(ball, xDelta, yDelta);
		lastTouch.x = xav;
		lastTouch.y = yav;
	}
}

function onTouchDown(event) {
	event.preventDefault();
	touchDownInsideBall = false;
	var touches = event.changedTouches;
	for (var i=0; i < touches.length && !touchDownInsideBall; i++) {
		var x = touches[i].pageX;
		var y = touches[i].pageY;
		if(	x > ball.x - ball.radius &&
			x < ball.x + ball.radius &&
			y-counterDivHeight > ball.y - ball.radius &&
			y-counterDivHeight < ball.y + ball.radius){
			touchDownInsideball = true;		
			lastTouch.x = x;
			lastTouch.y = y;			
		}
		else{
			touchDownInsideBall = false;
		}
	}
} 

function onTouchUp(event) {
	touchDownInsideball = false;
}

function onDeviceOrientationChange(event) {
	lastOrientation.gamma = event.gamma;
	lastOrientation.beta = event.beta;
}
function deviceOrientationTest(event) {
	window.removeEventListener('deviceorientation', deviceOrientationTest);
	if (event.beta != null && event.gamma != null) {
		window.addEventListener('deviceorientation', onDeviceOrientationChange, false);
		movementTimer = setInterval(onRenderUpdate, 10); 
	}
}

