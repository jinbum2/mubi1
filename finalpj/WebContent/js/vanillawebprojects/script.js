

function seatsInit(){
	const container = document.querySelector('.container');
	const seats = document.querySelectorAll('.row .seat:not(.occupied)');
	const count = document.getElementById('count');
	const total = document.getElementById('total');

	populateUI();

	let ticketPrice = 10000;

	// Update total and count
	function updateSelectedCount() {
	  const selectedSeats = document.querySelectorAll('.row .seat.selected');

	  const seatsIndex = [...selectedSeats].map(seat => [...seats].indexOf(seat));

	  localStorage.setItem('selectedSeats', JSON.stringify(seatsIndex));

	  const selectedSeatsCount = selectedSeats.length;

	  movie_seats = [...selectedSeats].map(seat => seat.textContent) + '';
	  count.innerText = selectedSeatsCount;
	  total.innerText = selectedSeatsCount * ticketPrice;
	}

	// Get data from localstorage and populate UI
	function populateUI() {
	  const selectedSeats = JSON.parse(localStorage.getItem('selectedSeats'));

	  if (selectedSeats !== null && selectedSeats.length > 0) {
	    seats.forEach((seat, index) => {
	      if (selectedSeats.indexOf(index) > -1) {
	        seat.classList.add('selected');
	      }
	    });
	  }
	}

	// Seat click event
	container.addEventListener('click', e => {
	  if (
	    e.target.classList.contains('seat') &&
	    !e.target.classList.contains('occupied')
	  ) {
	    e.target.classList.toggle('selected');
	    
	    updateSelectedCount();
	  }
	});

	// Initial count and total set
	updateSelectedCount();
}