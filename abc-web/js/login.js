document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way

    // Get input values
    const mobile = document.getElementById('mobile').value.trim();
    const password = document.getElementById('password').value.trim();

    // Perform basic validation
    if (!mobile || !password) {
        document.getElementById('errorMessage').textContent = 'Both fields are required.';
        return;
    }

    // Prepare request data
    const requestData = {
        mobile: mobile,
        password: password
    };

    // Send login request
    fetch('http://localhost:8000/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                // Login successful, save details in the local storage
                localStorage.setItem('userName', data.name);
                localStorage.setItem('mobile', data.mobile);
                localStorage.setItem('email', data.email);
                localStorage.setItem('token', data.token);

                window.location.href = 'index.html'; // Redirect to home page
            } else {
                // Login failed, show error message
                document.getElementById('errorMessage').textContent = data.error || 'Login failed. Please try again.';
            }
        })
        .catch(error => {
            document.getElementById('errorMessage').textContent = 'An error occurred. Please try again.';
            console.error('Error:', error);
        });
});

window.onload = function () {
    handleLocalStore()
};

