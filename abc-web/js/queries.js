// Function to get query parameters
function getQueryParams() {
    const params = new URLSearchParams(window.location.search);
    return {
        reservationId: params.get('reservationId')
    };
}

window.onload = function () {
    handleLocalStore();

    const {reservationId} = getQueryParams();

    if (reservationId) {
        displayReservationDetails(reservationId);
        fetchAndDisplayQueries(reservationId);
    } else {
        console.error('No reservationId found in query parameters');
    }

};

function displayReservationDetails(reservationId) {
    const tableBody = document.getElementById('reservationTableBody');

    fetch(`http://localhost:8000/reservation/fetch/${reservationId}`, {
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

            if (data) {
                const row = document.createElement('tr');

                row.innerHTML = `
                <td class="text-white">${data.id}</td>
                <td class="text-white">${formatText(data.serviceType)}</td>
                <td class="text-white">${formatDate(data.createdTime)}</td>
                <td class="text-white">${data.dateTime}</td>
                <td class="text-white">${data.participants}</td>
                <td class="text-white">${formatText(data.status)}</td>
                <td class="text-white">${data.description}</td>
            `;

                tableBody.appendChild(row);
            } else {
                tableBody.innerHTML = '<tr><td colspan="7" class="text-white">No data available</td></tr>';
            }
        })
        .catch(error => console.error('Error fetching service details:', error));
}

// Function to handle query form submission
document.getElementById('queryForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const {reservationId} = getQueryParams();
    const queryText = document.getElementById('queryText').value;

    fetch(`http://localhost:8000/query/submit`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({
            reservationId: reservationId,
            query: queryText
        })
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('queryText').value = ''; // Clear the textarea
            fetchAndDisplayQueries(reservationId); // Refresh the query list
        })
        .catch(error => {
            console.error('Error submitting query:', error);
            alert('There was an error submitting your query. Please try again.');
        });
});

// Function to fetch and display queries
function fetchAndDisplayQueries(reservationId) {

    fetch(`http://localhost:8000/query/fetch/${reservationId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(data => {
            const queryList = document.getElementById('queryList');
            queryList.innerHTML = ''; // Clear the existing list

            data.forEach(item => {
                const queryDiv = document.createElement('div');
                queryDiv.classList.add('mb-3');

                const formattedCreatedTime = formatDate(item.createdTime);
                const formattedModifiedTime = item.modifiedTime ? formatDate(item.modifiedTime) : 'N/A';

                queryDiv.innerHTML = `
                <p><strong>Query:</strong> ${item.query}</p>
                <p><small>${formattedCreatedTime}</p>
                <p><strong>Response:</strong> ${item.response || 'No response yet'}</p>
                 <p><small>${formattedModifiedTime}</p>
                <hr class="bg-light">
            `;

                queryList.appendChild(queryDiv);
            });
        })
        .catch(error => console.error('Error fetching queries:', error));
}

