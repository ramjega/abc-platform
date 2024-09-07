document.getElementById('userForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent the default form submission

    // Collect form data
    const userName = document.getElementById('userName').value;
    const userEmail = document.getElementById('userEmail').value;
    const userMobile = document.getElementById('userMobile').value;
    const userRole = document.getElementById('userRole').value;
    const password = document.getElementById('password').value;

    // Prepare request payload
    const requestData = {
        name: userName,
        mobile: userMobile,
        email: userEmail,
        role: userRole,
        password: password
    };

    // Make a POST request to the API
    fetch('http://localhost:8000/profile/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.id) {
                // Handle success (e.g., show a success message or redirect)
                alert('User created successfully!');
            } else {
                // Handle failure (e.g., show an error message)
                alert('Failed to create user: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while creating the user.');
        });
});

document.addEventListener('DOMContentLoaded', function () {
    // Fetch user data when the page loads
    fetch('http://localhost:8000/profile/fetch', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(users => {
            const userTableBody = document.getElementById('userTableBody');
            userTableBody.innerHTML = '';

            // Check if users array is not empty
            if (users.length > 0) {
                // Iterate over each user and add a row to the table
                users.forEach(user => {
                    const row = document.createElement('tr');

                    // Create table cells for each user property
                    row.innerHTML = `
                        <td class="text-white">${user.id || 'N/A'}</td>
                        <td class="text-white">${capitalizeFirstLetter(user.role || 'N/A')}</td>
                        <td class="text-white">${user.name || 'N/A'}</td>
                        <td class="text-white">${user.email || 'N/A'}</td>
                        <td class="text-white">${user.mobile || 'N/A'}</td>
                        <td class="text-white">${capitalizeFirstLetter(user.status || 'N/A')}</td>
                        <td class="text-white">
                            <button class="btn btn-sm btn-primary" onclick="editUser(${user.id})">Edit</button>
                            <button class="btn btn-sm btn-danger" onclick="deleteUser(${user.id})">Delete</button>
                        </td>
                    `;
                    userTableBody.appendChild(row);
                });
            } else {
                const row = document.createElement('tr');
                row.innerHTML = `<td colspan="7" class="text-white text-center">No users found</td>`;
                userTableBody.appendChild(row);
            }
        })
        .catch(error => {
            console.error('Error fetching users:', error);
            const userTableBody = document.getElementById('userTableBody');
            const row = document.createElement('tr');
            row.innerHTML = `<td colspan="7" class="text-white text-center">Error fetching user data</td>`;
            userTableBody.appendChild(row);
        });
});

// Helper function to capitalize the first letter of a string
function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

// Placeholder functions for edit and delete actions
function editUser(userId) {
    alert(`Edit user with ID: ${userId}`);
}

function deleteUser(userId) {
    alert(`Delete user with ID: ${userId}`);
}

