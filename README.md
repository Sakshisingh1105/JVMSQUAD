# Java Multithreaded Chat Application

A simple console-based chat application built using Java and multithreading. This project demonstrates a basic client-server architecture where multiple clients can connect to a server and exchange messages in real time.

## 🚀 Features

- Multi-client chat support
- Real-time message broadcasting
- Console-based interface
- Clean and modular code structure using Java threads

## 🛠 Technologies Used

- Java
- Socket Programming
- Multithreading

## 📁 Project Structure

```
ChatAppJava/
│
├── Client.java        # Code for the chat client
├── Server.java        # Code for the chat server
└── README.md          # Project documentation
```

## 🧑‍💻 How to Run

### 1. Compile the Code

Open terminal in the project folder and run:
```bash
javac Server.java
javac Client.java
```

### 2. Run the Server
```bash
java Server
```

### 3. Run the Clients (in separate terminals)
```bash
java Client
```

You can run multiple clients simultaneously.

## 📷 Sample Output

**Server Console:**
```
Server started on port 1234
New client connected
Received: Hello everyone!
```

**Client Console:**
```
Connected to the chat server
Server: Hello everyone!
```

## 📌 Future Enhancements

- Add GUI using JavaFX or Swing
- Add username/login system
- Include emoji and file-sharing support

## 🙏 Acknowledgements

This project was created as part of an academic assignment to demonstrate socket programming and multithreading in Java.

---

> Created by: JVMSQUAD  
> Department of B.Tech Computer Science & Engineering.
> Section - 05
