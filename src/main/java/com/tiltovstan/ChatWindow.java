package com.textassistant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow {

    private static JTextArea chatHistory;
    private static JTextField userInput;

    public static void main(String[] args) {
        // Set the look and feel to the system's default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show the chat window
        SwingUtilities.invokeLater(() -> createChatWindow());
    }

    private static void createChatWindow() {
        // Create the JFrame (window)
        JFrame frame = new JFrame("Text Assistant Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);  // Center the window

        // Create the chat history (JTextArea) - non-editable area for conversation
        chatHistory = new JTextArea();
        chatHistory.setEditable(false);  // Don't allow the user to modify the chat history
        chatHistory.setLineWrap(true);
        chatHistory.setWrapStyleWord(true);

        // Make the chat history scrollable
        JScrollPane scrollPane = new JScrollPane(chatHistory);
        scrollPane.setPreferredSize(new Dimension(480, 400));

        // Create the user input field (JTextField)
        userInput = new JTextField();
        userInput.setPreferredSize(new Dimension(400, 30));
        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Create the send button
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Create a JPanel to hold the input field and send button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(userInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Show the frame
        frame.setVisible(true);

        // Focus on the text input field
        userInput.requestFocus();
    }

    // This method handles sending the message
    private static void sendMessage() {
        String input = userInput.getText().trim();
        if (!input.isEmpty()) {
            // Show the user's message in the chat history
            chatHistory.append("You: " + input + "\n");

            // Get the assistant's response based on the input
            String response = processCommand(input);
            chatHistory.append("Assistant: " + response + "\n");

            // Clear the input field
            userInput.setText("");

            // Scroll to the bottom of the chat history
            chatHistory.setCaretPosition(chatHistory.getDocument().getLength());
        }
    }

    // This method processes the user's input and returns a response
    private static String processCommand(String input) {
        switch (input.toLowerCase()) {
            case "hello":
                return "Hi there! How can I assist you today?";
            case "time":
                return "The current time is: " + java.time.LocalTime.now();
            case "open browser":
                openApplication("safari", "google");
                return "Opening browser...";
            case "open chatgpt":
                openApplication("safari", "chatgpt");
                return "Opening ChatGPT...";
            case "open spotify":
                openApplication("spotify", "");
                return "Opening Spotify...";
            case "factorio":
                launchSteamGame("427520"); // Factorio Steam game ID
                return "Launching Factorio...";
            case "exit":
                System.exit(0);
                return "Goodbye!";
            default:
                return "I didn't understand that. Try something else.";
        }
    }

    // This method simulates opening an application (like opening Safari, Spotify, etc.)
    private static void openApplication(String appName, String site) {
        try {
            String command = "";
            switch (appName) {
                case "safari":
                    if (site.equals("google")) {
                        String startPage = "https://www.google.com";
                        command = "open -a Safari " + startPage;
                    } else if (site.equals("chatgpt")) {
                        String chatgptPage = "https://chat.openai.com";
                        command = "open -a Safari " + chatgptPage;
                    } else {
                        System.out.println("Unsupported website.");
                        return;
                    }
                    break;

                case "spotify":
                    command = "open -a Spotify";  // For Spotify
                    break;

                default:
                    System.out.println("Unsupported application.");
                    return;
            }

            // Execute the command to open the application
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This method launches a Steam game based on its Steam ID
    private static void launchSteamGame(String gameId) {
        try {
            String command = "steam://rungameid/" + gameId;
            Runtime.getRuntime().exec(command);  // Execute the Steam command to run the game
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
