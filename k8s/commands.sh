#!/bin/bash

# Kubernetes Commands for ms-productos Microservice Deployment (Minikube)

# 1. Start Minikube (if not running)
echo "Starting Minikube..."
minikube start

# 2. Configure Docker environment for Minikube
echo "Configuring Docker environment..."
eval $(minikube docker-env)

# 3. Build Docker Image in Minikube environment
echo "Building Docker image..."
docker build -t maylinjg/ms-producto-fri:latest .

# 4. Apply ConfigMap for port 8081
echo "Applying ConfigMap for port 8081..."
kubectl apply -f k8s/configmap.yaml

# 5. Apply Deployment for port 8081
echo "Applying Deployment for port 8081..."
kubectl apply -f k8s/deployment.yaml

# 6. Apply Service for port 8081
echo "Applying Service for port 8081..."
kubectl apply -f k8s/service.yaml

# 7. Apply ConfigMap for port 9081
echo "Applying ConfigMap for port 9081..."
kubectl apply -f k8s/configmap-port2.yaml

# 8. Apply Deployment for port 9081
echo "Applying Deployment for port 9081..."
kubectl apply -f k8s/deployment-port2.yaml

# 9. Apply Service for port 9081
echo "Applying Service for port 9081..."
kubectl apply -f k8s/service-port2.yaml

# 10. Wait for pods to be ready
echo "Waiting for pods to be ready..."
kubectl wait --for=condition=ready pod -l app=ms-productos --timeout=120s
kubectl wait --for=condition=ready pod -l app=ms-productos-port2 --timeout=120s

# 11. Verify Deployments
echo "Verifying Deployments..."
kubectl get deployments

# 12. Verify Pods
echo "Verifying Pods..."
kubectl get pods

# 13. Verify Services
echo "Verifying Services..."
kubectl get services

# 14. Verify ConfigMaps
echo "Verifying ConfigMaps..."
kubectl get configmaps

# 15. Get Minikube URLs
echo "Getting Minikube URLs..."
echo "Port 8081: $(minikube service ms-productos-service --url)"
echo "Port 9081: $(minikube service ms-productos-service-port2 --url)"

# 16. Enable tunnel for external access (optional)
echo "To enable external access, run: minikube tunnel"

# 17. View Logs (uncomment to use)
# echo "Viewing logs (replace with pod name)..."
# kubectl logs -f <pod-name>

# 18. Delete all resources (cleanup)
echo "Deleting all resources..."
kubectl delete -f k8s/service.yaml
kubectl delete -f k8s/deployment.yaml
kubectl delete -f k8s/configmap.yaml
kubectl delete -f k8s/service-port2.yaml
kubectl delete -f k8s/deployment-port2.yaml
kubectl delete -f k8s/configmap-port2.yaml
