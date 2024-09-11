<script>
    let username = '';
    let password = '';
    let email = '';

    const register = async () => {
        const res = await fetch('http://localhost:8080/api/users/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password, email })
        });

        if (res.ok) {
            alert('Registration successful');
            currentPage = 'login';
        } else {
            alert('Registration failed');
        }
    };
</script>

<h1>Register</h1>
<input placeholder="Username" bind:value={username} />
<input placeholder="Email" bind:value={email} />
<input type="password" placeholder="Password" bind:value={password} />
<button on:click={register}>Register</button>
