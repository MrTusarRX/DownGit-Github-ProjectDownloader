# DownGit - GitHub Project Downloader

**DownGit** is a lightweight tool that simplifies downloading specific files or entire directories from GitHub repositories. It provides an efficient and user-friendly way to fetch GitHub content without cloning the entire repository.

---

## Features

- **Selective Downloading**: Fetch specific files or directories from a GitHub repository.
- **Simple User Interface**: Straightforward interface for easy navigation and use.
- **Fast and Lightweight**: Designed for speed and low resource consumption.
- **Direct URL Input**: Enter a GitHub URL, and DownGit handles the rest.

---

## Prerequisites

To use DownGit, ensure the following:

1. **Java Development Kit (JDK)**: Version 8 or higher installed. [Download JDK](https://www.oracle.com/java/technologies/javase-jdk-downloads.html).
2. **GitHub URL**: You need the URL of the file or directory to download.

---

## Installation

1. Clone this repository:

    ```bash
    git clone https://github.com/MrTusarRX/DownGit-Github-ProjectDownloader.git
    cd DownGit-Github-ProjectDownloader
    ```

2. Build the project using Maven (if applicable):

    ```bash
    mvn clean install
    ```

3. Run the application:

    ```bash
    java -jar target/DownGit.jar
    ```

---

## Usage

1. Launch the application from the command line or GUI (if available).
2. Paste the GitHub URL of the file or directory you want to download.
3. Specify the destination folder.
4. Click **Download** to fetch the content.

### Example

- To download a specific directory from a GitHub repository:
    ```bash
    java -jar DownGit.jar --url https://github.com/username/repository/path/to/directory --destination ./downloads
    ```

- To download a single file:
    ```bash
    java -jar DownGit.jar --url https://github.com/username/repository/path/to/file --destination ./downloads
    ```

---

## Configuration Options

| Option             | Description                                                   | Required |
|--------------------|---------------------------------------------------------------|----------|
| `--url`           | URL of the GitHub file or directory to download               | Yes      |
| `--destination`   | Path to the folder where the content will be saved            | Yes      |

---

## Troubleshooting

1. **URL Not Found**
    - Ensure the provided GitHub URL is correct and publicly accessible.

2. **Permission Denied**
    - Verify that you have write permissions for the destination directory.

3. **Java Runtime Error**
    - Ensure Java is installed and the version meets the requirements.

---

## Contributing

Contributions are welcome! If you'd like to enhance this project, follow these steps:

1. Fork the repository.
2. Create a feature branch:
    ```bash
    git checkout -b feature-name
    ```
3. Commit your changes:
    ```bash
    git commit -m "Description of the feature"
    ```
4. Push your branch and open a pull request.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Contact

For questions or suggestions, reach out:

- **GitHub**: [MrTusarRX](https://github.com/MrTusarRX)

