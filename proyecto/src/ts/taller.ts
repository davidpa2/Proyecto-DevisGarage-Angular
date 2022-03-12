/*function allowDrop(ev:any) {
    ev.preventDefault();
}

function drag(ev:any) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev:any) {
    ev.preventDefault();
    ev.dataTransfer.getData("text").draggable = true;

    console.log(ev.currentTarget.innerHTML)

    if (!ev.currentTarget.innerHTML) {
        var data = ev.dataTransfer.getData("text");
        ev.target.appendChild(document.getElementById(data));
    } else {
        console.log('hola')
        ev.dataTransfer.getData("text").draggable = false;
    }
}*/
