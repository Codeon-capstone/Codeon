(function () {
    const token = document.querySelector('meta[name="_csrf"]').content;
    let upvoteButtonClass = document.querySelectorAll(".upvote-button");
    upvoteButtonClass.forEach(button => {
        button.addEventListener("click", function() {
            let idSplit = this.getAttribute("id").split("-");
            let postId = idSplit[1];
            fetch(`http://localhost:8080/upvote/${postId}`, {
                method: 'GET',
                mode: 'no-cors',
                headers: {
                    'X-CSRF-TOKEN': token
                }
            })
                .then(res => {
                    location.reload(true);
                })
        })
    })
    let downvoteButtonClass = document.querySelectorAll(".downvote-button");
    downvoteButtonClass.forEach(button => {
        button.addEventListener("click", function() {
            let idSplit = this.getAttribute("id").split("-");
            let postId = idSplit[1];
            fetch(`http://localhost:8080/downvote/${postId}`, {
                method: 'GET',
                mode: 'no-cors',
                headers: {
                    'X-CSRF-TOKEN': token
                }
            })
                .then(res => {
                    location.reload(true);
                })
        })
    })
})();