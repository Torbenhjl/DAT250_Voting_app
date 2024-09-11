<script>
    let username = '';
    let password = '';
    let currentPage = 'login';  // This will trigger conditional page display
    let loginError = '';  // Store error message
    
    // Simulate navigation or page change
    const navigateTo = (page) => {
        currentPage = page;
    };

    const login = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/users/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (res.ok) {
                const data = await res.text(); // Check for the success message or token
                console.log('Login successful:', data);
                navigateTo('home');  // Navigate to the home page
            } else {
                const errorMessage = await res.text();  // Display server's error message
                loginError = errorMessage || 'Login failed';
                console.error('Login failed:', errorMessage);
            }
        } catch (error) {
            console.error('Error during login:', error);
            loginError = 'An error occurred during login';
        }
    };
</script>

<!-- Display error if login failed -->
{#if loginError}
    <p style="color: red;">{loginError}</p>
{/if}

{#if currentPage === 'login'}
    <h1>Login</h1>
    <input placeholder="Username" bind:value={username} />
    <input type="password" placeholder="Password" bind:value={password} />
    <button on:click={login}>Login</button>
{:else if currentPage === 'home'}
    <h1>Welcome Home!</h1>
    <!-- Add other home page content here -->
{/if}

