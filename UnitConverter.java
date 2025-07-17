// Jason He
// 2025
// UnitConverter.java
/// PLEASE READ THE README.md FILE FOR MORE INFORMATION BASED ON USAGE AND 
/// REQUIREMENTS.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class UnitConverter extends JFrame
{
    private JPanel mainPanel; // Main panel that holds all pages
    private CardLayout cardLayout; // CardLayout for switching between pages
    private Map<String, JTextField> inputFields; // Stores input fields for each conversion page
    private Map<String, JComboBox<String>> fromUnitCombos; // Stores 'from' unit dropdowns for each conversion page
    private Map<String, JComboBox<String>> toUnitCombos; // Stores 'to' unit dropdowns for each conversion page
    private Map<String, JLabel> resultLabels; // Stores result labels for each conversion page
    private Map<String, JButton> convertButtons; // Stores convert buttons for each conversion page
    private Map<String, JButton> clearButtons; // Stores clear buttons for each conversion page
    private Map<String, Map<String, Double>> conversionFactors; // Conversion factors for different units
    private List<String> pastConversions; // List to store past conversions
    private JTextArea pastConversionsArea; // Text area to display past conversions
    private String currentPage = "Home"; // Tracks the current page

    public UnitConverter()
    {
        setTitle("Unit Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Initialize maps for components
        inputFields = new HashMap<>();
        fromUnitCombos = new HashMap<>();
        toUnitCombos = new HashMap<>();
        resultLabels = new HashMap<>();
        convertButtons = new HashMap<>();
        clearButtons = new HashMap<>();
        
        // Initialize past conversions
        pastConversions = new ArrayList<>();

        initializeConversionFactors();
        createGUI();
    }

    private void initializeConversionFactors()
    {
        conversionFactors = new HashMap<>();

        // Length/Distance conversions
        Map<String, Double> lengthFactors = new HashMap<>();
        lengthFactors.put("Meters", 1.0);
        lengthFactors.put("Kilometers", 1000.0);
        lengthFactors.put("Centimeters", 0.01);
        lengthFactors.put("Millimeters", 0.001);
        lengthFactors.put("Miles", 1609.344);
        lengthFactors.put("Yards", 0.9144);
        lengthFactors.put("Feet", 0.3048);
        lengthFactors.put("Inches", 0.0254);
        conversionFactors.put("Length", lengthFactors);

        // Mass/Weight conversions
        Map<String, Double> massFactors = new HashMap<>();
        massFactors.put("Kilograms", 1.0);
        massFactors.put("Grams", 0.001);
        massFactors.put("Milligrams", 0.000001);
        massFactors.put("Pounds", 0.45359237);
        massFactors.put("Ounces", 0.028349523125);
        massFactors.put("Tons", 907.18474);
        conversionFactors.put("Mass", massFactors);

        // Time conversions
        Map<String, Double> timeFactors = new HashMap<>();
        timeFactors.put("Seconds", 1.0);
        timeFactors.put("Minutes", 60.0);
        timeFactors.put("Hours", 3600.0);
        timeFactors.put("Days", 86400.0);
        timeFactors.put("Weeks", 604800.0);
        timeFactors.put("Years", 31536000.0);
        conversionFactors.put("Time", timeFactors);

        // Temperature conversions (special handling needed)
        Map<String, Double> tempFactors = new HashMap<>();
        tempFactors.put("Celsius", 1.0);
        tempFactors.put("Fahrenheit", 1.0);
        tempFactors.put("Kelvin", 1.0);
        conversionFactors.put("Temperature", tempFactors);

        // Volume/Capacity conversions
        Map<String, Double> volumeFactors = new HashMap<>();
        volumeFactors.put("Liters", 1.0);
        volumeFactors.put("Milliliters", 0.001);
        volumeFactors.put("Cubic Meters", 1000.0);
        volumeFactors.put("Gallons", 3.78541);
        volumeFactors.put("Quarts", 0.946353);
        volumeFactors.put("Pints", 0.473176);
        volumeFactors.put("Cups", 0.236588);
        conversionFactors.put("Volume", volumeFactors);

        // Area conversions
        Map<String, Double> areaFactors = new HashMap<>();
        areaFactors.put("Square Meters", 1.0);
        areaFactors.put("Square Kilometers", 1000000.0);
        areaFactors.put("Square Centimeters", 0.0001);
        areaFactors.put("Square Miles", 2589988.110336);
        areaFactors.put("Acres", 4046.8564224);
        areaFactors.put("Square Feet", 0.09290304);
        areaFactors.put("Square Inches", 0.00064516);
        conversionFactors.put("Area", areaFactors);

        // Energy conversions
        Map<String, Double> energyFactors = new HashMap<>();
        energyFactors.put("Joules", 1.0);
        energyFactors.put("Kilojoules", 1000.0);
        energyFactors.put("Calories", 4.184);
        energyFactors.put("Kilocalories", 4184.0);
        energyFactors.put("Watt-hours", 3600.0);
        energyFactors.put("Kilowatt-hours", 3600000.0);
        conversionFactors.put("Energy", energyFactors);

        // Power conversions
        Map<String, Double> powerFactors = new HashMap<>();
        powerFactors.put("Watts", 1.0);
        powerFactors.put("Kilowatts", 1000.0);
        powerFactors.put("Horsepower", 745.7);
        powerFactors.put("BTU/hour", 0.29307107);
        conversionFactors.put("Power", powerFactors);
    }

    private void createGUI()
    {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create home page
        mainPanel.add(createHomePage(), "Home");

        // Create conversions page
        mainPanel.add(createConversionsPage(), "Conversions");

        // Create past conversions page
        mainPanel.add(createPastConversionsPage(), "PastConversions");

        // Create pages for each conversion type
        String[] pages = { "Length", "Mass", "Time", "Temperature", "Volume", "Area", "Energy", "Power" };

        for (String page : pages)
        {
            mainPanel.add(createPage(page), page);
        }

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHomePage()
    {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(240, 248, 255));

        // Title
        JLabel titleLabel = new JLabel("Unit Converter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(25, 25, 112));

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome to the comprehensive unit converter!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setForeground(new Color(70, 130, 180));

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton conversionsButton = new JButton("Conversions");
        conversionsButton.setFont(new Font("Arial", Font.BOLD, 18));
        conversionsButton.setBackground(new Color(70, 130, 180));
        conversionsButton.setForeground(Color.BLACK); // Changed to black for readability
        conversionsButton.setFocusPainted(false);
        conversionsButton.addActionListener(e -> cardLayout.show(mainPanel, "Conversions"));

        JButton historyButton = new JButton("Past Conversions");
        historyButton.setFont(new Font("Arial", Font.BOLD, 18));
        historyButton.setBackground(new Color(46, 139, 87));
        historyButton.setForeground(Color.BLACK); // Changed to black for readability
        historyButton.setFocusPainted(false);
        historyButton.addActionListener(e -> {
            updatePastConversionsDisplay();
            cardLayout.show(mainPanel, "PastConversions");
        });

        buttonPanel.add(conversionsButton);
        buttonPanel.add(historyButton);

        // Add components
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createConversionsPage()
    {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Navigation panel
        JPanel navPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] pages = { "Length", "Mass", "Time", "Temperature", "Volume", "Area", "Energy", "Power" };

        for (String page : pages)
        {
            JButton navButton = new JButton(page);
            navButton.addActionListener(e ->
            {
                cardLayout.show(mainPanel, page);
                currentPage = page;
            });
            navButton.setForeground(Color.BLACK); // Changed to black for readability
            navPanel.add(navButton);
        }

        // Home button
        JButton homeButton = new JButton("← Home");
        homeButton.setBackground(new Color(220, 20, 60));
        homeButton.setForeground(Color.BLACK); // Changed to black for readability
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(homeButton, BorderLayout.WEST);
        topPanel.add(navPanel, BorderLayout.CENTER);

        // Placeholder for conversion area
        JLabel placeholderLabel = new JLabel("Select a conversion type from above", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        placeholderLabel.setForeground(Color.GRAY);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(placeholderLabel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPastConversionsPage()
    {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel titleLabel = new JLabel("Past Conversions", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Past conversions area
        pastConversionsArea = new JTextArea();
        pastConversionsArea.setEditable(false);
        pastConversionsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(pastConversionsArea);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton homeButton = new JButton("← Home");
        homeButton.setBackground(new Color(220, 20, 60));
        homeButton.setForeground(Color.BLACK); // Changed to black for readability
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        JButton clearHistoryButton = new JButton("Clear History");
        clearHistoryButton.setBackground(new Color(255, 140, 0));
        clearHistoryButton.setForeground(Color.BLACK); // Changed to black for readability
        clearHistoryButton.addActionListener(e ->
        {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to clear all past conversions?",
                "Confirm Clear History",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION)
            {
                pastConversions.clear();
                updatePastConversionsDisplay();
            }
        });

        buttonPanel.add(homeButton);
        buttonPanel.add(clearHistoryButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updatePastConversionsDisplay()
    {
        StringBuilder sb = new StringBuilder();
        if (pastConversions.isEmpty())
        {
            sb.append("No past conversions yet.\n");
            sb.append("Start converting units to see your history here!");
        }
        else
        {
            for (String conversion : pastConversions)
            {
                sb.append(conversion).append("\n");
            }
        }
        pastConversionsArea.setText(sb.toString());
    }

    private JPanel createPage(String pageName)
    {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header with home button
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel pageTitle = new JLabel(pageName + " Converter", SwingConstants.CENTER);
        pageTitle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton homeButton = new JButton("← Home");
        homeButton.setBackground(new Color(220, 20, 60));
        homeButton.setForeground(Color.BLACK); // Changed to black for readability
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        headerPanel.add(homeButton, BorderLayout.WEST);
        headerPanel.add(pageTitle, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel inputLabel = new JLabel("Enter value (1-100,000,000):");
        JTextField inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputFields.put(pageName, inputField);

        JLabel fromLabel = new JLabel("From:");
        JComboBox<String> fromUnitCombo = new JComboBox<>();
        fromUnitCombo.setPreferredSize(new Dimension(120, 25));
        fromUnitCombos.put(pageName, fromUnitCombo);

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(fromLabel);
        inputPanel.add(fromUnitCombo);

        // Output panel
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel toLabel = new JLabel("To:");
        JComboBox<String> toUnitCombo = new JComboBox<>();
        toUnitCombo.setPreferredSize(new Dimension(120, 25));
        toUnitCombos.put(pageName, toUnitCombo);

        JLabel resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(new Color(0, 100, 0));
        resultLabels.put(pageName, resultLabel);

        outputPanel.add(toLabel);
        outputPanel.add(toUnitCombo);
        outputPanel.add(resultLabel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton convertButton = new JButton("Convert");
        convertButton.setBackground(new Color(70, 130, 180));
        convertButton.setForeground(Color.BLACK); // Changed to black for readability
        convertButton.setFocusPainted(false);
        convertButton.addActionListener(e -> convert(pageName));
        convertButtons.put(pageName, convertButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.BLACK); // Changed to black for readability
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clear(pageName));
        clearButtons.put(pageName, clearButton);

        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);

        // Stack input, output, and button panels vertically
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(inputPanel);
        centerPanel.add(outputPanel);
        centerPanel.add(buttonPanel);

        // Add components to panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Initialize combo boxes for this page
        updateComboBoxes(pageName);

        return panel;
    }

    private void updateComboBoxes(String pageName)
    {
        Map<String, Double> factors = conversionFactors.get(pageName);
        if (factors != null)
        {
            String[] units = factors.keySet().toArray(new String[0]);

            JComboBox<String> fromCombo = fromUnitCombos.get(pageName);
            JComboBox<String> toCombo = toUnitCombos.get(pageName);

            fromCombo.removeAllItems();
            toCombo.removeAllItems();

            for (String unit : units)
            {
                fromCombo.addItem(unit);
                toCombo.addItem(unit);
            }

            // Set default selections
            if (units.length > 1)
            {
                toCombo.setSelectedIndex(1);
            }
        }
    }

    private void convert(String pageName)
    {
        try
        {
            JTextField inputField = inputFields.get(pageName);
            JComboBox<String> fromCombo = fromUnitCombos.get(pageName);
            JComboBox<String> toCombo = toUnitCombos.get(pageName);
            JLabel resultLabel = resultLabels.get(pageName);

            double value = Double.parseDouble(inputField.getText());

            // Validate input range (1 to 100 million)
            if (value < 1 || value > 100000000)
            {
                resultLabel.setText("Error: Please enter a number between 1 and 100,000,000");
                return;
            }

            String fromUnit = (String) fromCombo.getSelectedItem();
            String toUnit = (String) toCombo.getSelectedItem();

            if (fromUnit == null || toUnit == null)
            {
                resultLabel.setText("Please select units");
                return;
            }

            double result;

            if (pageName.equals("Temperature"))
            {
                result = convertTemperature(value, fromUnit, toUnit);
            }
            else
            {
                Map<String, Double> factors = conversionFactors.get(pageName);
                double fromFactor = factors.get(fromUnit);
                double toFactor = factors.get(toUnit);
                result = (value * fromFactor) / toFactor;
            }

            String resultText = String.format("Result: %.6f %s", result, toUnit);
            resultLabel.setText(resultText);

            // Add to past conversions
            String conversionRecord = String.format("[%s] %.2f %s = %.6f %s",
                pageName, value, fromUnit, result, toUnit);
            pastConversions.add(conversionRecord);

        }
        catch (NumberFormatException e)
        {
            JLabel resultLabel = resultLabels.get(pageName);
            resultLabel.setText("Please enter a valid number");
        }
    }

    private double convertTemperature(double value, String fromUnit, String toUnit)
    {
        // Convert to Celsius first
        double celsius;
        switch (fromUnit)
        {
            case "Celsius":
                celsius = value;
                break;
            case "Fahrenheit":
                celsius = (value - 32) * 5.0 / 9.0;
                break;
            case "Kelvin":
                celsius = value - 273.15;
                break;
            default:
                return value;
        }

        // Convert from Celsius to target unit
        switch (toUnit)
        {
            case "Celsius":
                return celsius;
            case "Fahrenheit":
                return celsius * 9.0 / 5.0 + 32;
            case "Kelvin":
                return celsius + 273.15;
            default:
                return celsius;
        }
    }

    private void clear(String pageName)
    {
        JTextField inputField = inputFields.get(pageName);
        JLabel resultLabel = resultLabels.get(pageName);
        inputField.setText("");
        resultLabel.setText("Result: ");
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            try
            {
                // Try to set system look and feel, fall back to default if not available
                String systemLookAndFeel = UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(systemLookAndFeel);
            }
            catch (Exception e)
            {
                // If system look and feel fails, use default
                try
                {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            UnitConverter converter = new UnitConverter();
            converter.setVisible(true);
        });
    }
}
