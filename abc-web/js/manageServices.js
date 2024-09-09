const serviceTypes = ['dine_in', 'private_dine_in', 'take_out', 'delivery', 'catering', 'event_hosting', 'additional'];

// Utility function to capitalize and format service types
function formatServiceType(type) {
    return type.split('_').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
}

// Fetch service data when the page loads
document.addEventListener('DOMContentLoaded', function () {
    fetchServiceData();
});

function fetchServiceData() {
    fetch('http://localhost:8000/service/fetch', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(services => {
            renderServices(services);
        })
        .catch(error => {
            console.error('Error fetching services:', error);
            displayError('Error fetching service data');
        });
}

function renderServices(services) {
    const serviceTableBody = document.getElementById('serviceTableBody');
    serviceTableBody.innerHTML = '';

    if (services.length > 0) {
        services.forEach(service => {
            const row = document.createElement('tr');
            row.setAttribute('data-id', service.id);
            row.innerHTML = `
                <td class="text-white">${service.id || 'N/A'}</td>
                <td><input name="name" type="text" class="form-control" value="${service.name}"></td>
                <td><input name="description" type="text" class="form-control" value="${service.description}"></td>
                <td>
                    <select name="serviceType" class="form-control">
                        ${serviceTypes.map(type => `
                            <option value="${type}" ${service.serviceType === type ? 'selected' : ''}>
                                ${formatServiceType(type)}
                            </option>
                        `).join('')}
                    </select>
                </td>
                <td><input name="price" type="number" class="form-control" value="${service.price}"></td>
                <td>
                    <select name="additionalService" class="form-control">
                        <option value="false" ${!service.additionalService ? 'selected' : ''}>No</option>
                        <option value="true" ${service.additionalService ? 'selected' : ''}>Yes</option>
                    </select>
                </td>
                <td class="text-white">
                    <i class="fas fa-save text-success me-2" style="cursor: pointer;" onclick="updateService(${service.id}, this)"></i>
                    <i class="fas fa-trash-alt text-danger" style="cursor: pointer;" onclick="deleteService(${service.id})"></i>
                </td>
            `;
            serviceTableBody.appendChild(row);
        });
    } else {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="7" class="text-white text-center">No services found</td>`;
        serviceTableBody.appendChild(row);
    }

    // Add a new row for creating a new service
    addCreateServiceRow();
}

function addCreateServiceRow() {
    const serviceTableBody = document.getElementById('serviceTableBody');
    const createRow = document.createElement('tr');
    createRow.classList.add('new-service-row');
    createRow.setAttribute('data-type', 'new-service');

    createRow.innerHTML = `
        <td class="text-white">New</td>
        <td><input name="name" type="text" class="form-control" id="newServiceName" placeholder="Enter service name"></td>
        <td><input name="description" type="text" class="form-control" id="newServiceDescription" placeholder="Enter service description"></td>
        <td>
            <select name="serviceType" class="form-control">
                ${serviceTypes.map(type => `
                    <option value="${type}">${formatServiceType(type)}</option>
                `).join('')}
            </select>
        </td>
        <td><input name="price" type="number" class="form-control" id="newServicePrice" placeholder="Enter price"></td>
        <td>
            <select name="additionalService" class="form-control" id="newAdditionalService">
                <option value="false">No</option>
                <option value="true">Yes</option>
            </select>
        </td>
        <td class="text-white">
            <i class="fas fa-save text-success" style="cursor: pointer;" onclick="createService()"></i>
        </td>
    `;
    serviceTableBody.appendChild(createRow);
}

function displayError(message) {
    const serviceTableBody = document.getElementById('serviceTableBody');
    const row = document.createElement('tr');
    row.innerHTML = `<td colspan="7" class="text-white text-center">${message}</td>`;
    serviceTableBody.appendChild(row);
}

function createService() {
    const lastRow = document.querySelector('tr[data-type="new-service"]');
    const name = lastRow.querySelector('input[name="name"]').value;
    const description = lastRow.querySelector('input[name="description"]').value;
    const serviceType = lastRow.querySelector('select[name="serviceType"]').value;
    const price = lastRow.querySelector('input[name="price"]').value;
    const additionalService = lastRow.querySelector('select[name="additionalService"]').value === 'true';

    if (!name || !description || !price) {
        alert('Please fill in all required fields.');
        return;
    }

    const newService = {
        name,
        description,
        serviceType,
        price: parseFloat(price),
        additionalService
    };

    fetch('http://localhost:8000/service/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(newService)
    })
        .then(response => response.json())
        .then(data => {
            if (data) {
                alert('Service created successfully!');
                renderNewService(data);
            } else {
                alert('Failed to create the service');
            }
        })
        .catch(error => {
            console.error('Error creating service:', error);
            alert('Failed to create the service');
        });
}

function renderNewService(service) {
    const serviceTableBody = document.getElementById('serviceTableBody');
    const newRow = document.createElement('tr');
    newRow.setAttribute('data-id', service.id);

    newRow.innerHTML = `
        <td class="text-white">${service.id || 'N/A'}</td>
        <td><input name="name" type="text" class="form-control" value="${service.name}"></td>
        <td><input name="description" type="text" class="form-control" value="${service.description}"></td>
        <td>
            <select name="serviceType" class="form-control">
                ${serviceTypes.map(type => `
                    <option value="${type}" ${service.serviceType === type ? 'selected' : ''}>
                        ${formatServiceType(type)}
                    </option>
                `).join('')}
            </select>
        </td>
        <td><input name="price" type="number" class="form-control" value="${service.price}"></td>
        <td>
            <select name="additionalService" class="form-control">
                <option value="false" ${!service.additionalService ? 'selected' : ''}>No</option>
                <option value="true" ${service.additionalService ? 'selected' : ''}>Yes</option>
            </select>
        </td>
        <td class="text-white">
            <i class="fas fa-save text-success me-2" style="cursor: pointer;" onclick="updateService(${service.id}, this)"></i>
            <i class="fas fa-trash-alt text-danger" style="cursor: pointer;" onclick="deleteService(${service.id})"></i>
        </td>
    `;

    const createRow = document.querySelector('tr[data-type="new-service"]');
    serviceTableBody.insertBefore(newRow, createRow);

    // Clear the input fields
    createRow.querySelector('input[name="name"]').value = '';
    createRow.querySelector('input[name="description"]').value = '';
    createRow.querySelector('input[name="price"]').value = '';
    createRow.querySelector('select[name="additionalService"]').value = 'false';
}

function updateService(id, button) {
    const row = button.closest('tr');
    const name = row.querySelector('input[name="name"]').value;
    const description = row.querySelector('input[name="description"]').value;
    const serviceType = row.querySelector('select[name="serviceType"]').value;
    const price = row.querySelector('input[name="price"]').value;
    const additionalService = row.querySelector('select[name="additionalService"]').value === 'true';

    const updatedService = {
        id,
        name,
        description,
        serviceType,
        price: parseFloat(price),
        additionalService
    };

    fetch(`http://localhost:8000/service/update`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(updatedService)
    })
        .then(response => response.json())
        .then(data => {
            if (data) {
                alert('Service updated successfully!');
            } else {
                alert('Failed to update the service');
            }
        })
        .catch(error => {
            console.error('Error updating service:', error);
            alert('Failed to update the service');
        });
}

function deleteService(serviceId) {
    fetch(`http://localhost:8000/service/remove/${serviceId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data) {
                alert('Service deleted successfully!');
                document.querySelector(`tr[data-id="${serviceId}"]`).remove();
            } else {
                alert('Failed to delete the service');
            }
        })
        .catch(error => {
            console.error('Error deleting service:', error);
            alert('Failed to delete the service');
        });
}

window.onload = function () {
    handleLocalStore();
};