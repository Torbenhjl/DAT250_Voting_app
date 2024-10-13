<script>
    import { createEventDispatcher } from 'svelte';

    let username = '';
    let password = '';
    let currentPage = 'login';  // This will trigger conditional page display
    let loginError = '';  // Store error message
    
    const dispatch = createEventDispatcher();  // Create event dispatcher

    // Simulate navigation or page change
    const navigateTo = (page) => {
        currentPage = page;
    };

    const login = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/users/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
                credentials: 'include' // Include credentials (session cookies)
            });

            if (res.ok) {
                const data = await res.text();
                console.log('Login successful:', data);
                loginError = '';  // Clear any previous error

                // Emit login success event for App.svelte to handle user state
                dispatch('loginSuccess');

                navigateTo('home');  // Navigate to the home page after successful login
            } else {
                const errorMessage = await res.text();
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

<!-- Login form and conditional content display -->
{#if currentPage === 'login'}
    <h1>Login</h1>
    <input placeholder="Username" bind:value={username} />
    <input type="password" placeholder="Password" bind:value={password} />
    <button on:click={login}>Login</button>
{:else if currentPage === 'home'}
    <h1>Welcome Home!</h1>
    <!-- Add other home page content here -->
{/if}
