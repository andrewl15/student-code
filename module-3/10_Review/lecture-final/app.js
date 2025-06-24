import { getHotels, reservationsByHotel } from "./services/hotel.js";
import { addReservation } from "./services/reservation.js";

let hotels = [];
let targetHotel = 0;

document.addEventListener("DOMContentLoaded", () => {
  getHotels().then((response) => {
    console.table(response.data);
    hotels = response.data;
    displayHotels();
  });

  const saveBtn = document.getElementById('btnSubmitRes');
  saveBtn.addEventListener('click', (event) => {
        const name = document.getElementById('resName').value;
        const date = document.getElementById('resDate').value;
        const guests = document.getElementById('resGuests').value;
        const nights = document.getElementById('resNights').value;
        const newRes = {
            hotelId: targetHotel,
            fullName: name,
            checkinDate: date,
            guests: parseInt(guests),
            nights: parseInt(nights)
        }
        console.log(newRes);
        addReservation(newRes);
        reset();
  })
});

function displayHotels() {
  if ("content" in document.createElement("template")) {
    hotels.forEach((item) => {
      buildHotel(item);
    });
  } else {
    console.error("Upgrade your browser");
  }
}

function buildHotel(hotel) {
  const container = document.getElementById("hotel-list");
  // clone template
  const tmpl = document.getElementById("hotel-tmpl").content.cloneNode(true);
  // add the data
  // let's add an id
  tmpl.querySelector("section").id = `hotel${hotel.id}`;
  tmpl.querySelector("h1").textContent = hotel.name;
  tmpl.querySelector("#address").textContent = hotel.address.address;
  tmpl.querySelector("#address2").textContent = hotel.address.address2;
  tmpl.querySelector(
    "#city-state-zip"
  ).textContent = `${hotel.address.city}, ${hotel.address.state} ${hotel.address.zip}`;
  tmpl.querySelector("#rooms > span").textContent = hotel.roomsAvailable;
  tmpl.querySelector("#cost > span").textContent = `\$${hotel.costPerNight}`;
  tmpl.querySelector("#rating > span").textContent = hotel.stars;
  // add the eventlistener
  tmpl.querySelector("div").addEventListener("click", (event) => {
    getReservationsForHotel(hotel.id);
  });

  // add to the page
  container.appendChild(tmpl);
  // after I added the tmpl to the dom, 
  const newHotel = document.getElementById(`hotel${hotel.id}`);
  newHotel.querySelector("button").addEventListener("click", (event) => {
    targetHotel = hotel.id;
    newHotel.querySelector("div").classList.add("targetHotel");
    reset();
    const form = document
      .getElementById("reservation-form")
      .classList.remove("hidden");
  });
}

function getReservationsForHotel(hotelID) {
  reservationsByHotel(hotelID)
    .then((response) => {
      // display the reservations
      displayReservations(response.data);
    })
    .catch((error) => {
      if (error.response.status == 404) {
        reset();
        const container = document.getElementById("reservation-list");
        container.innerHTML = "<strong>No reservations for this hotel</strong>";
      }
    });
}

function displayReservations(resArray) {
  reset();
  if ("content" in document.createElement("template")) {
    resArray.forEach((item) => {
      buildReservation(item);
    });
  } else {
    console.error("Upgrade your browser");
  }
}

function buildReservation(reservation) {
  const container = document.getElementById("reservation-list");
  // clone the template
  const tmpl = document
    .getElementById("reservation-tmpl")
    .content.cloneNode(true);
  // add the data
  tmpl.querySelector("#checkinDate").textContent = reservation.checkinDate;
  tmpl.querySelector("#guest > span").textContent = reservation.guests;
  tmpl.querySelector("#nights > span").textContent = reservation.nights;
  // append to container
  container.appendChild(tmpl);
}

function reset() {
  // reset reservation list
  const container = document.getElementById("reservation-list");
  container.textContent = "";
  const form = document
    .getElementById("reservation-form")
    .classList.add("hidden");
    // reset the colors
    const allHotels = document.querySelectorAll('#hotel-list > section');
    allHotels.forEach((item) => {
        item.classList.remove('targetHotel')
    })
}
// reservationsByHotel(1)
// .then((response) => {
//     console.table(response.data)
// })
