window.onload = function () {
    handleLocalStore();

    fetch('http://localhost:8000/service/fetch')
        .then(response => response.json())
        .then(data => {
            allServices = data;
            displayServices(allServices); // Initially display all services
        })
        .catch(error => console.error('Error fetching services:', error));
};
