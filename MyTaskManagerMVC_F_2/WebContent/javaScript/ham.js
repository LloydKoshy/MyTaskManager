const MENU = document.querySelector(".item-menu");
const REMOVE = document.querySelector(".remove");
const HAM = document.querySelector(".ham");
const GRIDCONT = document.querySelector(".grid-container");

var menuRemove=true;

HAM.addEventListener("click", function(){
	GRIDCONT.style.gridTemplateAreas = '"header" "menu" "main"  "main" "main" "footer "';
});

REMOVE.addEventListener("click", function(){
	GRIDCONT.style.gridTemplateAreas = '"header" "main"  "main" "main" "footer "';
});

function reveal(){
	menuRemove=MENU.classList.toggle("hide");
	HAM.classList.toggle("hide");
	REMOVE.classList.toggle("hide");
}

function resizeFunc(x) {
	if (x.matches) {
		MENU.classList.remove("hide");
		
		GRIDCONT.style.gridTemplateAreas = '"header header header header header header" "menu main main main main main" "menu main main main main main" "footer footer footer footer footer footer"';
		
		
	  } else {
		  
		  if(menuRemove==true){
			  MENU.classList.add("hide");
			  GRIDCONT.style.gridTemplateAreas = '"header" "main"  "main" "main" "footer "';
		  }else{
			  MENU.classList.remove("hide");
			  GRIDCONT.style.gridTemplateAreas = '"header" "menu" "main"  "main" "main" "footer "';
		  }
	    
	  }
}


var x = window.matchMedia("(min-width: 700px)");
x.addListener(resizeFunc)
if (x.matches) {
MENU.classList.remove("hide");
}else{
	MENU.classList.add("hide");
}