// Common functions which are used in multiple pages

// deals with the local store and show menu conditionally
function handleLocalStore() {
    const userName = localStorage.getItem('userName');
    const loginLink = document.querySelector('a[href="login.html"]');
    const staffLoginLink = document.querySelector('a[href="staffLogin.html"]');
    const adminLoginLink = document.querySelector('a[href="adminLogin.html"]');
    const signupLink = document.querySelector('a[href="signup.html"]');
    const usernameDisplay = document.getElementById('usernameDisplay');

    if (userName) {
        // User is logged in
        if (loginLink) loginLink.style.display = 'none';
        if (staffLoginLink) staffLoginLink.style.display = 'none';
        if (adminLoginLink) adminLoginLink.style.display = 'none';
        if (signupLink) signupLink.style.display = 'none';
        if (usernameDisplay) usernameDisplay.innerText = `${userName}`;
    } else {
        // User is not logged in
        if (loginLink) loginLink.style.display = 'block'; // Show login link
        if (signupLink) signupLink.style.display = 'block'; // Show signup link
        if (usernameDisplay) usernameDisplay.style.display = 'none'; // Hide Display username
    }
}

// removes the saved information during logout
function logoutUser() {
    // Remove the user's data from local storage
    localStorage.removeItem('userName');
    localStorage.removeItem('token');

    // Redirect to the homepage
    window.location.href = 'index.html';
}

// search services
function filterServices() {
    const searchTerm = document.getElementById('serviceSearch').value.toLowerCase();
    const filteredServices = allServices.filter(service => service.name.toLowerCase().includes(searchTerm));
    displayServices(filteredServices);
}

function displayServices(services) {
    // Get the container for the services
    const container = document.querySelector('.service-container');
    container.innerHTML = '';

    services.forEach(service => {
        // Create a new service item
        <!-- Inside displayServices function -->
        const serviceElement = document.createElement('div');
        serviceElement.classList.add('col-lg-3', 'col-sm-6', 'wow', 'fadeInUp');
        serviceElement.setAttribute('data-wow-delay', '0.1s'); // Adjust delay based on index or other logic

        const imageUrl = getImage(service.name);

        serviceElement.innerHTML = `
          <div class="service-item rounded pt-4">
             <div class="p-4 text-center">
              <a href="booking.html?serviceID=${service.id}" class="text-decoration-none">
                <img src="${imageUrl}" alt="" class="img-fluid mb-4" style="max-width: 150px; max-height: 150px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
                <h5>${service.name}</h5>
                <p>${service.description}</p>
              </a>
             </div>
          </div>
        `;

        container.appendChild(serviceElement);
    })

}

// Dynamically change images based on the service type
function getImage(serviceName) {
    switch (serviceName) {
        case 'Dine-In':
            return '/img/dine-in.jpg';
        case 'Private Dining':
            return '/img/private-dine-in.jpg';
        case 'Takeout':
            return '/img/take-out.jpg';
        case 'Delivery':
            return '/img/delivery.jpg';
        case 'Catering':
            return '/img/catering.jpg';
        case 'Event Hosting':
            return '/img/event-hosting.jpg';
        case '24/7 Customer Support':
            return '/img/customer-support.jpg';
        default:
            return '/img/dine-in.jpg';
    }
}

// check if the user is logged in
function isLoggedIn() {
    return !!localStorage.getItem('userName');
}

document.addEventListener('DOMContentLoaded', function() {
    // Retrieve values from localStorage
    const storedName = localStorage.getItem('userName');
    const storedEmail = localStorage.getItem('email');
    const storedMobile = localStorage.getItem('mobile');

    // Set the values in the form inputs if they exist
    if (storedName) {
        document.getElementById('name').value = storedName;
    }
    if (storedEmail) {
        document.getElementById('email').value = storedEmail;
    }
    if (storedMobile) {
        document.getElementById('mobile').value = storedMobile;
    }
});

document.getElementById('logoutButton').onclick = logoutUser;

function formatText(text) {
    return text
        .replace(/_/g, ' ')       // Replace underscores with spaces
        .replace(/^\w/, c => c.toUpperCase()); // Capitalize the first letter
}

function formatDate(dateString) {
    const date = new Date(dateString);

    // Define options for formatting
    const options = {
        month: '2-digit',
        day: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: true // To use AM/PM
    };

    // Format the date
    const formatter = new Intl.DateTimeFormat('en-US', options);
    const formattedDate = formatter.format(date);

    // Replace the comma with an empty space for the desired format
    return formattedDate.replace(',', '');
}