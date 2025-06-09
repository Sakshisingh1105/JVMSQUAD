# Contributing to Java Chat Application

Thank you for considering contributing to our Java Chat Application! This document provides guidelines and instructions for contributing.

## 🤝 How to Contribute

### Reporting Bugs

Before creating bug reports, please check the existing issues to avoid duplicates. When creating a bug report, include:

- **Clear title and description**
- **Steps to reproduce** the issue
- **Expected vs actual behavior**
- **Environment details** (OS, Java version)
- **Screenshots** if applicable
- **Error messages** or stack traces

### Suggesting Enhancements

Enhancement suggestions are welcome! Please provide:

- **Clear title and description** of the enhancement
- **Use case** explaining why this would be useful
- **Possible implementation** approach if you have ideas
- **Mockups or examples** if applicable

### Pull Requests

1. **Fork** the repository
2. **Create a branch** from `main` for your feature
3. **Make changes** following our coding standards
4. **Add tests** for new functionality
5. **Update documentation** as needed
6. **Submit a pull request**

## 🛠 Development Setup

### Prerequisites
- Java JDK 8 or higher
- Git
- IDE of choice (IntelliJ IDEA recommended)

### Setup Steps
```bash
# Clone your fork
git clone https://github.com/yourusername/JavaChatApp.git
cd JavaChatApp

# Create a new branch
git checkout -b feature/your-feature-name

# Make your changes
# Test your changes
javac src/*.java
java -cp src Server
java -cp src Client

# Commit and push
git add .
git commit -m "Add your feature description"
git push origin feature/your-feature-name
```

## 📝 Coding Standards

### Java Code Style
- Use **4 spaces** for indentation (no tabs)
- **CamelCase** for class names
- **camelCase** for method and variable names
- **UPPER_SNAKE_CASE** for constants
- **Meaningful names** for variables and methods
- **Javadoc comments** for public methods and classes

### Example Code Style
```java
/**
 * Handles client connections and message broadcasting
 */
public class ClientHandler implements Runnable {
    private static final int MAX_MESSAGE_LENGTH = 500;
    private final Socket clientSocket;
    private PrintWriter outputWriter;
    
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    
    /**
     * Broadcasts message to all connected clients
     * @param message The message to broadcast
     */
    public void broadcastMessage(String message) {
        // Implementation here
    }
}
```

### File Organization
```
src/
├── Client.java           # GUI client implementation
├── Server.java           # Server implementation
└── utils/                # Utility classes (if needed)
    ├── MessageUtils.java
    └── ValidationUtils.java
```

## 🧪 Testing Guidelines

### Manual Testing
- Test with multiple clients (2-5 concurrent connections)
- Test all user commands (/help, /users, /time, /stats)
- Test error scenarios (network disconnection, invalid input)
- Test GUI responsiveness and appearance

### Testing Checklist
- [ ] Server starts successfully
- [ ] Multiple clients can connect
- [ ] Messages are broadcasted correctly
- [ ] Commands work as expected
- [ ] Error handling works properly
- [ ] GUI is responsive and user-friendly
- [ ] Resources are properly cleaned up

## 📋 Feature Development Process

### 1. Planning Phase
- **Discuss** the feature in an issue first
- **Get approval** from maintainers
- **Plan implementation** approach

### 2. Development Phase
- **Create feature branch**
- **Implement incrementally**
- **Test thoroughly**
- **Document changes**

### 3. Review Phase
- **Self-review** your code
- **Submit pull request**
- **Address review feedback**
- **Get approval**

## 🏷 Commit Message Guidelines

Use clear and descriptive commit messages:

```
feat: add private messaging functionality
fix: resolve client disconnection issue
docs: update README with new features
style: improve GUI button styling
refactor: extract message validation logic
test: add unit tests for server commands
```

### Commit Types
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

## 🎯 Priority Areas for Contribution

### High Priority
- **Bug fixes** and stability improvements
- **Performance optimizations**
- **Security enhancements**
- **Code documentation**

### Medium Priority
- **New features** from the roadmap
- **UI/UX improvements**
- **Additional commands**
- **Better error messages**

### Low Priority
- **Code refactoring**
- **Additional tests**
- **Documentation improvements**
- **Code comments**

## 💡 Feature Ideas

Looking for inspiration? Consider these features:

### Client Features
- **Emoji picker** for messages
- **Message formatting** (bold, italic)
- **Sound notifications**
- **Custom themes**
- **Message search**
- **User avatars**

### Server Features
- **Message logging**
- **User statistics**
- **Banned words filter**
- **Rate limiting**
- **Admin commands**
- **Backup/restore**

### Technical Improvements
- **Database integration**
- **REST API**
- **WebSocket support**
- **Mobile client**
- **Message encryption**
- **Load balancing**

## 🚫 What Not to Contribute

Please avoid:
- **Plagiarized code**
- **Unrelated features**
- **Breaking changes** without discussion
- **Code without tests**
- **Poor documentation**
- **Style-only changes** without functional improvements

## 📞 Getting Help

Need help? Here are your options:

1. **GitHub Issues** - Ask questions in issues
2. **Discussions** - Use GitHub Discussions for general questions
3. **Email** - Contact maintainers directly
4. **Documentation** - Check existing docs first

## 🙏 Recognition

Contributors will be:
- **Listed** in the README
- **Mentioned** in release notes
- **Credited** in commit history
- **Thanked** publicly

## 📊 Project Statistics

Help us track our progress:
- **Issues closed**
- **PRs merged**
- **Contributors**
- **Stars received**

---

**Thank you for contributing to make this project better!** 🚀

*Happy coding!* - JVMSQUAD Team