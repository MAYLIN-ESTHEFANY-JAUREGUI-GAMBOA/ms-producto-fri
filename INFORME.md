# Informe: Despliegue de Microservicios con Kubernetes

## 1. Conceptos Utilizados

### 1.1 Microservicios
- Arquitectura basada en servicios independientes
- Comunicación mediante APIs REST
- Escalabilidad individual de cada servicio

### 1.2 Docker
- Contenedorización de aplicaciones
- Imágenes Docker para portabilidad
- Docker Hub para distribución de imágenes

### 1.3 Kubernetes
- Orquestación de contenedores
- Gestión automática de escalado
- Balanceo de carga
- ConfigMaps para configuración externa

### 1.4 Spring Boot
- Framework para microservicios Java
- Spring WebFlux para programación reactiva
- R2DBC para acceso reactivo a bases de datos

### 1.5 PostgreSQL
- Base de datos relacional
- NeonDB como servicio en la nube
- Conexión SSL requerida

### 1.6 Clean Architecture
- Separación en capas: Domain, Application, Infrastructure
- Puertos y adaptadores
- Inversión de dependencias

## 2. Testeo de Comunicación Local (WebClient con localhost)

### 2.1 Configuración Local
```yaml
# application.yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/ms-producto
server:
  port: 8081

productos:
  service:
    url: http://localhost:8081/api/productos
```

### 2.2 Pruebas con WebClient
```java
// Ejemplo de WebClient para comunicación entre microservicios
WebClient webClient = WebClient.create("http://localhost:8081");
Mono<Producto> producto = webClient.get()
    .uri("/api/productos/{id}", 1)
    .retrieve()
    .bodyToMono(Producto.class);
```

### 2.3 Resultados Esperados
- GET http://localhost:8081/api/productos → Lista de productos
- GET http://localhost:8081/api/productos/1 → Producto específico
- POST http://localhost:8081/api/productos → Crear producto
- PUT http://localhost:8081/api/productos/1 → Actualizar producto
- DELETE http://localhost:8081/api/productos/1 → Eliminar producto

### 2.4 Comunicación entre Microservicios
- GET http://localhost:8081/api/pedidos/1 → Obtener pedido
- GET http://localhost:8081/api/pedidos/1/producto → Obtener producto asociado al pedido

**[CAPTURA: Ejecución local de GET /api/pedidos/1/producto]**
**[CAPTURA: Respuesta JSON con datos del producto]**

### 2.5 Ejemplo de Comunicación
```bash
# Crear un pedido
curl -X POST http://localhost:8081/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2, "total": 5000.00, "status": "PENDING"}'

# Obtener el producto del pedido
curl http://localhost:8081/api/pedidos/1/producto
```

**[CAPTURA: Ejecución de curl para crear pedido]**
**[CAPTURA: Ejecución de curl para obtener producto del pedido]**

## 3. Testeo con Kubernetes

### 3.1 Comunicación entre Microservicios en Kubernetes

#### 3.1.1 Prueba de Comunicación
```bash
# Obtener IP externa del servicio
kubectl get services ms-productos-service

# Probar endpoint de productos
curl http://<EXTERNAL-IP>/api/productos

**[CAPTURA: curl a /api/productos en Kubernetes]**

# Probar endpoint de pedidos
curl http://<EXTERNAL-IP>/api/pedidos

**[CAPTURA: curl a /api/pedidos en Kubernetes]**

# Probar comunicación entre servicios (obtener producto del pedido)
curl http://<EXTERNAL-IP>/api/pedidos/1/producto

**[CAPTURA: curl a /api/pedidos/1/producto en Kubernetes]**
**[CAPTURA: Respuesta JSON con datos del producto obtenido del servicio de productos]**
```

#### 3.1.2 Prueba con dos puertos diferentes
```bash
# Probar puerto 8081
curl http://<EXTERNAL-IP-8081>/api/productos

**[CAPTURA: curl a puerto 8081]**

# Probar puerto 9081
curl http://<EXTERNAL-IP-9081>/api/productos

**[CAPTURA: curl a puerto 9081]**
```

### 3.2 Manifiestos Kubernetes

#### 3.2.1 ConfigMap (Puerto 8081)
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: ms-productos-config
data:
  server.port: "8081"
  spring.application.name: "ms-productos"
```

#### 3.2.2 Deployment (Puerto 8081)
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ms-productos-deployment
spec:
  replicas: 2
  selector:
    matchLabels:
      app: ms-productos
  template:
    metadata:
      labels:
        app: ms-productos
    spec:
      containers:
      - name: ms-productos
        image: maylinjg/ms-producto-fri:latest
        ports:
        - containerPort: 8081
        envFrom:
        - configMapRef:
            name: ms-productos-config
```

