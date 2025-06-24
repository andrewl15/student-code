// Wait until DOM is loaded
document.addEventListener("DOMContentLoaded", () => {
  // finding an element where I want to listen for event
  const changeButton = document.getElementById("clicker");
  // add the event listener
  changeButton.addEventListener("click", (event) => {
    changeGreeting(event);
  });
  changeButton.addEventListener("contextmenu", (event) => {
    clearGreeting(event);
  });
  // write the event handler
  function changeGreeting(event) {
    const p = document.querySelector("p");
    event.target.textContent = "Thank you.";
    if (p.textContent.includes("World")) {
      p.textContent = "Hello my ragtime gal";
    } else {
      p.textContent = "Hello World!";
    }
  }

  function clearGreeting() {
    const p = document.querySelector("p");
    p.textContent = "";
  }

  const targetDiv = document.querySelector("div");
  targetDiv.addEventListener("mousemove", (event) => {
    onMouseMove(event);
  });
  function onMouseMove(event) {
    const xbox = document.getElementById("xcoord");
    const ybox = document.getElementById("ycoord");
    xbox.textContent = event.clientX;
    ybox.textContent = event.clientY;
  }

  const ol = document.getElementById("orderedList");
  ol.addEventListener("click", (event) => {
    alert(
      `You clicked item ${event.target.textContent} handled by ${event.currentTarget}`
    );
  });

  ol.children[0].addEventListener("click", (event) => {
    alert(event.target);
  });

  const bodyTag = document.querySelector("body");
  bodyTag.addEventListener("click", (event) => {
    const section = document.querySelector("section");
    section.textContent = event.target.tagName;
  });
});
