<script>
    let question = '';
    let voteOptions = [''];

    const addOption = () => {
        voteOptions = [...voteOptions, ''];
    };

    const submitPoll = async () => {
        const options = voteOptions.map((option, index) => ({
            caption: option,
            presentationOrder: index + 1
        }));

        const res = await fetch('http://localhost:8080/api/polls', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ question, voteOptions: options })
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
<button on:click={submitPoll}>Submit Poll</button>