#### 3.2.3 Service (LoadBalancer)
```yaml
apiVersion: v1
kind: Service
metadata:
  name: ms-productos-service
spec:
  selector:
    app: ms-productos
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8081
  type: LoadBalancer
```

### 3.3 Deployments
- **ms-productos-deployment**: 2 réplicas en puerto 8081
- **ms-productos-deployment-port2**: 2 réplicas en puerto 9081

### 3.4 Services
- **ms-productos-service**: LoadBalancer para puerto 8081
- **ms-productos-service-port2**: LoadBalancer para puerto 9081

### 3.5 LoadBalancer
- Tipo: LoadBalancer
- Puerto externo: 80
- Puerto target: 8081/9081
- Distribuye tráfico entre pods

### 3.6 Ejecución de Comandos Kubernetes

```bash
# Construir imagen Docker
docker build -t maylinjg/ms-producto-fri:latest .

**[CAPTURA: docker build]**

# Push a Docker Hub
docker push maylinjg/ms-producto-fri:latest

**[CAPTURA: docker push]**

# Aplicar ConfigMap puerto 8081
kubectl apply -f k8s/configmap.yaml

**[CAPTURA: kubectl apply configmap.yaml]**

# Aplicar Deployment puerto 8081
kubectl apply -f k8s/deployment.yaml

**[CAPTURA: kubectl apply deployment.yaml]**

# Aplicar Service puerto 8081
kubectl apply -f k8s/service.yaml

**[CAPTURA: kubectl apply service.yaml]**

# Aplicar ConfigMap puerto 9081
kubectl apply -f k8s/configmap-port2.yaml

# Aplicar Deployment puerto 9081
kubectl apply -f k8s/deployment-port2.yaml

# Aplicar Service puerto 9081
kubectl apply -f k8s/service-port2.yaml

# Verificar deployments
kubectl get deployments

**[CAPTURA: kubectl get deployments]**

# Verificar pods
kubectl get pods

**[CAPTURA: kubectl get pods]**

# Verificar services
kubectl get services

**[CAPTURA: kubectl get services]**

# Verificar configmaps
kubectl get configmaps

**[CAPTURA: kubectl get configmaps]**

# Obtener IPs externas
kubectl get services -o wide

**[CAPTURA: kubectl get services -o wide]**

# Ver logs
kubectl logs -f <pod-name>

**[CAPTURA: kubectl logs de un pod]**

# Eliminar recursos
kubectl delete -f k8s/
```

## 4. Imagen Docker

### 4.1 Dockerfile
```dockerfile
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/ms-productos-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 4.2 Publicación en Docker Hub
```bash
docker tag ms-productos:latest maylinjg/ms-producto-fri:latest
docker push maylinjg/ms-producto-fri:latest
```

**[CAPTURA: docker tag]**
**[CAPTURA: docker push]**

## 5. Conclusiones

### 5.1 Ventajas de Kubernetes
- **Escalabilidad**: Fácil escalamiento horizontal con replicas
- **Alta disponibilidad**: Múltiples pods aseguran continuidad
- **Balanceo de carga**: Distribución automática del tráfico
- **Configuración flexible**: ConfigMaps para variables de entorno
- **Portabilidad**: Despliegue consistente en diferentes entornos

### 5.2 Desafíos Encontrados
- Configuración de SSL para PostgreSQL
- Gestión de secrets para credenciales
- Tiempo de despliegue inicial
- Debugging en entorno distribuido

### 5.3 Lecciones Aprendidas
- Importancia de la configuración externa (ConfigMaps)
- Uso de LoadBalancer para exposición de servicios
- Monitoreo de logs para troubleshooting
- Separación de configuración por puerto

### 5.4 Recomendaciones
- Usar Secrets para credenciales sensibles
- Implementar health checks
- Configurar límites de recursos
- Usar namespaces para ambientes separados
- Implementar CI/CD para automatización

## 6. Anexos

### 6.1 Estructura del Proyecto
```
ms-pedidos/
├── src/
│   └── main/
│       ├── java/
│       │   └── pe/edu/vallegrande/msproductos/
│       │       ├── application/
│       │       ├── domain/
│       │       ├── infrastructure/
│       │       └── service/
│       └── resources/
│           ├── application.yaml
│           └── db/
├── k8s/
│   ├── configmap.yaml
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap-port2.yaml
│   ├── deployment-port2.yaml
│   └── service-port2.yaml
├── Dockerfile
├── pom.xml
└── INFORME.md
```

### 6.2 Endpoints API
- GET /api/productos
- GET /api/productos/{id}
- POST /api/productos
- PUT /api/productos/{id}
- DELETE /api/productos/{id}
- GET /api/pedidos
- GET /api/pedidos/{id}
- POST /api/pedidos
- PUT /api/pedidos/{id}
- DELETE /api/pedidos/{id}
- GET /api/pedidos/{id}/producto (comunicación entre microservicios)
