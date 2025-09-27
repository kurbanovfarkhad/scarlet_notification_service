# scarlet_notification_service
Scarlett project an education work made for improving skills and make some experiments with a technical news or best practices

at this repository i am going to гз service's responsible for notification from scratch 

what we have at this point
1) system should sustain **push**, **sms**, and **email** notification
2) there are two different way to receive command to send message: asynchronously and synchronously
3) ASYNC will be implemented by **Kafka**
4) SYNC should use **grpc** just because messages is going to be pretty big


system consists from :
1) API layer / consumer
2) something responsible for template finder and message generation. lets call it **Template engine**
3) **senders integration layer**
