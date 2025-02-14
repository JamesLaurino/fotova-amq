# Getting Started

### Reference Documentation

1. Starting rabbitMQ

docker run -it -p 15672:15672 -p 5672:5672 rabbitmq:latest

2. Tester RabbitMQ : @GetMapping("/send")

http://localhost:8080/send?message=HelloRabbitMQ
http://localhost:8080/send?message=error

3. Tester avec un Objet dans la queue

POST => http://localhost:8080/send

{
"id":1,
"name": "test",
"quantity": 2,
"price": 3.5
}