<script>
    import Login from './Login.svelte';
    import Register from './Register.svelte';
    import Home from './Home.svelte';
    import Polls from './Polls.svelte';
    import CreatePoll from './CreatePoll.svelte';

    let currentPage = 'login';

    function navigateTo(page) {
        currentPage = page;
    }
</script>

<header>
    <nav>
        <button on:click={() => navigateTo('home')}>Home</button>
        <button on:click={() => navigateTo('polls')}>Polls</button>
        <button on:click={() => navigateTo('createPoll')}>Create Poll</button>
        <button on:click={() => navigateTo('login')}>Login</button>
        <button on:click={() => navigateTo('register')}>Register</button>
    </nav>
</header>

<main>
    {#if currentPage === 'home'}
        <Home />
    {:else if currentPage === 'polls'}
        <Polls />
    {:else if currentPage === 'createPoll'}
        <CreatePoll />
    {:else if currentPage === 'login'}
        <Login />
    {:else if currentPage === 'register'}
        <Register />
    {/if}
</main>
