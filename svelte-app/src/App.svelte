<script>
    import { onMount } from 'svelte'; 
    import Login from './Login.svelte';
    import Register from './Register.svelte';
    import Home from './Home.svelte';
    import Polls from './Polls.svelte';
    import CreatePoll from './CreatePoll.svelte';

    let currentPage = 'login';  // Default page is 'login'
    let currentUser = null;     // Logged-in user
    let error = '';

    // Function to fetch the current user
    const getCurrentUser = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/users/current', {
                credentials: 'include',
            });
            if (res.ok) {
                currentUser = await res.json();
                if (currentUser) {
                    currentPage = 'home';  // Redirect to 'home' if user is logged in
                }
            } else {
                currentUser = null;
            }
        } catch (err) {
            console.error('Error fetching user:', err);
            error = 'Could not fetch the logged-in user.';
        }
    };

    // Function to handle logout
    const logout = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/users/logout', {
                method: 'POST',
                credentials: 'include'
            });
            if (res.ok) {
                currentUser = null;  // Clear the currentUser
                currentPage = 'login';  // Redirect to login
            } else {
                alert('Failed to log out');
            }
        } catch (error) {
            console.error('Error logging out:', error);
            alert('An error occurred while logging out');
        }
    };

    // Navigation function
    function navigateTo(page) {
        currentPage = page;
    }

    // Fetch the current user when the app loads
    onMount(() => {
        getCurrentUser();
    });
</script>

<header>
    <nav>
        {#if currentUser}
            <span>Logged in as: {currentUser.username}</span>
            <button on:click={() => navigateTo('home')}>Home</button>
            <button on:click={() => navigateTo('polls')}>Polls</button>
            <button on:click={() => navigateTo('createPoll')}>Create Poll</button>
            <button on:click={logout}>Logout</button>
        {:else}
            <button on:click={() => navigateTo('login')}>Login</button>
            <button on:click={() => navigateTo('register')}>Register</button>
        {/if}
    </nav>
</header>

<main>
    {#if currentPage === 'home' && currentUser}
        <Home />
    {:else if currentPage === 'polls' && currentUser}
        <Polls />
    {:else if currentPage === 'createPoll' && currentUser}
        <CreatePoll />
    {:else if currentPage === 'login'}
        <Login on:loginSuccess={() => getCurrentUser()} />
    {:else if currentPage === 'register'}
        <Register />
    {/if}
</main>

{#if error}
    <p style="color: red;">{error}</p>
{/if}

<style>
    nav {
        display: flex;
        gap: 1rem;
    }
    span {
        margin-right: auto;  /* Push other buttons to the right */
    }
</style>
