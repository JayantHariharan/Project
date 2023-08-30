console.log("*")
const fileMode = localStorage.getItem("file");

const title = document.getElementById("title");
if (fileMode === "private") {
    title.innerHTML = "PRIVATE FILES";
} else {
    title.innerHTML = "PUBLIC FILES";
}

const fileModeInput = document.getElementById("fileModeInput");
fileModeInput.value = fileMode;

async function fetchFileNames() {
    try {
          const response = await fetch(`FolderList?fileMode=${fileMode}`);
         console.log(response);
        const fileNames = await response.json();
        console.log('Fetched file names:', fileNames);
        return fileNames;
    } catch (error) {
        console.error('Error fetching file names:', error);
        throw error;
    }
}

async function displayFileNames() {
    const fileNames = await fetchFileNames();
    const container = document.getElementById('container');

    fileNames.forEach(fileName => {
        const fileElement = document.createElement('div');
        fileElement.classList.add('file-item');

        const linkElement = document.createElement('a');
        linkElement.href = 'DownloadFile?fileMode=' + encodeURIComponent(fileMode) + '&fileName=' + encodeURIComponent(fileName);
        linkElement.textContent = fileName;

        fileElement.appendChild(linkElement);
        container.appendChild(fileElement);
    });
}
document.addEventListener('DOMContentLoaded', () => {
    displayFileNames();
});

const home = document.getElementById("homebtn");
home.addEventListener("click", () => {
    console.log("btn");
    window.location.href = "fstore.html";
});
