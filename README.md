# Scarlet Notification Service

**Scarlet Project** is an educational project designed to improve skills and experiment with modern technical practices.  
This repository contains the implementation of a **notification service** built from scratch.

---

## 🚀 Features
- Supports sending notifications via **Push**, **SMS**, and **Email**.
- Two ways to receive commands for sending messages:
    - **Asynchronous** — via **Kafka**.
    - **Synchronous** — via **gRPC** (suitable for large messages).
- Flexible **template engine** for event-driven messages.
- Delivery status tracking and retry mechanism.
- User-specific channel preferences.

---

## 🏗 Architecture

The service consists of the following components:

1. **API Layer / Consumer**  
   Handles incoming requests (both sync and async).

2. **Template Engine**  
   Finds appropriate templates and generates message content.

3. **Senders Integration Layer**  
   Integrates with external delivery providers (**Push, SMS, Email**).  
   Implemented using the **Abstract Factory** pattern — each channel has its own factory.

4. **Delivery Service**  
   Core business logic that controls the notification delivery flow and retries.

5. **Dispatcher**  
   Selects the most suitable delivery channel based on user preferences and availability.

---

## 🌐 API Capabilities
- **Template Management** — CRUD operations for `TemplateEntity`.
- **Notification Management** — CRUD operations for `Notification`.
- **Notification History** — view and resend notifications.
- **User Preferences** — set delivery channel priority for each user.

---

## 📊 High-Level Design

```mermaid
flowchart TD
    A[API Layer] --> B[Delivery Service]
    B --> C[Dispatcher]
    C --> D[Template Engine]
    C --> E[Senders Integration Layer]
    E --> F[Push Provider]
    E --> G[SMS Provider]
    E --> H[Email Provider]
    B --> I[(Database)]
    A -->|Async (Kafka)| B
    A -->|Sync (gRPC)| B
