<script>
    import { onMount } from "svelte";
    
    let polls = [];

    // Fetch polls from the backend
    const getPolls = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/polls');
            if (!res.ok) {
                throw new Error(`Failed to fetch polls: ${res.statusText}`);
            }
            polls = await res.json();
        } catch (error) {
            console.error('Error fetching polls:', error);
        }
    };

    const submitVote = async (pollId, selectedOptionId) => {
    try {
        const res = await fetch('http://localhost:8080/api/votes', { // POST to the correct URL
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ optionId: selectedOptionId, pollId: pollId })
        });

        if (res.ok) {
            alert("Vote submitted successfully!");
        } else {
            alert("Failed to submit vote.");
        }
    } catch (error) {
        console.error('Error submitting vote:', error);
    }
};


    onMount(() => {
        getPolls();
    });
</script>

<h1>Available Polls</h1>

{#if polls.length === 0}
    <p>Loading polls...</p>
{:else}
    {#each polls as poll}
        <div class="poll">
            <h3>{poll.question}</h3>
            <ul>
                {#each poll.voteOptions as option}
                    <li>
                        <label>
                            <input type="radio" bind:group={poll.selectedOptionId} value={option.id} />
                            {option.caption}
                        </label>
                    </li>
                {/each}
            </ul>
            <button on:click={() => submitVote(poll.id, poll.selectedOptionId)}>Submit Vote</button>
        </div>
    {/each}
{/if}

<style>
    .poll {
        margin-bottom: 1rem;
    }
</style>
