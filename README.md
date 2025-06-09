# 💬 Java Multithreaded Chat Application (GUI)

A sophisticated multi-client chat application built with Java sockets and multithreading, featuring a modern **Telegram-inspired GUI**. This application supports real-time messaging, user commands, and robust error handling.

## 🚀 Features

### Core Features
- ✅ **Multi-client support** (up to 50 concurrent users)
- ✅ **Modern Telegram-like GUI** using Java Swing
- ✅ **Real-time message broadcasting**
- ✅ **Threaded architecture** for optimal performance
- ✅ **Robust error handling** and connection management
- ✅ **Graceful server shutdown** with client notification

### Advanced Features
- 🎨 **Modern UI Design** with hover effects and responsive layout
- ⚡ **User Commands** (/help, /users, /time, /stats)
- 🔐 **Input validation** and message length limits
- 📊 **Server statistics** and monitoring
- 🕒 **Timestamped messages**
- 👥 **User join/leave notifications**
- 🔄 **Automatic reconnection handling**
- 📱 **Responsive design** with scrollable chat

## 🛠 Technologies Used

- **Java SE** - Core programming language
- **Java Swing** - GUI framework
- **Socket Programming** - Network communication
- **Multithreading** - Concurrent client handling
- **Collections Framework** - Thread-safe data structures

## 📁 Project Structure

```
JavaChatApp/
├── src/
│   ├── Client.java         # GUI Client with modern interface
│   └── Server.java         # Enhanced multithreaded server
├── docs/
│   ├── screenshots/        # Application screenshots
│   └── ARCHITECTURE.md     # Technical architecture details
├── .gitignore             # Git ignore rules
├── LICENSE                # MIT License
├── README.md              # This file
└── CONTRIBUTING.md        # Contribution guidelines
```

## 💻 Quick Start

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/JavaChatApp.git
cd JavaChatApp
```

### 2. Compile the Code
```bash
# Navigate to src directory
cd src

# Compile both files
javac Server.java Client.java
```

### 3. Run the Application

**Start the Server:**
```bash
java Server
```

**Start Client(s) (in separate terminals):**
```bash
java Client
```

### 4. Usage
1. **Launch Server**: Run the server first to start listening for connections
2. **Connect Clients**: Launch multiple client instances
3. **Enter Username**: Each client will prompt for a username
4. **Start Chatting**: Type messages and press Enter or click Send
5. **Use Commands**: Try `/help` to see available commands

## 🎮 User Commands

| Command | Description |
|---------|-------------|
| `/help` | Show all available commands |
| `/users` | List all online users |
| `/time` | Display current server time |
| `/stats` | Show server statistics and uptime |

## 🖼 Screenshots

### Client Interface
![Client GUI](docs/screenshots/client-interface.png)
*Modern chat interface with timestamp and user-friendly design*

### Server Console
![Server Console](docs/screenshots/server-console.png)
*Server monitoring with real-time client statistics*

## 🏗 Architecture

### Client-Server Model
- **Server**: Handles multiple client connections using thread-per-client model
- **Client**: Modern Swing-based GUI with separate threads for sending and receiving
- **Communication**: TCP sockets for reliable message delivery

### Threading Strategy
- **Server**: Main thread accepts connections, separate thread per client
- **Client**: GUI thread + background thread for message listening
- **Synchronization**: Thread-safe collections and proper synchronization

### Key Components
1. **ClientHandler**: Manages individual client connections
2. **Message Broadcasting**: Efficient message distribution to all clients
3. **Command Processing**: Server-side command handling
4. **GUI Components**: Modern Swing interface with event handling

## 🔧 Configuration

### Server Settings
```java
private static final int PORT = 1234;           // Server port
private static final int MAX_CLIENTS = 50;      // Maximum concurrent clients
```

### Client Settings
```java
private static final String SERVER_HOST = "localhost";  // Server address
private static final int SERVER_PORT = 1234;            // Server port
```

## 🧪 Testing

### Test Scenarios
1. **Single Client**: Basic connection and messaging
2. **Multiple Clients**: Concurrent users chatting
3. **Command Testing**: All user commands functionality
4. **Error Handling**: Network disconnection scenarios
5. **Load Testing**: Maximum client connections

### Manual Testing Steps
1. Start server
2. Connect 2-3 clients with different usernames
3. Send messages between clients
4. Test all commands (/help, /users, /time, /stats)
5. Disconnect clients and observe server behavior

## 🚀 Future Enhancements

### Planned Features
- [ ] **Private Messaging** - Direct messages between users
- [ ] **File Sharing** - Send and receive files
- [ ] **Emoji Support** - Rich text with emojis
- [ ] **Chat History** - Persistent message storage
- [ ] **User Authentication** - Login/registration system
- [ ] **Room/Channel Support** - Multiple chat rooms
- [ ] **Voice Messages** - Audio message support
- [ ] **Typing Indicators** - Show when users are typing
- [ ] **Message Encryption** - Secure communication
- [ ] **Mobile Client** - Android/iOS compatibility

### Technical Improvements
- [ ] Database integration (SQLite/MySQL)
- [ ] RESTful API for web clients
- [ ] Message queuing system
- [ ] Load balancing for multiple servers
- [ ] WebSocket support for web clients

## 🤝 Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

### How to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

**JVMSQUAD Team**
- Department of B.Tech Computer Science & Engineering
- Galgotias University - 2nd Semester GUI Project

## 🙏 Acknowledgments

- Java Socket Programming documentation
- Swing GUI design patterns
- Multithreading best practices
- Open source community


---

⭐ **Star this repository if you found it helpful!**

**Made with ❤️ by JVMSQUAD**
