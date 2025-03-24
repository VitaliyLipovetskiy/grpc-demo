
## Environment variables:

`CLIENT_HTTP_PORT` - client port (8082)

`CLIENT_BOOT_HTTP_PORT` - client spring-boot port (8182)

`CLIENT_GRPC_PORT` - client gRPC port (9092)

`CLIENT_BOOT_GRPC_PORT` - client gRPC spring-boot port (9192)

`SERVER_GRPC_HOST` - server gRPC host (grpc-server)

`SERVER_BOOT_GRPC_HOST` - server gRPC spring-boot host (grpc-server-boot)

`SERVER_HTTP_PORT` - server port (8081)

`SERVER_BOOT_HTTP_PORT` - server spring-boot port (8181)

`SERVER_GRPC_PORT` - server gRPC port (9091)

`SERVER_BOOT_GRPC_PORT` - server gRPC spring-boot port (9191)

`GRPC_DEADLINE` - gRPC DEADLINE (60000)


## Запуск в докері
```
docker compose up --build -d
```
Зупинка
```
docker compose down 
```

## Запуск в докері (звичайного не boot)
```
docker compose up --build -d grpc-server grpc-client
```
Зупинка
```
docker compose down 
```


## Запуск в докері grpc-boot
```
docker compose up --build -d grpc-server-boot grpc-client-boot
```
Зупинка
```
docker compose down 
