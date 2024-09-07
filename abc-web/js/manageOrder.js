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
        // Fetch reservation details
        displayReservationDetails(reservationId);
        // Fetch and display queries on initial page load
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
                <td class="text-white">${data.customer.name}</td>
                <td class="text-white">${data.customer.mobile}</td>
                <td class="text-white">${data.dateTime}</td>
                <td class="text-white">${data.participants}</td>
                <td class="text-white">${formatText(data.status)}</td>
                <td class="text-white">${data.description}</td>
                <td class="text-white">
                   ${getStatusButtons(data.status, data.id)}
                </td>
            `;

                tableBody.appendChild(row);
            } else {
                tableBody.innerHTML = '<tr><td colspan="7" class="text-white">No data available</td></tr>';
            }
        })
        .catch(error => console.error('Error fetching service details:', error));
}

function getStatusButtons(status, reservationId) {
    let buttons = '';

    if (status === 'initial') {
        buttons = `
            <button class="btn btn-warning btn-sm" onclick="changeStatus(${reservationId}, 'in_progress')">Start</button>
            <button class="btn btn-danger btn-sm mt-1" onclick="changeStatus(${reservationId}, 'cancelled')">Cancel</button>
        `;
    } else if (status === 'in_progress') {
        buttons = `
            <button class="btn btn-success btn-sm" onclick="changeStatus(${reservationId}, 'ready')">Ready</button>
        `;
    } else if (status === 'ready') {
        buttons = `
            <button class="btn btn-primary btn-sm" onclick="changeStatus(${reservationId}, 'completed')">Complete</button>
        `;
    }

    return buttons;
}

function changeStatus(reservationId, newStatus) {
    const requestData = {
        id: reservationId,
        status: newStatus
    };

    fetch('http://localhost:8000/reservation/move', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json())
        .then(data => {
            if (data && data.status === newStatus) {
                // Status updated successfully, now update the table row dynamically
                displayReservationDetails(reservationId)
                // updateStatusInTable(reservationId, newStatus);
            } else {
                alert('Failed to update status.');
            }
        })
        .catch(error => {
            console.error('Error updating status:', error);
            alert('An error occurred while updating the status.');
        });
}

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
                <p><small>${formattedCreatedTime}</small></p>
                <p><strong>Response:</strong> ${item.response || 'No response yet'}</p>
                <p><small>${formattedModifiedTime}</small></p>
                <hr class="bg-light">
                <div class="reply-box">
        <textarea class="form-control reply-input" id="reply_${item.id}" placeholder="Enter your response"></textarea>
        <button class="btn btn-primary reply-button" onclick="submitReply(${item.id})">
            <i class="fas fa-paper-plane"></i>
        </button>
    </div>
                <hr class="bg-light">
            `;

                queryList.appendChild(queryDiv);
            });
        })
        .catch(error => console.error('Error fetching queries:', error));
}

function submitReply(queryId) {
    const replyText = document.getElementById(`reply_${queryId}`).value.trim();

    if (!replyText) {
        alert('Reply cannot be empty');
        return;
    }

    const requestData = {
        response: replyText
    };

    fetch(`http://localhost:8000/query/reply/${queryId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.response) {
                // Reload the queries to display the updated response
                fetchAndDisplayQueries(data.reservationId);
            } else {
                alert('Failed to submit reply. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error submitting reply:', error);
            alert('An error occurred. Please try again.');
        });
}
