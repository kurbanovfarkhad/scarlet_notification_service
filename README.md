# Scarlet Notification Service

**Scarlet Project** is an educational project designed to improve skills and experiment with modern technical
practices.  
This repository contains the implementation of a **notification service** built from scratch.

---

## 🚀 Features

- Supports sending notifications via **Push**, **SMS**, and **Email**.
- Two ways to receive commands for sending messages:
    - **Asynchronous** — via **Kafka**.
    - **Synchronous** — via **REST** (suitable for large messages).
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
- Bulk messaging
- NotificationStatus api
- **Notification History** — view and resend notifications.
- **User Preferences** — set delivery channel priority for each user.

# 🔔 Notification Service — System Capabilities

## 1. Event & Template Management

- Users can create new **events** in the system.
- Users can add **notification templates**, each template must be linked to an existing event.

## 2. Event Consumption

- Whenever a **Kafka broker** publishes a new event, the notification service consumes it.
- The service checks for templates associated with that event and generates notifications if such templates exist.

## 3. Event Payload Schema

- Each **event record** in the database contains its **Avro schema** definition.
- This schema defines the payload structure used to populate templates.

## 4. User Preferences & Fallbacks

- Before sending, the system checks if the **user has channel preferences** (e.g., prefers SMS over email).
- If no preference is found, the system uses the **default channel** defined in the template configuration.

## 5. Channel Implementations

- Delivery channels (e.g., **Email, SMS, Push**) are initialized during system bootstrap.
- Each channel follows a **common abstraction**, so new channels can be plugged in easily.

## 6. Delivery Channel Failure

- Each delivery attempt is recorded in the **DeliveryAttempts** table.
- If delivery fails, the system decreases the remaining attempts counter.
- The system retries sending until the number of attempts reaches **0**.
- Once attempts are exhausted, the notification status is marked as **FAILED**.