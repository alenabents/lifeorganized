

let table = document.getElementById('table1');
let currentElem = null;
table.onmouseover = function (event) {
    if (currentElem) return;
    let target = event.target.closest('tr');
    if (!target) return;
    if (!table.contains(target)) return;
    currentElem = target;
    console.log(currentElem)
    target.style.opacity = 0.5;
};

table.onmouseout = function (event) {

    let relatedTarget = event.relatedTarget;

    while (relatedTarget) {
        if (relatedTarget == currentElem) return;

        relatedTarget = relatedTarget.parentNode;
    }

    currentElem.style.opacity = 1;
    currentElem = null;
};
