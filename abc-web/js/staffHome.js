window.onload = function () {
    handleLocalStore();
    fetchAndDisplayAllReservations();

};

// Function to fetch and reservations
function fetchAndDisplayAllReservations() {
    const tableBody = document.getElementById('reservationTableBody');


    // Fetch the reservations from the API
    fetch('http://localhost:8000/reservation/fetch', {
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
                <td class="text-white">${reservation.customer.name}</td>
                <td class="text-white">${reservation.customer.mobile}</td>
                <td class="text-white">${reservation.dateTime}</td>
                <td class="text-white">${reservation.participants}</td>
                <td class="text-white">${formatText(reservation.status)}</td>
                <td class="text-white">${reservation.description}</td>
                 <td>
                    <a href="manageOrder.html?reservationId=${reservation.id}" >Manage</a>
                </td>
            `;

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching reservations:', error);
        });
}
