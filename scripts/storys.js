let storysTable = document.getElementById('storysTable');
checkLogin().then(sendRequest);

async function sendRequest() {
    // sending basic GET request to /storys
    let httpResponse = await fetch('http://localhost:8080/pets');

    if (httpResponse && httpResponse.status===200) {
        let responseBody = await httpResponse.json();
        showStorys(responseBody);
    }
}

function showStorys(storysArr) {
    storysTable.innerHTML = `<tr>
    <th>ID</th>
    <th>AuthorName</th>
    <th>Genre</th>
    <th>OneSentence</th>
    <th>CompletionData</th>
    <th>LengthOfStory</th>
    <th>Title</th>
    <th>Status</th>
    <th>Description</th>
    <th>Submit</th>
    </tr>`;
    
    
    for (let story of storysArr) {
        
        let row = document.createElement('tr');
        row.innerHTML = `
        <td>${story.id}</td>
        <td>${story.status}
        <td>${story.authorname}</td>
        <td>${story.title}</td>
        <td>${story.genre}</td>
        <td>${story.description}</td>
        <td>${story.lengthOfstory}</td>
        <td>${story.onesentence}</td>
        <td>${story.completiondata}</td>
         
            <td><button type="button" id="adopt${story.id}">Submit</submit></td>
        `;
        // add the row to the table
        storysTable.appendChild(row);
        let submitBtn = document.getElementById('submit' + story.id);
        submitBtn.addEventListener('click', submitStory);
    }
}

async function submitStory() {
    let storyId = event.target.id;
    storyId = storyId.slice(5);
    let messageBox = document.getElementById('messageBox');

    let userJSON = JSON.stringify(loggedInUser);
    if (loggedInUser) {
        let httpResponse = await fetch('http://localhost:8080/storys/' + storyId + '/submit',
            {method:'PUT', body:userJSON});
        if (httpResponse && httpResponse.status===200) {
            messageBox.innerText = 'story submit successfully.';
            loggedInUser = await httpResponse.json();
            await sendRequest();
        } else {
            messageBox.innerText = 'Something went wrong, please try again.';
        }
    } else {
        messageBox.innerText = 'You have to be logged in to adopt a story. (You may need to refresh the page.)';
    }
}
