let allServices = [];

window.onload = function () {
    handleLocalStore();

    fetch('http://localhost:8000/service/fetch')
        .then(response => response.json())
        .then(data => {
            allServices = data;
            displayServices(allServices); // Initially display all services
        })
        .catch(error => console.error('Error fetching services:', error));
};

document.querySelector('.btn-primary[href="#reservationSection"]').addEventListener('click', function (e) {
    e.preventDefault();
    document.querySelector('#reservationSection').scrollIntoView({
        behavior: 'smooth'
    });
});

document.querySelector('.btn-primary[href="#serviceSection"]').addEventListener('click', function (e) {
    e.preventDefault();
    document.querySelector('#serviceSection').scrollIntoView({
        behavior: 'smooth'
    });
});

document.getElementById('reservationForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent the form from submitting the default way

    // Get form data
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const mobile = document.getElementById('mobile').value.trim();
    const datetime = document.getElementById('datetime').value.trim();
    const people = document.getElementById('people').value.trim();
    const message = document.getElementById('message').value.trim();
    const privateRoom = document.getElementById('privateRoom').checked;

    // Basic form validation
    if (!name || !email || !mobile || !datetime || !people) {
        alert('Please fill out all required fields.');
        return;
    }

    // Additional email validation
    const emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/;
    if (!emailPattern.test(email)) {
        alert('Please enter a valid email address.');
        return;
    }

    // Prepare data for POST request
    const reservationData = {
        description: message,
        participants: parseInt(people, 10),
        serviceType: privateRoom ? "private_dine_in" : "dine_in",
        dateTime: datetime
    };

    const token = localStorage.getItem('token');

    // Send data to server
    fetch('http://localhost:8000/reservation/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(reservationData)
    })
        .then(response => response.json())
        .then(data => {
            window.location.href = 'booking.html#viewReservation'; // Redirect to the booking page
        })
        .catch(error => {
            console.error('Error:', error);
            alert('There was an error creating your reservation. Please try again.');
        });
});

// Event listener for 'Book A Table' button
document.getElementById('bookTableButton').addEventListener('click', function(event) {
    if (!isLoggedIn()) {
        event.preventDefault(); // Prevent the default anchor action
        window.location.href = 'login.html'; // Redirect to the login page
    }
});

// Event listener for 'Place an Order' button
document.getElementById('placeOrderButton').addEventListener('click', function(event) {
    if (!isLoggedIn()) {
        event.preventDefault(); // Prevent the default anchor action
        window.location.href = 'login.html'; // Redirect to the login page
    }
});
