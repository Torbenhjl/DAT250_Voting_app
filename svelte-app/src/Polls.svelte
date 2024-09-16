<script>
    import { onMount } from "svelte";
    
    let polls = [];
    let submitting = false;  // Track if a vote is being submitted
    let error = '';  // Track if any error happens

    // Fetch polls from the backend
 // Log the received polls to see if isPrivate is correct
 const getPolls = async () => {
    try {
        const res = await fetch('http://localhost:8080/api/polls', {
            credentials: 'include',
        });
        if (!res.ok) {
            throw new Error(`Failed to fetch polls: ${res.statusText}`);
        }
        polls = await res.json();
        
        // Log the full response to ensure the voteCount is correct
        console.log("Received Polls:", polls);  // Ensure this matches your database vote counts

        polls = polls.map(poll => ({ ...poll, selectedOptionId: null }));
    } catch (error) {
        console.error('Error fetching polls:', error);
    }
};




    // Submit the selected vote
    const submitVote = async (pollId, selectedOptionId, isUpvote) => {
        if (!selectedOptionId) {
            error = 'Please select an option before submitting your vote.';
            return;
        }

        try {
            submitting = true;  // Disable further submissions while submitting
            error = '';  // Clear any previous errors

            const res = await fetch('http://localhost:8080/api/votes', { 
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ optionId: selectedOptionId, pollId: pollId, isUpvote }),
                credentials: 'include'
            });

            if (res.ok) {
                alert("Vote submitted successfully!");
                getPolls();  // Refresh polls after voting
            } else {
                const errorMessage = await res.text();
                alert("Failed to submit vote: " + errorMessage);
            }
        } catch (error) {
            console.error('Error submitting vote:', error);
            alert('An error occurred while submitting your vote.');
        } finally {
            submitting = false;  // Re-enable submission after completing
        }
    };

    // Delete a poll
    const deletePoll = async (pollId) => {
        if (confirm("Are you sure you want to delete this poll?")) {
            try {
                const res = await fetch(`http://localhost:8080/api/polls/${pollId}`, {
                    method: 'DELETE',
                    credentials: 'include'
                });

                if (res.ok) {
                    alert("Poll deleted successfully!");
                    getPolls();  // Refresh polls after deletion
                } else {
                    alert("Failed to delete poll.");
                }
            } catch (error) {
                console.error('Error deleting poll:', error);
                alert('An error occurred while deleting the poll.');
            }
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
            <p><strong>Status:</strong> {poll.isPrivate ? 'Private' : 'Public'}</p>
            <p><strong>Published At:</strong> {new Date(poll.publishedAt).toLocaleString()}</p>
            <p><strong>Valid Until:</strong> {poll.validUntil ? new Date(poll.validUntil).toLocaleString() : 'No expiration date'}</p>
            <ul>
                {#each poll.voteOptions as option}
                    <li>
                        <label>
                            <input type="radio" bind:group={poll.selectedOptionId} value={option.id} />
                            {option.caption} ({option.voteCount || 0} votes)
                            

                        </label>
                    </li>
                {/each}
            </ul>
            <button on:click={() => submitVote(poll.id, poll.selectedOptionId, true)} disabled={submitting} class = "upvote">
                {submitting ? 'Submitting...' : 'Upvote'}
                
            </button>
            <button on:click={() => submitVote(poll.id, poll.selectedOptionId, false)} disabled={submitting} class = "downvote">
                {submitting ? 'Submitting...' : 'Downvote'}
            </button>
            <button on:click={() => deletePoll(poll.id)} class="delete-button">
                Delete Poll
            </button>
        </div>
    {/each}
{/if}


{#if error}
    <p style="color: red;">{error}</p>
{/if}

<style>
    .poll {
        margin-bottom: 1rem;
    }
    button[disabled] {
        opacity: 0.6;
        cursor: not-allowed;
        
    }
    .delete-button {
        background-color: rgb(195, 81, 81);
        color: white;
        margin-left: 10px;
        cursor: pointer;
    }
    .upvote {
        color: rgb(0, 0, 0);
    }
    .upvote {
        background: linear-gradient(90deg, #5eff00 0%, #a3efab 100%);
        
    }
    .downvote {
        background: linear-gradient(90deg, #ff0000 0%, #e98c8c 100%);
        
    }
    
</style>
