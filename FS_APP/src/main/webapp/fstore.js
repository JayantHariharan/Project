const pwd=document.getElementById("password")
pwd.addEventListener("click",paswdToggle)

function paswdToggle(){
    const blur=document.getElementById("blur")
    blur.classList.toggle('active')
    const popup=document.getElementById("popup")
    popup.classList.toggle('active')
}

const submit=document.getElementById("submitbtn")
const paswd=document.getElementById("passwordtext")
submit.addEventListener("click",passwordCheck);

function passwordCheck(){
    if(paswd.value==="lucifer1234"){
		localStorage.setItem("file","private")
        window.location.href="filepage.html"
    }else{
        alert("Incorrect password")
    }
}
const publicPage=document.getElementById("public")
publicPage.addEventListener("click",()=>{
	localStorage.setItem("file","public")
	window.location.href="filepage.html"
})