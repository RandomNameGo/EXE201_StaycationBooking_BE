# EXE201_StaycationBooking_BE

This is the backend service for a Staycation Booking Website. It is built using **Spring Boot** and integrates essential features like image/video upload, email notifications, and online payment. The service is deployed on **Azure Web App** using **GitHub Actions** for CI/CD.

---

## 📌 Technologies Used

### 🧠 Core
- **Spring Boot**: Main backend framework.
- **MySQL**: Relational database management system.
  
### ☁️ Media Storage
- **Cloudinary**: Used to upload and manage images and videos for homestays.

### 📧 Email Notification
- **SMTP (Simple Mail Transfer Protocol)**: Used to send booking confirmations and other system emails.

### 💳 Payment Integration
- **PayOS**:
  - Supports **QR code** generation for bank transfer.
  - Handles payment status through **webhooks**.

### 🚀 Deployment
- **Azure Web App**: Hosting backend services.
- **GitHub Actions**: Automates CI/CD pipeline for deployment to Azure.

---

## 📂 Project Structure


