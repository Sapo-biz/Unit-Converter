# Unit Converter

A comprehensive Java application that provides unit conversions across eight different categories.

## Features

The application includes eight different conversion pages:

1. **Length/Distance** - Meters, Kilometers, Centimeters, Millimeters, Miles, Yards, Feet, Inches
2. **Mass/Weight** - Kilograms, Grams, Milligrams, Pounds, Ounces, Tons
3. **Time** - Seconds, Minutes, Hours, Days, Weeks, Years
4. **Temperature** - Celsius, Fahrenheit, Kelvin
5. **Volume/Capacity** - Liters, Milliliters, Cubic Meters, Gallons, Quarts, Pints, Cups
6. **Area** - Square Meters, Square Kilometers, Square Centimeters, Square Miles, Acres, Square Feet, Square Inches
7. **Energy** - Joules, Kilojoules, Calories, Kilocalories, Watt-hours, Kilowatt-hours
8. **Power** - Watts, Kilowatts, Horsepower, BTU/hour

## How to Use

1. **Compile the program:**
   ```bash
   javac UnitConverter.java
   ```

2. **Run the program:**
   ```bash
   java UnitConverter
   ```

3. **Using the interface:**
   - Click on any of the eight category buttons at the top to switch between conversion types
   - Enter a number in the text field
   - Select your current unit from the "From" dropdown menu
   - Select your desired unit from the "To" dropdown menu
   - Click "Convert" to see the result
   - Use "Clear" to reset the input and result

## Interface Layout

- **Top Navigation**: Eight buttons to switch between conversion categories
- **Input Section**: Text field for entering values and dropdown for selecting source unit
- **Output Section**: Dropdown for selecting target unit and result display
- **Action Buttons**: Convert and Clear buttons

## Special Features

- **Temperature Conversions**: Handles the special case of temperature conversions (Celsius, Fahrenheit, Kelvin) which require different formulas than simple multiplication
- **Modern UI**: Uses system look and feel for a native appearance
- **Error Handling**: Validates input and provides helpful error messages
- **Precise Results**: Shows results with up to 6 decimal places for accuracy

## Requirements

- Java 8 or higher
- Java Swing (included with standard Java installation)

## Author

Open sourced, may be used (With permission) - send an email at sapo.business.ventures@gmail.com
Jason He - 2025 