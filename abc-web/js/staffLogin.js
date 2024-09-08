document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const mobile = document.getElementById('mobile').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!mobile || !password) {
        document.getElementById('errorMessage').textContent = 'Both fields are required.';
        return;
    }

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
                // Check if the user is a staff member
                if (data.role === 'staff') {
                    // Login successful for staff, save details in the local storage
                    localStorage.setItem('userName', data.name);
                    localStorage.setItem('mobile', data.mobile);
                    localStorage.setItem('email', data.email);
                    localStorage.setItem('token', data.token);

                    // Redirect to the staff home page
                    window.location.href = 'staffHome.html';
                } else {
                    // If the role is not staff, show an error message
                    document.getElementById('errorMessage').textContent = 'Login failed. You are not authorized as a staff member.';
                }
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

