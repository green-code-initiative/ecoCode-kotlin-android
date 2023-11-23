![Logo](docs/resources/5ekko.png)
======================================

Mobile apps running on top of battery-limited, android-powered devices are more than others concerned by the reduction 
of their environmental footprint. Hence, we created `ecoCode android for Kotlin`, the version of ecoCode project fully 
dedicated to the Android Kotlin projects. 

It provides static code analyzers to highlight code structures that may have a negative ecological impact: energy over-consumption, 
"fatware", shortening devices' lifespan, etc.

ecoCode android for Kotlin is based on an evolving catalog of [best practices for Android](https://github.com/cnumr/best-practices-mobile#-android-platform). 
A SonarQube plugin then implements this catalog as rules for scanning your native Android projects. 

To work on Android projects developed in Java, you can use the [ecoCode Android plugin for Java projects](https://github.com/green-code-initiative/ecoCode-android).

This project is based on [SonarSource - sonar-kotlin project](https://github.com/SonarSource/sonar-kotlin), fork of the version of the 2023/07/03.

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

🚀 Quickstart
-------------

To build the project and run the test simply launch:

```bash
./gradlew build
```

Then, a docker file is configured to launch a SonarQube instance with the plugin installed:

```bash
docker compose up --build -d
```

Wait a little bit during first start initialization, and go to [http://localhost:9000](http://localhost:9000). Default credentials are `admin`/`admin`

🧩 Plugins version compatibility
------------------

| Plugins Version | SonarQube version           |
|-----------------|-----------------------------|
| 0.0.+           | SonarQube 9.9.+ LTS to 10.3 |
