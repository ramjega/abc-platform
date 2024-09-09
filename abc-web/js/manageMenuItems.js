const itemTypes = ['MEAL', 'BEVERAGE', 'CURRY', 'DESSERT', 'STARTER', 'SALAD', 'SNACK', 'SOUP', 'PASTA', 'PIZZA', 'GRILL', 'SEAFOOD', 'RISOTTO', 'TACOS', 'WRAP', 'SUSHI'];

function formatItemType(type) {
    return type.charAt(0).toUpperCase() + type.slice(1).toLowerCase();
}

// Fetch menu items when the page loads
document.addEventListener('DOMContentLoaded', function () {
    fetchMenuItems();
});

function fetchMenuItems() {
    fetch('http://localhost:8000/menu-item/fetch', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(items => {
            renderMenuItems(items);
        })
        .catch(error => {
            console.error('Error fetching menu items:', error);
            displayError('Error fetching menu item data');
        });
}

function renderMenuItems(items) {
    const menuItemTableBody = document.getElementById('menuItemTableBody');
    menuItemTableBody.innerHTML = '';

    if (items.length > 0) {
        items.forEach(item => {
            const row = document.createElement('tr');
            row.setAttribute('data-id', item.id);
            row.innerHTML = `
                <td class="text-white">${item.id || 'N/A'}</td>
                <td><input name="name" type="text" class="form-control" value="${item.name}"></td>
                <td>
                    <select name="type" class="form-control">
                        ${itemTypes.map(type => `
                            <option value="${type}" ${item.type === type ? 'selected' : ''}>
                                ${formatItemType(type)}
                            </option>
                        `).join('')}
                    </select>
                </td>
                <td><input name="description" type="text" class="form-control" value="${item.description}"></td>
                <td><input name="price" type="number" class="form-control" value="${item.price}"></td>
                <td>
                    <select name="vegetarian" class="form-control">
                        <option value="false" ${!item.vegetarian ? 'selected' : ''}>No</option>
                        <option value="true" ${item.vegetarian ? 'selected' : ''}>Yes</option>
                    </select>
                </td>
                <td class="text-white">
                    <i class="fas fa-save text-success me-2" style="cursor: pointer;" onclick="updateMenuItem(${item.id}, this)"></i>
                    <i class="fas fa-trash-alt text-danger" style="cursor: pointer;" onclick="deleteMenuItem(${item.id})"></i>
                </td>
            `;
            menuItemTableBody.appendChild(row);
        });
    } else {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="7" class="text-white text-center">No menu items found</td>`;
        menuItemTableBody.appendChild(row);
    }

    // Add a new row for creating a new menu item
    addCreateMenuItemRow();
}

function addCreateMenuItemRow() {
    const menuItemTableBody = document.getElementById('menuItemTableBody');
    const createRow = document.createElement('tr');
    createRow.classList.add('new-service-row');
    createRow.setAttribute('data-type', 'new-menu-item');

    createRow.innerHTML = `
        <td class="text-white">New</td>
        <td><input name="name" type="text" class="form-control" id="newItemName" placeholder="Enter item name"></td>
        <td>
            <select name="type" class="form-control" id="newItemType">
                ${itemTypes.map(type => `
                    <option value="${type}">${formatItemType(type)}</option>
                `).join('')}
            </select>
        </td>
        <td><input name="description" type="text" class="form-control" id="newItemDescription" placeholder="Enter item description"></td>
        <td><input name="price" type="number" class="form-control" id="newItemPrice" placeholder="Enter price"></td>
        <td>
            <select name="vegetarian" class="form-control" id="newItemVegetarian">
                <option value="false">No</option>
                <option value="true">Yes</option>
            </select>
        </td>
        <td class="text-white">
            <i class="fas fa-save text-success" style="cursor: pointer;" onclick="createMenuItem()"></i>
        </td>
    `;
    menuItemTableBody.appendChild(createRow);
}

function displayError(message) {
    const menuItemTableBody = document.getElementById('menuItemTableBody');
    const row = document.createElement('tr');
    row.innerHTML = `<td colspan="7" class="text-white text-center">${message}</td>`;
    menuItemTableBody.appendChild(row);
}

function createMenuItem() {
    const lastRow = document.querySelector('tr[data-type="new-menu-item"]');
    const name = lastRow.querySelector('input[name="name"]').value;
    const type = lastRow.querySelector('select[name="type"]').value;
    const description = lastRow.querySelector('input[name="description"]').value;
    const price = lastRow.querySelector('input[name="price"]').value;
    const vegetarian = lastRow.querySelector('select[name="vegetarian"]').value === 'true';

    if (!name || !description || !price) {
        alert('Please fill in all required fields.');
        return;
    }

    const newItem = {
        name,
        type,
        description,
        price: parseFloat(price),
        vegetarian
    };

    fetch('http://localhost:8000/menu-item/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(newItem)
    })
        .then(response => response.json())
        .then(data => {
            if (data.id) {
                alert('Menu item created successfully!');
                renderNewMenuItem(data);
            } else {
                alert('Failed to create the menu item');
            }
        })
        .catch(error => {
            console.error('Error creating menu item:', error);
            alert('Failed to create the menu item');
        });
}

function renderNewMenuItem(item) {
    const menuItemTableBody = document.getElementById('menuItemTableBody');
    const newRow = document.createElement('tr');
    newRow.setAttribute('data-id', item.id);

    newRow.innerHTML = `
        <td class="text-white">${item.id || 'N/A'}</td>
        <td><input name="name" type="text" class="form-control" value="${item.name}"></td>
        <td>
            <select name="type" class="form-control">
                ${itemTypes.map(type => `
                    <option value="${type}" ${item.type === type ? 'selected' : ''}>
                        ${formatItemType(type)}
                    </option>
                `).join('')}
            </select>
        </td>
        <td><input name="description" type="text" class="form-control" value="${item.description}"></td>
        <td><input name="price" type="number" class="form-control" value="${item.price}"></td>
        <td>
            <select name="vegetarian" class="form-control">
                <option value="false" ${!item.vegetarian ? 'selected' : ''}>No</option>
                <option value="true" ${item.vegetarian ? 'selected' : ''}>Yes</option>
            </select>
        </td>
        <td class="text-white">
            <i class="fas fa-save text-success me-2" style="cursor: pointer;" onclick="updateMenuItem(${item.id}, this)"></i>
            <i class="fas fa-trash-alt text-danger" style="cursor: pointer;" onclick="deleteMenuItem(${item.id})"></i>
        </td>
    `;

    const createRow = document.querySelector('tr[data-type="new-menu-item"]');
    menuItemTableBody.insertBefore(newRow, createRow);

    // Clear the input fields
    createRow.querySelector('input[name="name"]').value = '';
    createRow.querySelector('input[name="description"]').value = '';
    createRow.querySelector('input[name="price"]').value = '';
    createRow.querySelector('select[name="vegetarian"]').value = 'false';
}

function updateMenuItem(id, button) {
    const row = button.closest('tr');
    const name = row.querySelector('input[name="name"]').value;
    const type = row.querySelector('select[name="type"]').value;
    const description = row.querySelector('input[name="description"]').value;
    const price = row.querySelector('input[name="price"]').value;
    const vegetarian = row.querySelector('select[name="vegetarian"]').value === 'true';

    const updatedItem = {
        id,
        name,
        type,
        description,
        price: parseFloat(price),
        vegetarian
    };

    fetch(`http://localhost:8000/menu-item/update`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(updatedItem)
    })
        .then(response => response.json())
        .then(data => {
            if (data) {
                alert('Menu item updated successfully!');
            } else {
                alert('Failed to update the menu item');
            }
        })
        .catch(error => {
            console.error('Error updating menu item:', error);
            alert('Failed to update the menu item');
        });
}

function showAlert(message, type = 'success') {
    const alertModalBody = document.getElementById('alertModalBody');
    alertModalBody.textContent = message;
    const alertModal = new bootstrap.Modal(document.getElementById('alertModal'));
    alertModal.show();
}

function deleteMenuItem(id) {
    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
    confirmationModal.show();

    document.getElementById('confirmDeleteButton').onclick = function() {
        fetch(`http://localhost:8000/menu-item/remove/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(response => {
                if (response.ok) {
                    showAlert('Menu item deleted successfully!', 'success');
                    document.querySelector(`tr[data-id="${id}"]`).remove();
                } else {
                    showAlert('Failed to delete the menu item', 'danger');
                }
            })
            .catch(error => {
                console.error('Error deleting menu item:', error);
                showAlert('Failed to delete the menu item', 'danger');
            });

        // Close the confirmation modal
        confirmationModal.hide();
    };
}

window.onload = function () {
    handleLocalStore();
};
