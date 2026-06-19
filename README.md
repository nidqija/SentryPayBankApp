# 🏦 GXBank Next-Gen

**Core Banking Infrastructure & SentryPay Engine**

*The world's first core-embedded real-time subscription firewall — built for the next generation of digital banking.*
 
---

![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Apache ActiveMQ](https://img.shields.io/badge/ActiveMQ-Broker-D22128?style=for-the-badge&logo=apacheactivemq&logoColor=white)
![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![GnuCOBOL](https://img.shields.io/badge/GnuCOBOL-3.x-0078D7?style=for-the-badge&logo=gnu&logoColor=white)

![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-Material_3-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material-3-757575?style=flat-square&logo=material-design&logoColor=white)
![License](https://img.shields.io/badge/License-Proprietary-red?style=flat-square)
![Build](https://img.shields.io/badge/Build-Passing-brightgreen?style=flat-square)
![Status](https://img.shields.io/badge/Status-Active_Development-blue?style=flat-square)


---

## Overview

GXBank Next-Gen is a high-performance, full-scale digital banking platform built from the ground up. It fuses a highly responsive modern mobile layer with a robust, ultra-secure legacy financial ledger — delivering seamless retail banking features alongside its flagship capability:

> **SentryPay** — the world's first core-embedded real-time subscription firewall. Users can intercept, freeze, or legally revoke recurring merchant authorizations (e.g. Netflix, Spotify) directly at the ledger authorization level, *before* charges ever reach the card processor network.
 
---

## Key Platform Features

| Feature | Description |
|---|---|
| ⚡ **Instant Account Provisioning** | Full core-ledger customer onboarding via a secure API gateway directly into GnuCOBOL storage |
| 📊 **Real-Time Ledger Processing** | Sub-millisecond transaction evaluation for deposits, transfers, and ledger lookups |
| 🛡️ **SentryPay Firewall** | Intercept and revoke recurring merchant authorizations at the ledger level before they reach card processors |
 
---

## System Architecture

GXBank Next-Gen uses a decoupled, multi-tier asynchronous architecture to achieve massive throughput and ironclad transactional reliability.

```
┌─────────────────────────────────────────────────────────────┐
│                   Android Client (Kotlin)                    │
│              Jetpack Compose · Material 3 UI                 │
└────────────────────────┬────────────────────────────────────┘
                         │ REST / HTTPS
┌────────────────────────▼────────────────────────────────────┐
│           Orchestration Middleware (Spring Boot)              │
│       Auth · API Gateway · Merchant Mock Payloads            │
└──────────────┬──────────────────────────┬───────────────────┘
               │ JMS                       │ JMS
┌──────────────▼──────────────────────────▼───────────────────┐
│                   Apache ActiveMQ Broker                      │
│   gxbank.ledger.updates · sentrypay.subscription.validation  │
└────────────────────────┬────────────────────────────────────┘
                         │ IPC / Sidecar
┌────────────────────────▼────────────────────────────────────┐
│              GnuCOBOL Core Banking Engine                    │
│       Ledger Records · SentryPay Firewall Matrix             │
└─────────────────────────────────────────────────────────────┘
```

### Tier Breakdown

**1 · Frontend Client**
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)

Built in 100% Kotlin with Jetpack Compose (Material 3). Employs declarative state management, unified styling contexts, and a lightweight asynchronous `NavHost` routing pipeline.

**2 · Orchestration Middleware**
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)

High-throughput Spring Boot API gateway managing user authentication sessions, REST endpoint mapping, and third-party mock merchant authorization payloads.

**3 · Event Brokerage**
![ActiveMQ](https://img.shields.io/badge/ActiveMQ-D22128?style=flat-square&logo=apacheactivemq&logoColor=white)

Apache ActiveMQ orchestrates safe asynchronous message streams across critical channels — guaranteeing atomic transaction integrity across all banking operations.

**4 · Core Banking System of Record**
![GnuCOBOL](https://img.shields.io/badge/GnuCOBOL_3.x-0078D7?style=flat-square&logo=gnu&logoColor=white)

GnuCOBOL engine sidecar implementing flat-byte arrays for calculations, ledger card updates, and binary flag lookups at maximum throughput.
 
---

## Tech Stack

### Mobile
| Layer | Technology |
|---|---|
| Language | Kotlin 1.9+ |
| UI Framework | Jetpack Compose (Material 3) |
| Concurrency | Kotlin Coroutines |
| Navigation | Jetpack Navigation Compose (`androidx.navigation:navigation-compose`) |

### Backend & Middleware
| Layer | Technology |
|---|---|
| Framework | Spring Boot (Java 17+ / Kotlin Backend) |
| Messaging | Apache ActiveMQ (JMS) |
| Core Engine | GnuCOBOL v3.x+ |
| Data Structures | Fixed-Width Record Copybooks (`PIC X` / `PIC 9(5)V99`) |
 
---

## Repository Layout

```
gxbank-nextgen/
│
├── gxbank-android/                         # Jetpack Compose Digital Banking App
│   └── src/main/java/com/example/sentrypaybank/
│       ├── MainActivity.kt                 # App Shell & NavHost Controller
│       ├── navigation/
│       │   └── NavBar.kt                   # Sealed View Routing Maps
│       └── pages/
│           ├── SignInActivity.kt           # Authentication Gateway View
│           └── HomeActivity.kt             # Financial Dashboard & SentryPay Panel
│
├── gxbank-middleware/                      # Spring Boot Enterprise Router
│   └── src/main/resources/
│       └── application.yml                 # ActiveMQ Topic & Connection Config
│
└── gxbank-core-ledger/                     # Mainframe-Emulated Financial Core
    ├── copybooks/
    │   ├── LK-CUSTOMER-REC.cpy             # Bank Account Schema Allocation
    │   └── LK-TXN-REQUEST.cpy              # SentryPay Transaction Request Structure
    └── src/
        ├── GXCORELEDGER.cob                # Retail Banking Ledger Business Logic
        └── SENTRYFIREWALL.cob              # Subscription Firewall Matrix Loop
```
 
---

## ActiveMQ Message Channels

| Channel | Purpose |
|---|---|
| `gxbank.ledger.updates` | Real-time balance and ledger state propagation |
| `sentrypay.subscription.validation` | Merchant authorization intercept and validation pipeline |
 
---

