# **Wearable Use Cases with Java**

## Table of Contents

 * [Introduction](#introduction)
 * [What You Will Need](#what-you-will-need)
 * [Installation](#installation)
 * [Configuration](#configuration )
 * [Implemented Features](#implemented-features)

## Introduction
This application contains multiple use cases. You can check API request, Wear Engine and ZXing integrations in HarmonyOS. API Request was used to receive news data, ZXing was used to generate QR codes, and Wear Engine was used to interact with the phone.

## What You Will Need

### Hardware Requirements
 • A computer that can run DevEco Studio.<br/> • A Huawei Watch for debugging.

### Software Requirements
 • Harmony SDK - API 5 <br/> • DevEco Studio 2.X

## Installation
 • Install DevEco Studio<br/> • Set up the DevEco Studio development environment.The DevEco Studio development environment needs to depend on the network environment. It needs to be connected to the network to ensure the normal use of the tool.The development environment can be configured according to the following two situations <br />1.If you can directly access the Internet, just download the HarmonyOS SDK <br />2.If the network cannot access the Internet directly, it can be accessed through a proxy server<br /> • Generate secret key and apply for certificate

## Configuration
 • Download this Project<br/> • Open HUAWEI DevEco Studio, click File> Open> Then select and open this Project<br/> • Click Build> Build App(s)/Hap(s)>Build Debug Hap(s) to compile the hap package<br/> • Click Run> Run 'entry' to run the hap package<br/> • In order to use the Wear Engine service, you need permission from the Console.

## Implemented Features
| <br />**Use Case** | <br />**Screenshot** |
| ------ | ------ |
| **Demo News App:**<br/> - In this application, News data is accessed using the Free News API.<br/> - Then this information is displayed on the screen both on the phone and on the watch. <br/> - Using Wear Engine, showed the news opened on the watch to the user using WebView on the phone. | ![Screenshots](screenshots/NewspaperWatchDemo.gif) |
| **Demo QR Code App:**<br/> - In this application, First, the PDF file, which is the QR code, is selected from the phone. <br/> - Then the QR Code information on the phone to the Watch using Wear Engine. <br/> - Using the ZXing library, a QR code is generated on the Watch with the information from the phone. | ![Screenshots](screenshots/qrCode.gif) |