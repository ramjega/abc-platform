const userRoles = ['admin', 'staff', 'customer'];

// Fetch user data when the page loads
document.addEventListener('DOMContentLoaded', function () {
    fetchUserData();
});

function fetchUserData() {
    fetch('http://localhost:8000/profile/fetch', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(users => {
            renderUsers(users);
        })
        .catch(error => {
            console.error('Error fetching users:', error);
            displayError('Error fetching user data');
        });
}

function renderUsers(users) {
    const userTableBody = document.getElementById('userTableBody');
    userTableBody.innerHTML = '';

    if (users.length > 0) {
        users.forEach(user => {
            const row = document.createElement('tr');
            row.setAttribute('data-id', user.id);
            row.innerHTML = `
                <td class="text-white">${user.id || 'N/A'}</td>
                <td>
                  <select name="role" class="form-control">
                     ${userRoles.map(role => `
                                 <option value="${role}" ${user.role === role ? 'selected' : ''}>
                                     ${capitalizeFirstLetter(role)}
                                 </option>
                     `).join('')}
                  </select>
                </td>
                <td><input name="name" type="text" class="form-control" value="${user.name || 'N/A'}"></td>
                <td><input name="email" type="text" class="form-control" value="${user.email || 'N/A'}"></td>
                <td><input name="mobile" type="text" class="form-control" value="${user.mobile || 'N/A'}"></td>
                <td>
                    <select name="status" class="form-control">
                        <option value="active" ${user.status === 'active' ? 'selected' : ''}>Active</option>
                        <option value="inactive" ${user.status === 'suspended' ? 'selected' : ''}>Suspended</option>
                    </select>
                </td>
                <td class="text-white">
                    <i class="fas fa-save text-success me-2" style="cursor: pointer;" onclick="updateUser(${user.id}, this)"></i>
                    <i class="fas fa-trash-alt text-danger" style="cursor: pointer;" onclick="deleteUser(${user.id})"></i>
                </td>
            `;
            userTableBody.appendChild(row);
        });
    } else {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="7" class="text-white text-center">No users found</td>`;
        userTableBody.appendChild(row);
    }

    // Add a new row for creating a new user
    addCreateUserRow();
}

function addCreateUserRow() {
    const userTableBody = document.getElementById('userTableBody');
    const createRow = document.createElement('tr');
    createRow.classList.add('new-service-row');
    createRow.setAttribute('data-type', 'new-user');

    createRow.innerHTML = `
        <td class="text-white">New</td>
        <td>
            <select name="role" class="form-control">
                ${userRoles.map(role => `
                    <option value="${role}">${capitalizeFirstLetter(role)}</option>
                `).join('')}
            </select>
        </td>
        <td><input name="name" type="text" class="form-control" id="newUserName" placeholder="Enter user name"></td>
        <td><input name="email" type="text" class="form-control" id="newUserEmail" placeholder="Enter user email"></td>
        <td><input name="mobile" type="text" class="form-control" id="newUserMobile" placeholder="Enter user mobile"></td>
        <td>
            <select name="status" class="form-control" id="newUserStatus">
                <option value="active">Active</option>
                <option value="suspended">Suspended</option>
            </select>
        </td>
        <td class="text-white">
            <i class="fas fa-save text-success" style="cursor: pointer;" onclick="createUser()"></i>
        </td>
    `;
    userTableBody.appendChild(createRow);
}

function displayError(message) {
    const userTableBody = document.getElementById('userTableBody');
    const row = document.createElement('tr');
    row.innerHTML = `<td colspan="7" class="text-white text-center">${message}</td>`;
    userTableBody.appendChild(row);
}

function createUser() {
    const lastRow = document.querySelector('tr[data-type="new-user"]');
    const name = lastRow.querySelector('input[name="name"]').value;
    const email = lastRow.querySelector('input[name="email"]').value;
    const mobile = lastRow.querySelector('input[name="mobile"]').value;
    const role = lastRow.querySelector('select[name="role"]').value
    const status = lastRow.querySelector('select[name="status"]').value;
    const password = 'Test@123';

    if (!name || !email || !mobile) {
        alert('Please fill in all required fields.');
        return;
    }

    const newUser = {
        email,
        mobile,
        name,
        password,
        role,
        status
    };

    fetch('http://localhost:8000/profile/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(newUser)
    })
        .then(response => response.json())
        .then(data => {
            if (data.id) {
                showAlert('User created successfully!', 'success');
                renderNewUser(data);
            } else {
                showAlert('Failed to create user: ' + data.error, 'danger');
                alert();
            }
        })
        .catch(error => {
            console.error('Error creating user:', error);
            alert('Failed to create user');
        });
}

function renderNewUser(user) {
    const userTableBody = document.getElementById('userTableBody');
    const newRow = document.createElement('tr');
    newRow.setAttribute('data-id', user.id);

    newRow.innerHTML = `
        <td class="text-white">${user.id || 'N/A'}</td>
         <td>
            <select name="role" class="form-control">
                ${userRoles.map(role => `
                            <option value="${role}" ${user.role === role ? 'selected' : ''}>
                                ${capitalizeFirstLetter(role)}
                            </option>
                `).join('')}
            </select>
        </td>
        <td><input name="name" type="text" class="form-control" value="${user.name || 'N/A'}"></td>
        <td><input name="email" type="text" class="form-control" value="${user.email || 'N/A'}"></td>
        <td><input name="mobile" type="text" class="form-control" value="${user.mobile || 'N/A'}"></td>
        <td>
            <select name="status" class="form-control">
                <option value="active" ${user.status === 'active' ? 'selected' : ''}>Active</option>
                <option value="suspended" ${user.status === 'suspended' ? 'selected' : ''}>Suspended</option>
            </select>
        </td>
        <td class="text-white">
            <i class="fas fa-save text-success me-2" style="cursor: pointer;" onclick="updateUser(${user.id}, this)"></i>
            <i class="fas fa-trash-alt text-danger" style="cursor: pointer;" onclick="deleteUser(${user.id})"></i>
        </td>
    `;

    const createRow = document.querySelector('tr[data-type="new-user"]');
    userTableBody.insertBefore(newRow, createRow);

    // Clear the input fields
    createRow.querySelector('input[name="name"]').value = '';
    createRow.querySelector('input[name="email"]').value = '';
    createRow.querySelector('input[name="mobile"]').value = '';
    createRow.querySelector('select[name="status"]').value = 'active';
}

function updateUser(id, button) {
    const row = button.closest('tr');
    const name = row.querySelector('input[name="name"]').value;
    const email = row.querySelector('input[name="email"]').value;
    const mobile = row.querySelector('input[name="mobile"]').value;
    const role = row.querySelector('select[name="role"]').value;
    const status = row.querySelector('select[name="status"]').value;

    const updatedUser = {
        id,
        name,
        email,
        mobile,
        role,
        status
    };

    fetch(`http://localhost:8000/profile/update`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(updatedUser)
    })
        .then(response => response.json())
        .then(data => {
            if (data) {
                showAlert('User updated successfully!', 'success');
            } else {
                showAlert('Failed to update user: ' + data.message, 'danger');
            }
        })
        .catch(error => {
            console.error('Error updating user:', error);
            alert('Failed to update user');
        });
}

function deleteUser(userId) {
    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
    confirmationModal.show();

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch(`http://localhost:8000/profile/remove/${userId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(response => {
                if (response.ok) {
                    showAlert('User deleted successfully!', 'success');
                    document.querySelector(`tr[data-id="${userId}"]`).remove();
                } else {
                    showAlert('Failed to delete the user', 'danger');
                }
            })
            .catch(error => {
                console.error('Error deleting user:', error);
                showAlert('Failed to delete the user', 'danger');
            });

        // Close the confirmation modal
        confirmationModal.hide();
    }

}

function showAlert(message, type = 'success') {
    const alertModalBody = document.getElementById('alertModalBody');
    alertModalBody.textContent = message;
    const alertModal = new bootstrap.Modal(document.getElementById('alertModal'));
    alertModal.show();
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

window.onload = function () {
    handleLocalStore();
};