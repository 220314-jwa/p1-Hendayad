let petsTable = document.getElementById('storysTable');
let messageBox = document.getElementById('messageBox');

checkLogin().then(setMessageBox);

function setMessageBox() {
    if (loggedInUser) {
        if (loggedInUser.storys && loggedInUser.storys.length>0) {
            showPets(loggedInUser.storys);
        } else {
            messageBox.innerText = 'You don\'t seem to have any pets. Try adopting some!';
        }
    } else {
        messageBox.innerText = 'You need to be logged in to view your pets! (You may need to refresh the page.)';
    }
}

function showStorys(petsArr) {
    storysTable.innerHTML = `<tr>
    <th>ID</th>
    <th>AuthorName</th>
    <th>Title</th>
    <th>Genre</th>
    <th>Description</th>
    <th>LengthOfStory</th>
    <th>Status</th>
    <th>CompletionData</th>
    <th>OneSentence</th>
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
        `;
        // add the row to the table
        storysTable.appendChild(row);
    }
}
