<script>
    let question = '';
    let voteOptions = [''];
    let validUntil = '';  // Store the validUntil field
    let isPrivate = false;  // To store whether the poll is private or public

    const addOption = () => {
        voteOptions = [...voteOptions, ''];
    };

    const submitPoll = async () => {
        const options = voteOptions.map((option, index) => ({
            caption: option,
            presentationOrder: index + 1
        }));

        const pollData = {
            question,
            voteOptions: options,
            validUntil,
            isPrivate  // Include isPrivate field in the request body
        };

        console.log("poll Data:", pollData);

        const res = await fetch('http://localhost:8080/api/polls', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(pollData),
            credentials: 'include'
        });

        if (res.ok) {
            alert('Poll created successfully');
        } else {
            alert('Failed to create poll');
        }
    };
</script>

<h1>Create a Poll</h1>

<input placeholder="Poll Question" bind:value={question} />

{#each voteOptions as option, index}
    <input placeholder="Option" bind:value={voteOptions[index]} />
{/each}

<button on:click={addOption}>Add Option</button>

<label>Valid Until:</label>
<input type="datetime-local" bind:value={validUntil} />

<!-- Checkbox for private poll -->
<label>Private Poll:</label>
<input type="checkbox" bind:checked={isPrivate} />

<button on:click={submitPoll}>Submit Poll</button>
