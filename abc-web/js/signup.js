window.onload = function () {
    handleLocalStore()
};

document.getElementById('signupForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    // Gather form data
    const name = document.getElementById('signupName').value;
    const email = document.getElementById('signupEmail').value;
    const mobile = document.getElementById('signupMobile').value;
    const address = document.getElementById('signupAddress').value;
    const password = document.getElementById('signupPassword').value;
    const confirmPassword = document.getElementById('signupConfirmPassword').value;

    // Validate passwords
    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return;
    }

    // Prepare request body
    const requestBody = {
        name: name,
        mobile: mobile,
        email: email,
        address: address,
        role: "customer",
        password: password
    };

    try {
        // Send the POST request
        const response = await fetch('http://localhost:8000/profile/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        });

        // Handle response
        if (response.ok) {
            const responseData = await response.json();
            alert('Registration successful! Redirecting to login page.');
            window.location.href = 'login.html';
        } else {
            const errorData = await response.json();
            alert(`Registration failed: ${errorData.error}`);
        }
    } catch (error) {
        alert(`An error occurred: ${error.message}`);
    }
});

