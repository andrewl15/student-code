//wait until DOM is fully loaded
document.addEventListener('DOMContentLoaded', () => {
    const changeButton = document.getElementById('clicker')

    changeButton.addEventListener('click', (event) => {
        alert(event.target)

        changeGreeting(event);
    })

    changeButton.addEventListener('contextmenu', (event) => {
        clearGreeting();
    })

    function changeGreeting(event) {
        const p = document.querySelector('p');
        if (p.textContent.includes('Hello World')) {
            p.textContent = 'Hello Andrew'
        } else {
            p.textContent = 'Hello World'
        }
    }

    function clearGreeting() {
        const p = document.querySelectorAll('p')
        p.textContent = ''
    }

    const targetDiv = document.querySelector('div')
    targetDiv.addEventListener('mousemove', (event) => {
        onmousemove(event)
    })

    function onMouseMove(event) {
        const xbox = document.getElementById('xcoord')
        const ybox = document.getElementById('ycoord')
        xbox.textContent = event.clientX
        ybox.textContent = event.clientY
    }

})