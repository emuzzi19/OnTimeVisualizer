# OnTimeVisualizer
## Introduction

OnTimeDisplay is a JavaFX-based graphics application that visualizes month-by-month average on-time percentages for public transportation in the Pittsburgh area. Users can view subsets of the data based on transport type, day type, home garage, and year. The data is sourced from the [Western Pennsylvania Regional Data Center](https://data.wprdc.org/dataset/port-authority-monthly-average-on-time-performance-by-route) and has undergone data cleansing for accurate visualization.

## Table of Contents

- [Introduction](#introduction)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Data](#data)
- [Features](#features)
- [References](#references)

## Installation

### Prerequisites

Ensure you have the following installed:

- Java Development Kit (JDK) 18 or higher
- JavaFX SDK 18 or higher

### Setup

1. Clone the repository:

    ```bash
    git clone <repository-url>
    cd OnlineDisplayEM
    ```

2. Set up JavaFX SDK path:

    ```bash
    set PATH_TO_FX="C:\path\to\javafx-sdk-18.0.1\lib"
    ```

3. Compile the application:

    ```bash
    javac --module-path %PATH_TO_FX% --add-modules javafx.controls OntimeDisplayEM.java
    ```

4. Run the application:

    ```bash
    java --module-path %PATH_TO_FX% --add-modules javafx.controls OntimeDisplayEM
    ```

## Usage

Upon launching the application, the main window will display a bar chart representing the average on-time percentages for each month. Users can interact with the following controls to filter and explore the data:

- **Mode**: Filter data by transport type (All, Bus, Light Rail).
- **Day Type**: Filter data by day type (All, Weekday, Saturday, Sunday).
- **Garage**: Filter data by home garage.
- **Year**: Enter a specific year to filter data.
- **Reset**: Reset the visualization to show the entire dataset.

## Project Structure

- `OntimeDisplayEM.java`: Main application file containing the JavaFX application logic.
- `RouteItemEM.java`: Class representing a single route's data.
- `MonthEM.java`: Class representing a month's data and visualization.

## Data

The data used in this application is sourced from the [Western Pennsylvania Regional Data Center](https://data.wprdc.org/dataset/port-authority-monthly-average-on-time-performance-by-route). The dataset includes monthly average on-time performance percentages for various public transportation routes in the Pittsburgh area. The data has been cleansed to ensure accuracy and consistency.

## Features

- **Interactive Visualization**: View monthly on-time performance percentages with an easy-to-understand bar chart.
- **Data Filtering**: Filter data by transport type, day type, home garage, and year.
- **Responsive UI**: The JavaFX-based UI provides a smooth and responsive user experience.

## References

- Western Pennsylvania Regional Data Center: [Dataset](https://data.wprdc.org/dataset/port-authority-monthly-average-on-time-performance-by-route)
- JavaFX Documentation: [JavaFX SDK](https://openjfx.io/)

For more detailed information, please refer to the source code and documentation included in the project repository.
