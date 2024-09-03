let allServices = [];

window.onload = function () {
    fetch('http://localhost:8000/service/fetch')
        .then(response => response.json())
        .then(data => {
            allServices = data;
            displayServices(allServices); // Initially display all services
        })
        .catch(error => console.error('Error fetching services:', error));
};

function displayServices(services) {
    // Get the container for the services
    const container = document.querySelector('.service-container');
    container.innerHTML = '';

    services.forEach(service => {
        // Create a new service item
        const serviceElement = document.createElement('div');
        serviceElement.classList.add('col-lg-3', 'col-sm-6', 'wow', 'fadeInUp');
        serviceElement.setAttribute('data-wow-delay', '0.1s'); // Adjust delay based on index or other logic

        const imageUrl = getImage(service.name);

        serviceElement.innerHTML = `
                  <div class="service-item rounded pt-4">
                     <div class="p-4 text-center">
                      <img src="${imageUrl}" alt="" class="img-fluid mb-4" style="max-width: 150px; max-height: 150px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
                      <h5>${service.name}</h5>
                     <p>${service.description}</p>
                   </div>
                  </div>
                `;

        // Append the new service item to the container
        container.appendChild(serviceElement);
    })

}

function filterServices() {
    const searchTerm = document.getElementById('serviceSearch').value.toLowerCase();
    const filteredServices = allServices.filter(service => service.name.toLowerCase().includes(searchTerm));
    displayServices(filteredServices);
}

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
