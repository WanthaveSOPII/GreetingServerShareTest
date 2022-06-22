window.onload=function(){
    var btn=document.getElementById("addGroupBtn");
    var close=document.getElementsByClassName("close");
    var form=document.getElementsByClassName("addGroupform");
    btn.addEventListener('click',function(){
        form[0].className="addGroupform open";
    })
    close[0].addEventListener('click',function(){
        form[0].className="addGroupform";
    })
}