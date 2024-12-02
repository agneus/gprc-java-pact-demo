# gRPC Contract Testing Demo

## Overview

This project demonstrates a gRPC-based service that provides Hatsune Miku's message and phone number. It uses **consumer-driven contract testing** with **Pact** and the **Protobuf plugin** to ensure the provider meets the consumer's expectations.

---

## Components

### 1. Consumer

The consumer simulates an application that interacts with the gRPC service. It generates a **Pact contract** during testing to define the expected behavior of the provider.

#### **Consumer Methods**
- **`getMikuMessage()`**:
  - Sends a `Miku.EmptyRequest` to the gRPC `GetMessage` RPC.
  - Expects a response:
    ```json
    {
      "message": "Hatsune Miku says hello!"
    }
    ```

- **`getMikuPhoneNumber()`**:
  - Sends a `Miku.EmptyRequest` to the gRPC `GetPhoneNumber` RPC.
  - Expects a response:
    ```json
    {
      "phoneNumber": "+81-123-456-7890"
    }
    ```

---

### 2. Provider

The provider implements a gRPC service as defined in the `.proto` file. It adheres to the contract generated by the consumer, ensuring compliance with the expected request and response formats.

#### **Service Methods**
- **`GetMessage`**:
  - Input: `Miku.EmptyRequest`
  - Output: `Miku.MessageResponse`
    ```json
    {
      "message": "Hatsune Miku says hello!"
    }
    ```

- **`GetPhoneNumber`**:
  - Input: `Miku.EmptyRequest`
  - Output: `Miku.PhoneNumberResponse`
    ```json
    {
      "phoneNumber": "+81-123-456-7890"
    }
    ```

---

## Contract Testing

### Workflow

1. **Consumer Test**:
   - Simulates the consumer's interaction with the gRPC service.
   - Generates a **Pact file** defining the expected Protobuf-based request and response.

2. **Provider Test**:
   - Starts the gRPC provider.
   - Verifies the provider's behavior matches the Pact contract for all defined interactions.

### Tests

- **Consumer Test**:
  - Uses a gRPC mock server provided by Pact to simulate provider responses.
  - Verifies consumer behavior and generates the Pact contract in `target/pacts`.

- **Provider Test**:
  - Runs the provider service against the Pact file.
  - Uses the Pact Protobuf plugin to validate the gRPC methods and responses.

---

## How it Works

1. **Consumer**:
   - Defines interactions for the `GetMessage` and `GetPhoneNumber` gRPC methods using the Pact Protobuf plugin.
   - Pact generates a contract capturing these interactions.

2. **Provider**:
   - Implements the gRPC service to comply with the contract.
   - Validates its behavior against the Pact contract during provider testing.

This approach ensures seamless integration between the consumer and provider while leveraging the gRPC and Protobuf ecosystem for performance and scalability.