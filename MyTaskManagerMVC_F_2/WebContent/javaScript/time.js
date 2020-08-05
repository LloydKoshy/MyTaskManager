function formattedNumber(myNumber) {
	                    return  ("0" + myNumber).slice(-2);
	                      }




function runClock() {

	    var date = new Date();
	let hr = formattedNumber(date.getHours());
	let min = formattedNumber(date.getMinutes());
	let sec = formattedNumber(date.getSeconds());

	let hrPosition = hr;
	let minPosition = min;
	let secPosition = sec;

	var syncTime = document.querySelector(".syncTime");
	syncTime.innerHTML =" "+ hr + " : " + min + " : " + sec +" ";
}
var interval = setInterval(runClock,1000);