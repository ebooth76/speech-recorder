// set up basic variables for app
var record = document.querySelector('.record');
var stop = document.querySelector('.stop');
var soundClips = document.querySelector('.sound-clips');
var mainSection = document.querySelector('.main-controls');

// disable stop button while not recording
stop.disabled = true;

// visualiser setup - create web audio api context and canvas
//var audioCtx = new (window.AudioContext || webkitAudioContext)();
//var canvasCtx = canvas.getContext("2d");

//main block for doing the audio recording
if (navigator.mediaDevices.getUserMedia) {

	console.log('getUserMedia supported.');

	var constraints = { audio: true };
	var chunks = [];

	var onSuccess = function(stream) {
	var mediaRecorder = new MediaRecorder(stream);

	//visualize(stream);

	record.onclick = function() {
		mediaRecorder.start();
		console.log(mediaRecorder.state);
		console.log("recorder started");
		record.style.background = "red";

		stop.disabled = false;
		record.disabled = true;
	}

	stop.onclick = function() {
		mediaRecorder.stop();
		console.log(mediaRecorder.state);
		console.log("recorder stopped");
		record.style.background = "";
		record.style.color = "";
		// mediaRecorder.requestData();

		stop.disabled = true;
		record.disabled = false;
	}

	mediaRecorder.onstop = function(e) {

		console.log("data available after MediaRecorder.stop() called.");

		var clipContainer = document.createElement('article');
		var clipLabel = document.createElement('p');
		var audio = document.createElement('audio');
		//var deleteButton = document.createElement('button');

		var clipName = Date.now();
		console.log(clipName);
		clipContainer.classList.add('clip');
		audio.setAttribute('controls', '');
		//deleteButton.textContent = 'Delete';
		//deleteButton.className = 'delete';

		clipContainer.appendChild(audio);
		clipContainer.appendChild(clipLabel);
		//clipContainer.appendChild(deleteButton);
		soundClips.appendChild(clipContainer);

		audio.controls = true;

		// get blob, convert to base64 wav
		var blob = new Blob(chunks, { 'type' : 'audio/wav' });
		var reader = new FileReader();
		var base64data;
		reader.readAsDataURL(blob);
		reader.onloadend = function() {
			base64data = reader.result;
			//console.log(base64data);
		}

		// send audio to server with post
		//function reqListener () {
		//	console.log(this.responseText);
		//}
		//var xhr = new XMLHttpRequest();
		//xhr.addEventListener("load", reqListener);
		//xhr.open("POST", "/audio", true);
		//xhr.onload = function (oEvent) {
		//	do stuff?
		//};
		//xhr.send(base64data);


		// send with websocket

		var stompClient = null;

		var socket = new SockJS('/audio-websocket');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			console.log('Connected: ' + frame);
			stompClient.send("/app/audio", {}, base64data);
			//disconnect
			if (stompClient !== null) {
			  stompClient.disconnect();
			}
			console.log("Disconnected");
		});

		chunks = [];
		var audioURL = window.URL.createObjectURL(blob);
		audio.src = audioURL;
		console.log("recorder stopped");

		//deleteButton.onclick = function(e) {
		//	evtTgt = e.target;
		//	evtTgt.parentNode.parentNode.removeChild(evtTgt.parentNode);
		//}
	}

	mediaRecorder.ondataavailable = function(e) {
		chunks.push(e.data);
	}
}

var onError = function(err) {
	console.log('The following error occurred: ' + err);
}

navigator.mediaDevices.getUserMedia(constraints).then(onSuccess, onError);

} else {
   console.log('getUserMedia not supported on your browser!');
}

//function visualize(stream) {
//
//	var source = audioCtx.createMediaStreamSource(stream);
//
//	var analyser = audioCtx.createAnalyser();
//	analyser.fftSize = 2048;
//	var bufferLength = analyser.frequencyBinCount;
//	var dataArray = new Uint8Array(bufferLength);
//
//	source.connect(analyser);
//	//analyser.connect(audioCtx.destination);
//
//	draw()
//
//	function draw() {
//	WIDTH = canvas.width
//	HEIGHT = canvas.height;
//
//	requestAnimationFrame(draw);
//
//	analyser.getByteTimeDomainData(dataArray);
//
//	canvasCtx.fillStyle = 'rgb(200, 200, 200)';
//	canvasCtx.fillRect(0, 0, WIDTH, HEIGHT);
//
//	canvasCtx.lineWidth = 2;
//	canvasCtx.strokeStyle = 'rgb(0, 0, 0)';
//
//	canvasCtx.beginPath();
//
//	var sliceWidth = WIDTH * 1.0 / bufferLength;
//	var x = 0;
//
//	for(var i = 0; i &lt; bufferLength; i++) {
//
//		var v = dataArray[i] / 128.0;
//		var y = v * HEIGHT/2;
//
//		if(i === 0) {
//			canvasCtx.moveTo(x, y);
//		} else {
//			canvasCtx.lineTo(x, y);
//		}
//
//		x += sliceWidth;
//	}
//
//	canvasCtx.lineTo(canvas.width, canvas.height/2);
//	canvasCtx.stroke();
//
//	}
//}

//window.onresize = function() {
//  canvas.width = mainSection.offsetWidth;
//}
//
//window.onresize();
