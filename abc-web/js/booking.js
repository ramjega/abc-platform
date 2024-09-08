// Function to get query parameters
function getQueryParams() {
    const params = new URLSearchParams(window.location.search);
    return {
        serviceID: params.get('serviceID')
    };
}

window.onload = function () {
    handleLocalStore();
    fetchAndDisplayReservations();

    const {serviceID} = getQueryParams();

    if (serviceID) {
        // Fetch service details
        fetchServiceDetails(serviceID);
    } else {
        console.error('No serviceID found in query parameters');
    }

};

function fetchServiceDetails(serviceID) {
    fetch(`http://localhost:8000/service/fetch/${serviceID}`)
        .then(response => response.json())
        .then(data => {
            // Display service name in the form
            document.getElementById('serviceName').textContent = "Make a reservation for " + data.name;
            document.getElementById('serviceType').value = data.serviceType;
        })
        .catch(error => console.error('Error fetching service details:', error));
}

document.getElementById('reservationForm').addEventListener('submit', function (event) {
    event.preventDefault();

    // Get form data
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const mobile = document.getElementById('mobile').value.trim();
    const datetime = document.getElementById('datetime').value.trim();
    const people = document.getElementById('people').value.trim();
    const message = document.getElementById('message').value.trim();
    const serviceType = document.getElementById("serviceType").value.trim();

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

    const reservationData = {
        description: message,
        participants: parseInt(people, 10),
        serviceType: serviceType,
        dateTime: datetime
    };

    // Send data to server
    fetch('http://localhost:8000/reservation/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(reservationData)
    })
        .then(response => response.json())
        .then(data => {
            window.location.href = 'booking.html#viewReservation'; // Redirect to the booking page
            fetchAndDisplayReservations(); // Refresh the reservation list
        })
        .catch(error => {
            console.error('Error:', error);
            alert('There was an error creating your reservation. Please try again.');
        });
});

function fetchAndDisplayReservations() {
    const tableBody = document.getElementById('bookingTableBody');


    // Fetch the reservations from the API
    fetch('http://localhost:8000/reservation/fetch/mine', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(data => {
            // Clear existing rows
            tableBody.innerHTML = '';

            // Add each reservation as a new row in the table
            data.forEach(reservation => {
                const row = document.createElement('tr');

                row.innerHTML = `
                <td class="text-white">${reservation.id}</td>
                <td class="text-white">${formatText(reservation.serviceType)}</td>
                <td class="text-white">${formatDate(reservation.createdTime)}</td>
                <td class="text-white">${reservation.dateTime}</td>
                <td class="text-white">${reservation.participants}</td>
                <td class="text-white">${formatText(reservation.status)}</td>
                <td class="text-white">${reservation.description}</td>
                 <td>
                    <a href="queries.html?reservationId=${reservation.id}" >View Queries</a>
                </td>
            `;

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching reservations:', error);
        });
}
